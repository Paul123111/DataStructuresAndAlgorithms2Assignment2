package com.example.assignment2.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GraphTest {
    private Graph<Node, String> graph;
    @BeforeEach
    void setup(){
        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(new Node("Junction", "1", 100, 120, "", 0, 0));
        nodes.add(new Node("Junction", "2", 400, 160, "", 0, 0));
        nodes.add(new Node("Junction", "3", 20,  20 , "", 0, 0));
        nodes.add(new Node("Junction", "4", 30,  500, "", 0, 0));
        nodes.add(new Node("Junction", "5", 30,  500, "", 0, 0));
        nodes.add(new Node("Junction", "6", 30,  500, "", 0, 0));
        nodes.add(new Node("Junction", "7", 30,  500, "", 0, 0));

        graph = new Graph<>(nodes);
    }

    @AfterEach
    void tearDown(){
        graph = null;
    }
    void connectGraphUpSetupFunction(){
        graph.connectNodes("1","3",1);
        graph.connectNodes("1","2",3);
        graph.connectNodes("2","4",1);
        graph.connectNodes("4","5",1);
        graph.connectNodes("3","5",1);
        graph.connectNodes("5","6",1);
        graph.connectNodes("6","7",1);
    }

    @Test
    void findIndexByField(){
        assertEquals(graph.findIndexByField("1"),0 );
        assertEquals(graph.findIndexByField("4"),3 );
    }
    @Test
    void connectNodes(){
        graph.connectNodes("1", "2",3);
        assertTrue(AdjacencyMatrix.isConnected(graph.getAMat(),0,1));
        assertEquals(AdjacencyMatrix.connectedValue(graph.getAMat(),0,1), 3);
    }

    @Test
    void displayPossibleLinks(){
        assertTrue(graph.displayPossibleLinks("2").contains("1"));
        assertTrue(graph.displayPossibleLinks("2").contains("3"));
        assertTrue(graph.displayPossibleLinks("2").contains("2"));
    }

    @Test
    void findPathDepthFirst(){
        connectGraphUpSetupFunction();
        assertArrayEquals(graph.findPathDepthFirstWrapper("1", "7").toArray(),
                new Node[] {graph.getNodes().get(0),
                graph.getNodes().get(1),
                graph.getNodes().get(3),
                graph.getNodes().get(4),
                graph.getNodes().get(5),
                graph.getNodes().get(6)});
    }

    @Test
    void findAllPathsDepthFirst(){
        connectGraphUpSetupFunction();
        List<List<Node>> paths = graph.findAllPathsDepthFirstWrapper("1","7", 100);
        assertEquals(paths.size(), 2);
        assertArrayEquals(paths.get(0).toArray(),
                new Node[] {graph.getNodes().get(0),
                        graph.getNodes().get(1),
                        graph.getNodes().get(3),
                        graph.getNodes().get(4),
                        graph.getNodes().get(5),
                        graph.getNodes().get(6)});

        assertArrayEquals(paths.get(1).toArray(),
                new Node[] {graph.getNodes().get(0),
                        graph.getNodes().get(2),
                        graph.getNodes().get(4),
                        graph.getNodes().get(5),
                        graph.getNodes().get(6)});
    }

    @Test
    void initialiseNodeValues(){
        Map<Node, Double> map = graph.initialiseNodeValues(graph.getNodes().get(1));
        assertEquals(map.get(graph.getNodes().get(0)),Double.MAX_VALUE);
        assertEquals(map.get(graph.getNodes().get(1)),0);
        assertEquals(map.get(graph.getNodes().get(2)),Double.MAX_VALUE);
    }

    @Test
    void findCheapestPathDijkstra(){
        connectGraphUpSetupFunction();
        CostedPath<Node> path = graph.findCheapestPathDijkstraWrapper("1","7",new ArrayList<>(), 1);
        assertArrayEquals(path.getCheapestPath().toArray(), new Node[] {graph.getNodes().get(0),
                graph.getNodes().get(2),
                graph.getNodes().get(4),
                graph.getNodes().get(5),
                graph.getNodes().get(6)});
    }

}
