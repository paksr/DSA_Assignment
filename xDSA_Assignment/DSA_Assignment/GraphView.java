

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

class GraphView extends Pane {
    public GraphView(UnweightedGraph graph) {
        int size = graph.getSize();
        if (size == 0) return;
        int width = 600;
        int height = 400;
        int centerX = width / 2;
        int centerY = height / 2;
        int radius = Math.min(width, height) / 3;

        // Simple circular layout
        for (int i = 0; i < size; i++) {
            double angle = 2 * Math.PI * i / size;
            int x = (int) (centerX + radius * Math.cos(angle));
            int y = (int) (centerY + radius * Math.sin(angle));
            Circle circle = new Circle(x, y, 15);
            circle.setFill(Color.WHITE);
            circle.setStroke(Color.BLACK);
            getChildren().add(circle);
            getChildren().add(new Text(x - 10, y - 20, graph.getAllDiseases()[i]));
        }

        // Draw edges
        for (int i = 0; i < size; i++) {
            String u = graph.getAllDiseases()[i];
            String[] neighbors = graph.getNeighbors(u);
            int x1 = (int) (centerX + radius * Math.cos(2 * Math.PI * i / size));
            int y1 = (int) (centerY + radius * Math.sin(2 * Math.PI * i / size));
            for (String v : neighbors) {
                int vIndex = graph.getIndex(v);
                if (vIndex > i) { // Avoid duplicate edges
                    int x2 = (int) (centerX + radius * Math.cos(2 * Math.PI * vIndex / size));
                    int y2 = (int) (centerY + radius * Math.sin(2 * Math.PI * vIndex / size));
                    getChildren().add(new Line(x1, y1, x2, y2));
                }
            }
        }
    }
}