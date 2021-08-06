package alternative1;

/**
 * The main function of the assginment basic question
 *
 *
 * @author Tao Xin <taoxin.se@gmail.com>
 * @date 2021-07-28
 */

import common.ConfigLoader;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;


public class GetPath {

    private final static String CONFIG_FILE_PATH = "./config_file/assignment_bonus1_config.txt";

    public static void main(String[] args) {
        if (args.length < 2 || args.length > 2) {
            System.out.println("Wrong number of input arguments, please verify and try again.");
            System.exit(0);
        }

        getPathToObj(args[0].toLowerCase(), args[1].toLowerCase(), CONFIG_FILE_PATH);
    }


    private static void getPathToObj(String lo, String obj, String configFilePath) {

        ConfigLoader config = new ConfigLoader();

        HashMap<String, Location> locationsMap = config.loadConfigFromFile(configFilePath);
        HashMap<String, ArrayList<Location>> objectsMap = config.getObjectMap();

        if (!locationsMap.containsKey(lo)) {
            System.out.println("The input Location is not available! ");
            System.out.println("Available locations are: " + locationsMap.keySet() + "\nPlease try again!");
            return;
        }

        if (!objectsMap.containsKey(obj)) {
            System.out.println("The input Object (" + obj + ") is not available! Please try again!");
            return;
        }

        Location startLocation = locationsMap.get(lo);
        ArrayList<Location> locationsWithObject = objectsMap.get(obj);

        if (locationsWithObject.contains(startLocation)) {
            System.out.println("You are in the " + startLocation.getName() + ". You can get " + obj + " right here.");
            return;
        }

        Location dest = getNode(startLocation, locationsWithObject);
        ArrayList<Location> pathToDest = getPath(startLocation, dest);

        if (pathToDest == null) {
            System.out.println("Sorry, we cant find the path between the two location.");
            return;
        }

        System.out.println("You are in the " + startLocation.getName());
        for (Location p : pathToDest) {
            System.out.print("Go to " + p.getName() + " ");
        }
        System.out.print("get " + obj + ".\n");
    }

    private static ArrayList<Location> getPath(Location from, Location dest) {
        Location temp = from;
        ArrayList<Location> dest_pathToRoot = dest.getPathToRoot();
        ArrayList<Location> path = new ArrayList<Location>();

        while (temp.hasParent()) {
            temp = temp.getParent();
            path.add(temp);

            if (dest_pathToRoot.contains(temp)) {// Find the common ancestor
                if (dest_pathToRoot.removeAll(temp.getPathToRoot())) {
                    path.addAll(dest_pathToRoot);
                }
                return path;
            }
        }
        return null;
    }
}