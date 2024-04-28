package com.example.assignment2.utils;

import java.util.ArrayList;
import java.util.List;

public class CostedPath<T> {
    private List<T> cheapestPath = new ArrayList<>();
    private int cost = Integer.MAX_VALUE;

    public int getCost() {
        return cost;
    }

    public List<T> getCheapestPath() {
        return cheapestPath;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setCheapestPath(List<T> cheapestPath) {
        this.cheapestPath = cheapestPath;
    }
}
