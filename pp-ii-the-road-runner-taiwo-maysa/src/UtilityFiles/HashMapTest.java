package UtilityFiles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

public class HashMapTest {

//    static  Scanner scannerReader;
//    static  int mColumns;
//    static int [][] matrixDescription;
//    static int [] start;
//    static  int current;

    public static int n;
    public static int m;

    public  static  int [][] matrix;


    public static int[][] readFile() {
        try {

            String path = "/C:\\Users\\ALU_Student\\IdeaProjects\\pp-ii-the-road-runner-taiwo-maysa\\src\\Taiwo_Maysa_TestInput1.txt";
           FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);

            String sCurrentLine;
            sCurrentLine = br.readLine(); //reading first line
            String[] matrixDescription = sCurrentLine.split(" ");
            n = Integer.parseInt(matrixDescription[0]);
            m = Integer.parseInt(matrixDescription[1]);
            //making the 2d array...
            matrix = new int[n][m];
            boolean[][]visited = new boolean[n][m];
            int rowCount = 0;

            while ((sCurrentLine = br.readLine()) != null) {

                for (int i = 0; i < sCurrentLine.length(); i++) {
                    matrix[rowCount][i] = Character.getNumericValue(sCurrentLine.charAt(i));
                    visited[rowCount][i] = false;

                }
                if(rowCount<n-1){
                    rowCount++; //incrementing rowcount
                }
            }
            br.close();// closing the file


        } catch (IOException e) {

            System.out.println("File not found");
        }

        return matrix;

    }

    public static void main(String[] args) {
        HashMapTest test = new HashMapTest();
        System.out.println(Arrays.deepToString(test.readFile()));
//        System.out.println(Arrays.deepToString(matrixDescription));
    }

//    private int n, m;
//
//    private int [][] matrix;
//    private int [][] ReadFile (){
//        try{
//            FileReader reader = new FileReader("/C:\\Users\\ALU_Student\\IdeaProjects\\pp-ii-the-road-runner-taiwo-maysa\\src\\Taiwo_Maysa_TestInput3.txt");
//            BufferedReader buffRead_ = new BufferedReader(reader);
//
//            String dim = buffRead_.readLine();
////            char no_of_rows = dim.charAt(0);
//            char no_of_cols = dim.charAt(2);
//            char no_of_rows = dim.charAt(0);
//
////            Scanner scann = new Scanner();
//
//            n = Character.getNumericValue(no_of_rows);
//            m = Character.getNumericValue(no_of_cols);
//
//
//            int [][] big = new int [n][m];
//            for(int i = 0; i < n; i++){
//                String nextLine_ = buffRead_.readLine();
//
//                int [] cols = new int[m];
//                for(int j = 0; j < m; j++){
//                    int elements = Character.getNumericValue(nextLine_.charAt(j));
//                    cols[j] = elements;
//                }
//
//                big[i] = cols;
//            }
//            matrix = big;
//            System.out.println(Arrays.deepToString(matrix));
//        }
//        catch(Exception e){
//            System.out.println("File not found");
//        }
//        return matrix;
//    }
//
//    public static void main(String[] args) {
//        HashMapTest test = new HashMapTest();
//
//        System.out.println(Arrays.deepToString(test.ReadFile()));
//    }
}

//    public static void main(String[] args) {\


//        int [] cellBlocks = {0, 0};
//
//        int [][] block  = {cellBlocks};
//        AStarAlgo test = new AStarAlgo(4, 4, 3, 1, 0, 3, block);
//
////        test.display();
////        test.process();
////
////        int [][] finals = test.displaySolution( 4 );
////        System.out.println(Arrays.deepToString(finals));
//
//        int [][] maybe = {{1,1,1,1}, {1,1,1,1}, {1,1,1,1}, {0,0,0,0} };
//
//
//
//        int [][] twodtest = new int [4][4];
//
//        for(int i =0; i< 4; i++){
//            for(int j = 0; j<4; j++){
//
//                if(maybe[i][j]== 1){
//                    twodtest[i][j] = 3;
//                }
//                else{
//                    twodtest[i][j] = 4;
//                }
//            }
//        }
//
//        System.out.println(Arrays.deepToString(twodtest));
//    }

//    public static void main(String[] args) {
//
//        int count = 1;
//
//        int [] ee = new int [100];
//
//        ee[count] = 5;
//
//        try
//        {
//            String fileContent = "Hello Learner !! Welcome to howtodoinjava.com.";
//
//            BufferedWriter writer = new BufferedWriter(new FileWriter("/C:\\Users\\ALU_Student\\IdeaProjects\\pp-ii-the-road-runner-taiwo-maysa\\src\\out.txt"));
//            writer.write(ee[0] + " " + ee[1]);
//            writer.close();
//        }
//
//        catch (IOException fnf){
//            System.out.println("File not found");
//        }
//    }
//}
