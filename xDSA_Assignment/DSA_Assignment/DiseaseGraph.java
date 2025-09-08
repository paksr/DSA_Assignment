import java.util.*;

class DiseaseGraph implements Graph {
    protected ArrayList<String> diseases;
    protected ArrayList<String> symptoms;
    protected HashMap<String, ArrayList<String>> connections;

    public DiseaseGraph() {
        diseases = new ArrayList<>();
        symptoms = new ArrayList<>();
        connections = new HashMap<>();
    }

    // disease
    @Override
    public boolean addVertex1(String disease) {
        if (disease == null || disease.trim().isEmpty() || diseases.contains(disease)) {
            return false; // Disease already exists or invalid
        }
        diseases.add(disease);
        connections.put(disease, new ArrayList<>());
        return true;
    }

    // disease
    @Override
    public boolean removeVertex1(String disease) {
        if (!diseases.contains(disease)) {
            return false; // Disease doesn't exist
        }

        // Remove all connections to this disease
        for (String otherDisease : diseases) {
            connections.get(otherDisease).remove(disease);
        }

        // Remove the disease itself
        diseases.remove(disease);
        connections.remove(disease);
        return true;
    }

    // symptom
    @Override
    public boolean addVertex2(String symptom) {
        if (symptom == null || symptom.trim().isEmpty() || symptoms.contains(symptom)) {
            return false;
        }
        symptoms.add(symptom);
        connections.put(symptom, new ArrayList<>());
        return true;
    }

    // symptom
    @Override
    public boolean removeVertex2(String symptom) {
        if (!symptoms.contains(symptom)) {
            return false;
        }

        // Remove connections to this symptom
        for (ArrayList<String> list : connections.values()) {
            list.remove(symptom);
        }

        symptoms.remove(symptom);
        connections.remove(symptom);
        return true;
    }

    @Override
    public boolean addEdge(String disease, String symptom) {
        if (!diseases.contains(disease) || !symptoms.contains(symptom)) {
            return false;
        }

        if (!connections.get(disease).contains(symptom)) {
            connections.get(disease).add(symptom);
        }
        if (!connections.get(symptom).contains(disease)) {
            connections.get(symptom).add(disease);
        }
        return true;
    }

    @Override
    public boolean removeEdge(String disease, String symptom) {
        if (!diseases.contains(disease) || !symptoms.contains(symptom)) {
            return false;
        }
        boolean removed1 = connections.get(disease).remove(symptom);
        boolean removed2 = connections.get(symptom).remove(disease);
        return removed1 || removed2;
    }

    @Override
    public void displayGraph() {
        System.out.println("\n=== Disease Network ===");
        if (diseases.isEmpty()) {
            System.out.println("No diseases in the network.");
            return;
        }

        for (String disease : diseases) {
            System.out.print(disease + " is connected to: ");
            ArrayList<String> neighbors = connections.get(disease);
            if (neighbors.isEmpty()) {
                System.out.println("(no connections)");
            } else {
                for (int i = 0; i < neighbors.size(); i++) {
                    System.out.print(neighbors.get(i));
                    if (i < neighbors.size() - 1) {
                        System.out.print(", ");
                    }
                }
                System.out.println();
            }
        }
        System.out.println("========================\n");
    }
    

    @Override
    public String[] getNeighbors1(String disease) {
        if (!connections.containsKey(disease))
            return new String[0];
        return connections.get(disease).toArray(new String[0]);
    }

    @Override
    public String[] getNeighbors2(String symptom) {
        if (!connections.containsKey(symptom))
            return new String[0];
        return connections.get(symptom).toArray(new String[0]);
    }

    @Override
    public boolean hasVertex1(String disease) {
        return diseases.contains(disease);
    }

    @Override
    public boolean hasVertex2(String symptom) {
        return symptoms.contains(symptom);
    }

    public int getSize1() {
        return diseases.size();
    }

    public int getSize2() {
        return symptoms.size();
    }

    public String[] getAllDiseases() {
        return diseases.toArray(new String[0]);
    }

    public String[] getAllSymptoms() {
        return symptoms.toArray(new String[0]);
    }

    public int getIndex1(String vertex) {
        return diseases.indexOf(vertex);
    }

    public int getIndex2(String vertex) {
        return symptoms.indexOf(vertex);
    }

    public void traverseFrom(String startDisease) {
        if (!hasVertex1(startDisease)) {
            System.out.println("Disease '" + startDisease + "' not found.");
            return;
        }

        System.out.println("\nTraversal starting from: " + startDisease);
        ArrayList<String> visited = new ArrayList<>();
        traverseHelper(startDisease, visited, 0);
    }

    private void traverseHelper(String disease, ArrayList<String> visited, int level) {
        visited.add(disease);

        for (int i = 0; i < level; i++) {
            System.out.print("  ");
        }
        System.out.println("- " + disease);

        String[] neighbors = getNeighbors1(disease);
        for (String neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                traverseHelper(neighbor, visited, level + 1);
            }
        }
    }
}