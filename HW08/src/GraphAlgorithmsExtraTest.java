import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Just an extra set of some extra tests, not too complicated but will test some cases. Good luck on this
 * final homework!
 * @author Menelik Gebremariam
 * @version 1.0
 */
public class GraphAlgorithmsExtraTest {
    private static final int TIMEOUT = 250;
    private Graph<Character> directedGraph;
    private Graph<Integer> undirectedGraph;

    @Before
    public void setUp() {
        directedGraph = createDirectedGraph();
        undirectedGraph = createUndirectedGraph();
    }

    private Graph<Character> createDirectedGraph() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        for (int i = 71; i <= 77; i++) {
            vertices.add(new Vertex<>((char) i));
        }
        // G H I J K L M
        Set<Edge<Character>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('H'), 0));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('J'), 0));
        edges.add(new Edge<>(new Vertex<>('J'), new Vertex<>('I'), 0));
        edges.add(new Edge<>(new Vertex<>('J'), new Vertex<>('K'), 0));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('L'), 0));
        edges.add(new Edge<>(new Vertex<>('L'), new Vertex<>('I'), 0));
        edges.add(new Edge<>(new Vertex<>('M'), new Vertex<>('J'), 0));
        edges.add(new Edge<>(new Vertex<>('M'), new Vertex<>('L'), 0));
        edges.add(new Edge<>(new Vertex<>('M'), new Vertex<>('H'), 0));
        edges.add(new Edge<>(new Vertex<>('K'), new Vertex<>('M'), 0));
        edges.add(new Edge<>(new Vertex<>('I'), new Vertex<>('H'), 0));
        edges.add(new Edge<>(new Vertex<>('I'), new Vertex<>('G'), 0));

        return new Graph<>(vertices, edges);
    }

    private Graph<Integer> createUndirectedGraph() {
        Set<Vertex<Integer>> vertices = new HashSet<>();
        for (int i = 50; i <= 60; i++) {
            vertices.add(new Vertex<>(i));
        }

        Set<Edge<Integer>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>(50), new Vertex<>(51), 5));
        edges.add(new Edge<>(new Vertex<>(51), new Vertex<>(50), 5));
        edges.add(new Edge<>(new Vertex<>(50), new Vertex<>(53), 7));
        edges.add(new Edge<>(new Vertex<>(53), new Vertex<>(50), 7));
        edges.add(new Edge<>(new Vertex<>(52), new Vertex<>(55), 2));
        edges.add(new Edge<>(new Vertex<>(55), new Vertex<>(52), 2));
        edges.add(new Edge<>(new Vertex<>(50), new Vertex<>(54), 4));
        edges.add(new Edge<>(new Vertex<>(54), new Vertex<>(50), 4));
        edges.add(new Edge<>(new Vertex<>(54), new Vertex<>(55), 1));
        edges.add(new Edge<>(new Vertex<>(55), new Vertex<>(54), 1));
        edges.add(new Edge<>(new Vertex<>(52), new Vertex<>(56), 3));
        edges.add(new Edge<>(new Vertex<>(56), new Vertex<>(52), 3));
        edges.add(new Edge<>(new Vertex<>(52), new Vertex<>(57), 8));
        edges.add(new Edge<>(new Vertex<>(57), new Vertex<>(52), 8));
        edges.add(new Edge<>(new Vertex<>(55), new Vertex<>(56), 6));
        edges.add(new Edge<>(new Vertex<>(56), new Vertex<>(55), 6));
        edges.add(new Edge<>(new Vertex<>(59), new Vertex<>(60), 5));
        edges.add(new Edge<>(new Vertex<>(60), new Vertex<>(59), 5));
        edges.add(new Edge<>(new Vertex<>(54), new Vertex<>(59), 2));
        edges.add(new Edge<>(new Vertex<>(59), new Vertex<>(54), 2));
        edges.add(new Edge<>(new Vertex<>(60), new Vertex<>(53), 9));
        edges.add(new Edge<>(new Vertex<>(53), new Vertex<>(60), 9));
        edges.add(new Edge<>(new Vertex<>(58), new Vertex<>(52), 12));
        edges.add(new Edge<>(new Vertex<>(52), new Vertex<>(58), 12));
        edges.add(new Edge<>(new Vertex<>(58), new Vertex<>(57), 10));
        edges.add(new Edge<>(new Vertex<>(57), new Vertex<>(58), 10));

        return new Graph<>(vertices, edges);
    }

    @Test(timeout = TIMEOUT)
    public void testDFS1() {
        List<Vertex<Character>> dfsActual =
                GraphAlgorithms.dfs(new Vertex<>('G'), directedGraph);

        List<Vertex<Character>> dfsExpected = new LinkedList<>();
        dfsExpected.add(new Vertex<>('G'));
        dfsExpected.add(new Vertex<>('H'));
        dfsExpected.add(new Vertex<>('L'));
        dfsExpected.add(new Vertex<>('I'));
        dfsExpected.add(new Vertex<>('J'));
        dfsExpected.add(new Vertex<>('K'));
        dfsExpected.add(new Vertex<>('M'));

        assertEquals(dfsExpected, dfsActual);
    }

    @Test(timeout = TIMEOUT)
    public void testDijkstras1() {
        Map<Vertex<Integer>, Integer> dijkActual = GraphAlgorithms.dijkstras(
                new Vertex<>(54), undirectedGraph);

        Map<Vertex<Integer>, Integer> dijkExpected = new HashMap<>();
        dijkExpected.put(new Vertex<>(54), 0);
        dijkExpected.put(new Vertex<>(55), 1);
        dijkExpected.put(new Vertex<>(59), 2);
        dijkExpected.put(new Vertex<>(52), 3);
        dijkExpected.put(new Vertex<>(50), 4);
        dijkExpected.put(new Vertex<>(56), 6);
        dijkExpected.put(new Vertex<>(60), 7);
        dijkExpected.put(new Vertex<>(51), 9);
        dijkExpected.put(new Vertex<>(57), 11);
        dijkExpected.put(new Vertex<>(53), 11);
        dijkExpected.put(new Vertex<>(58), 15);

        assertEquals(dijkExpected, dijkActual);
    }

    @Test(timeout = TIMEOUT)
    public void testPrims1() {
        Set<Edge<Integer>> mstActual =
                GraphAlgorithms.prims(new Vertex<>(52), undirectedGraph);

        Set<Edge<Integer>> mstExpected = new HashSet<>();
        mstExpected.add(new Edge<>(new Vertex<>(52), new Vertex<>(55), 2));
        mstExpected.add(new Edge<>(new Vertex<>(55), new Vertex<>(52), 2));
        mstExpected.add(new Edge<>(new Vertex<>(55), new Vertex<>(54), 1));
        mstExpected.add(new Edge<>(new Vertex<>(54), new Vertex<>(55), 1));
        mstExpected.add(new Edge<>(new Vertex<>(54), new Vertex<>(59), 2));
        mstExpected.add(new Edge<>(new Vertex<>(59), new Vertex<>(54), 2));
        mstExpected.add(new Edge<>(new Vertex<>(52), new Vertex<>(56), 3));
        mstExpected.add(new Edge<>(new Vertex<>(56), new Vertex<>(52), 3));
        mstExpected.add(new Edge<>(new Vertex<>(54), new Vertex<>(50), 4));
        mstExpected.add(new Edge<>(new Vertex<>(50), new Vertex<>(54), 4));
        mstExpected.add(new Edge<>(new Vertex<>(59), new Vertex<>(60), 5));
        mstExpected.add(new Edge<>(new Vertex<>(60), new Vertex<>(59), 5));
        mstExpected.add(new Edge<>(new Vertex<>(50), new Vertex<>(51), 5));
        mstExpected.add(new Edge<>(new Vertex<>(51), new Vertex<>(50), 5));
        mstExpected.add(new Edge<>(new Vertex<>(50), new Vertex<>(53), 7));
        mstExpected.add(new Edge<>(new Vertex<>(53), new Vertex<>(50), 7));
        mstExpected.add(new Edge<>(new Vertex<>(52), new Vertex<>(57), 8));
        mstExpected.add(new Edge<>(new Vertex<>(57), new Vertex<>(52), 8));
        mstExpected.add(new Edge<>(new Vertex<>(57), new Vertex<>(58), 10));
        mstExpected.add(new Edge<>(new Vertex<>(58), new Vertex<>(57), 10));

        assertEquals(mstExpected, mstActual);
    }

    @Test(timeout = TIMEOUT)
    public void testPrims2() {
        undirectedGraph.getVertices().add(new Vertex<>(100));
        assertNull(GraphAlgorithms.prims(new Vertex<>(52), undirectedGraph));
        undirectedGraph.getVertices().remove(new Vertex<>(100));
    }
}
