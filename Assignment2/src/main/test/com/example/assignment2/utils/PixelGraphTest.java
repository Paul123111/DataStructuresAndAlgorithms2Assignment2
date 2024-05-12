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
        PixelGraph.breadthFirstSearchWrapper(19,4,graph);
    }

    @org.junit.jupiter.api.Test
    void getPixels() {

    }

    @org.junit.jupiter.api.Test
    void asciiImage() {

    }
}