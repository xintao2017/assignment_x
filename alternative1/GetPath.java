package alternative1;

/**
 * The main function of the assginment basic question
 *
 *
 * @author Tao Xin <taoxin.se@gmail.com>
 * @date 2021-08-06
 */

import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Comparator;


public class GetPath {

    private final static String CONFIG_FILE_PATH = "./config_file/assignment_config.txt";

    public static void main(String[] args) {
        if (args.length < 2 || args.length > 2) {
            System.out.println("Wrong number of input arguments, please verify and try again.");
            System.exit(0);
        }

        getPathToObj(args[0].toLowerCase(), args[1].toLowerCase(), CONFIG_FILE_PATH);
    }


    /**
     * Get the Path to the user inputs, and print out the paths.
     *
     * @param lo
     * @param obj
     * @param configFilePath
     */
    private static void getPathToObj(String lo, String obj, String configFilePath) {

        ConfigLoader config = new ConfigLoader();

        HashMap<String, Tree> treeMap = config.loadConfigFromFile(configFilePath);
        HashMap<String, ArrayList<Tree>> objectsMap = config.getObjectMap();

        if (!treeMap.containsKey(lo)) {
            System.out.println("The input is not available! ");
            System.out.println("Available Tree are: " + objectsMap.keySet() + "\nPlease try again!");
            return;
        }

        if (!objectsMap.containsKey(obj)) {
            System.out.println("The input Object (" + obj + ") is not available! Please try again!");
            return;
        }

        Tree startT = treeMap.get(lo);
        ArrayList<Tree> treeWithObject = objectsMap.get(obj);

        if (treeWithObject.contains(startT)) {
            System.out.println("You are in the " + startT.getName() + ". You can get " + obj + " right here.");
            return;
        }

        ArrayList<Tree> tNodes = objectsMap.get(obj);
        ArrayList<Tree> path = new ArrayList<Tree>();
        ArrayList<ArrayList<Tree>> pathsToDest = new ArrayList<ArrayList<Tree>>();
        int i;
        for (Tree t : tNodes) {

            pathsToDest.add(getPath(startT, t));
        }

        if (pathsToDest.size() == 0) {
            System.out.println("Sorry, we cant find the path between the two tree nodes.");
            return;
        }
        else if (pathsToDest.size() == 1) {
            path = pathsToDest.get(0);
        }
        else {
            path = pathsToDest.stream()
                    .min(Comparator.comparingInt(List::size))
                    .orElse(new ArrayList<>());
        }

        System.out.println("You are in the " + startT.getName());
        for (Tree t : path) {
            System.out.print("Go to " + t.getName() + " ");
        }
        System.out.print("get " + obj + ".\n");
    }


    /**
     * Get the path between two nodes
     *
     * @param from
     * @param dest
     * @return
     */
    private static ArrayList<Tree> getPath(Tree from, Tree dest) {
        Tree temp;
        Queue<Tree> qTree = new LinkedList<Tree>();
        ArrayList<Tree> path = new ArrayList<Tree>();
        qTree.add(from);

        while (!qTree.isEmpty()) {
            temp = qTree.poll();
            if(!path.contains(temp)) {
                path.add(temp);
            }

            if (temp.getAllConnected().contains(dest)) {// connection point
                path.add(dest);
                System.out.println("path: - " + path);
                return path;
            }
            else if (temp.getAllConnected().size() == 1) {
                path.remove(temp);
            }
            for (Tree t : temp.getAllConnected()) {
                if (!qTree.contains(t)) {
                    qTree.add(t);
                }
            }
        }
        return null;
    }
}