package common;

/**
 * Define the Node class, a node can have neighbors that connected to
 * each other.
 *
 *
 * @author Tao Xin <taoxin.se@gmail.com>
 * @date 2021-08-04
 */

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class Node {
    private String name;
    private String object = "";
    private ArrayList<Node> neighbors;
    private ArrayList<Node> pathToRoot = new ArrayList<Node>();
    public boolean visited = false;

    public Node (String name) {
        this.name = name;
        this.object = "";
        this.neighbors = new ArrayList<Node>();
    }

    public Node(String name, String object) {
        this.name = name;
        this.object = object;
        this.neighbors = new ArrayList<Node>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setObject(String obj) {
        this.object = obj;
    }

    public String getObject() {
        return object;
    }

    public void addToPath(Node node) {
        this.pathToRoot.add(node);
    }

    public void setPathToRoot(ArrayList<Node> path) {
        this.pathToRoot.addAll(path);
    }

    public ArrayList<Node> getPathToRoot() {
        return pathToRoot;
    }

    public void addNeighbor(Node n) { this.neighbors.add(n); }

    public ArrayList<Node> getNeighbors() { return neighbors; }

    public boolean areNeighbors(Node n) { return neighbors.contains(n) ? false : true; }

    public boolean isVisited() {
        return visited;
    }

    public String toString() {
        return name;
    }
}