package com.example.assignment2.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openjdk.jmh.annotations.Setup;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PixelGraphTest {

    int[] graph;

    @BeforeEach
    void setup(){
        graph = new int[]{
                 0, 0, 0, 0, 0,
                -1, 0, 0,-1,-1,
                -1, 0,-1,-1,-1,
                -1,-1, 0, 0, 0
        };
        for (int i = 0; i < graph.length; i++) {
            if (graph[i] == 0) {
                graph[i] = Integer.MAX_VALUE;
            }
        }
    }

    @AfterEach
    void tearDown(){
        graph = null;
    }

    @org.junit.jupiter.api.Test
    void breadthFirstSearch() {
        PixelGraph.setWidth(5);
        ArrayList<Integer> a = PixelGraph.breadthFirstSearchWrapper2(19,4,graph, 5);
        System.out.println(a);
        int[] b = new int[a.size()];
        for (int i = 0; i < a.size(); i++) {
            b[i] = a.get(i);
        }

        assertArrayEquals(b, new int[]{4,3,7,11,17,18,19});
    }

    @org.junit.jupiter.api.Test
    void getPixels() {

    }

    @org.junit.jupiter.api.Test
    void asciiImage() {

    }
}