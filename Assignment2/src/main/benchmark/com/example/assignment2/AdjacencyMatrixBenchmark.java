package com.example.assignment2;

import com.example.assignment2.utils.AdjacencyMatrix;
import com.example.assignment2.utils.Graph;
import com.example.assignment2.utils.Node;
import org.openjdk.jmh.annotations.*;


import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class AdjacencyMatrixBenchmark {


    private Graph<Node, String> graph;
    @Setup
    public void setup(){

        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(new Node("Junction", "junction1", 100, 120, "", 0, 0));
        nodes.add(new Node("Junction", "junction1",400, 160, "", 0, 0));
        nodes.add(new Node("Junction", "junction1", 20, 20, "", 0, 0));
        nodes.add(new Node("Junction", "junction1", 30, 500, "", 0, 0));

        graph = new Graph<>(nodes);
    }

    @TearDown
    public void tearDown(){
        graph = null;
    }

    @Benchmark
    @Measurement(iterations = 1, timeUnit = TimeUnit.MILLISECONDS)
    @Fork(value = 1, warmups = 1)
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 1)
    public void createMatrix(){
        AdjacencyMatrix.createMatrix(graph.getNodes().size());
    }

    @Benchmark
    @Measurement(iterations = 1, timeUnit = TimeUnit.MILLISECONDS)
    @Fork(value = 1, warmups = 1)
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 1)
    public void connectNodesUndirected(){
        AdjacencyMatrix.connectNodesUndirected(graph.getAMat(), 1, 3, 4);
    }

    @Benchmark
    @Measurement(iterations = 1, timeUnit = TimeUnit.MILLISECONDS)
    @Fork(value = 1, warmups = 1)
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 1)
    public void isConnected(){
        AdjacencyMatrix.isConnected(graph.getAMat(),1,2);
    }

}
