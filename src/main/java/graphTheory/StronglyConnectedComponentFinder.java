package graphTheory;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class StronglyConnectedComponentFinder {

    static class Kosaraju {
        Graph graph, reversedGraph;
        boolean visited[];
        boolean visitedForReverseGraph[];
        List<List<Integer>> connectedComponents;
        Kosaraju(Graph g1, Graph g2) {
            graph = g1;
            reversedGraph = g2;
            connectedComponents = new ArrayList<List<Integer>>();
        }

        public List<List<Integer>> solve() {
            visited = new boolean[graph.getVertices()];
            visitedForReverseGraph = new boolean[reversedGraph.getVertices()];
            Stack<Integer> postOrder = new Stack<Integer>();
            for (int at=0; at<graph.getVertices(); at++) {
                if (!visited[at]) {
                    dfs(at, postOrder, graph, visited);
                }
            }

            while (postOrder.isEmpty() == false) {
                int curVertex = postOrder.pop();
                if (!visitedForReverseGraph[curVertex]) {
                    Stack<Integer> connectedComponent = new Stack<Integer>();
                    dfs(curVertex, connectedComponent, reversedGraph, visitedForReverseGraph);
                    connectedComponents.add(new ArrayList<Integer>(connectedComponent));
                }
            }

            return connectedComponents;
        }

        void dfs(int from, Stack<Integer> current, Graph g, boolean vis[]) {
            vis[from] = true;
            for (int to: g.getNeighboursOf(from)) {
                if (!vis[to]) {
                    dfs(to, current, g, vis);
                }
            }
            current.push(from);
        }
    }

    public static void main(String[] args) {
        Graph graph = new Graph(6);
        Graph reversedGraph = new Graph(6);

        graph.addDirectedEdge(0, 1);
        reversedGraph.addDirectedEdge(1, 0);

        graph.addDirectedEdge(1, 0);
        reversedGraph.addDirectedEdge(0, 1);

        graph.addDirectedEdge(2, 3);
        reversedGraph.addDirectedEdge(3, 2);

        graph.addDirectedEdge(2, 0);
        reversedGraph.addDirectedEdge(0, 2);

        graph.addDirectedEdge(3, 4);
        reversedGraph.addDirectedEdge(4, 3);

        graph.addDirectedEdge(4, 2);
        reversedGraph.addDirectedEdge(2, 4);

        graph.addDirectedEdge(4, 0);
        reversedGraph.addDirectedEdge(0, 4);

        graph.addDirectedEdge(4, 5);
        reversedGraph.addDirectedEdge(5, 4);

        graph.addDirectedEdge(5, 1);
        reversedGraph.addDirectedEdge(1, 5);

        Kosaraju kosaraju = new Kosaraju(graph, reversedGraph);

        for (List<Integer> connectedComponent: kosaraju.solve()) {
            System.out.println(connectedComponent);
        }
    }
}
