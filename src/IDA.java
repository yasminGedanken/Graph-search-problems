import javafx.util.Pair;

import java.io.*;
import java.util.*;

public class IDA {

    public Node IDA(Node start, String [][] goalMat) {

        String stringSolution ="";
        for (int i = 0; i < goalMat.length; i++)
            stringSolution += Arrays.toString(goalMat[i]);

        int manhattan = manhattanDistance(start, goalMat);

        Stack<Node> stack = new Stack<>();
        Hashtable<String, Node> HT = new Hashtable<String, Node>();

        int minF=0 , sumOfNodes = 1, manhattanTemp=0;

        while( manhattan != Integer.MAX_VALUE) {

            minF = Integer.MAX_VALUE;
            start.out = false;
            start.lastMove = "start again";

            for (int i = 0; i < start.mat.length; i++)
                start.stringMat += Arrays.toString(start.mat[i]);

            stack.push(start);
            HT.put(start.stringMat, start);

            while (!stack.isEmpty()) {
                Node current = stack.pop();

                if (current.out) HT.remove(current.stringMat);
                else {
                    current.out = true;
                    stack.push(current);

                    List<Node> listNodes = new Children().makeChildren(current,stringSolution);
                    sumOfNodes += listNodes.size();

                    for (Node element : listNodes) {

                        if (manhattan < (manhattanTemp = (manhattanDistance(element, goalMat) + element.cost))) {
                            minF = Math.min(minF, manhattanTemp);
                            continue;
                        }


                        if (HT.containsKey(element.stringMat) && HT.get(element.stringMat).out) continue;


                        if (HT.containsKey(element.stringMat) && !(HT.get(element.stringMat).out)) {
                            if (manhattanTemp < (manhattanDistance(HT.get(element.stringMat), goalMat) + HT.get(element.stringMat).cost)) {
                                HT.remove(element.stringMat);
                                stack.remove(element.stringMat);

                            } else continue;
                        }

                        if (stringSolution.equals(element.stringMat)) {
                            element.totalNodes = sumOfNodes;
                            return element;
                        }

                        stack.push(element);
                        HT.put(element.stringMat, element);

                    }
                }


            }
            manhattan = minF;
        }
    return null;
    }


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
            sum += (Math.abs(goalHash.get(stringKey).getKey() - childHash.get(stringKey).getKey())*5
                    + Math.abs(goalHash.get(stringKey).getValue() - childHash.get(stringKey).getValue())*5);
        }


        return sum;
    }


}