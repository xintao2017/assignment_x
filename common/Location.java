package common;

/**
 * Define the Location/room Class, where it has Four attributes: Name, Object, Parent, Children.
 *
 *
 * @author Tao Xin <taoxin.se@gmail.com>
 * @date 2021-07-28
 */

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class Location {
    private String name;
    private String object = "";
    private Location parent = null;
    private List<Location> children;
    private ArrayList<Location> neighbors = new ArrayList<Location>();
    private ArrayList<Location> pathToRoot = new ArrayList<Location>();

    public Location (String name) {
        this.name = name;
        this.object = "";
        this.children = new ArrayList<Location>();
    }

    public Location(String name, String object) {
        this.name = name;
        this.object = object;
        this.children = new ArrayList<Location>();
    }

    public boolean hasParent() { return parent == null ? false : true; }

    public void setParent(Location parent) {
        this.parent = parent;
    }

    public Location getParent() { return parent; }

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

    public void addChild(Location child) {
        this.children.add(child);
    }

    public List<Location> getAllChildren() { return children; }

    public void addToPath(Location location) {
        this.pathToRoot.add(location);
    }

    public void setPathToRoot(ArrayList<Location> path) {
        this.pathToRoot.addAll(path);
    }

    public ArrayList<Location> getPathToRoot() {
        return pathToRoot;
    }

    public void addNeighbor(Location n) {
        this.neighbors.add(n);
    }

    public ArrayList<Location> getNeighbors() {
        return neighbors;
    }

    public boolean hasNeighbors() { return neighbors.size() == 0 ? false : true; }

    public String toString() {
        return name;
    }
}