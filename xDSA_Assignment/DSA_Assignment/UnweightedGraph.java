
import java.util.ArrayList;

class UnweightedGraph extends DiseaseGraph {
    public UnweightedGraph() {
        super();
    }

    public void display() {
        System.out.println("Vertices and their neighbors:");
        for (int i = 0; i < diseases.size(); i++) {
            System.out.print(diseases.get(i) + ": ");
            for (String connections : connections.get(i)) {
                System.out.print(connections + " ");
            }
            System.out.println();
        }
    }
}