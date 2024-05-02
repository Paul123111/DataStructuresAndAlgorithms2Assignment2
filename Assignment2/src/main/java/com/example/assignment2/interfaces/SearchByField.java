package com.example.assignment2.interfaces;

public interface SearchByField<R> {
    boolean matchesID(R id);
    R getID();
    int getX();
    int getY();
    int getCulture();
    //boolean hasCulture();
}
