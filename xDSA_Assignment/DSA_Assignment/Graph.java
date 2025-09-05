
interface Graph {
    boolean addVertex(String disease);
    boolean removeVertex(String disease);
    boolean addEdge(String disease1, String disease2);
    boolean removeEdge(String disease1, String disease2);
    void displayGraph();
    String[] getNeighbors(String disease);
    boolean hasVertex(String disease);
}