package com.example.assignment2.utils;

import com.example.assignment2.interfaces.SearchByField;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BreadthFirstGraph<T extends SearchByField<R>, R> extends Graph<T, R>{
    public BreadthFirstGraph(List<T> nodes) {
        super(nodes);
    }


    public void traverseGraphBreadthFirst(List<T> agenda, List<T> encountered ){
        if(agenda.isEmpty()) return;

        T next=agenda.remove(0);
        System.out.println(next.getID());

        if(encountered==null) encountered=new ArrayList<>(); //First node so create new (empty) encountered list
        encountered.add(next);

        for(int i = 0; i < nodes.size(); i++) {
            int index = nodes.indexOf(next);
            if (amat[index][i]!=0 && !encountered.contains(nodes.get(i)) && !agenda.contains(nodes.get(i)))
                agenda.add(nodes.get(i)); //Add children to end of agenda
        }
        traverseGraphDepthFirst(agenda, encountered ); //Tail call
    }

    public void traverseGraphBreadthFirstWrapper(R id) {
        ArrayList<T> arrayList = new ArrayList<>();
        arrayList.add(nodes.get(findIndexByField(id)));
        traverseGraphBreadthFirst(arrayList, null);
    }

    public List<T> findPathBreadthFirst(T startNode, T lookingfor){
        List<List<T>> agenda=new ArrayList<>(); //Agenda comprised of path lists here!
        List<T> firstAgendaPath=new ArrayList<>(),resultPath;
        firstAgendaPath.add(startNode);
        agenda.add(firstAgendaPath);
        resultPath=findPathBreadthFirst(agenda,null,lookingfor); //Get single BFS path (will be shortest)
        Collections.reverse(resultPath); //Reverse path (currently has the goal node as the first item)
        return resultPath;
    }

    //Recursive depth-first search of graph (first single path identified returned)
    public List<T> findPathBreadthFirst(List<List<T>> agenda, List<T> encountered, T lookingfor) {
        if(agenda.isEmpty()) return null; //Search failed
        List<T> nextPath=agenda.remove(0); //Get first item (next path to consider) off agenda
        T currentNode=nextPath.get(0); //The first item in the next path is the current node
        if(currentNode.equals(lookingfor))return nextPath; //If that's the goal, we've found our path (so return it)
        if(encountered==null) encountered=new ArrayList<>(); //First node considered in search so create new (empty) encountered list
        encountered.add(currentNode); //Record current node as encountered so it isn't revisited again
        for(int i = 0; i < nodes.size(); i++) //For each adjacent node
            if(amat[nodes.indexOf(currentNode)][i] != 0 && !encountered.contains(nodes.get(i))) { //If it hasn't already been encountered
                List<T> newPath=new ArrayList<>(nextPath); //Create a new path list as a copy of the current/next path
                newPath.add(0,nodes.get(i)); //And add the adjacent node to the front of the new copy
                agenda.add(newPath); //Add the new path to the end of agenda (end->BFS!)
            }
        return findPathBreadthFirst(agenda,encountered,lookingfor); //Tail call
    }


    public List<T> findPathBreadthFirstWrapper(R from, R lookingfor){
        return findPathBreadthFirst(nodes.get(findIndexByField(from)),
                nodes.get(findIndexByField(lookingfor)));
    }
}
