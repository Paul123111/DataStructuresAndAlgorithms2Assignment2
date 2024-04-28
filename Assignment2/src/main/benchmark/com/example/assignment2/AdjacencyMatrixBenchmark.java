package com.example.assignment2;

import org.openjdk.jmh.annotations.*;


import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class AdjacencyMatrixBenchmark {

    @Benchmark
    @Measurement(iterations = 1, timeUnit = TimeUnit.MILLISECONDS)
    @Fork(value = 1, warmups = 1)
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 1)
    public void test(){
     }
}
