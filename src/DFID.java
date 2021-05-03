import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class DFID {

    int numberOfNodes =0;
    boolean isCutoff = false;

    public Node DFID(Node start, String goalMat){


        for (int depth = 1; depth <  Integer.MAX_VALUE; depth++) {
            Hashtable<String ,Node> HT = new Hashtable<String,Node>();
            Node result = Limited_DFS(start, goalMat, depth , HT);
            if(!result.imCutoff) {result.totalNodes= numberOfNodes; return result;}
        }
        return null;
    }

    private Node Limited_DFS(Node nodeCu, String goalMat, int limit, Hashtable<String,Node> HT) {

        String currentN = "";
        for (int i = 0; i < nodeCu.mat.length; i++)
            currentN += Arrays.toString(nodeCu.mat[i]);
        System.out.println(currentN);
        if(currentN.equals(goalMat)){ return nodeCu;}
        else {
            if (limit == 0) {
                nodeCu.imCutoff = true;
                return nodeCu;
            } else {
                HT.put(currentN, nodeCu);
                isCutoff = false;
                List<Node> listNodes = new Children().makeChildren(nodeCu);
                numberOfNodes += listNodes.size();
                System.out.println("num: "+ listNodes.size());
                for (Node element : listNodes) {
                    String gNode = "";
                    for (int j = 0; j < element.mat.length; j++)
                        gNode += Arrays.toString(element.mat[j]);
                    System.out.println("%%%%%%%%%%%%%%%% "+ gNode);
                    if (HT.containsKey(gNode)) continue;
                    System.out.println("********** " + limit );
                    Node result = Limited_DFS(element, goalMat, limit - 1, HT);
                    if (result.imCutoff) isCutoff = true;
                    else if (!result.fail) return result;
                }
                HT.remove(currentN);
                if (isCutoff)  nodeCu.imCutoff = true;
                else nodeCu.fail = true;

                return nodeCu;

            }
        }
    }

}
