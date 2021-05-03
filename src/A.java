import javafx.util.Pair;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.*;

public class A {

    public Node A(Node start, String [][] goalMat){

        int sumOfNodes =1;
        String stringSolution ="";
        for (int i = 0; i < goalMat.length; i++)
            stringSolution += Arrays.toString(goalMat[i]);


        String stringStart ="";
        for (int i = 0; i < start.mat.length; i++)
            stringStart += Arrays.toString(start.mat[i]);

        //Priority Queue of Node by node.cost
        PriorityQueue<Node> pQueue = new PriorityQueue<>(nodeCostComparator);
        Hashtable<String, Node> openList = new Hashtable<String, Node>();
        Hashtable<String, Node> closedList = new Hashtable<String, Node>();

        pQueue.add(start);
        openList.put(stringStart, start);

        while (!pQueue.isEmpty()){
            Node current = pQueue.poll();


            if(stringSolution.equals(current.stringMat)) {
                System.out.println(current.stringMat);
                current.totalNodes=sumOfNodes;
                return current;
            }

            closedList.put(current.stringMat, current);
            List<Node> listNodes = new Children().makeChildren(current, stringSolution);
            sumOfNodes += listNodes.size();

            for( Node element: listNodes){

                if(!openList.containsKey(element.stringMat) && !closedList.containsKey(element.stringMat)) {
                    openList.put(element.stringMat, element);
                    pQueue.add(element);
                }else{
                    if(openList.containsKey(element.stringMat)){
                        if((manhattanDistance(element, goalMat) +element.cost) < (manhattanDistance(openList.get(element.stringMat),goalMat) + openList.get(element.stringMat).cost)){

                            openList.remove(element.stringMat);
                            pQueue.remove(element);

                            openList.put(element.stringMat, element);
                            pQueue.add(element);

                        }
                    }

                }
            }

        }

        return null;
    }


    Comparator<Node> nodeCostComparator = new Comparator<Node>() {
        @Override
        public int compare(Node o1, Node o2) {
            return (o1.cost - o2.cost);
        }
    };


    public int manhattanDistance(Node current, String [][] goalMat) {
        int sum = 0 , empty =0;
        Hashtable<String, Pair<Integer,Integer>> goalHash = new Hashtable<String, Pair<Integer,Integer>>();
        Hashtable<String, Pair<Integer,Integer>> childHash = new Hashtable<String, Pair<Integer,Integer>>();

        for (int i = 0; i < goalMat.length; i++) {
            for (int j = 0; j < goalMat[0].length; j++) {
                if (goalMat[i][j].equals("_")) empty++;
                if(empty > 1) {goalHash.put("__" , new Pair<>(i,j));
                    childHash.put("__" , new Pair<>(i,j));
                }else {
                    goalHash.put(goalMat[i][j], new Pair<>(i, j));
                    childHash.put(current.mat[i][j], new Pair<>(i, j));
                }
            }
        }

        for (Map.Entry<String, Pair<Integer, Integer>> entry : childHash.entrySet()) {
            String stringKey = entry.getKey();
            Pair<Integer, Integer> integerIntegerPair = entry.getValue();
            sum += (Math.abs(goalHash.get(stringKey).getKey() - childHash.get(stringKey).getKey())
                    + Math.abs(goalHash.get(stringKey).getValue() - childHash.get(stringKey).getValue()));
        }


        return sum;
    }


}
