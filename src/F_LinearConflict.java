import java.lang.reflect.Array;
import java.util.*;

public class F_LinearConflict {

    Node current = null;
    Hashtable<String, Pair<Integer, Integer>> goal;
    String [][] transpose;
    boolean flagAfterTranspose = false;

    private Pair<Integer, Integer> rowIndex(String key) {
        Pair<Integer, Integer> rowTemp = goal.get(key);
        return rowTemp;
    }

    //this function return pair of indexes in the goal
    private Pair<Integer, Integer> colIndex(String key) {
        int i, j;
        i = goal.get(key).first;
        j = goal.get(key).second;
        Pair<Integer, Integer> colTemp = new Pair<>(j, i);
        return colTemp;
    }

   //transpose the matrix of the current node
    private String[][] transpose() {
        String[][] temp = new String[current.mat[0].length][current.mat.length];
        for (int i = 0; i < current.mat[0].length; i++) {
            for (int j = 0; j < current.mat.length; j++) {
                temp[i][j] = current.mat[j][i];
            }

        }

        return temp;
    }

    //this function get node for the current matrix and hash table
    //with the indexes of the goal matrix.
    //its return the sum*2 of the comflicts
    public int linearConflict(Node cur, Hashtable<String, Pair<Integer, Integer>> goal) {
        this.current = cur;
        this.goal = goal;

        int sum =0;

        int row[] = new int[current.mat.length];
        int col[] = new int[current.mat[0].length];

        for (int j = 0; j < current.mat.length; j++) {
            row[j] = linearSum(j, (current.mat[j]));
            sum += row[j];
        }

        this.transpose = transpose();

        flagAfterTranspose = true;
        for (int j = 0; j < current.mat.length; j++) {
            col[j] = linearSum(j, (this.transpose[j]));
            sum += col[j];
        }

        return sum*2;
    }


    //this function get a number of row that we check in the current matrix- owIndexOriginal.
    //and get arr- the row of the that we now check.
    private int linearSum(int rowIndexOriginal, String [] arr){
        int conflictSum =0;

        //conflictMapping- hash that hold for key- string of the number.
        //          value- Pair<Integer, Set<String>> that int to count how much nbrs this key have , and set to hold
        //                 a list of his nbrs.
        Hashtable<String, Pair<Integer, Set<String>>> conflictMapping = new Hashtable<String, Pair<Integer, Set<String>>>();

        for (int j = 0; j < arr.length; j++) {
            if (arr[j].equals("_")) continue; //empty block

            int indexX, indexY;
            if (flagAfterTranspose) { //if we already did transpose to the matrix or not- to replace the indexes.
                indexX = goal.get(arr[j]).second;
                indexY = goal.get(arr[j]).first;
            } else {
                indexX = goal.get(arr[j]).first;
                indexY = goal.get(arr[j]).second;
            }

            if (rowIndexOriginal != indexX) continue; //not in the same row

            for (int k = j + 1; k < arr.length; k++) {
                String nextCheck = arr[k];
                if (nextCheck.equals("_")) continue;

                int indexTX, indexTY;

                if (flagAfterTranspose) {
                    indexTX = goal.get(arr[k]).second;
                    indexTY = goal.get(arr[k]).first;
                } else {
                    indexTX = goal.get(arr[k]).first;
                    indexTY = goal.get(arr[k]).second;
                }

                if (indexX == indexTX && indexY >= indexTY) { //in the same row but the numbers are switch places.

                    //we create/update the data in the conflictMapping for a key and for his nbrs.
                   Pair<Integer, Set<String>> addToCurrent = conflictMapping.get(arr[j]);
                    int currentDegree = 1;
                    Set<String> currentNrs = new HashSet<>();
                    if( addToCurrent != null) {
                        currentDegree = addToCurrent.first + 1;
                        currentNrs = addToCurrent.second;
                    }

                   currentNrs.add(nextCheck);
                    conflictMapping.put(arr[j], new Pair<Integer, Set<String>>(currentDegree, currentNrs));

                    //we create/update the data in the conflictMapping for a nbrs of the current key.
                   Pair<Integer, Set<String>> ne_current = conflictMapping.get(nextCheck);
                    int nbsDegree = 1;
                    Set<String> nbsArr = new HashSet<>();
                    if( ne_current != null) {
                        nbsDegree = ne_current.first + 1;
                        nbsArr = ne_current.second;
                    }

                    nbsArr.add(arr[j]);
                    conflictMapping.put(nextCheck, new Pair<Integer, Set<String>>(nbsDegree, nbsArr));

                }
            }
        }

        //now we count all the conflict that we have.
        //we start with the key that have the most conflicts to the lowest.
        while(hasDegree(conflictMapping.values()))
        {

            //get the key with the most conflicts
            String popped ="";
            int maxValue = 0;
            for(Map.Entry<String,Pair<Integer, Set<String>>> entry : conflictMapping.entrySet()) {
                if(entry.getValue().getFirst() > maxValue) {
                    maxValue = entry.getValue().first;
                    popped = entry.getKey();
                }
            }



            //we go over all the nbrs of the key, and remove from them the current key.
            for(String neighbour : conflictMapping.get(popped).getSecond())

            {
                Pair<Integer, Set<String>> deg_nbrs = conflictMapping.get(neighbour);
                 deg_nbrs.getSecond().remove(popped);
                conflictMapping.put(neighbour, new Pair<Integer, Set<String>>(deg_nbrs.getFirst()-1, deg_nbrs.getSecond()));

            }
            conflictSum++;
            conflictMapping.remove(popped);

        }

        return conflictSum;

    }
    // check if there is vertex with degree > 0
    private boolean hasDegree(Collection<Pair<Integer, Set<String>>> collec)
    {
        for(Pair<Integer, Set<String>> empty : collec)
        {
            // check degree of each vertex
            if(empty.getFirst() > 0)
                return true;
        }
        return false;
    }



}


