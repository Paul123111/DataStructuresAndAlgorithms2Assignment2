package com.example.assignment2.utils;

import javafx.scene.image.*;
import javafx.scene.paint.Color;

import java.util.*;

public class PixelGraph {

    static int width;

    public static WritableImage getWritableImage(Image image) {
        return new WritableImage(image.getPixelReader() ,(int) image.getWidth(), (int) image.getHeight());
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

    public static int[] breadthFirstSearchWrapper(int origin, int destination,  int[] pixels){
        ArrayList<int[]> agenda =  new ArrayList<>();
        int[] firstAgendaPath=new int[1],resultPath;
        firstAgendaPath[0] = origin;
        agenda.add(firstAgendaPath);
        resultPath=findPathBreadthFirst(agenda,null,destination,pixels); //Get single BFS path (will be shortest)
        //resultPath = Arrays.sort(resultPath, ((int a, b) -> ((int) a - (int) b)));
        return resultPath;
    }

   public static int[] findPathBreadthFirst(ArrayList<int[]> agenda, ArrayList<Integer> encountered, int destination, int[] pixels){
       if(agenda.isEmpty()) return null; //Search failed

       int[] nextPath=agenda.remove(0); //Get first item (next path to consider) off agenda
       int currentNode=nextPath[0]; //The first item in the next path is the current node
       if(currentNode==destination) return nextPath; //If that's the goal, we've found our path (so return it)
       if(encountered==null) encountered=new ArrayList<>(); //First node considered in search so create new (empty)encountered list

       encountered.add(currentNode); //Record current node as encountered so it isn't revisited again
       for(int adjNode : getAdjacentPixels(currentNode,pixels,width)) { //For each adjacent node
           if (adjNode != -1 && !encountered.contains(adjNode)) { //If it hasn't already been encountered
               int[] newPath = new int[nextPath.length + 1]; //Create a new path list as a copy of the current/next path
               System.arraycopy(nextPath, 0, newPath, 1, nextPath.length);
               newPath[0] = adjNode; //And add the adjacent node to the front of the new copy
               agenda.add(newPath); //Add the new path to the end of agenda (end->BFS!)
           }
       }
       return findPathBreadthFirst(agenda,encountered,destination,pixels); //Tail call
   }

    public static ArrayList<Integer> breadthFirstSearchImageWrapper(ImageView imageView, Image image, int origin, int destination, int[] pixels){
        int[] pixelTempArray =  pixels.clone();
        ArrayList<Integer> agenda =  new ArrayList<>();
        agenda.add(origin);
        ArrayList<Integer> resultPath=findPathBreadthFirstImage(imageView, image, agenda,null, destination, pixels); //Get single BFS path (will be shortest)
        //resultPath = Arrays.sort(resultPath, ((int a, b) -> ((int) a - (int) b)));
        return resultPath;
    }

    public static ArrayList<Integer> findPathBreadthFirstImage(ImageView imageView, Image image, ArrayList<Integer> agenda, ArrayList<Integer> encountered, int destination, int[] pixels){
        if(agenda.isEmpty()) return null; //Search failed
        WritableImage writableImage = getWritableImage(image);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        //int nextPath=agenda.remove(0); //Get first item (next path to consider) off agenda
        int currentNode=agenda.remove(0); //The first item in the next path is the current node
        if(currentNode==destination) {
            asciiImage(pixels, width);
            return new ArrayList<>();
        }//If that's the goal, we've found our path (so return it)
        if(encountered==null) encountered=new ArrayList<>(); //First node considered in search so create new (empty)encountered list

        encountered.add(currentNode); //Record current node as encountered so it isn't revisited again
        for(int adjIndex : getAdjacentPixels(currentNode,pixels,width)) { //For each adjacent node
            if (adjIndex != -1 && !encountered.contains(adjIndex)) { //If it hasn't already been encountered
//                int[] newPath = new int[nextPath.length + 1]; //Create a new path list as a copy of the current/next path
//                System.arraycopy(nextPath, 0, newPath, 1, nextPath.length);
//                newPath[0] = adjNode; //And add the adjacent node to the front of the new copy
//                agenda.add(newPath); //Add the new path to the end of agenda (end->BFS!)

                pixels[adjIndex] = currentNode+1;
                agenda.add(adjIndex);

                System.out.println(adjIndex);
                pixelWriter.setColor((int) adjIndex%width, (int) adjIndex/width, Color.hsb(pixels[adjIndex], 1, 1));
                imageView.setImage(writableImage);

            }
        }
        return findPathBreadthFirstImage(imageView, writableImage, agenda, encountered, destination, pixels); //Tail call
    }

    public static ArrayList<Integer> findPathBreadthFirst2(ArrayList<Integer> agenda, ArrayList<Integer> encountered, int destination, int[] pixels){
        if(agenda.isEmpty()) return null; //Search failed

        //int nextPath=agenda.remove(0); //Get first item (next path to consider) off agenda
        int currentNode=agenda.remove(0); //The first item in the next path is the current node
        if(currentNode==destination) {
            asciiImage(pixels, width);
            return new ArrayList<>();
        }//If that's the goal, we've found our path (so return it)
        if(encountered==null) encountered=new ArrayList<>(); //First node considered in search so create new (empty)encountered list

        encountered.add(currentNode); //Record current node as encountered so it isn't revisited again
        for(int adjIndex : getAdjacentPixels(currentNode,pixels,width)) { //For each adjacent node
            if (adjIndex != -1 && !encountered.contains(adjIndex)) { //If it hasn't already been encountered
//                int[] newPath = new int[nextPath.length + 1]; //Create a new path list as a copy of the current/next path
//                System.arraycopy(nextPath, 0, newPath, 1, nextPath.length);
//                newPath[0] = adjNode; //And add the adjacent node to the front of the new copy
//                agenda.add(newPath); //Add the new path to the end of agenda (end->BFS!)

                pixels[adjIndex] = currentNode+1;
                agenda.add(adjIndex);

                System.out.println(adjIndex);

            }
        }
        return findPathBreadthFirst2(agenda, encountered, destination, pixels); //Tail call
    }

    public static ArrayList<Integer> breadthFirstSearchWrapper2(int origin, int destination, int[] pixels){
        ArrayList<Integer> agenda =  new ArrayList<>();
        agenda.add(origin);
        return findPathBreadthFirst2(agenda,null,destination,pixels); //Get single BFS path (will be shortest)
        //resultPath = Arrays.sort(resultPath, ((int a, b) -> ((int) a - (int) b)));
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
           if(color != 0x000000ff){
              result[i] = 0;
           }else {
               result[i] = -1;
           }
       }
       return result;
   }

   //Index 0 starts left and goes clockwise around the pixel
   static int[] getAdjacentPixels(int pixel, int[] pixels, int width){
       int[] adjacentPixels = new int[8];
       adjacentPixels[0] =  isTouchingLeft(pixel,width) ? -1: pixel-1;
       adjacentPixels[1] = isTouchingTop(pixel,width) || isTouchingLeft(pixel,width)? -1: pixel-width-1;
       adjacentPixels[2] = isTouchingTop(pixel,width)? -1:pixel-width;
       adjacentPixels[3] = isTouchingTop(pixel,width) || isTouchingRight(pixel, width)? -1:pixel-width+1;
       adjacentPixels[4] = isTouchingRight(pixel, width)? -1:pixel+1;
       adjacentPixels[5] = isTouchingRight(pixel,width) || isTouchingBottom(pixel,pixels,width)? -1:pixel+width+1;
       adjacentPixels[6] = isTouchingBottom(pixel,pixels,width)? -1:pixel+width;
       adjacentPixels[7] = isTouchingBottom(pixel,pixels,width) || isTouchingLeft(pixel,width)? -1:pixel+width-1;

        for(int i = 0; i<adjacentPixels.length;i++){
            if(adjacentPixels[i]!=-1 && pixels[adjacentPixels[i]] == -1){
                adjacentPixels[i] = -1;
            }
        }
//        int[] validPixels = new int[validPixelCount];
//        int index = 0;
//        for(int i = 0; i<validPixels.length;i++){
//            if(adjacentPixels[i] != -1){
//                validPixels[index] = adjacentPixels[i];
//                index ++;
//            }
//        }


       return adjacentPixels;
   }

   static boolean isTouchingLeft(int pixel, int width){
        return pixel%width==0;
    }
   static boolean isTouchingTop(int pixel, int width){
        return pixel-width<=0;
   }

    static boolean isTouchingRight(int pixel, int width){
        return pixel%width==width-1;
    }
    static boolean isTouchingBottom(int pixel, int[] pixels, int width){
       return pixel+width >= pixels.length;
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
