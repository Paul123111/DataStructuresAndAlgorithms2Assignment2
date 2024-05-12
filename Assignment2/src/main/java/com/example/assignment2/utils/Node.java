package com.example.assignment2.utils;

import com.example.assignment2.interfaces.SearchByField;

public class Node implements SearchByField<String> {
    private int x = 0, y = 0, year = 0, imageX = 0, imageY = 0;
    private String name = "", image = "", type = "";

    public Node(String type, String name, int x, int y, String image, int imageX, int imageY) {
        setType(type);
        setName(name);
        setX(x);
        setY(y);
        setImage(image);
        setImageX(imageX);
        setImageY(imageY);
    }

    public Node(String type, String name, int year, int x, int y, String image, int imageX, int imageY) {
        setType(type);
        setName(name);
        setX(x);
        setY(y);
        setImage(image);
        setImageX(imageX);
        setImageY(imageY);
    }

    @Override
    public boolean matchesID(String name) {
        return (this.name.equals(name));
    }

    @Override
    public String getID() {
        return name;
    }

    //Getters and Setters
    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getCulture() {
        if (type.equals("Landmark"))
            return 2024-year;
        return 0;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getYear() {
        return year;
    }

    public String getImage() {
        return image;
    }

    public int getImageX() {
        return imageX;
    }

    public int getImageY() {
        return imageY;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setImageX(int imageX) {
        this.imageX = imageX;
    }

    public void setImageY(int imageY) {
        this.imageY = imageY;
    }
}
