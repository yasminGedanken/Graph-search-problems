
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Children {

    public List<Node> makeChildren(Node father, String stringGoal){
        List<Node> nodeList = new ArrayList<>();

        int position1I = -8, position1J = -8 , position2I = -10, position2J = -10; //the location of the empty. initial to some number
        boolean pos1= false; //if position 1 already taken
        for (int i = 0; i < father.mat.length; i++) {
            for (int j = 0; j < father.mat[0].length; j++) {
                if(father.mat[i][j].equals("_")){
                    if(!pos1){
                        position1I = i;
                        position1J = j;
                        pos1 = true;
                    } else{ //position 2
                        position2I = i;
                        position2J = j;
                        break; //no need to check more
                    }
                }
            }

        }
            boolean flag = false;
        //if they are 2 and together
        if(((Math.abs(position1I - position2I) == 1) && (position1J == position2J)) ||
                ((Math.abs(position1J - position2J) == 1) && (position1I == position2I)) ) {

            flag=true;
            nodeList = twoEmptyTogether(father,stringGoal , position1I, position1J, position2I, position2J);
        }
        if(flag){ //if their are 2 empty together, we check also separately
            nodeList.addAll(oneEmpty(father, stringGoal, position1I, position1J));
            nodeList.addAll(oneEmpty(father, stringGoal, position2I, position2J));
            } else { //their are not 2 together.
            nodeList = oneEmpty(father,stringGoal, position1I, position1J);
        if (!(position2I == -10 && position2J == -10)) { //2 empty, but not together
            nodeList.addAll(oneEmpty(father,stringGoal, position2I, position2J));
        }

     }
       return nodeList;
    }



    //if the empty block separately ore only one empty block.
    private List<Node> oneEmpty(Node father,String stringGoal, int position1I, int position1J) {
        List<Node> nodeList = new ArrayList<>();

        //block to the left, empty to the right.
        if(!father.lastMove.equals("right")) { //not doing the opposite move!
            if (((position1J + 1) < father.mat[0].length) && !(father.mat[position1I][position1J + 1].equals("_"))) {
                Node left = new Node();
                left.lastMove = "left";
                left.cost = father.cost + 5;
                left.father = father;
                left.path = father.path + "-" + father.mat[position1I][position1J + 1] + "L";
                left.mat = new String[father.mat.length][father.mat[0].length];
                left.iteration= father.iteration+1;
                for (int i = 0; i < left.mat.length; i++)
                    left.mat[i] = Arrays.copyOf(father.mat[i], father.mat[i].length);

                left.mat[position1I][position1J] = father.mat[position1I][position1J + 1];
                left.mat[position1I][position1J + 1] = "_";

                for (int j = 0; j < left.mat.length; j++)
                    left.stringMat += Arrays.toString(left.mat[j]);

                nodeList.add(left);
              //  System.out.println("left mat: "+ left.stringMat);
               // System.out.println("ans mat: "+ stringGoal);
                if(left.stringMat.equals(stringGoal)) return nodeList;
            }
        }


        //block to the up, empty to the down.
        if(!father.lastMove.equals("down")) { //not doing the opposite move!
            if (((position1I + 1) < father.mat.length) && !(father.mat[position1I + 1][position1J].equals("_"))){
                Node up = new Node();
                up.lastMove = "up";
                up.cost = father.cost + 5;
                up.father = father;
                up.path = father.path + "-" + father.mat[position1I + 1][position1J] + "U";
                up.mat = new String[father.mat.length][father.mat[0].length];
                up.iteration= father.iteration+1;
                for (int i = 0; i < up.mat.length; i++)
                    up.mat[i] = Arrays.copyOf(father.mat[i], father.mat[i].length);

                up.mat[position1I][position1J] = father.mat[position1I + 1][position1J];
                up.mat[position1I + 1][position1J] = "_";

                for (int j = 0; j < up.mat.length; j++)
                    up.stringMat += Arrays.toString(up.mat[j]);

                nodeList.add(up);
              //  System.out.println("up mat: "+ up.stringMat);
             //   System.out.println("ans mat: "+ stringGoal);
                if(up.stringMat.equals(stringGoal)) return nodeList;
            }
        }

        //block to the right, empty to the left.
        if(!father.lastMove.equals("left")) { //not doing the opposite move!
            if (((position1J - 1) >= 0) &&  !(father.mat[position1I][position1J - 1].equals("_"))) {
                Node right = new Node();
                right.lastMove = "right";
                right.cost = father.cost + 5;
                right.father = father;
                right.path = father.path + "-" + father.mat[position1I][position1J - 1] + "R";
                right.mat = new String[father.mat.length][father.mat[0].length];
                right.iteration= father.iteration+1;
                for (int i = 0; i < right.mat.length; i++)
                    right.mat[i] = Arrays.copyOf(father.mat[i], father.mat[i].length);

                right.mat[position1I][position1J] = father.mat[position1I][position1J - 1];
                right.mat[position1I][position1J - 1] = "_";

                for (int j = 0; j < right.mat.length; j++)
                    right.stringMat += Arrays.toString(right.mat[j]);

                nodeList.add(right);
               // System.out.println("right mat: "+ right.stringMat);
               // System.out.println("ans mat: "+ stringGoal);
                if(right.stringMat.equals(stringGoal)) return nodeList;
            }
        }

        //block to the down, empty to the up.
        if(!father.lastMove.equals("up")) { //not doing the opposite move!
            if (((position1I - 1) >= 0) && !(father.mat[position1I-1][position1J].equals("_"))) {
                Node down = new Node();
                down.lastMove = "down";
                down.cost = father.cost + 5;
                down.father = father;
                down.path = father.path + "-" + father.mat[position1I - 1][position1J] + "D";
                down.mat = new String[father.mat.length][father.mat[0].length];
                down.iteration= father.iteration+1;

                for (int i = 0; i < down.mat.length; i++)
                    down.mat[i] = Arrays.copyOf(father.mat[i], father.mat[i].length);

                down.mat[position1I][position1J] = father.mat[position1I - 1][position1J];
                down.mat[position1I - 1][position1J] = "_";

                for (int j = 0; j < down.mat.length; j++)
                    down.stringMat += Arrays.toString(down.mat[j]);

                nodeList.add(down);
               // System.out.println("down  mat: "+ down.stringMat);
              //  System.out.println("ans mat: "+ stringGoal);
                if (down.stringMat.equals(stringGoal)) return nodeList;

            }
        }

        return nodeList;
    }


//if the two empty block stick together.
    private List<Node> twoEmptyTogether(Node father, String stringGoal, int position1I, int position1J, int position2I, int position2J) {
        List<Node> nodeList = new ArrayList<>();

        if (position1J == position2J)
            nodeList = twoEmptyPositionI(father, stringGoal, position1I, position2I, position1J);
        else nodeList = twoEmptyPositionJ(father, stringGoal, position1J, position2J, position1I);

        return nodeList;
    }








    private List<Node> twoEmptyPositionJ(Node father, String stringGoal, int position1J, int position2J, int position1I) {
        List<Node> nodeList = new ArrayList<>();

        //block to the up, empty to the down.
        if(!father.lastMove.equals("down")){ //not doing the opposite move!
            if(position1I+1 < father.mat.length ){
                Node up = new Node();
                up.lastMove = "up";
                up.cost = father.cost + 7;
                up.father = father;
                up.mat = new String[father.mat.length][father.mat[0].length];
                up.iteration= father.iteration+1;

                for (int i = 0; i < up.mat.length; i++)
                    up.mat[i] = Arrays.copyOf(father.mat[i], father.mat[i].length);

                    up.path = father.path + "-" + father.mat[position1I + 1][position1J] + "&" + father.mat[position1I + 1][position2J] + "U";
                    up.mat[position1I][position1J] = father.mat[position1I + 1][position1J];
                    up.mat[position1I][position2J] = father.mat[position1I + 1][position2J];
                    up.mat[position1I + 1][position1J] = "_";
                    up.mat[position1I + 1][position2J] = "_";


                for (int j = 0; j < up.mat.length; j++)
                    up.stringMat += Arrays.toString(up.mat[j]);

                nodeList.add(up);
                if (up.stringMat.equals(stringGoal)) return nodeList;

            }
        }

        //block to the down, empty to the up.
        if(!father.lastMove.equals("up")){ //not doing the opposite move!
            if(position1I - 1 >= 0 ){
                Node down = new Node();
                down.lastMove = "down";
                down.cost = father.cost +7;
                down.father = father;
                down.mat = new String[father.mat.length][father.mat[0].length];
                down.iteration= father.iteration+1;
                for (int i = 0; i < down.mat.length; i++)
                    down.mat[i] = Arrays.copyOf(father.mat[i], father.mat[i].length);


                    down.path = father.path + "-" + father.mat[position1I - 1][position1J] + "&" + father.mat[position1I  - 1][position2J] + "D";
                    down.mat[position1I][position1J] = father.mat[position1I - 1][position1J];
                    down.mat[position1I][position2J] = father.mat[position1I - 1][position2J];
                    down.mat[position1I - 1][position1J] = "_";
                    down.mat[position1I - 1][position2J] = "_";

                for (int j = 0; j < down.mat.length; j++)
                    down.stringMat += Arrays.toString(down.mat[j]);

                nodeList.add(down);
                if (down.stringMat.equals(stringGoal)) return nodeList;

            }
        }
        return nodeList;
    }

    private List<Node> twoEmptyPositionI(Node father, String stringGoal, int position1I, int position2I, int position1J) {
        List<Node> nodeList = new ArrayList<>();

        //block to the left, empty to the right.
        if(!father.lastMove.equals("right")){ //not doing the opposite move!
            if(position1J+1 < father.mat[0].length ){
                Node left = new Node();
                left.lastMove = "left";
                left.cost = father.cost + 6;
                left.father = father;
                left.mat = new String[father.mat.length][father.mat[0].length];
                left.iteration= father.iteration+1;

                for (int i = 0; i < left.mat.length; i++)
                    left.mat[i] = Arrays.copyOf(father.mat[i], father.mat[i].length);


                    left.path = father.path + "-" + father.mat[position1I][position1J + 1] + "&" + father.mat[position2I][position1J + 1] + "L";
                    left.mat[position1I][position1J] = father.mat[position1I][position1J + 1];
                    left.mat[position2I][position1J] = father.mat[position2I][position1J + 1];
                    left.mat[position1I][position1J + 1] = "_";
                    left.mat[position2I][position1J + 1] = "_";


                for (int j = 0; j < left.mat.length; j++)
                    left.stringMat += Arrays.toString(left.mat[j]);

                nodeList.add(left);

                if (left.stringMat.equals(stringGoal)) return nodeList;
            }

        }


        //block to the right, empty to the left.
        if(!father.lastMove.equals("left")){ //not doing the opposite move!
            if(position1J-1 >=0 ){
                Node right = new Node();
                right.lastMove = "right";
                right.cost = father.cost + 6;
                right.father = father;
                right.mat = new String[father.mat.length][father.mat[0].length];
                right.iteration= father.iteration+1;

                for (int i = 0; i < right.mat.length; i++)
                    right.mat[i] = Arrays.copyOf(father.mat[i], father.mat[i].length);


                    right.path = father.path + "-" + father.mat[position1I][position1J - 1] + "&" + father.mat[position2I][position1J - 1] + "R";
                    right.mat[position1I][position1J] = father.mat[position1I][position1J - 1];
                    right.mat[position2I][position1J] = father.mat[position2I][position1J - 1];
                    right.mat[position1I][position1J - 1] = "_";
                    right.mat[position2I][position1J - 1] = "_";

                for (int j = 0; j < right.mat.length; j++)
                    right.stringMat += Arrays.toString(right.mat[j]);

                nodeList.add(right);

                if (right.stringMat.equals(stringGoal)) return nodeList;

            }
        }


        return nodeList ;
    }


}
