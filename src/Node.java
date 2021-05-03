

import javafx.util.Pair;

public class Node {

    Node father;
    String lastMove;
    String path;
    String mat[][]; //the current matrix
    int cost;
    int totalNodes = 0;
    String key ="";
    boolean imCutoff =false; //for DFID algo.
    boolean fail = false; //for DFID algo.
    boolean out = false; // for IDA algo.

    public Node(){
        this.mat = null;
        this.father = null;
        this.lastMove = "";
        this.path = "";
        this.cost=0;


    }
    public Node(String [][] prevMatrix, Node father, String move, String path){

        this.mat = prevMatrix;
        this.father = father;
        this.lastMove = move;
        this.path=path;
        this.cost=0;


    }







}