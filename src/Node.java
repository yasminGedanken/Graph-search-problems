

import javafx.util.Pair;

public class Node {

    Node father;
    String lastMove;
    String path;
    String stringMat="";
    String mat[][]; //the current matrix
    int cost;
    int totalNodes = 1;
    int iteration=0;
    boolean imCutoff =false; //for DFID algo.
    boolean fail = false; //for DFID algo.
    boolean out = false; // for IDA algo.
    String withPath = ""; // for printing path of open list

    public Node(){
        this.mat = null;
        this.father = null;
        this.lastMove = "";
        this.path = "";
        this.cost=0;


    }
    public Node(String [][] prevMatrix, Node father, String move, String path, String withPath){

        this.mat = prevMatrix;
        this.father = father;
        this.lastMove = move;
        this.path=path;
        this.cost=0;
        this.withPath = withPath;


    }







}