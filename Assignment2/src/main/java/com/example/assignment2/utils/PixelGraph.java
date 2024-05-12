package com.example.assignment2.utils;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

import java.util.ArrayList;

public class PixelGraph {


    public static ArrayList<Integer> breadthFirstSearch(int origin, int destination,  int[] pixels){
        ArrayList<Integer> result = new ArrayList<>();


        return result;
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

   //Index 0 starts left and goes clockwise around the pixel
   static int[] getAdjacentPixels(int pixel, int[] pixels, int width){
       int[] adjacentPixels = new int[8];
       adjacentPixels[0]=  isTouchingLeft(pixel,width) ? -1: pixel-1;
       adjacentPixels[1] = isTouchingTop(pixel,width)&&isTouchingLeft(pixel,width)? -1: pixel-width-1;
       adjacentPixels[2] = isTouchingTop(pixel,width)? -1:pixel-width;
       adjacentPixels[3] = isTouchingTop(pixel,width) && isTouchingRight(pixel, width)? -1:pixel-width+1;
       adjacentPixels[4] = isTouchingRight(pixel, width)? -1:pixel+1;
       adjacentPixels[5] = isTouchingRight(pixel,width) && isTouchingBottom(pixel,pixels,width)? -1:pixel+width+1;
       adjacentPixels[6] = isTouchingBottom(pixel,pixels,width)? -1:pixel+width;
       adjacentPixels[7] = isTouchingBottom(pixel,pixels,width) && isTouchingLeft(pixel,width)? -1:pixel+width-1;

       int validPixelCount = 8;
        for(int i = 0; i<adjacentPixels.length;i++){
            if(adjacentPixels[i]!=-1 && pixels[adjacentPixels[i]] == 0){
                adjacentPixels[i] = -1;
                validPixelCount --;
            }
        }
        int[] validPixels = new int[validPixelCount];
        int index = 0;
        for(int i = 0; i<validPixels.length;i++){
            if(adjacentPixels[i] != -1){
                validPixels[index] = adjacentPixels[i];
                index ++;
            }
        }
       return validPixels;
   }

   static boolean isTouchingLeft(int pixel, int width){
        return pixel%width!=0;
    }
   static boolean isTouchingTop(int pixel, int width){
        return pixel - width<0;
   }

    static boolean isTouchingRight(int pixel, int width){
        return pixel%width != width-1;
    }
    static boolean isTouchingBottom(int pixel, int[] pixels, int width){
       return pixel+width > pixels.length;
    }




    public static void asciiImage(int[] pixelArray, int width) {
        for (int i = 0; i < pixelArray.length; i++) {
            System.out.print(pixelArray[i] + (((i+1)%width==0) ? "\n" : " "));
        }
    }

}
