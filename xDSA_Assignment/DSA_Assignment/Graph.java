interface Graph {
    boolean addVertex1(String disease);
    boolean removeVertex1(String disease);
    boolean addVertex2(String symptom);
    boolean removeVertex2(String symptom);
    boolean addEdge(String disease, String symptom);  // Fixed parameter names
    boolean removeEdge(String disease, String symptom);  // Fixed parameter names
    void displayGraph();
    String[] getNeighbors1(String disease);
    boolean hasVertex1(String disease);
    String[] getNeighbors2(String symptom);
    boolean hasVertex2(String symptom);
}