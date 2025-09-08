
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

    // Save network to file
    public void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Save diseases
            writer.println("# Diseases");
            for (String d : diseases) {
                writer.println(d);
            }

            // Save symptoms
            writer.println("# Symptoms");
            for (String s : symptoms) {
                writer.println(s);
            }

            // Save connections
            writer.println("# Connections");
            for (String disease : diseases) {
                ArrayList<String> neighbors = connections.get(disease);
                for (String symptom : neighbors) {
                    writer.println(disease + " -> " + symptom);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load network from file
    public void loadFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            String section = "";

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())
                    continue;

                if (line.startsWith("#")) {
                    section = line;
                } else {
                    switch (section) {
                        case "# Diseases":
                            addVertex1(line);
                            break;
                        case "# Symptoms":
                            addVertex2(line);
                            break;
                        case "# Connections":
                            String[] parts = line.split("->");
                            if (parts.length == 2) {
                                String disease = parts[0].trim();
                                String symptom = parts[1].trim();
                                addEdge(disease, symptom);
                            }
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}