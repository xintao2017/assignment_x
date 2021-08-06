package assignment.bonus1;

/**
 * The Main function for Bonus #1, with also the main logic to get
 * the shortest path from the starting point to the object.
 *
 * @author Tao Xin <taoxin.se@gmail.com>
 * @date 2021-08-06
 */

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import java.util.HashMap;
import java.util.Comparator;


import common.*;


public class GetPathToObj {
    private final static String BONUS_CONFIG_FILE_PATH = "./config_file/assignment_bonus1_config.txt";


    /**
     * The main function for Bonus question #1
     * @param args
     */
    public static void main(String[] args) {
        if (args.length < 2 || args.length > 2) {
            System.out.println("Wrong number of input arguments, please verify and try again.");
            System.exit(0);
        }

        getPathToObj(args[0].toLowerCase(), args[1].toLowerCase(), BONUS_CONFIG_FILE_PATH);
    }


    /**
     * This method will get and PRINT OUT the Path to the user input.
     *
     * @param nodeS
     * @param obj
     * @param configFilePath
     */
    private static void getPathToObj(String nodeS, String obj, String configFilePath) {

        ConfigLoader config = new ConfigLoader();

        HashMap<String, Node> nodeMap = config.loadNodesFromFile(configFilePath);
        HashMap<String, ArrayList<Node>> objNodeMap = config.getObjectNodeMap();

        if (!nodeMap.containsKey(nodeS)) {
            System.out.println("The input Location is not available! ");
            System.out.println("Available Nodes are: " + nodeMap.keySet() + "\nPlease try again!");
            return;
        }

        if (!objNodeMap.containsKey(obj)) {
            System.out.println("The input Object (" + obj + ") is not available! Please try again!");
            return;
        }

        Node nodeStart = nodeMap.get(nodeS);  // Get the starting Node

        if (nodeStart.getObject().equals(obj)) {
            System.out.println("You are in the " + nodeStart.getName() + ". You can get " + obj + " right here.");
            return;
        }

        ArrayList<LinkedList<Node>> paths = new ArrayList<LinkedList<Node>>();
        ArrayList<Node> nodes = objNodeMap.get(obj);
        LinkedList<Node> path = new LinkedList<Node>();

        for (Node d : nodes) { // Get all the paths to the Node(with Object)
            paths.add(getShortestPathBetweenNodes(nodeStart, d));
        }

        // Get the Shortest Path to the object
        if (paths.size() == 0) {
            System.out.println("Sorry, we cant find the path between the two location.");
            return;
        }
        else if (paths.size() == 1) {
            path = paths.get(0);
        }
        else {
            path = paths.stream()
                    .min(Comparator.comparingInt(List::size))
                    .orElse(new LinkedList<>());
        }

        // Print out the output
        System.out.println("You are in the " + nodeStart.getName());
        for (Node n : path) {
            System.out.print("Go to " + n.getName() + " ");
        }
        System.out.print("get " + obj + ".\n");
    }

    /**
     * This method will return the Shortest Path between two Nodes(s,d).
     *
     * @param s
     * @param d
     * @return
     */
    private static LinkedList<Node> getShortestPathBetweenNodes(Node s, Node d) {
        ArrayList<Node> visitedNodes = new ArrayList<Node>();
        LinkedList<Node> path = new LinkedList<Node>();
        Stack<Node> nodeStack = new Stack<Node>();
        nodeStack.push(s);

        while(!nodeStack.isEmpty()) {
            Node node = nodeStack.pop();

            if(!node.isVisited()) {
                node.visited = true;
                visitedNodes.add(node);
                path.add(node);
                ArrayList<Node> notVisitedNeighbors = getNotVisitedNeighbors(node);

                if (node.equals(d)) {
                    resetNodeState(visitedNodes);
                    path.remove(s);
                    return path;
                }

                if (notVisitedNeighbors.size() > 0) {
                    for (Node n : notVisitedNeighbors) {
                        if (!nodeStack.contains(n)) {
                            nodeStack.push(n);
                        }
                    }
                }

                while(!path.isEmpty() && !nodeStack.isEmpty()) {
                    if (s.getNeighbors().contains(nodeStack.peek())) {
                        path = new LinkedList<Node>();
                    }
                    else if (!path.getLast().getNeighbors().contains(nodeStack.peek())) {
                        path.removeLast();
                    }
                    else
                        break;
                }
            }
        }
        return null;
    }


    /**
     * This method will reset All the nodes' visited state to 'false'
     *
     * @param nodes
     */
    private static void resetNodeState(List<Node> nodes) {
        for (Node n : nodes) {
            n.visited = false;
        }
    }


    /**
     * Return the Node's non - visited Neighbors
     * @param node
     * @return ArrayList<Node>
     */
    private static ArrayList<Node> getNotVisitedNeighbors(Node node) {
        ArrayList<Node> nodeList = new ArrayList<Node>();

        if (node.getNeighbors().size() > 0) {
            for (Node n : node.getNeighbors()) {
                if (!n.isVisited()) {
                    nodeList.add(n);
                }
            }
        }
        return nodeList;
    }
}
