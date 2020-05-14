package graphTheory;

import java.util.ArrayList;
import java.util.List;

public class BridgeAndArticulationPointFinder {

    static class Edge {
        int from, to;
        Edge(int from, int to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public String toString() {
            return from + " -> " + to;
        }
    }

    static class BridgeFinder {
        int low[];
        int id[];
        int n;
        int timer;
        boolean visited[];
        Graph graph;

        BridgeFinder(Graph graph, int n) {
            this.graph = graph;
            this.n = n;
        }

        static BridgeFinder create(Graph graph, int n) {
            return new BridgeFinder(graph, n);
        }

        void dfs(int from, List<Edge> bridges, int parent) {
            visited[from] = true;
            low[from] = id[from] = ++timer;

            for (int to: graph.getNeighboursOf(from)) {
                if (to == parent) continue;
                if (!visited[to]) {
                    dfs(to, bridges, from);
                    low[from] = Math.min(low[from], low[to]);
                    if (id[from] < low[to]) {
                        bridges.add(new Edge(from, to));
                    }
                } else {
                    low[from] = Math.min(low[from], id[to]);
                }
            }
        }

        List<Edge> findBridges() {
            List<Edge> bridges = new ArrayList<Edge>();
            timer = -1;
            visited = new boolean[n];
            low = new int[n];
            id = new int[n];
            for (int at=0; at<n; at++) {
                if (!visited[at]) {
                    dfs(at, bridges, -1);
                }
            }
            return bridges;
        }
    }

    static class ArticulationPointFinder {
        int childrenOfRoot;
        int low[];
        int id[];
        int n;
        int timer;
        boolean visited[];
        boolean isArticulationPoint[];
        int root;
        Graph graph;

        private ArticulationPointFinder(Graph graph, int n) {
            this.graph = graph;
            this.n = n;
        }

        static ArticulationPointFinder create(Graph graph, int n) {
            return new ArticulationPointFinder(graph, n);
        }

        void dfs(int from, int parent) {
            if (parent == root) childrenOfRoot++;
            visited[from] = true;
            low[from] = id[from] = ++timer;
            for (int to: graph.getNeighboursOf(from)) {
                if (parent == to) continue;
                if (!visited[to]) {
                    dfs(to, from);
                    low[from] = Math.min(low[from], low[to]);
                    if (id[from] <= low[to]) {
                        isArticulationPoint[from] = true;
                    }
                } else {
                    low[from] = Math.min(low[from], id[to]);
                }
            }
        }

        List<Integer> findArticulationPoints() {
            List<Integer> articulationPoints = new ArrayList<Integer>();
            timer = 0;
            visited = new boolean[n];
            isArticulationPoint = new boolean[n];
            low = new int[n];
            id = new int[n];
            for (int at=0; at<n; at++) {
                if (!visited[at]) {
                    childrenOfRoot = 0;
                    root = at;
                    dfs(at, -1);
                    isArticulationPoint[at] = childrenOfRoot > 1;
                }
            }
            for (int at=0; at<n; at++) {
                if (isArticulationPoint[at]) articulationPoints.add(at);
            }
            return articulationPoints;
        }
    }

    public static void main(String[] args) {
        int n = 9;
        Graph graph = new Graph(n);

        graph.addUnDirectedEdge(0, 1);
        graph.addUnDirectedEdge(0, 2);
        graph.addUnDirectedEdge(1, 2);
        graph.addUnDirectedEdge(2, 3);
        graph.addUnDirectedEdge(3, 4);
        graph.addUnDirectedEdge(2, 5);
        graph.addUnDirectedEdge(5, 6);
        graph.addUnDirectedEdge(6, 7);
        graph.addUnDirectedEdge(7, 8);
        graph.addUnDirectedEdge(8, 5);

        BridgeFinder bridgeFinder = BridgeFinder.create(graph, n);
        ArticulationPointFinder articulationPointFinder = ArticulationPointFinder.create(graph, n);

        System.out.println("Bridges in the graph are as following");
        for (Edge bridge: bridgeFinder.findBridges()) {
            System.out.println(bridge);
        }
        System.out.println("Articulation points in the graph are as following");

        for (Integer articulationPoint: articulationPointFinder.findArticulationPoints()) {
            System.out.println(articulationPoint);
        }
    }
}
