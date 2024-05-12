package com.example.assignment2.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openjdk.jmh.annotations.Setup;

import static org.junit.jupiter.api.Assertions.*;

class PixelGraphTest {

    int[] graph;

    @BeforeEach
    void setup(){
        graph = new int[]{
                1,1,1,1,1,
                0,1,1,0,0,
                0,1,0,0,0,
                0,0,1,1,1
        };
    }

    @AfterEach
    void tearDown(){
        graph = null;
    }

    @org.junit.jupiter.api.Test
    void breadthFirstSearch() {
        PixelGraph.setWidth(5);
        int[] a = PixelGraph.breadthFirstSearchWrapper(19,4,graph);
        for (int i : a) {
            System.out.println(i);
        }
    }

    @org.junit.jupiter.api.Test
    void getPixels() {

    }

    @org.junit.jupiter.api.Test
    void asciiImage() {

    }
}