package com.example.assignment2.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdjacencyMatrix {
    //TODO set up adjacency matrix utilities
    // store int values that can used for x and y coords in nodes (maybe) - DONE

    public static <T> int[][] createMatrix(int size) {
        return new int[size][size];
    }

    //not needed because nodes will only be added outside of program runtime
//    public static <T> int[][] increaseMatrixSize(int[][] mat, T node) {
//        return new int[][];
//    }

    public static void connectNodesUndirected(int[][] mat, int nodeIndex1, int nodeIndex2, int value) {
        mat[nodeIndex1][nodeIndex2] = value;
        mat[nodeIndex2][nodeIndex1] = value;
    }

    public static boolean isConnected(int[][] mat, int nodeIndex1, int nodeIndex2) {
        return (mat[nodeIndex1][nodeIndex2]!=0);
    }

    public static int connectedValue(int[][] mat, int nodeIndex1, int nodeIndex2) {
        return (mat[nodeIndex1][nodeIndex2]);
    }

    //slow method for testing - won't be used for actual program
    public static String toString(int[][] mat, int size) {
        String matrixList = "   ";
        for (int i = 0; i < size; i++) {
            matrixList += i + "N ";
        }

        for(int i = 0; i < size; i++) {
            matrixList += "\n";
            for (int j = -1; j < size; j++) {
                if (j==-1) {
                    matrixList += i + "N ";
                } else {
                        matrixList += connectedValue(mat, i, j) + "  ";
                }
            }
        }

        return matrixList;

//        String matrixList = "";
//        matrixList += mat[0] + " ";
//        for (int i = 1; i < mat.length; i++) {
//            if (i%width==0)
//                matrixList += "\n";
//            matrixList+=mat[i] + " ";
//        }
//        return matrixList;
    }
}
