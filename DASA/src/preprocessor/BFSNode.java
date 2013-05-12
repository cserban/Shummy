package preprocessor;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Queue;

public class BFSNode
{
    public DependencyNode n;
    boolean visited;
    public ArrayList<BFSNode> neighbours;
    public ArrayList<BFSNode> parents;
    public BFSNode (DependencyNode n, boolean visited){
        this.n = n;
        this.visited = visited;
        this.neighbours = new ArrayList<BFSNode>();
        this.parents = new ArrayList<BFSNode>();
        
        if (n.neighbours != null){
            Enumeration<String> enumKey = n.neighbours.keys();
            while(enumKey.hasMoreElements()) {
                String key = enumKey.nextElement();
                ArrayList<DependencyNode> valuesList = n.neighbours.get(key);
                for (int i = 0; i < valuesList.size(); i++)
                    this.neighbours.add(new BFSNode(valuesList.get(i), false));
            }
        }
    }

    public BFSNode (DependencyNode n){
        this.n = n;
        this.visited = false;
    }

}
 