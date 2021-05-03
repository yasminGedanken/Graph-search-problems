
import java.io.IOException;
import java.util.*;



public class BFS {


    Queue<Node> queue = new LinkedList<>();
    Hashtable<String ,Node> openList = new Hashtable<String,Node>();
    Hashtable<String ,Node> closedList = new Hashtable<String,Node>();



   public Node BFS(Node start, String  goalMat) throws IOException {
       int numberOfNodes = 0;
       queue.add(start);
        while (!queue.isEmpty()){

            Node currentNode = queue.remove();

            String nodeKey = "";
            for (int i = 0; i < currentNode.mat.length; i++)
               nodeKey += Arrays.toString(currentNode.mat[i]);

            closedList.put(nodeKey, currentNode);
            openList.remove(nodeKey);

            List<Node> listNodes = new Children().makeChildren(currentNode);
           numberOfNodes += listNodes.size();
            for (Node element : listNodes) {

                String tempKey = "";
                for (int i = 0; i < element.mat.length; i++)
                    tempKey += Arrays.toString(element.mat[i]);

                if(!closedList.containsKey(tempKey) && !openList.containsKey(tempKey)){
                    if(tempKey.equals(goalMat)) {
                        element.totalNodes = numberOfNodes;
                        System.out.println("ans!!!!!!!!!!");
                        return element;
                    }
                    else {
                        queue.add(element);
                        openList.put(tempKey, element);
                    }
                }
           }

        }
        return null;
   }


}
