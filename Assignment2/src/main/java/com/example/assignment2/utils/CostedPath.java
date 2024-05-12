package com.example.assignment2.utils;

import java.util.ArrayList;
import java.util.List;

public class CostedPath<T> {
    private List<T> cheapestPath = new ArrayList<>();
    private int cost = 0;
    private int culture = 0;

    public int getCost() {
        return cost;
    }

    public List<T> getCheapestPath() {
        return cheapestPath;
    }

    public int getCulture() {
        return culture;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setCheapestPath(List<T> cheapestPath) {
        this.cheapestPath = cheapestPath;
    }

    public void setCulture(int culture) {
        this.culture = culture;
    }
}
