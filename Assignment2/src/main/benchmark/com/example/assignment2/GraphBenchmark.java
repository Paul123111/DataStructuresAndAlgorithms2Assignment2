package com.example.assignment2;

import com.example.assignment2.utils.Graph;
import com.example.assignment2.utils.Node;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class GraphBenchmark {

    private Graph<Node, String> graph;

    @Setup
    public void setup(){

        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(new Node("Junction", "1", 100, 120, "", 0, 0));
        nodes.add(new Node("Junction", "2", 400, 160, "", 0, 0));
        nodes.add(new Node("Junction", "3", 20,  20 , "", 0, 0));
        nodes.add(new Node("Junction", "4", 30,  500, "", 0, 0));
        nodes.add(new Node("Junction", "5", 30,  500, "", 0, 0));
        nodes.add(new Node("Junction", "6", 30,  500, "", 0, 0));
        nodes.add(new Node("Junction", "7", 30,  500, "", 0, 0));

        graph = new Graph<>(nodes);
        connectGraphUpSetupFunction();
    }

    @TearDown
    public void tearDown(){
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


    @Benchmark
    @Measurement(iterations = 1, timeUnit = TimeUnit.MILLISECONDS)
    @Fork(value = 1, warmups = 1)
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 1)
    public void findIndexByField(){
       graph.findIndexByField("5");
    }




    @Benchmark
    @Measurement(iterations = 1, timeUnit = TimeUnit.MILLISECONDS)
    @Fork(value = 1, warmups = 1)
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 1)
    public void connectNodes(){
        graph.connectNodes("1","3",1);
    }


    @Benchmark
    @Measurement(iterations = 1, timeUnit = TimeUnit.MILLISECONDS)
    @Fork(value = 1, warmups = 1)
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 1)
    public void displayPossibleLinks(){
        graph.displayPossibleLinks("2");
    }


    @Benchmark
    @Measurement(iterations = 1, timeUnit = TimeUnit.MILLISECONDS)
    @Fork(value = 1, warmups = 1)
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 1)
    public void findPathDepthFirst(){
        graph.findPathDepthFirstWrapper("1", "7");
    }



    @Benchmark
    @Measurement(iterations = 1, timeUnit = TimeUnit.MILLISECONDS)
    @Fork(value = 1, warmups = 1)
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 1)
    public void findAllPathsDepthFirst(){
        graph.findAllPathsDepthFirstWrapper("1","7");
    }

    @Benchmark
    @Measurement(iterations = 1, timeUnit = TimeUnit.MILLISECONDS)
    @Fork(value = 1, warmups = 1)
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 1)
    public void initialiseNodeValues(){
        graph.initialiseNodeValues(graph.getNodes().get(1));
    }


    @Benchmark
    @Measurement(iterations = 1, timeUnit = TimeUnit.MILLISECONDS)
    @Fork(value = 1, warmups = 1)
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 1)
    public void findCheapestPathDijkstra(){
        graph.findCheapestPathDijkstraWrapper("1","7");
    }
}

