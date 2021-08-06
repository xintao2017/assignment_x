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

    private Tree createTreeStruct(String nodes, Tree root) {
        String[] nodes_arr = nodes.split(":");
        Tree temp = root;

        if (nodes_arr.length <= 1)
            throw new InvalidInputException("Invalid input"); // Input is not valid.

        for(int i = 0; i < nodes_arr.length; i++) {
            if (nodes_arr[i].contains("-")) { //End of the string, extract object
                String[] obj_arr = nodes_arr[i].split("-");
                Tree x = new Tree(obj_arr[0].trim());
                x.setObject(obj_arr[1]);

            }
            Tree node = new Tree(nodes_arr[i]);
            if (i == 0) { //The root node
                Tree root = new Tree(nodes_arr[i]);
                temp = root;
            }
            else {
                Tree node = new Tree(nodes_arr[i]);
                temp.addChild(node);
                node.setParent(temp);
                temp = node;
            }
        }
        return root;
    }



    /**
     * Get the object and their locations, there can be multi-locations for one object
     *
     * @return HashMap<String, ArrayList<Location>>
     */
    public HashMap<String, ArrayList<Tree>> getObjectMap(){
        return objMap;
    }
}
