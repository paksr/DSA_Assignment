import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

class GraphView extends Pane {
    public GraphView(UnweightedGraph graph) {
        int diseaseCount = graph.getSize1();
        int symptomCount = graph.getSize2();

        if (diseaseCount == 0 && symptomCount == 0) return;

        int width = 600;
        int height = 400;

        // --- Positioning ---
        int diseaseCenterX = width / 4;   // left side
        int symptomCenterX = 3 * width / 4; // right side
        int diseaseSpacing = height / (diseaseCount + 1);
        int symptomSpacing = height / (symptomCount + 1);

        // Store coordinates for later when drawing edges
        double[][] diseaseCoords = new double[diseaseCount][2];
        double[][] symptomCoords = new double[symptomCount][2];

        // disease going down
        String[] allDiseases = graph.getAllDiseases();
        for (int i = 0; i < diseaseCount; i++) {
            int y = (i + 1) * diseaseSpacing; // stacked position
            diseaseCoords[i][0] = diseaseCenterX;
            diseaseCoords[i][1] = y;

            Circle circle = new Circle(diseaseCenterX, y, 15);
            circle.setFill(Color.LIGHTBLUE);
            circle.setStroke(Color.BLACK);
            getChildren().add(circle);
            getChildren().add(new Text(diseaseCenterX - 20, y - 20, allDiseases[i]));
        }

        // symptom goin down 
        String[] allSymptoms = graph.getAllSymptoms();
        for (int i = 0; i < symptomCount; i++) {
            int y = (i + 1) * symptomSpacing; // stacked position
            symptomCoords[i][0] = symptomCenterX;
            symptomCoords[i][1] = y;

            Circle circle = new Circle(symptomCenterX, y, 15);
            circle.setFill(Color.LIGHTGREEN);
            circle.setStroke(Color.BLACK);
            getChildren().add(circle);
            getChildren().add(new Text(symptomCenterX - 20, y - 20, allSymptoms[i]));
        }

        // --- Draw edges (disease ↔ symptom) ---
        for (int i = 0; i < diseaseCount; i++) {
            String disease = allDiseases[i];
            String[] neighbors = graph.getNeighbors1(disease);

            for (String symptom : neighbors) {
                int sIndex = graph.getIndex2(symptom);
                if (sIndex != -1) {
                    double x1 = diseaseCoords[i][0];
                    double y1 = diseaseCoords[i][1];
                    double x2 = symptomCoords[sIndex][0];
                    double y2 = symptomCoords[sIndex][1];

                    getChildren().add(new Line(x1, y1, x2, y2));
                }
            }
        }
    }
}
