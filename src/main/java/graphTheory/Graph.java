package graphTheory;

import java.util.ArrayList;
import java.util.List;

class Graph {
    int vertices;
    List<List<Integer>> adjacencyList;
    Graph(int n) {
        vertices = n;
        adjacencyList = new ArrayList<List<Integer>>();
        for (int i=0; i<n; i++) {
            adjacencyList.add(new ArrayList<Integer>());
        }
    }

    /**
     * Adds an edge from vetex 'from' to vertex 'to'
     * @param from
     * @param to
     */
    public void addDirectedEdge(int from, int to) {
        adjacencyList.get(from).add(to);
    }

    public void addUnDirectedEdge(int from, int to) {
        adjacencyList.get(from).add(to);
        adjacencyList.get(to).add(from);
    }

    public int getVertices() {
        return vertices;
    }

    public List<Integer> getNeighboursOf(int vertex) {
        return adjacencyList.get(vertex);
    }
}
