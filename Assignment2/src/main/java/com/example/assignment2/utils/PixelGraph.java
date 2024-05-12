package com.example.assignment2.utils;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class PixelGraph {

    static int width;

    public static WritableImage getWritableImage(Image image) {
        return new WritableImage((int) image.getWidth(), (int) image.getHeight());
    }

    public static Image changePixels(Image image, int[] pixels) {
        WritableImage writableImage = getWritableImage(image);
        final int width = (int) writableImage.getWidth();
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        for (int i : pixels) {
            pixelWriter.setColor(i%width, i/width, new Color(0, 0, 0, 1));
        }
        return writableImage;
    }

    public static ArrayList<Integer> breadthFirstSearchWrapper(int origin, int destination,  int[] pixels){
        ArrayList<Integer> agenda =  new ArrayList<>();



        return null;
    }

   public static int[] findPathBreadthFirst(ArrayList<int[]> agenda, ArrayList<Integer> encountered, int destination, int[] pixels){
       if(agenda.isEmpty()) return null; //Search failed

       int[] nextPath=agenda.remove(0); //Get first item (next path to consider) off agenda
       int currentNode=nextPath[0]; //The first item in the next path is the current node
       if(currentNode == destination) return nextPath; //If that's the goal, we've found our path (so return it)
       if(encountered==null) encountered=new ArrayList<>(); //First node considered in search so create new (empty)encountered list

       encountered.add(currentNode); //Record current node as encountered so it isn't revisited again
       for(int adjNode : getAdjacentPixels(currentNode,pixels,width)) //For each adjacent node
           if(!encountered.contains(adjNode)) { //If it hasn't already been encountered
               int[] newPath= new int[nextPath.length+1]; //Create a new path list as a copy of the current/next path
               System.arraycopy(nextPath,0,newPath,1,newPath.length);
               newPath[0]=adjNode; //And add the adjacent node to the front of the new copy
               agenda.add(newPath); //Add the new path to the end of agenda (end->BFS!)
           }
       return findPathBreadthFirst(agenda,encountered,destination,pixels); //Tail call
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

    public static void setWidth(int w){
        width = w;
    }

}
