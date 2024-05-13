package com.example.assignment2.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    void breadthFirstSearch2() {
        PixelGraph.setWidth(5);
        ArrayList<Integer> a = PixelGraph.breadthFirstSearchWrapper2(19,4,graph, 5);
        for (int i : a) {
            System.out.println(i);
        }
//        Object[] b = a.toArray();
//        int[] c = (int[]) b[0];
//
//
//        assertArrayEquals(new int[]{4,
//                3,
//                7,
//                11,
//                17,
//                18,
//                19}, c );
    }

    @org.junit.jupiter.api.Test
    void getPixels() {

    }

    @org.junit.jupiter.api.Test
    void asciiImage() {

    }

    @Test
    void getAdjacentPixels() {
        int[] a = PixelGraph.getAdjacentPixels(6, graph, 5);
        PixelGraph.asciiImage(graph, 5);
        for (int i : a) {
            System.out.println(i);
        }
    }
}