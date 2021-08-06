package alternative1;

/**
 * Implement the Tree Algorithm
 *
 *
 * @author Tao Xin <taoxin.se@gmail.com>
 * @date 2021-07-28
 */

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;


public class Tree {
    private String name;
    private String object;
    private Tree parent = null;
    private List<Tree> children;

    public Tree(String name) {
        this.name = name;
        this.object = null;
        this.children = new ArrayList<Tree>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setParent(Tree parent) {
        this.parent = parent;
    }

    public Tree getParent() {
        return parent;
    }

    public void addChild(Tree t) {
        children.add(t);
    }

    public List<Tree> getChildren() {
        return children;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getObject() {
        return object;
    }


    public Tree searchObj(String obj, Tree root) {
        Queue<Tree> queue = new LinkedList<Tree>();
        List<Tree> visited = new ArrayList<Tree>();
        queue.add(root);
        while(!queue.isEmpty()) {
            Tree ref = queue.poll();
            if (ref.getObject().equals(name))
                return ref;

            queue.add(ref.getChildren());
        }
        return null;
    }

    public Tree search(String name, Tree root) {
        Queue<Tree> queue = new LinkedList<Tree>();
        List<Tree> visited = new ArrayList<Tree>();
        queue.add(root);
        while(!queue.isEmpty()) {
            Tree ref = queue.poll();
            if (ref.getName().equals(name))
                return ref;

            queue.add(ref.getChildren());
        }
        return null;
    }
}