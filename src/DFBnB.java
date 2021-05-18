


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

        Node result = new Node();
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
                        if(start.withPath.equals("with open")) System.out.println(listNodes.get(i).stringMat);
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
        if(result.path.equals("")){
            Node noPath = start;
            noPath.path = "-no path";
            noPath.totalNodes= sumOfNodes;
            return noPath;
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
                System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$");
            }

        }
        System.out.println(sum);
        return sum +new F_LinearConflict().linearConflict(current,goalHash);
    }
}
