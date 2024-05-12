package com.example.assignment2.utils;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

public class PixelGraph {


    public static int[] breadthFirstSearch(int origin, int destination,  int[] pixels){


        return null;
    }

   public static int[] getPixels(Image image){
       PixelReader reader = image.getPixelReader();
       int width = (int)image.getWidth();
       int size = (int)(width*image.getHeight());
       int[] result = new int[size];
       for(int i=0; i< size;i++){
           int x = i%width;
           int y = i/width;
           int color = reader.getArgb(x,y);
           if(color != 0xff000000){
              result[i] = 1;
           }else {
               result[i] = 0;
           }
       }
       return result;
   }

    public static void asciiImage(int[] pixelArray, int width) {
        for (int i = 0; i < pixelArray.length; i++) {
            System.out.print(pixelArray[i] + (((i+1)%width==0) ? "\n" : " "));
        }
    }

}
