package com.example.assignment2.utils;

import com.example.assignment2.interfaces.SearchByField;

import java.util.*;

public class Graph<T extends SearchByField<R>, R> {
    //add adjacency matrix functions - all nodes will be added from csv file - DONE

    protected List<T> nodes = new ArrayList<>();
    protected int[][] amat = new int[0][0];

    public Graph(List<T> nodes) {
        this.nodes = nodes;
        amat = AdjacencyMatrix.createMatrix(nodes.size());
    }

    public List<T> getNodes() {
        return nodes;
    }

    public int[][] getAMat() {
        return amat;
    }

    public int findIndexByField(R id) {
        //linear search, probably won't need to optimise as this is a test method
        int count = 0;
        for (T t : nodes) {
            if (t.matchesID(id))
                return count;
            count++;
        }
        return -1;
    }

    public void connectNodes(R id1, R id2, int value) {
        AdjacencyMatrix.connectNodesUndirected(amat, findIndexByField(id1), findIndexByField(id2), value);
    }

    public String displayPossibleLinks(R id) {
        //returns list of every possible edge for one link
        T node1 = nodes.get(findIndexByField(id));
        String listOfLinks = "";
        for (T t : nodes) {
            listOfLinks += id + "," + t.getID() + "," + Utilities.getDistance(node1.getX(), node1.getY(), t.getX(), t.getY()) + ",\n";
        }
        return listOfLinks;
    }

    //Agenda list based depth-first graph traversal (tail recursive)
    public void traverseGraphDepthFirst(List<T> agenda, List<T> encountered ){
        if(agenda.isEmpty()) return;

        T next=agenda.remove(0);
        System.out.println(next.getID());

        if(encountered==null) encountered=new ArrayList<>(); //First node so create new (empty) encountered list
        encountered.add(next);

        for(int i = 0; i < nodes.size(); i++) {
            int index = nodes.indexOf(next);
            if (amat[index][i]!=0 && !encountered.contains(nodes.get(i)) && !agenda.contains(nodes.get(i)))
                agenda.add(0, nodes.get(i)); //Add children to front of agenda (order unimportant here)
        }
        traverseGraphDepthFirst(agenda, encountered ); //Tail call
    }

    public void traverseGraphDepthFirstWrapper(R id) {
        ArrayList<T> arrayList = new ArrayList<>();
        arrayList.add(nodes.get(findIndexByField(id)));
        traverseGraphDepthFirst(arrayList, null);
    }

    //Recursive depth-first search of graph (first single path identified returned)
    public List<T> findPathDepthFirst(T from, List<T> encountered, T lookingfor) {
        List<T> result;

        if(from.equals(lookingfor)) { //Found it
            result=new ArrayList<>(); //Create new list to store the path info (any List implementation could be used)
            result.add(from); //Add the current node as the only/last entry in the path list
            return result; //Return the path list
        }

        if(encountered==null) encountered=new ArrayList<>(); //First node so create new (empty) encountered list
        encountered.add(from);

        for(int i = 0; i < nodes.size(); i++) {
            if (amat[nodes.indexOf(from)][i] != 0 && !encountered.contains(nodes.get(i))) {
                result = findPathDepthFirst(nodes.get(i), encountered, lookingfor);
                if (result != null) { //Result of the last recursive call contains a path to the solution node
                    result.add(0, from); //Add the current node to the front of the path list
                    return result; //Return the path list
                }
            }
        }

        return null;
    }

    public List<T> findPathDepthFirstWrapper(R from, R lookingfor){
        return findPathDepthFirst(nodes.get(findIndexByField(from)), null,
                nodes.get(findIndexByField(lookingfor)));
    }

    //Recursive depth-first search of graph (all paths identified returned)
    public List<List<T>> findAllPathsDepthFirst(T from, List<T> encountered, T lookingfor){
        List<List<T>> result=null, temp2;
        if(from.equals(lookingfor)) { //Found it
            List<T> temp=new ArrayList<>(); //Create new single solution path list
            temp.add(from); //Add current node to the new single path list
            result=new ArrayList<>(); //Create new "list of lists" to store path permutations
            result.add(temp); //Add the new single path list to the path permutations list
            return result; //Return the path permutations list
        }

        if(encountered==null) encountered=new ArrayList<>(); //First node so create new (empty) encountered list
        encountered.add(from); //Add current node to encountered list

        for(int i = 0; i < nodes.size(); i++) {
            if (amat[nodes.indexOf(from)][i] != 0 && !encountered.contains(nodes.get(i))) {
                temp2 = findAllPathsDepthFirst(nodes.get(i), new ArrayList<>(encountered), lookingfor); //Use clone of encountered list for recursive call!

                if (temp2 != null) { //Result of the recursive call contains one or more paths to the solution node
                    for (List<T> x : temp2) //For each partial path list returned
                        x.add(0, from); //Add the current node to the front of each path list
                    if (result == null)
                        result = temp2; //If this is the first set of solution paths found use it as the result
                    else result.addAll(temp2); //Otherwise append them to the previously found paths
                }
            }
        }
        return result;
    }

    public List<List<T>> findAllPathsDepthFirstWrapper(R from, R lookingfor){
        return findAllPathsDepthFirst(nodes.get(findIndexByField(from)), null,
                nodes.get(findIndexByField(lookingfor)));
    }

    public Map<T, Integer> initialiseNodeValues(T startNode) {
        //method to initialise node values for dijkstra's algorithm
        Map<T, Integer> nodeValues = new HashMap<>();
        for (T t : nodes) {
            nodeValues.put(t, Integer.MAX_VALUE);
        }
        nodeValues.replace(startNode, 0);
        return nodeValues;
    }

    public CostedPath<T> findCheapestPathDijkstra(T startNode, T lookingfor){
        Map<T, Integer> nodeValues = initialiseNodeValues(startNode);
        List<T> encountered=new ArrayList<>(), unencountered=new ArrayList<>();
        CostedPath<T> costedPath = new CostedPath();
        int size = nodes.size();

        unencountered.add(startNode); //Add the start node as the only value in the unencountered list to start
        T currentNode;

        do{ //Loop until unencountered list is empty
            currentNode=unencountered.remove(0); //Get the first unencountered node (sorted list, so will have lowest value)
            encountered.add(currentNode); //Record current node in encountered list

            if(currentNode.equals(lookingfor)){ //Found goal - assemble path list back to start and return it
                costedPath.getCheapestPath().add(currentNode); //Add the current (goal) node to the result list (only element)
                costedPath.setCost(nodeValues.get(currentNode)); //The total cheapest path cost is the node value of the current/goal node

                while(currentNode!=startNode) { //While we're not back to the start node...
                    for (int i = 0; i < size; i++) {
                        //if current node is connected to this node on adj matrix &&
                        // (currentNode.value-linkBetweenCurrentAndThis.value==thisNode.value)
                        if(amat[nodes.indexOf(currentNode)][i]!=0 &&
                                (nodeValues.get(currentNode)-amat[nodes.indexOf(currentNode)][i])==nodeValues.get(nodes.get(i))) {
                            //System.out.println(nodes.get(i).getID() + ", " + currentNode.getID());

                            costedPath.getCheapestPath().add(0, nodes.get(i)); //Add the identified path node to the front of the result list
                            currentNode=nodes.get(i); //Move the currentNode reference back to the identified path node
                            //foundPrevPathNode=true; //Set the flag to break the outer loop
                            break;
                        }
                    }
                }
                //Reset the node values for all nodes to (effectively) infinity so we can search again (leave no footprint!)
//                for(T t : encountered) nodeValues.replace(t, Integer.MAX_VALUE);
//                for(T t : unencountered) nodeValues.replace(t, Integer.MAX_VALUE);
                return costedPath; //The costed (cheapest) path has been assembled, so return it!
            }

            //We're not at the goal node yet, so...
            for(int i = 0; i < size; i++) //For each edge/link from the current node...
                if(amat[nodes.indexOf(currentNode)][i]!=0 && !encountered.contains(nodes.get(i))) { //If the node it leads to has not yet been encountered (i.e. processed)
                    nodeValues.replace(nodes.get(i), Integer.min(nodeValues.get(nodes.get(i)),
                            nodeValues.get(currentNode)+amat[nodes.indexOf(currentNode)][i])); //Update the node value at the end
//of the edge to the minimum of its current value or the total of the current node's value plus the cost of the edge
                    if(!unencountered.contains(nodes.get(i))) unencountered.add(nodes.get(i));
                }
            Collections.sort(unencountered,(n1, n2)->nodeValues.get(n1)-nodeValues.get(n2)); //Sort in ascending node value order
        }while(!unencountered.isEmpty());
        return null; //No path found, so return null
    }

    public CostedPath<T> findCheapestPathDijkstraWrapper(R startNode, R lookingfor) {
        return findCheapestPathDijkstra(nodes.get(findIndexByField(startNode)), nodes.get(findIndexByField(lookingfor)));
    }
}
