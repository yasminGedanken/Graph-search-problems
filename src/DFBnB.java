import javafx.util.Pair;

import java.util.*;

public class DFBnB {
    String [][] goalMatManhattan;

    public Node DFBnB(Node start, String [][] goalMat){

        goalMatManhattan = goalMat;

        Stack<Node> stack = new Stack<>();
        Hashtable<String, Node> HT = new Hashtable<String, Node>();

        String stringSolution ="";
        for (int i = 0; i < goalMat.length; i++)
            stringSolution += Arrays.toString(goalMat[i]);

        stack.push(start);
        HT.put(start.stringMat, start);

        Node result =null;
        int t =Integer.MAX_VALUE , sumOfNodes =1 , manhattan =0;

        while(!stack.isEmpty()){
            Node current = stack.pop();
        if(current.out) HT.remove(current.stringMat);
        else{
            current.out = true;
            stack.push(current);
            List<Node> listNodes = new Children().makeChildren(current,stringSolution);
            sumOfNodes += listNodes.size();

            listNodes.sort(nodeCostComparator);

            for (int i = 0; i < listNodes.size(); i++) {
                if((manhattan = (manhattanDistance(listNodes.get(i), goalMat)+listNodes.get(i).cost)) >= t){
                    for (int j = listNodes.size()-1 ; j >= i  ; j--)
                        listNodes.remove(j);
                }
                else if(HT.containsKey(listNodes.get(i).stringMat) && listNodes.get(i).out)
                    listNodes.remove(i);
                else if(HT.containsKey(listNodes.get(i).stringMat) && !listNodes.get(i).out){
                    if( manhattan >= (manhattanDistance(HT.get(listNodes.get(i).stringMat), goalMat)+ HT.get(listNodes.get(i).stringMat).cost))
                        listNodes.remove(i);
                    else{
                        HT.remove(listNodes.get(i).stringMat);
                        stack.remove(listNodes.get(i));
                    }

                } else if(listNodes.get(i).stringMat.equals(stringSolution)){
                    t = manhattan;
                    result = listNodes.get(i);
                    for (int j = listNodes.size()-1 ; j >= i  ; j--)
                        listNodes.remove(j);
                }
            }
            for (int j = listNodes.size()-1 ; j >= 0  ; j--){
                HT.put(listNodes.get(j).stringMat, listNodes.get(j));
                stack.push(listNodes.get(j));
            }
        }

        }

        result.totalNodes = sumOfNodes;
        return result;
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
//        if(empty >1){
//        if((Math.abs(goalHash.get("_").getKey() - childHash.get("__").getKey()) == 1 && (goalHash.get("_").getValue() == childHash.get("__").getValue())) ||
//                (Math.abs(goalHash.get("_").getValue() - childHash.get("__").getValue()) == 1 && (goalHash.get("_").getKey() == childHash.get("__").getKey()))){
//
//
//        }} else {
        for (Map.Entry<String, Pair<Integer, Integer>> entry : childHash.entrySet()) {
            String stringKey = entry.getKey();
            Pair<Integer, Integer> integerIntegerPair = entry.getValue();
            sum += (Math.abs(goalHash.get(stringKey).getKey() - childHash.get(stringKey).getKey())*5
                    + Math.abs(goalHash.get(stringKey).getValue() - childHash.get(stringKey).getValue())*5);
            //}

        }
        return sum;
    }

}