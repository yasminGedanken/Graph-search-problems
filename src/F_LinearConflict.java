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

    private Pair<Integer, Integer> colIndex(String key) {
        int i, j;
        i = goal.get(key).first;
        j = goal.get(key).second;
        Pair<Integer, Integer> colTemp = new Pair<>(j, i);
        return colTemp;
    }


    private String[][] transpose() {
        String[][] temp = new String[current.mat[0].length][current.mat.length];
        for (int i = 0; i < current.mat[0].length; i++) {
            for (int j = 0; j < current.mat.length; j++) {
                temp[i][j] = current.mat[j][i];
            }

        }

        return temp;
    }

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

        return sum;
    }


    private int linearSum(int rowIndexOriginal, String [] arr){
        int conflictSum =0;
        Hashtable<String, Pair<Integer, Set<String>>> conflictMapping = new Hashtable<String, Pair<Integer, Set<String>>>();

        for (int j = 0; j < arr.length; j++) {
            if (arr[j].equals("_")) continue;

            int indexX, indexY; //x,y
            if (flagAfterTranspose) {
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

                int indexTX, indexTY; //tx,ty

                if (flagAfterTranspose) {
                    indexTX = goal.get(arr[k]).second;
                    indexTY = goal.get(arr[k]).first;
                } else {
                    indexTX = goal.get(arr[k]).first;
                    indexTY = goal.get(arr[k]).second;
                }

                if (indexX == indexTX && indexY >= indexTY) {

                   Pair<Integer, Set<String>> addToCurrent = conflictMapping.get(arr[j]);
                    int currentDegree = 1;
                    Set<String> currentNrs = new HashSet<>();
                    if( addToCurrent != null) {
                        currentDegree = addToCurrent.first + 1;
                        currentNrs = addToCurrent.second;
                    }

                   currentNrs.add(nextCheck);
                    conflictMapping.put(arr[j], new Pair<Integer, Set<String>>(currentDegree, currentNrs));

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

        while(hasDegree(conflictMapping.values()))
        {

            String popped ="";
            int maxValue = 0;
            for(Map.Entry<String,Pair<Integer, Set<String>>> entry : conflictMapping.entrySet()) {
                if(entry.getValue().getFirst() > maxValue) {
                    maxValue = entry.getValue().first;
                    popped = entry.getKey();
                }
            }



            for(String neighbour : conflictMapping.get(popped).getSecond())

            {
                Pair<Integer, Set<String>> deg_nbrs = conflictMapping.get(neighbour);
                 deg_nbrs.getSecond().remove(popped);
                conflictMapping.put(neighbour, new Pair<Integer, Set<String>>(deg_nbrs.getFirst()-1, deg_nbrs.getSecond()));
                conflictSum++;
            }
            conflictMapping.remove(popped);

        }

        return conflictSum;

    }
    /** while there is atleast one vertex with degree > 0 */
    private boolean hasDegree(Collection<Pair<Integer, Set<String>>> pair)
    {
        Iterator<Pair<Integer, Set<String>>> iter = pair.iterator();
        while(iter.hasNext())
        {
            // check degree of each vertex
            if(iter.next().getFirst() > 0)
                return true;
        }
        return false;
    }

    /** # return the key that has most degree */
    private String maxDegree(HashMap<String, Pair<Integer, Set<String>>> conflict_graph)
    {
        Map.Entry<String, Pair<Integer, Set<String>>> maxEntry = null;
        for (Map.Entry<String, Pair<Integer, Set<String>>> entry : conflict_graph.entrySet())
        {
            if (maxEntry == null || entry.getValue().getFirst() > maxEntry.getValue().getFirst()) {
                maxEntry = entry;
            }
        }
        return maxEntry.getKey();
    }


}


