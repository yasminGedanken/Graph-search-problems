
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.*;


public class A {

    String [][] goalMatManhattan;


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

        pQueue.add(start);
        openList.put(stringStart, start);

        while (!pQueue.isEmpty()){
            Node current = pQueue.poll();


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
                        if((manhattanDistance(element, goalMat) +element.cost) < (manhattanDistance(openList.get(element.stringMat),goalMat) + openList.get(element.stringMat).cost)){

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

        return null;
    }

// (a<b) =-1, (a>b)=1
    Comparator<Node> nodeCostComparator = new Comparator<Node>() {
        @Override
        public int compare(Node o1, Node o2) {
            int costO1 = manhattanDistance(o1 , goalMatManhattan) + o1.cost;
            int costO2 = manhattanDistance(o2 , goalMatManhattan) + o2.cost;
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
        Hashtable<String, Pair> goalHash = new Hashtable<String, Pair>();
        Hashtable<String, Pair> childHash = new Hashtable<String, Pair>();

        for (int i = 0; i < goalMat.length; i++) {
            for (int j = 0; j < goalMat[0].length; j++) {
                if (goalMat[i][j].equals("_")) empty++;
                if(empty > 1) {goalHash.put("__" , new Pair(i,j));
                    childHash.put("__" , new Pair(i,j));
                }else {
                    goalHash.put(goalMat[i][j], new Pair(i, j));
                    childHash.put(current.mat[i][j], new Pair(i, j));
                }
            }
        }
//        if(empty >1){
//        if((Math.abs(goalHash.get("_").getKey() - childHash.get("__").getKey()) == 1 && (goalHash.get("_").getValue() == childHash.get("__").getValue())) ||
//                (Math.abs(goalHash.get("_").getValue() - childHash.get("__").getValue()) == 1 && (goalHash.get("_").getKey() == childHash.get("__").getKey()))){
//
//
//        }} else {
            for (Map.Entry<String, Pair> entry : childHash.entrySet()) {
                String stringKey = entry.getKey();
                Pair integerIntegerPair = entry.getValue();
                sum += (Math.abs(goalHash.get(stringKey).getFirst() - childHash.get(stringKey).getFirst())*5
                        + Math.abs(goalHash.get(stringKey).getSecond() - childHash.get(stringKey).getSecond())*5);
            //}

        }
        return sum;
    }


}
