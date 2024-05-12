package com.example.assignment2.utils;

import javafx.scene.control.TextField;

public class Utilities {
    public static int doubleToInt(double doubleToConvert) {
        return (int) Math.round(doubleToConvert);
    }

    public static void numericText(TextField textField, int num) {
        textField.setText(Integer.toString(num));
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
    public static int parseInt(String i) {
        try {
            return Integer.parseInt(i);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static int getDistance(int x1, int y1, int x2, int y2) {
        //System.out.println(Math.abs(indexToCoords(index1, width)[0]-indexToCoords(index2, width)[0]) + ", " + Math.abs(indexToCoords(index1, width)[1]-indexToCoords(index2, width)[1]));
        return (int) Math.hypot(Math.abs(x2-x1), Math.abs(y2-y1));
    }

    public static int[] reversArray(int [] input){
        int[] output = new int[input.length];
        int inIndex = 0;
        for(int i = output.length-1; i != 0;i--){
            output[i] = input[inIndex];
            inIndex ++;
        }
        return output;
    }

}

