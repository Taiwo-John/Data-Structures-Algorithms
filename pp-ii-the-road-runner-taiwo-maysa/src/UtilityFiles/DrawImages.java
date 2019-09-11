package UtilityFiles;
import javafx.scene.image.*;
import javafx.scene.layout.GridPane;
import java.io.*;

public class DrawImages {
    public void drawImage(String pathname, int col, int row, GridPane grid_, GameLogic logic){
        ImageView imageView = null;
        try{
            Image image = new Image(new FileInputStream(pathname));
            imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight((float)(250/ logic.getRows()));
            imageView.setFitWidth((float)(600/ logic.getCols()));
        }
        catch (FileNotFoundException fnf){System.out.println("File not found");}
        grid_.add(imageView, col, row);
    }
}