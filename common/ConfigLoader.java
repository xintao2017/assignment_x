package common;

/**
 * Read and Load the configuration file which will be a common function
 * shared by all other assginments
 *
 *
 * @author Tao Xin <taoxin.se@gmail.com>
 * @date 2021-07-28
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;


public class ConfigLoader {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private HashMap<String, ArrayList<Location>> objMap = new HashMap<String, ArrayList<Location>>();
    private HashMap<String, ArrayList<Node>> objNodeMap = new HashMap<String, ArrayList<Node>>();

    /**
     * Parse the config file to read all locations and objects
     *
     * @param path
     * @return HashMap<String, Location>
     */
    public HashMap<String, Location> loadConfigFromFile(String path) {
        BufferedReader bufferedReader = null;
        HashMap<String, Location> loadedLocations = new HashMap<String, Location>();
        HashMap<String, Node> loadedNodes = new HashMap<String, Node>();

        try {
            bufferedReader = new BufferedReader(new FileReader(path));
            String line = bufferedReader.readLine();

            while (line != null) {
                loadedLocations = getLocations(line.trim(), loadedLocations);
                loadedNodes = loadNodes(line.trim(), loadedNodes);
                line = bufferedReader.readLine();
            }
        }
        catch (IOException e) {
            LOGGER.log(Level.WARNING, "Config file wouldn't load!");
        }
        catch (InvalidInputException e) {
            LOGGER.log(Level.WARNING, "Invalid input on Config file.");
        }
        finally {
            try {
                bufferedReader.close();
            }
            catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Failed to close BufferedReader Stream.", e);
            }
        }

        return loadedLocations;
    }


    /**
     * This method is for Bonus #1 to load all Nodes from the config file
     * @param path
     * @return
     */
    public HashMap<String, Node> loadNodesFromFile(String path) {
        BufferedReader bufferedReader = null;
        HashMap<String, Node> loadedNodes = new HashMap<String, Node>();

        try {
            bufferedReader = new BufferedReader(new FileReader(path));
            String line = bufferedReader.readLine();

            while (line != null) {
                loadedNodes = loadNodes(line.trim(), loadedNodes);
                line = bufferedReader.readLine();
            }
        }
        catch (IOException e) {
            LOGGER.log(Level.WARNING, "Config file wouldn't load!");
        }
        catch (InvalidInputException e) {
            LOGGER.log(Level.WARNING, "Invalid input on Config file.");
        }
        finally {
            try {
                bufferedReader.close();
            }
            catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Failed to close BufferedReader Stream.", e);
            }
        }

        return loadedNodes;
    }


    /**
     * The method to read the config string to create and load Nodes
     *
     * @param locationConfig
     * @param loadedNodes
     * @return
     * @throws InvalidInputException
     */
    private HashMap<String, Node> loadNodes(String locationConfig, HashMap<String, Node> loadedNodes)
            throws InvalidInputException {
        HashMap<String, Node> nodeMap = loadedNodes;
        String[] node_arr = locationConfig.split(":");
        Node node = null;

        if (node_arr.length <= 1) {
            throw new InvalidInputException("Invalid Input from the Config file");
        }

        for (int i = 0; i < node_arr.length; i++) {
            String lo_key = node_arr[i].toLowerCase();
            String object = "";

            //first node
            if (i == 0 && !nodeMap.containsKey(lo_key)) {
                node = new Node(node_arr[i]);
            } else if(i == 0) { // Assuming the first Node will be the same.
                continue;
            }
            else {
                Node preNode = nodeMap.get(node_arr[i-1].toLowerCase());

                if (node_arr[i].contains("-")) {
                    String[] lo_obj = node_arr[i].split(" - ");
                    lo_key = lo_obj[0].trim().toLowerCase();
                    object = lo_obj[1].trim();

                    if (nodeMap.containsKey(lo_key)) {
                        node = nodeMap.get(lo_key);
                        node.setObject(object);
                        addObjectToNodeMap(object, node);
                    }
                    else {
                        node = new Node(lo_obj[0].trim(), object);
                        addObjectToNodeMap(object, node);
                    }
                }
                else {
                    if (nodeMap.containsKey(lo_key)) {
                        node = nodeMap.get(lo_key);
                    }
                    else {
                        node = new Node(node_arr[i]);
                    }
                }

                if (!preNode.getNeighbors().contains(node)){
                    preNode.addNeighbor(node);
                    node.addNeighbor(preNode);
                }
            }
            nodeMap.put(lo_key, node);
        }

        return nodeMap;
    }


    /**
     * Parse the location string and objects, and return a HashMap of all locations.
     * Location object will be created for each location, they are connected with their parent.
     *
     * @param locationConfig
     * @param loadedLocations
     * @return HashMap<String, Location>
     */
    private HashMap<String, Location> getLocations(String locationConfig, HashMap<String, Location> loadedLocations) {
        HashMap<String, Location> locationsMap = loadedLocations;
        String[] locations_arr = locationConfig.split(":");
        ArrayList<Location> pathToRoot = new ArrayList<Location>();
        ArrayList<Location> listOfLocations = new ArrayList<Location>();
        Location lo_x = null;
        Location parent = null;

        for(int i = 0; i < locations_arr.length ; i++) {
            String lo_key = locations_arr[i].toLowerCase();
            String object = "";

            if (i == 0 && !locationsMap.containsKey(lo_key)) {// Create root
                lo_x = new Location(locations_arr[i]);
            }
            else {
                if (lo_key.contains("-")) { // Update the object if the location already exists
                    String[] lo_obj = locations_arr[i].split("-");
                    object = lo_obj[1].trim();
                    lo_key = lo_obj[0].trim().toLowerCase();
                    if (locationsMap.containsKey(lo_key)) { // If the location has existed
                        locationsMap.get(lo_key).setObject(object);

                        addObjectToMap(object, locationsMap.get(lo_key));
                        continue;
                    } else {
                        parent = locationsMap.get(locations_arr[i - 1].toLowerCase());
                        lo_x = new Location(lo_obj[0].trim(), object);
                        addObjectToMap(object, lo_x);
                    }
                } else { // Create new Location
                    if (locationsMap.containsKey(lo_key)) {
                        continue;
                    }
                    parent = locationsMap.get(locations_arr[i - 1].toLowerCase());
                    lo_x = new Location(locations_arr[i], "");
                }
                lo_x.setParent(parent);
                lo_x.setPathToRoot(parent.getPathToRoot());
                parent.addChild(lo_x);
            }
            lo_x.addToPath(lo_x);
            locationsMap.put(lo_key, lo_x);
        }
        return locationsMap;
    }


    /**
     * Add the Object to Nodes HashMap, which saves objects and their nodes*
     *
     * @param object
     * @param node
     */
    private void addObjectToNodeMap(String object, Node node) {
        ArrayList<Node> listOfNodes = new ArrayList<Node>();
        if (this.objNodeMap.containsKey(object)) {
            listOfNodes = objNodeMap.get(object);
        }
        listOfNodes.add(node);
        this.objNodeMap.put(object, listOfNodes);
    }

    /**
     * Get the object and their locations, there can be multi-locations for one object
     *
     * @return HashMap<String, ArrayList<Node>>
     */
    public HashMap<String, ArrayList<Node>> getObjectNodeMap(){
        return objNodeMap;
    }


    /**
     * Add the Object to HashMap, which saves objects and their locations*
     *
     * @param object
     * @param location
     */
    private void addObjectToMap(String object, Location location) {
        ArrayList<Location> listOfLocations = new ArrayList<Location>();
        if (this.objMap.containsKey(object)) {
            listOfLocations = objMap.get(object);
        }
        listOfLocations.add(location);
        this.objMap.put(object, listOfLocations);
    }


    /**
     * Get the object and their locations, there can be multi-locations for one object
     *
     * @return HashMap<String, ArrayList<Location>>
     */
    public HashMap<String, ArrayList<Location>> getObjectMap(){
        return objMap;
    }
}
