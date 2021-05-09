
import java.io.IOException;
import java.util.*;



public class BFS {



   public Node BFS(Node start, String  goalMat) throws IOException {
       Queue<Node> queue = new LinkedList<>();
       Hashtable<String ,Node> openList = new Hashtable<String,Node>();
       Hashtable<String ,Node> closedList = new Hashtable<String,Node>();

       int numberOfNodes = 1;
       queue.add(start);
        while (!queue.isEmpty()){

            Node currentNode = queue.remove();


            closedList.put(currentNode.stringMat, currentNode);
            if(start.withPath.equals("with open")) System.out.println(currentNode.stringMat);
            openList.remove(currentNode.stringMat);

            List<Node> listNodes = new Children().makeChildren(currentNode, goalMat);
                numberOfNodes += listNodes.size();

            for (Node element : listNodes) {


                if(!closedList.containsKey(element.stringMat) && !openList.containsKey(element.stringMat)){
                    if(element.stringMat.equals(goalMat)) {
                        element.totalNodes = numberOfNodes;
                        return element;
                    }
                    else {
                        queue.add(element);
                        openList.put(element.stringMat, element);
                    }
                }
           }

        }
        return null;
   }


}
