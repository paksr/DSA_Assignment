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

    // Add missing methods that are called from TheDoctor
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
                    if (symptoms.contains(symptom)) {
                        writer.println(disease + " -> " + symptom);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

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
            System.out.println("Error loading from file: " + e.getMessage());
        }
    }
}