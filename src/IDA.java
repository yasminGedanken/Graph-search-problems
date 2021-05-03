import javafx.util.Pair;

import java.io.*;
import java.util.*;

public class IDA {

    public Node IDA(Node start, String [][] goalMat) {
        System.out.println("@@@@@@@@@@@@@@@@@@");
        String stringSolution ="";
        for (int i = 0; i < goalMat.length; i++)
            stringSolution += Arrays.toString(goalMat[i]);

        int manhattan = manhattanDistance(start, goalMat);

        start.lastMove = "start1";
        System.out.println("start2");
        Stack<Node> stack = new Stack<>();
        Hashtable<String, Node> HT = new Hashtable<String, Node>();

        int minF=0 , sumOfNodes = 0, manhattanTemp=0;

        while( manhattan != Integer.MAX_VALUE) {
            //System.out.println("helloooooo");
            minF = Integer.MAX_VALUE;
            start.out = false;
            start.lastMove = "start again";
            String startMat = "";
            for (int i = 0; i < start.mat.length; i++)
                startMat += Arrays.toString(start.mat[i]);
            stack.push(start);
            HT.put(startMat, start);
            //System.out.println(stringSolution);
            while (!stack.isEmpty()) {
                Node current = stack.pop();
            //    System.out.println("^^^^^^^^^^^^^^^^^");
                String currentMat = "";
                for (int i = 0; i < current.mat.length; i++)
                    currentMat += Arrays.toString(current.mat[i]);

                if (current.out) HT.remove(currentMat);
                else {
                    current.out = true;
                    stack.push(current);

                    List<Node> listNodes = new Children().makeChildren(current);
                    sumOfNodes += listNodes.size();

                    for (Node element : listNodes) {

                        if (manhattan < (manhattanTemp = (manhattanDistance(element, goalMat) + element.cost))) {
                            minF = Math.min(minF, manhattanTemp);
                            continue;
                        }
                        String elementMat = "";
                        for (int i = 0; i < element.mat.length; i++)
                            elementMat += Arrays.toString(element.mat[i]);

                        System.out.println("&&&&&&&&&&&&&&"+elementMat);

                        if (HT.containsKey(elementMat) && HT.get(elementMat).out) {
                            System.out.println("ente to if with elemet : " + elementMat);
                            continue;
                        }

                        if (HT.containsKey(elementMat) && !(HT.get(elementMat).out)) {
                            if (manhattanTemp < (manhattanDistance(HT.get(elementMat), goalMat) + HT.get(elementMat).cost)) {
                                HT.remove(elementMat);
                                stack.remove(elementMat);

                            } else continue;
                        }

                        System.out.println("elem : " + elementMat + "@");
                        System.out.println("solu: " + stringSolution + "@");
                        System.out.println();

                        if (stringSolution.equals(elementMat)) {
                            System.out.println("this is ans ");
                            return element;
                        }

                        stack.push(element);
                        HT.put(elementMat, element);
                        element.key = elementMat;
                       // System.out.println("ans!!!!!!!!!!!!! "+HT.get(elementMat).key);

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
            sum += (Math.abs(goalHash.get(stringKey).getKey() - childHash.get(stringKey).getKey())
                    + Math.abs(goalHash.get(stringKey).getValue() - childHash.get(stringKey).getValue()));
        }


        return sum;
    }


}