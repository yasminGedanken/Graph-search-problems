
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.*;


public class A {

    String [][] goalMatManhattan;
    boolean ioException = false;


    public Node A(Node start, String [][] goalMat){
        goalMatManhattan = goalMat;

        int sumOfNodes =1;
        String stringSolution ="";
        for (int i = 0; i < goalMat.length; i++)
            stringSolution += Arrays.toString(goalMat[i]);


        String stringStart ="";
        for (int i = 0; i < start.mat.length; i++)
            stringStart += Arrays.toString(start.mat[i]);

        //Priority Queue of Node by manhattan distance+ cost +made first
        PriorityQueue<Node> pQueue = new PriorityQueue<>(nodeCostComparator);
        Hashtable<String, Node> openList = new Hashtable<String, Node>();
        Hashtable<String, Node> closedList = new Hashtable<String, Node>();

        if(ioException){
            Node noPath = start;
            noPath.path = "-no path";
            noPath.totalNodes= 1;
            return noPath;
        }

        pQueue.add(start);
      //  openList.put(stringStart, start);

        while (!pQueue.isEmpty()){
            Node current = pQueue.poll();
          //  openList.remove(current.stringMat,current);

            if(stringSolution.equals(current.stringMat)) {
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
                        if(nodeCostComparator.compare(element, openList.get(element.stringMat)) == -1 ){

                            if(start.withPath.equals("with open")) System.out.println(element.stringMat);
                            openList.remove(element.stringMat);
                            pQueue.remove(element);

                            openList.put(element.stringMat, element);
                            pQueue.add(element);

                        }
                    }

                }
            }

        }

        Node noPath = start;
        noPath.path = "-no path";
        noPath.totalNodes= sumOfNodes;
        return noPath;
    }

//  (a<b) =-1, (a>b)=1
    Comparator<Node> nodeCostComparator = new Comparator<Node>() {
        @Override
        public int compare(Node o1, Node o2) {
            int costO1 = manhattanDistance(o1 , goalMatManhattan) + o1.cost;
            int costO2 = manhattanDistance(o2 , goalMatManhattan) + o2.cost;
            if(costO1 == -1 || costO2 == -1) ioException = true;
        if(costO1 < costO2) return -1;
        if(costO1 > costO2) return 1;
        if(costO1 == costO2) {
            if(o1.iteration < o2.iteration) return -1;
            if(o1.iteration > o2.iteration) return 1;
            if(o1.iteration == o2.iteration) {
                if(o1.lastMove.equals("left")) return -1;
                if(o2.lastMove.equals("left")) return 1;
                if(o1.lastMove.equals("up")) return -1;
                if(o2.lastMove.equals("up")) return 1;
                if(o1.lastMove.equals("right")) return -1;
                if(o2.lastMove.equals("right")) return 1;
                if(o1.lastMove.equals("down")) return -1;
                if(o2.lastMove.equals("down")) return 1;
            }
        }
            return 0;
        }
    };


    public int manhattanDistance(Node current, String [][] goalMat) {
        int sum = 0 , empty =0;
        Hashtable<String, Pair<Integer,Integer>> goalHash = new Hashtable<String, Pair<Integer,Integer>>();
        Hashtable<String, Pair<Integer,Integer>> childHash = new Hashtable<String, Pair<Integer,Integer>>();

        for (int i = 0; i < goalMat.length; i++) {
            for (int j = 0; j < goalMat[0].length; j++) {
                if (goalMat[i][j].equals("_") && !current.mat[i][j].equals("_")) {
                    childHash.put(current.mat[i][j], new Pair<Integer, Integer>(i, j));
                } else{
                    if (current.mat[i][j].equals("_")) {
                        if (empty == 0) {
                            empty++;
                        } else {
                            empty++;
                        }
                        if (!goalMat[i][j].equals("_"))
                            goalHash.put(goalMat[i][j], new Pair<Integer, Integer>(i, j));
                    } else if ((!goalMat[i][j].equals("_")) && (!current.mat[i][j].equals("_"))) {
                        goalHash.put(goalMat[i][j], new Pair<Integer, Integer>(i, j));
                        childHash.put(current.mat[i][j], new Pair<Integer, Integer>(i, j));
                    }
                }
            }}

        for (Map.Entry<String, Pair<Integer,Integer>> entry : childHash.entrySet()) {
            String stringKey = entry.getKey();

            if (empty > 1) {
                sum += (Math.abs(goalHash.get(stringKey).getFirst() - childHash.get(stringKey).getFirst()) * 3.5
                        + Math.abs(goalHash.get(stringKey).getSecond() - childHash.get(stringKey).getSecond()) * 3);
            }else{
                sum += (Math.abs(goalHash.get(stringKey).getFirst() - childHash.get(stringKey).getFirst()) * 5
                        + Math.abs(goalHash.get(stringKey).getSecond() - childHash.get(stringKey).getSecond()) * 5);
            }

        }

        return sum +new F_LinearConflict().linearConflict(current,goalHash);
    }


}
