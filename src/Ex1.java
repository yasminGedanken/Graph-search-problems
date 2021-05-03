import javafx.util.Pair;
import java.io.FileWriter;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files


public class Ex1 {


    public static void main(String[] args) {

        try {
            long start = System.currentTimeMillis();
        File myObj = new File("src\\input.txt");
        Scanner myReader = new Scanner(myObj);
        String algo =myReader.nextLine();
        String time = myReader.nextLine();
        String open = myReader.nextLine();
        String size= myReader.nextLine();


            System.out.println("algo: "+algo+", time: "+time+", open: "+open+", size: "+size);

        String [] sizeMat = size.split("x",2);
        int sizeI = Integer.parseInt(sizeMat[0]);
        int sizeJ = Integer.parseInt(sizeMat[1]);

            String [][] myArray = new String [sizeI][sizeJ];
            String [][] mySolutionArray = new String [sizeI][sizeJ];


            for ( int i=0; i<sizeI; i++) {
                String[] line = myReader.nextLine().trim().split(",");
                myArray[i] = line;
            }

            myReader.nextLine();

            String stringSolution ="";
            for (int i = 0; i < myArray.length; i++) {
                String[] line = myReader.nextLine().trim().split(",");
                stringSolution += Arrays.toString(line);
                mySolutionArray[i] = line;
            }


            Node root = new Node(myArray,null,"","");
            //Node ans = new DFID().DFID(root, stringSolution);
            //Node ans = new BFS().BFS(root, stringSolution);
            Node ans = new IDA().IDA(root, mySolutionArray);

            long end = System.currentTimeMillis();
            NumberFormat formatter = new DecimalFormat("#0.000");
          //  System.out.print("Execution time is " + formatter.format((end - start) / 1000d) + " seconds");

if(!(ans == null)) {
    FileWriter myWriter = new FileWriter("src\\output111.txt");
    //  ans.path = ans.path.substring(1);
    myWriter.write(ans.path);
    myWriter.write("\n");
    myWriter.write("Num: " + ans.totalNodes);
    myWriter.write("\n");
    myWriter.write("Cost: " + ans.cost);
    myWriter.write("\n");
    myWriter.write("sec: " + formatter.format((end - start) / 10000d));
    myWriter.close();
}
else System.out.println("null");

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
