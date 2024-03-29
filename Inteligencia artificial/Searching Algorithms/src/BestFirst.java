
import java.util.Vector;
/**
 * Created by Gerardo Ayala on 3/16/16.
 */
public abstract class BestFirst extends Search
{
    private static Result bestFirst(Node node, Node goal)
    {
        Node nextChild;
        int i;
        Vector<Node> route;
        //--------------------
        if (!found)
        {
            node.expand();
            memory.add(node.getState());
            node.sortChildren();
            if (node.getChildren() != null)
            {
                i = 0;
                while ( (i < node.getChildren().size()) &&(!found))
                {
                    nextChild = node.getChildren().get(i);
                    if ((nodeIsFinalState(nextChild, goal)) ||
                            (nextChild.heuristicValue==0))
                    {
                        found = true;
                        foundNode = nextChild;
                    }//end if
                    else
                    if(!memory.contains(nextChild.getState()))
                        // recursive call
                        result = bestFirst(nextChild, goal);
                    //end if
                    //end else
                    i = i + 1;
                }//end while
            }//end if
            if (found)
            {
                result.setFound(true);
                route = foundNode.getRoute();
                result.setPlan(route);
            }//end if
            else
            {
                result.setFound(false);
                result.setPlan(null);
            }//end else
        }//end if
        return result;
    }//end depthFirst




    public static Result search(Situation initialState, Situation finalState)
    {
        Vector<Node> route;
        Node root;
        Node goal;
        //
        root = new Node();
        root.setState(initialState);
        goal = new Node();
        goal.setState(finalState);
        memory = new Vector<Situation>();
        found = false;
        result = new Result();
        // if the initial state is not null
        if (root != null)
        {
            // if the initial state is not the final state
            if (!nodeIsFinalState(root, goal))
            {
                result = bestFirst(root, goal);
            }//end if
            else
            {
                result.setFound(true);
                route = new Vector<Node>();
                route.add(root);
                result.setPlan(route);
                found = true;
            }//end else
        }//end if
        System.out.print("MOVES:");
        System.out.println(result.getPlan().size()-1);
        return result;
    }//end search
}//end class


