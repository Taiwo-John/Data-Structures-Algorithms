package TheGame;
import UtilityFiles.GameLogic;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Environment extends Application {
    private GameLogic game = new GameLogic();
    @Override
    public void start(Stage primaryStage){

        primaryStage.setTitle("The Road Runner Project || Designed by Taiwo & Maysa");

        Pane root = new Pane();
        GridPane grid_ = new GridPane();
        grid_.relocate(300, 100);
        grid_.setVgap(15); grid_.setHgap(15);
        Scene scene_ = new Scene(root, 1000, 600);
        game.drawMap(grid_, game);
        primaryStage.setScene(scene_);
        primaryStage.show();
        scene_.setOnKeyPressed(e ->{
            if (e.getCode() == KeyCode.RIGHT){
                String altpath = game.getAltImage(game.currentCol, game.currentRow);
                game.moveEast(game.roadrunnerpath, altpath, game.currentCol, game.currentRow, grid_, game);
            }

             if (e.getCode() == KeyCode.LEFT){
                String altpath = game.getAltImage(game.currentCol, game.currentRow);
                game.moveWest(game.roadrunnerpath, altpath, game.currentCol, game.currentRow, grid_, game);
            }

            else if (e.getCode() == KeyCode.UP){
                String altpath = game.getAltImage(game.currentCol, game.currentRow);
                game.moveNorth(game.roadrunnerpath, altpath, game.currentCol, game.currentRow, grid_, game);
            }

            else if (e.getCode() == KeyCode.DOWN){
                String altpath = game.getAltImage(game.currentCol, game.currentRow);
                game.moveSouth(game.roadrunnerpath, altpath, game.currentCol, game.currentRow, grid_, game);
            }

            else if (e.getCode() == KeyCode.Q){
                String altpath = game.getAltImage(game.currentCol, game.currentRow);
                game.moveNorthWest(game.roadrunnerpath, altpath, game.currentCol, game.currentRow, grid_, game);
            }
            
            else if (e.getCode() == KeyCode.A){
                String altpath = game.getAltImage(game.currentCol, game.currentRow);
                game.moveSouthWest(game.roadrunnerpath, altpath, game.currentCol, game.currentRow, grid_, game);
            }

            else if (e.getCode() == KeyCode.W){
                String altpath = game.getAltImage(game.currentCol, game.currentRow);
                game.moveNorthEast(game.roadrunnerpath, altpath, game.currentCol, game.currentRow, grid_, game);
            }

            else if (e.getCode() == KeyCode.S){
                String altpath = game.getAltImage(game.currentCol, game.currentRow);
                game.moveSouthEast(game.roadrunnerpath, altpath, game.currentCol, game.currentRow, grid_, game);
            }

        });
        root.getChildren().addAll(grid_);

        Button startStop = new Button("Start");
        startStop.setPrefSize(130, 35);
        root.getChildren().addAll(startStop);
        startStop.relocate(50, 30);

        Button toggle8Direction = new Button("Enable 8 Directions");
        toggle8Direction.setPrefSize(130, 35);
        root.getChildren().addAll(toggle8Direction);
        toggle8Direction.relocate(50, 80);
        toggle8Direction.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                game.toggleDirection_(toggle8Direction);
            }
        });

        Button undo = new Button("Undo");
        undo.setPrefSize(130, 35);
        root.getChildren().addAll(undo);
        undo.relocate(50, 130);

        undo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                game.undo_(grid_, game);
            }
        });

        Button redo = new Button("Redo");
        redo.setPrefSize(130, 35);
        root.getChildren().addAll(redo);
        redo.relocate(50, 180);

        redo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                game.redo_(grid_, game);
            }
        });

        Button setNewStart = new Button("Set New Start");
        setNewStart.setPrefSize(130, 35);
        root.getChildren().addAll(setNewStart);
        setNewStart.relocate(50, 230);

        Button changeWeights = new Button("Change Weights");
        changeWeights.setPrefSize(130, 35);
        root.getChildren().addAll(changeWeights);
        changeWeights.relocate(50, 280);

        changeWeights.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                game.changeWeights();
            }

        });

        Button loadNewMap = new Button("Load New Map");
        loadNewMap.setPrefSize(130, 35);
        root.getChildren().addAll(loadNewMap);
        loadNewMap.relocate(50, 330);

        loadNewMap.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                game.loadMap_(primaryStage, grid_, game);
            }

        });


        Button solveWithAStar = new Button("Solve with A-Star");
        solveWithAStar.setPrefSize(130, 35);
        root.getChildren().addAll(solveWithAStar);
        solveWithAStar.relocate(50, 380);

        solveWithAStar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Solving wiith Astar Algo");
                game.AStar_(grid_, game);
            }
        });

        Button solveWithDijkstra = new Button("Solve with Dijkstra");
        solveWithDijkstra.setPrefSize(130, 35);
        root.getChildren().addAll(solveWithDijkstra);
        solveWithDijkstra.relocate(50, 430);

        solveWithDijkstra.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                game.Dijkstra(grid_, game);
            }
        });

        Button solveWithDFS = new Button("Solve with DFS");
        solveWithDFS.setPrefSize(130, 35);
        root.getChildren().addAll(solveWithDFS);
        solveWithDFS.relocate(50, 480);

        Button reset = new Button("Reset");
        reset.setPrefSize(130, 35);
        root.getChildren().addAll(reset);
        reset.relocate(50, 530);

        Text scoreHolder = game.scoreholder;
        root.getChildren().addAll(scoreHolder);
        scoreHolder.relocate(60, 575);


        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                game.reset_(grid_, game);
            }

        });

    }

    public static void main(String[] args) {
        launch(args);
    }

}
