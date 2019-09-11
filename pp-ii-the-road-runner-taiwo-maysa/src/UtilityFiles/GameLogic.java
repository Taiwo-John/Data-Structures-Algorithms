package UtilityFiles;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.util.*;
import java.io.*;

public class GameLogic {

    private int n, m;
    private boolean eightdir = false;
    private String [] Images = {"road", "boulder", "pothole", "explosive", "coyote", "tarred", "gold", "road_runner", "goal"
    };

    private String [] alt_Images = {"road_alt", "boulder", "pothole_alt", "explosive_alt", "coyote_alt", "tarred_alt",
            "gold_alt", "start", "goal"};

    private HashMap<Integer, String> ImagePathMap(){
        HashMap<Integer, String> ImageNumberMap = new HashMap<>();
        for(int i = 0; i < Images.length; i++){
            if(i == 7){
                ImageNumberMap.put(8, Images[i]);
                ImageNumberMap.put(9, Images[i + 1]);
                break;
            }
            ImageNumberMap.put(i, Images[i]);
        }
        return ImageNumberMap;
    }

    private HashMap<Integer, String> alt_ImagePathMap(){
        HashMap<Integer, String> alt_ImageNumberMap = new HashMap<>();
        for(int i = 0; i < alt_Images.length; i++){
            if(i == 7){
                alt_ImageNumberMap.put(8, alt_Images[i]);
                alt_ImageNumberMap.put(9, alt_Images[i + 1]);
                break;
            }
            alt_ImageNumberMap.put(i, alt_Images[i]);
        }
        return alt_ImageNumberMap;
    }

    private  HashMap<String, Integer> Boulders = new HashMap<>();

    private static HashMap <String, Boolean> VisitedCells = new HashMap<>();

    private static Stack<Integer> UndoStack = new Stack<>();

    private static Stack<Integer> RedoStack = new Stack<>();

    private int [][] BoulderArray = new int[50][50];
    private  int blockCount = 0;

    private static Stack<Integer> AstarPathStack = new Stack<>();

    private static Stack<Integer> DijkstraPathStack = new Stack<>();

    private int [][] matrix = new int [m][n];

    private int [][] weightMap  = new int [20][20];


    private  static String path = "/C:\\Users\\ALU_Student\\IdeaProjects\\pp-ii-the-road-runner-taiwo-maysa\\src\\Taiwo_Maysa_TestInput3.txt";

    private  String pathname;

    private int [][] ReadFile (){
        try {
            FileReader fileread = new FileReader(path);
            BufferedReader buffRead = new BufferedReader(fileread);

            String sCurrentLine;
            sCurrentLine = buffRead.readLine(); //reading first line
            String[] matrixDescription = sCurrentLine.split(" ");
            n = Integer.parseInt(matrixDescription[0]);
            m = Integer.parseInt(matrixDescription[1]);
            //making the 2d array...
            matrix = new int[n][m];
            int rowCount = 0;

            while ((sCurrentLine = buffRead.readLine()) != null) {

                for (int i = 0; i < sCurrentLine.length(); i++) {
                    matrix[rowCount][i] = Character.getNumericValue(sCurrentLine.charAt(i));

                }
                if(rowCount<n-1){
                    rowCount++;
                }
            }
            buffRead.close();


        } catch (IOException e) {

            System.out.println("File not found");
        }

        return matrix;
    }

    public int getRows(){
        return n;
    }

    public int getCols(){return m;}

    public String getAltImage(int col, int row){
        return prePath + alt_ImagePathMap().get(map_[row][col]) + ".jpg";

    }

    public int startRow = 0; public  int startCol = 0;

    public  int stopRow = 0; public  int stopCol = 0;

    public int currentRow = 0; public  int currentCol = 0;

    public int prevcol, prevrow;


    public String roadrunnerpath;

    private String prePath = "C:\\Users\\ALU_Student\\IdeaProjects\\pp-ii-the-road-runner-taiwo-maysa\\src\\Images\\";

    private int [][] map_  = new int [m][n];


//    public boolean stop;

    private boolean isStop(){
        return this.currentCol == stopCol && this.currentRow == stopRow;
    }

    private boolean isBoulder(int cellRow, int cellCol){
        String i = String.valueOf(cellRow);
        String j = String.valueOf(cellCol);
        return (Boulders.containsKey(i + j));
    }

    private Boolean isVisited(int cellCol, int cellRow){
        String i = String.valueOf(cellCol);
        String j = String.valueOf(cellRow);
        return (VisitedCells.containsKey(i+j));
    }

    public void drawMap(GridPane grid, GameLogic map ){
        map_ = map.ReadFile();
        for(int i = 0; i < getRows(); i++){
            for(int j = 0; j < getCols(); j++){
                if (map.ImagePathMap().containsKey(map_[i][j])){
                    pathname = prePath + map.ImagePathMap().get(map_[i][j]) + ".jpg";
                    DrawImages drawer = new DrawImages();
                    drawer.drawImage(pathname, j, i, grid, map );
                }

                if(map_[i][j] == 1){
                    Boulders.put(String.valueOf(i) + String.valueOf(j), i+j);
                    int [] blocks = {i, j};
                    BoulderArray[blockCount] = blocks;
                    blockCount++;

                }

                if (map_[i][j] == 8){
                    this.startCol = j; this.startRow = i;
//                    UndoStack.push(startRow); UndoStack.push(startCol);
                    this.currentCol = this.startCol; this.currentRow = this.startRow;
                    roadrunnerpath = pathname;
                }

                if(map_[i][j] == 9){
                    this.stopCol = j; this.stopRow = i;
                }
            }
        }

    }

    private int[][] getWeightMap(){
        System.out.println(this.getRows() + " " + this.getCols());
        for(int i =0; i < getRows(); i++){
            for(int j = 0; j < this.getCols(); j++){
                if (this.map_[i][j] == 0){
                    weightMap[i][j] = 20;
                }

                else if(this.map_[i][j] == 2){
                    weightMap[i][j] = 30;
                }

                else if(this.map_[i][j] == 3){
                    weightMap[i][j] = 40;
                }

                else if(this.map_[i][j] == 4){
                    weightMap[i][j] = 60;
                }

                else if(this.map_[i][j] == 5){
                    weightMap[i][j] = 10;
                }

                else if(this.map_[i][j] == 6){
                    weightMap[i][j] = 5;
                }

                else{
                    weightMap[i][j] = 0;
                }
            }
        }

        return weightMap;
    }


    private int score = 0;
    public Text scoreholder = new Text("Your Score: " + score);

    private void newscore(int newScore){
        score= newScore;
        this.scoreholder.setText("Your Score: " + score + "");
    }
    private void plusscore (int scores){
        newscore(score + scores);
    }
    private void minuscore(int scores){
        newscore(score - scores);
    }
    private HashMap<Integer, Integer> mapWeight() {
        HashMap<Integer, Integer> map =  new HashMap<Integer, Integer>();
        map.put(0, -1);
        map.put(1, 0);
        map.put(2, -2);
        map.put(3, -4);
        map.put(4, -8);
        map.put(5, 1);
        map.put(6, 5);
        map.put(8, 0);
        map.put(9, 0);
        return map;
    }

    private int getWeight(int element){
        return mapWeight().get(element);
    }


    public void moveAI(String path, String altPath, int col, int row, int altCol, int altRow,  GridPane grid, GameLogic logic){
        if(!isVisited(col, row)){
            DrawImages imageDrawer = new DrawImages();
            imageDrawer.drawImage(path, col, row, grid, logic);
            imageDrawer.drawImage(altPath, altCol, altRow, grid, logic);
            VisitedCells.put(String.valueOf(col) + String.valueOf(row), true);
        }
    }

    public void moveEast(String path, String altPath, int col, int row, GridPane grid, GameLogic logic){
        if(!this.isStop() && this.currentCol < this.getCols()-1 && !isBoulder(this.currentRow, this.currentCol + 1)
                && !isVisited(this.currentCol +1, this.currentRow)
        ){
            DrawImages imageDrawer = new DrawImages();
            imageDrawer.drawImage(path, col+1, row, grid, logic);
            imageDrawer.drawImage(altPath, col, row, grid, logic);

            this.prevcol = this.currentCol; this.prevrow = this.currentRow;
            if (UndoStack.size() < 6){
                UndoStack.push(this.prevrow); UndoStack.push(this.prevcol);
            }

            else if(UndoStack.size() == 6){
                UndoStack.remove(0); UndoStack.remove(0);
                UndoStack.push(this.currentRow); UndoStack.push(this.currentCol);
            }

            VisitedCells.put(String.valueOf(this.currentCol) + String.valueOf(this.currentRow), true);
            this.currentCol++;

            RedoStack.push(this.currentRow); RedoStack.push(this.currentCol+1);
            plusscore(getWeight(map_[currentRow][currentCol]));
        }
        else {System.out.println(" ");}
    }

    public void moveWest(String path, String altPath, int col, int row, GridPane grid, GameLogic logic){
        if(!this.isStop() && this.currentCol > 0  && !isBoulder(this.currentRow, this.currentCol-1)
                && !isVisited(this.currentCol -1, this.currentRow)
        ){
            DrawImages imageDrawer = new DrawImages();
            imageDrawer.drawImage(path, col-1, row, grid, logic);
            imageDrawer.drawImage(altPath, col, row, grid, logic);
            if (UndoStack.size() < 6){
                UndoStack.push(this.currentRow); UndoStack.push(this.currentCol);
            }

            else if(UndoStack.size() == 6){
                UndoStack.remove(0); UndoStack.remove(0);
                UndoStack.push(this.currentRow); UndoStack.push(this.currentCol);
            }

            VisitedCells.put(String.valueOf(this.currentCol) + String.valueOf(this.currentRow), true);
            this.currentCol--;
            RedoStack.push(this.currentRow); RedoStack.push(this.currentCol-1);
            plusscore(getWeight(map_[currentRow][currentCol]));
        }
        else{
            System.out.println(" ");
        }
    }

    public void moveNorth(String path, String altPath, int col, int row, GridPane grid, GameLogic logic){
        if(!this.isStop() && this.currentRow > 0 && !isBoulder(this.currentRow-1, this.currentCol)
                && !isVisited(this.currentCol, this.currentRow-1)
        ){
            DrawImages imageDrawer = new DrawImages();
            imageDrawer.drawImage(path, col, row - 1, grid, logic);
            imageDrawer.drawImage(altPath, col, row, grid, logic);
            if (UndoStack.size() < 6){
                UndoStack.push(this.currentRow); UndoStack.push(this.currentCol);
            }

            else if(UndoStack.size() == 6){
                UndoStack.remove(0); UndoStack.remove(0);
                UndoStack.push(this.currentRow); UndoStack.push(this.currentCol);
            }

            VisitedCells.put(String.valueOf(this.currentCol) + String.valueOf(this.currentRow), true);
            this.currentRow--;
            RedoStack.push(this.currentRow-1); RedoStack.push(this.currentCol);
            plusscore(getWeight(map_[currentRow][currentCol]));
        }
        System.out.println(" ");

    }

    public void moveSouth(String path, String altPath, int col, int row, GridPane grid, GameLogic logic){
        if(!this.isStop() && this.currentRow < this.getRows() - 1 && !isBoulder(this.currentRow+1, this.currentCol)
                && !isVisited(this.currentCol, this.currentRow+1)
        ){
            DrawImages imageDrawer = new DrawImages();
            imageDrawer.drawImage(path, col, row + 1, grid, logic);
            imageDrawer.drawImage(altPath, col, row, grid, logic);
            if (UndoStack.size() < 6){
                UndoStack.push(this.currentRow); UndoStack.push(this.currentCol);
            }

            else if(UndoStack.size() == 6){
                UndoStack.remove(0); UndoStack.remove(0);
                UndoStack.push(this.currentRow); UndoStack.push(this.currentCol);
            }
            VisitedCells.put(String.valueOf(this.currentCol) + String.valueOf(this.currentRow), true);
            this.currentRow++;
            RedoStack.push(this.currentRow+1); RedoStack.push(this.currentCol);
            plusscore(getWeight(map_[currentRow][currentCol]));
        }
        System.out.println(" ");
    }

    public void moveNorthEast(String path, String altPath, int col, int row, GridPane grid, GameLogic logic){
        if(!isStop() && this.currentRow > 0 && this.currentCol < this.getCols() - 1 && eightdir &&
                !isBoulder(this.currentRow-1, this.currentCol + 1) && !isVisited(this.currentCol +1, this.currentRow-1)){
            DrawImages imageDrawer = new DrawImages();
            imageDrawer.drawImage(path, col+1, row -1, grid, logic);
            imageDrawer.drawImage(altPath, col, row, grid, logic);
            if (UndoStack.size() < 6){
                UndoStack.push(this.currentRow); UndoStack.push(this.currentCol);
            }

            else if(UndoStack.size() == 6){
                UndoStack.remove(0); UndoStack.remove(0);
                UndoStack.push(this.currentRow); UndoStack.push(this.currentCol);
            }
            VisitedCells.put(String.valueOf(this.currentCol) + String.valueOf(this.currentRow), true);
            this.currentCol++; this.currentRow--;
            RedoStack.push(this.currentRow-1); RedoStack.push(this.currentCol+1);
            plusscore(getWeight(map_[currentRow][currentCol]));
        }
        else{
            System.out.println(" ");
        }
    }

    public void moveSouthEast(String path, String altPath, int col, int row, GridPane grid, GameLogic logic){
        if(!isStop() && this.currentRow < this.getRows() -1 & this.currentCol < this.getCols() -1 && eightdir &&
                !isBoulder(this.currentRow +1, this.currentCol + 1) && !isVisited(this.currentCol +1, this.currentRow+1)){
            DrawImages imageDrawer = new DrawImages();
            imageDrawer.drawImage(path, col + 1, row + 1, grid, logic);
            imageDrawer.drawImage(altPath, col, row, grid, logic);
            if (UndoStack.size() < 6){
                UndoStack.push(this.currentRow); UndoStack.push(this.currentCol);
            }

            else if(UndoStack.size() == 6){
                UndoStack.remove(0); UndoStack.remove(0);
                UndoStack.push(this.currentRow); UndoStack.push(this.currentCol);
            }
            VisitedCells.put(String.valueOf(this.currentCol) + String.valueOf(this.currentRow), true);
            this.currentCol++; this.currentRow++;
            RedoStack.push(this.currentRow+1); RedoStack.push(this.currentCol+1);
            plusscore(getWeight(map_[currentRow][currentCol]));
        }
        else{
            System.out.println(" ");
        }
    }

    public void moveNorthWest(String path, String altPath, int col, int row, GridPane grid, GameLogic logic){
        if(!isStop() && this.currentCol> 0 && this.currentRow > 0 && eightdir &&
                !isBoulder(this.currentRow-1, this.currentCol - 1) && !isVisited(this.currentCol -1, this.currentRow-1)){
            DrawImages imageDrawer = new DrawImages();
            imageDrawer.drawImage(path, col-1, row - 1, grid, logic);
            imageDrawer.drawImage(altPath, col, row, grid, logic);
            if (UndoStack.size() < 6){
                UndoStack.push(this.currentRow); UndoStack.push(this.currentCol);
            }

            else if(UndoStack.size() == 6){
                UndoStack.remove(0); UndoStack.remove(0);
                UndoStack.push(this.currentRow); UndoStack.push(this.currentCol);
            }
            VisitedCells.put(String.valueOf(this.currentCol) + String.valueOf(this.currentRow), true);
            this.currentCol--; this.currentRow--;
            RedoStack.push(this.currentRow-1); RedoStack.push(this.currentCol-1);
            plusscore(getWeight(map_[currentRow][currentCol]));
        }
        else {
            System.out.println(" ");
        }

    }

    public void moveSouthWest(String path, String altPath, int col, int row, GridPane grid, GameLogic logic){
        if(!isStop() && this.currentCol > 0 && this.currentRow < this.getRows() -1 && eightdir &&
                !isBoulder(this.currentRow + 1, this.currentCol - 1) && !isVisited(this.currentCol -1, this.currentRow+1)){
            DrawImages imageDrawer = new DrawImages();
            imageDrawer.drawImage(path, col-1, row + 1, grid, logic);
            imageDrawer.drawImage(altPath, col, row, grid, logic);
            if (UndoStack.size() < 6){
                UndoStack.push(this.currentRow); UndoStack.push(this.currentCol);
            }

            else if(UndoStack.size() == 6){
                UndoStack.remove(0); UndoStack.remove(0);
                UndoStack.push(this.currentRow); UndoStack.push(this.currentCol);
            }
            VisitedCells.put(String.valueOf(this.currentCol) + String.valueOf(this.currentRow), true);
            this.currentCol--; this.currentRow++;
            RedoStack.push(this.currentRow+1); RedoStack.push(this.currentCol-1);
            plusscore(getWeight(map_[currentRow][currentCol]));
        }
        else {
            System.out.println(" ");
        }
//the very existence of this entire program is to enable us have a working Road Runner game replica.
    }

    public void toggleDirection_ (Button btn_){
        if (eightdir){eightdir = false;}
        else{eightdir = true;}

        if (btn_.getText().equals("Enable 8 Directions")){
            btn_.setText("Disable 8 Directions");
        }

        else{
            btn_.setText("Enable 8 Directions");
        }

    }

    public void reset_(GridPane grid, GameLogic gameLogic){
        VisitedCells.clear();
        Boulders.clear();
        blockCount= 0;
        UndoStack.clear();
        RedoStack.clear();
        BoulderArray = new int[50][50];
        AstarPathStack.clear();
        newscore(0);
        drawMap(grid, gameLogic);
    }

    public void undo_ (GridPane grid, GameLogic logic){

        if(!UndoStack.isEmpty()){
            this.prevcol = UndoStack.peek(); UndoStack.pop();
            this.prevrow = UndoStack.peek(); UndoStack.pop();
            VisitedCells.remove(String.valueOf(currentCol) + String.valueOf(currentRow));
            DrawImages imageDrawer = new DrawImages();
            String path = prePath + this.ImagePathMap().get(map_[this.currentRow][this.currentCol]) + ".jpg";
            imageDrawer.drawImage(this.roadrunnerpath, prevcol, prevrow, grid, logic);
            imageDrawer.drawImage(path, currentCol, currentRow, grid, logic);
            minuscore(getWeight(map_[currentRow][currentCol]));
            RedoStack.push(this.currentRow); RedoStack.push(this.currentCol);
            currentRow = prevrow; currentCol = prevcol;
            VisitedCells.put(String.valueOf(this.currentCol) + String.valueOf(this.currentRow), true);
        }
    }

    public void redo_ (GridPane grid, GameLogic logic){
        System.out.println(RedoStack);

        if(!RedoStack.isEmpty()){
            this.prevcol = RedoStack.peek(); RedoStack.pop();
            this.prevrow = RedoStack.peek(); RedoStack.pop();
            VisitedCells.remove(String.valueOf(currentCol) + String.valueOf(currentRow));
            DrawImages imageDrawer = new DrawImages();
            String path = getAltImage(this.currentCol, this.currentRow);
            imageDrawer.drawImage(this.roadrunnerpath, prevcol, prevrow, grid, logic);
            imageDrawer.drawImage(path, currentCol, currentRow, grid, logic);
            minuscore(getWeight(map_[currentRow][currentCol]));
            currentRow = prevrow; currentCol = prevcol;
            VisitedCells.put(String.valueOf(this.currentCol) + String.valueOf(this.currentRow), true);
        }
    }

    public void AStar_(GridPane grid, GameLogic logic){

        AStarAlgo aStar = new AStarAlgo(this.getRows(), this.getCols(), this.currentRow, this.currentCol, stopRow, stopCol, this.BoulderArray, eightdir);
        aStar.process(eightdir);
        int [][] pathArray = aStar.displaySolution();
        for(int i =0; i < pathArray.length; i++){
            for(int j = 0; j <2; j++){
                System.out.print(pathArray[i][j]);
                AstarPathStack.push(pathArray[i][j]);
            }
        }
        AstarPathStack.pop(); AstarPathStack.pop();
        while(!AstarPathStack.isEmpty()){
            int toCol = AstarPathStack.peek(); AstarPathStack.pop();
            int toRow = AstarPathStack.peek(); AstarPathStack.pop();
            String alt = getAltImage(this.currentCol, this.currentRow);
            moveAI(roadrunnerpath,alt, toCol, toRow, this.currentCol, this.currentRow, grid, logic );
            this.currentCol = toCol; this.currentRow = toRow;
        }
    }

    public void Dijkstra(GridPane grid, GameLogic logic){
        DijkstraAlgo dijkstra = new DijkstraAlgo(this.getWeightMap(), this.getRows(), this.getCols(), this.currentRow, this.currentCol, this.stopRow, this.stopCol,
                BoulderArray, eightdir);
        dijkstra.process(eightdir);
        int [][] pathArray = dijkstra.displaySolution();

        for(int i =0; i < pathArray.length; i++){
            for(int j = 0; j <2; j++){
                System.out.print(pathArray[i][j]);
                DijkstraPathStack.push(pathArray[i][j]);
            }
        }
        DijkstraPathStack.pop(); DijkstraPathStack.pop();
        while(!DijkstraPathStack.isEmpty()){
            int toCol = DijkstraPathStack.peek(); DijkstraPathStack.pop();
            int toRow = DijkstraPathStack.peek(); DijkstraPathStack.pop();
            String alt = getAltImage(this.currentCol, this.currentRow);
            moveAI(roadrunnerpath,alt, toCol, toRow, this.currentCol, this.currentRow, grid, logic );
            this.currentCol = toCol; this.currentRow = toRow;
        }
    }

    public void loadMap_(Stage primaryStage, GridPane grid, GameLogic gameLogic) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open  File");
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            path = file.getAbsolutePath();
            grid.getChildren().clear();
            reset_(grid, gameLogic);
        }
    }

    public void changeWeights()  {
        // Create the custom dialog.
        Dialog<Pair<Integer, String>> dialog = new Dialog<>();
        dialog.setTitle("Change Weights");
        dialog.setHeaderText("Please set the new weights");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField road = new TextField();
        TextField pothole = new TextField();
        TextField explosives = new TextField();
        TextField coyote = new TextField();
        TextField tarred = new TextField();
        TextField gold = new TextField();


        grid.add(new Label("Road:"), 0, 0);
        grid.add(road, 1, 0);

        grid.add(new Label("Pothole:"), 0, 1);
        grid.add(pothole, 1, 1);

        grid.add(new Label("Explosives:"), 0, 2);
        grid.add(explosives, 1, 2);

        grid.add(new Label("Coyote:"), 0, 3);
        grid.add(coyote, 1, 3);

        grid.add(new Label("Tarred Road:"), 0, 4);
        grid.add(tarred, 1, 4);

        grid.add(new Label("Gold Road:"), 0, 5);
        grid.add(gold, 1, 5);


        // Set the button types.
        ButtonType set = new ButtonType("Set", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(set, ButtonType.CANCEL);

        dialog.getDialogPane().setContent(grid);

        dialog.show();
        dialog.setOnCloseRequest(new EventHandler<DialogEvent>() {
            @Override
            public void handle(DialogEvent event) {
                dialog.close();
            }
            public void handle(ActionEvent event){
                mapWeight().clear();
                mapWeight().put(0, Integer.parseInt(road.getText()));
                mapWeight().put(2, Integer.parseInt(pothole.getText()));
                mapWeight().put(3, Integer.parseInt(explosives.getText()));
                mapWeight().put(4, Integer.parseInt(coyote.getText()));
                mapWeight().put(5, Integer.parseInt(tarred.getText()));
                mapWeight().put(6, Integer.parseInt(gold.getText()));
            }
        });

    }


}
