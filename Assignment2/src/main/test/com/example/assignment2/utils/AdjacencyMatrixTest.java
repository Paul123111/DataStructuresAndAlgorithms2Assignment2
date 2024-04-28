package com.example.assignment2.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AdjacencyMatrixTest {

    private Graph<Node, String> graph;

    @BeforeEach
    void setUp() {
        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(new Node("Junction", "junction1", 100, 120, "", 0, 0));
        nodes.add(new Node("Junction", "junction1",400, 160, "", 0, 0));
        nodes.add(new Node("Junction", "junction1", 20, 20, "", 0, 0));
        nodes.add(new Node("Junction", "junction1", 30, 500, "", 0, 0));

        graph = new Graph<>(nodes);
    }

    @AfterEach
    void tearDown() {
        graph = null;
    }

    @Test
    void createMatrix() {
        int[][] a = AdjacencyMatrix.createMatrix(graph.getNodes().size());
        System.out.println(AdjacencyMatrix.toString(a, graph.getNodes().size()));
        assertArrayEquals(new int[4][4], a);
    }

    @Test
    void connectNodesUndirected() {
        AdjacencyMatrix.connectNodesUndirected(graph.getAMat(), 1, 3, 4);
        AdjacencyMatrix.connectNodesUndirected(graph.getAMat(), 3, 2, 1);

        System.out.println(AdjacencyMatrix.toString(graph.getAMat(), graph.getNodes().size()));
        assertArrayEquals(new int[][]{{0,0,0,0},{0,0,0,4},{0,0,0,1},{0,4,1,0}}, graph.getAMat());
    }

    @Test
    void isConnected(){
        AdjacencyMatrix.connectNodesUndirected(graph.getAMat(), 1, 3, 4);
        AdjacencyMatrix.connectNodesUndirected(graph.getAMat(), 3, 2, 1);
        assertTrue(AdjacencyMatrix.isConnected(graph.getAMat(),1,3));
        assertFalse(AdjacencyMatrix.isConnected(graph.getAMat(),1,2));
        assertTrue(AdjacencyMatrix.isConnected(graph.getAMat(),2,3));
    }

}