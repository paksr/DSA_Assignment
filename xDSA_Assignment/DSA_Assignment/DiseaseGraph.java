import java.util.*;

class DiseaseGraph implements Graph {
    protected ArrayList<String> diseases;
    protected HashMap<String, ArrayList<String>> connections;
    
    public DiseaseGraph() {
        diseases = new ArrayList<>();
        connections = new HashMap<>();
    }
    
    @Override
    public boolean addVertex(String disease) {
        if (disease == null || disease.trim().isEmpty() || diseases.contains(disease)) {
            return false; // Disease already exists or invalid
        }
        diseases.add(disease);
        connections.put(disease, new ArrayList<>());
        return true;
    }
    
    @Override
    public boolean removeVertex(String disease) {
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
    
    @Override
    public boolean addEdge(String disease1, String disease2) {
        if (!diseases.contains(disease1) || !diseases.contains(disease2)) {
            return false;
        }
        
        if (connections.get(disease1).contains(disease2)) {
            return false;
        }
        
        connections.get(disease1).add(disease2);
        connections.get(disease2).add(disease1);
        return true;
    }
    
    @Override
    public boolean removeEdge(String disease1, String disease2) {
        if (!diseases.contains(disease1) || !diseases.contains(disease2)) {
            return false;
        }
        
        boolean removed1 = connections.get(disease1).remove(disease2);
        boolean removed2 = connections.get(disease2).remove(disease1);
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
    public String[] getNeighbors(String vertex) {
        if (!diseases.contains(vertex)) {
            return new String[0];
        }
        ArrayList<String> neighborList = connections.get(vertex); // Use vertex (disease name) as key
        return neighborList != null ? neighborList.toArray(new String[0]) : new String[0]; // Handle null
    }
    
    @Override
    public boolean hasVertex(String disease) {
        return diseases.contains(disease);
    }
    
    public int getSize() {
        return diseases.size();
    }
    
    public String[] getAllDiseases() {
        return diseases.toArray(new String[0]);
    }

    public int getIndex(String vertex) {
        return diseases.indexOf(vertex);
    }
    
    public void traverseFrom(String startDisease) {
        if (!hasVertex(startDisease)) {
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
        
        String[] neighbors = getNeighbors(disease);
        for (String neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                traverseHelper(neighbor, visited, level + 1);
            }
        }
    }
}