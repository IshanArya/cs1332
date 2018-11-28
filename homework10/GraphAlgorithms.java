import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Ishan Arya
 * @version 1.0
 * @userid iarya3
 * @GTID 903399427
 */
@SuppressWarnings("Duplicates")
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * {@code start} which represents the starting vertex.
     * <p>
     * When exploring a vertex, make sure to explore in the order that the
     * adjacency list returns the neighbors to you. Failure to do so may cause
     * you to lose points.
     * <p>
     * You may import/use {@code java.util.Set}, {@code java.util.List},
     * {@code java.util.Queue}, and any classes that implement the
     * aforementioned interfaces, as long as it is efficient.
     * <p>
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input
     *                                  is null, or if {@code start} doesn't
     *                                  exist in the graph
     */
    public static <T> List<Vertex<T>> breadthFirstSearch(Vertex<T> start,
                                                         Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Inputs cannot be null.");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Vertex start not in graph.");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        Set<Vertex<T>> visited = new HashSet<>(graph.getVertices().size());
        List<Vertex<T>> orderedList = new ArrayList<>(
                graph.getVertices().size());
        Queue<Vertex<T>> traversalQueue = new LinkedList<>();

        traversalQueue.add(start);
        visited.add(start);

        while (!traversalQueue.isEmpty()) {
            Vertex<T> current = traversalQueue.poll();
            orderedList.add(current);
            List<VertexDistance<T>> adjacentVertices = adjList.get(current);
            for (VertexDistance<T> x : adjacentVertices) {
                Vertex<T> adjacentVertex = x.getVertex();
                if (!visited.contains(adjacentVertex)) {
                    visited.add(adjacentVertex);
                    traversalQueue.add(adjacentVertex);
                }
            }
        }

        return orderedList;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * {@code start} which represents the starting vertex.
     * <p>
     * When deciding which neighbors to visit next from a vertex, visit the
     * vertices in the order presented in that entry of the adjacency list.
     * <p>
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * most if not all points for this method.
     * <p>
     * You may import/use {@code java.util.Set}, {@code java.util.List}, and
     * any classes that implement the aforementioned interfaces, as long as it
     * is efficient.
     * <p>
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input
     *                                  is null, or if {@code start}
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> depthFirstSearch(Vertex<T> start,
                                                       Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Inputs cannot be null.");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Vertex start not in graph.");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        Set<Vertex<T>> visited = new HashSet<>(graph.getVertices().size());
        List<Vertex<T>> orderedList = new ArrayList<>(
                graph.getVertices().size());

        depthFirstSearchHelper(start, adjList, visited, orderedList);
        return orderedList;

    }

    /**
     * Recursive helper method for depth first algorithm
     *
     * @param current     node to find adjacent vertices of
     * @param adjList     map containing adjacent vertices
     * @param visited     set of visited vertices
     * @param orderedList list to return
     * @param <T>         generic type of data
     */
    private static <T> void depthFirstSearchHelper(Vertex<T> current,
                                                   Map<Vertex<T>,
                                                   List<VertexDistance<T>>>
                                                           adjList,
                                                   Set<Vertex<T>> visited,
                                                   List<Vertex<T>>
                                                           orderedList) {
        orderedList.add(current);
        visited.add(current);

        List<VertexDistance<T>> adjacentVerticies = adjList.get(current);
        for (VertexDistance<T> x : adjacentVerticies) {
            if (!visited.contains(x.getVertex())) {
                depthFirstSearchHelper(x.getVertex(),
                        adjList,
                        visited,
                        orderedList);
            }
        }
    }


    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     * <p>
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from {@code start}, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     * <p>
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Map}, and {@code java.util.Set} and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     * <p>
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     * <p>
     * 1) Check that not all vertices have been visited.
     * 2) Check that the PQ is not empty yet.
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from {@code start} to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Inputs cannot be null.");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Vertex start not in graph.");
        }

        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        Set<Vertex<T>> finalized = new HashSet<>(graph.getVertices().size());
        Map<Vertex<T>, Integer> shortestDistances =
                new HashMap<>(graph.getVertices().size());

        for (Vertex<T> x : adjList.keySet()) {
            if (x.equals(start)) {
                shortestDistances.put(x, 0);
            } else {
                shortestDistances.put(x, Integer.MAX_VALUE);
            }
        }

        PriorityQueue<VertexDistance<T>> pQueue = new PriorityQueue<>();
        pQueue.add(new VertexDistance<>(start, 0));

        while (finalized.size() != graph.getVertices().size()
                && !pQueue.isEmpty()) {
            Vertex<T> current = pQueue.poll().getVertex();
            if (finalized.contains(current)) {
                continue;
            }
            finalized.add(current);

            List<VertexDistance<T>> distances = adjList.get(current);
            int currentDistanceFromStart = shortestDistances.get(current);
            for (VertexDistance<T> x : distances) {
                Vertex<T> adjacentVertex = x.getVertex();
                if (!finalized.contains(adjacentVertex)) {
                    int distanceFromStart =
                            currentDistanceFromStart + x.getDistance();
                    if (distanceFromStart
                            < shortestDistances.get(adjacentVertex)) {
                        shortestDistances.put(adjacentVertex,
                                distanceFromStart);
                    }
                    pQueue.add(new VertexDistance<>(adjacentVertex, distanceFromStart));
                }
            }
        }

        return shortestDistances;

    }


    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     * <p>
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     * <p>
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     * <p>
     * You may assume that there will only be one valid MST that can be formed.
     * <p>
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the {@code DisjointSet} and {@code DisjointSetNode} classes that
     * have been provided to you for more information.
     * <p>
     * You should NOT allow self-loops or parallel edges into the MST.
     * <p>
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Set}, and any class that implements the aforementioned
     * interface.
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null.");
        }

        PriorityQueue<Edge<T>> edges = new PriorityQueue<>(graph.getEdges());
        DisjointSet<Vertex<T>> vertices =
                new DisjointSet<>(graph.getVertices());
        Set<Edge<T>> mst = new HashSet<>(graph.getVertices().size() - 1);

        while (!edges.isEmpty()
                && mst.size() < 2 * (graph.getVertices().size() - 1)) {
            Edge<T> edge = edges.poll();
            Vertex<T> u = edge.getU();
            Vertex<T> v = edge.getV();
            if (!vertices.find(u).equals(vertices.find(v))) {
                vertices.union(u, v);
                mst.add(edge);
                mst.add(new Edge<>(v, u, edge.getWeight()));
            }
        }
        if (mst.size() != 2 * (graph.getVertices().size() - 1)) {
            return null;
        }
        return mst;
    }
}
