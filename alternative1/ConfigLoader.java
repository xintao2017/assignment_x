package alternative1;

/**
 * Load the configuration file which will be a common function
 * shared by all other assginments
 *
 *
 * @author Tao Xin <taoxin.se@gmail.com>
 * @date 2021-08-03
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

import common.InvalidInputException;

public class ConfigLoader {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private HashMap<String, ArrayList<Tree>> objMap = new HashMap<String, ArrayList<Tree>>();


    /**
     * Read the Config file and load the content into Tree struct.
     *
     * @param path
     * @return
     */
    public HashMap<String, Tree> loadConfigFromFile(String path) {
        BufferedReader bufferedReader = null;
        HashMap<String, Tree> treeMap = new HashMap<String, Tree>();

        try {
            bufferedReader = new BufferedReader(new FileReader(path));
            String line = bufferedReader.readLine();

            while (line != null) {
                treeMap = createTreeStruct(line.trim(), treeMap);
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

        return treeMap;
    }


    /**
     *  Read the contect of config file and load it into Tree structure.
     *
     * @param nodes
     * @param treeMap
     * @return
     * @throws InvalidInputException
     */
    private HashMap<String, Tree> createTreeStruct(String nodes, HashMap<String, Tree> treeMap)
                                                                    throws InvalidInputException{
        String[] nodes_arr = nodes.split(":");
        Tree temp = null;

        if (nodes_arr.length <= 1)
            throw new InvalidInputException("Invalid input"); // Input is not valid.

        for(int i = 0; i < nodes_arr.length ; i++) {
            String tree_key = nodes_arr[i].toLowerCase();
            String object = "";
            Tree tree_x = null;
            Tree parent = null;

            if (i == 0 && !treeMap.containsKey(tree_key)) {// Create root
                tree_x = new Tree(nodes_arr[i]);
            }
            else {
                if (tree_key.contains("-")) { // Update the object if the location already exists
                    String[] t_obj = nodes_arr[i].split("-");
                    object = t_obj[1].trim();
                    tree_key = t_obj[0].trim().toLowerCase();
                    if (treeMap.containsKey(tree_key)) { // If the location has existed
                        treeMap.get(tree_key).setObject(object);

                        addObjectToMap(object, treeMap.get(tree_key));
                        continue;
                    } else {
                        parent = treeMap.get(nodes_arr[i - 1].toLowerCase());
                        tree_x = new Tree(t_obj[0].trim());
                        tree_x.setObject(object);
                        addObjectToMap(object, tree_x);
                    }
                } else { // Create new Location
                    if (treeMap.containsKey(tree_key)) {
                        continue;
                    }
                    parent = treeMap.get(nodes_arr[i - 1].toLowerCase());
                    tree_x = new Tree(nodes_arr[i]);
                }
                tree_x.setParent(parent);
                parent.addChild(tree_x);
            }
            treeMap.put(tree_key, tree_x);
        }
        return treeMap;
    }


    /**
     * Add the Object to HashMap, which saves objects and their locations*
     *
     * @param object
     * @param location
     */
    private void addObjectToMap(String object, Tree tree) {
        ArrayList<Tree> listOfTrees = new ArrayList<Tree>();
        if (this.objMap.containsKey(object)) {
            listOfTrees = objMap.get(object);
        }
        listOfTrees.add(tree);
        this.objMap.put(object, listOfTrees);
    }


    /**
     * Get the object and their locations
     *
     * @return HashMap<String, ArrayList<Tree>>
     */
    public HashMap<String, ArrayList<Tree>> getObjectMap(){
        return objMap;
    }
}
