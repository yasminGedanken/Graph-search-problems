import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class DFID {

    int numberOfNodes =1;
    boolean isCutoff = false;
    String printPath = "";

    public Node DFID(Node start, String goalMat){

        printPath = start.withPath;
        for (int depth = 1; depth <  Integer.MAX_VALUE; depth++) {
            Hashtable<String ,Node> HT = new Hashtable<String,Node>();
            Node result = Limited_DFS(start, goalMat, depth , HT);
            if(!result.imCutoff) {result.totalNodes= numberOfNodes; return result;}
        }
        return null;
    }

    private Node Limited_DFS(Node nodeCu, String goalMat, int limit, Hashtable<String,Node> HT) {

        if(nodeCu.stringMat.equals(goalMat)) return nodeCu;
        else {
            if (limit == 0) {
                nodeCu.imCutoff = true;
                return nodeCu;
            } else {
                HT.put(nodeCu.stringMat, nodeCu);
                isCutoff = false;
                List<Node> listNodes = new Children().makeChildren(nodeCu, goalMat);


                for (Node element : listNodes) {
                    numberOfNodes ++;
                    if (HT.containsKey(element.stringMat)) continue;

                    Node result = Limited_DFS(element, goalMat, limit - 1, HT);
                    if (result.imCutoff) isCutoff = true;
                    else if (!result.fail) return result;
                }

                if(printPath.equals("with open")) System.out.println(nodeCu.stringMat);
                HT.remove(nodeCu.stringMat);
                if (isCutoff)  nodeCu.imCutoff = true;
                else nodeCu.fail = true;

                return nodeCu;

            }
        }
    }

}
