package pt.ipbeja.estig.po2.boulderdash.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pt.ipbeja.estig.po2.boulderdash.model.TimeTask;
import pt.ipbeja.estig.po2.boulderdash.model.pieces.Rockford;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


/**
 * @author Tomás Jorge
 * @version 21/05/2021
 * @number 20436
 */

public class Start extends Application {

    public static Text nMoves = new Text("0");
    public static Text nPoints = new Text("0");

    public static int timerValue = 0;
    public static Text textTime = new Text("0");
    public static Timer timer =  new Timer();
    public static TimerTask task =  new TimeTask();

    public static Button btnStart = new Button("Start");
    public static Button btnNextLevel = new Button("Next Level");
    public static int btnClicked = 0;

    public static int levelPassed;
    public static int bestScore = 0;

    public static Stage movesStage = new Stage();
    public static Text textMoves = new Text("Rockford Moves:\n\n");
    public static String setMovesAtScene = textMoves.getText();


    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        setStages(primaryStage);

        btnNextLevel.setOnAction(event -> {
            Board board = new Board();
            primaryStage.setScene(board.createNextLevelScene());
        });
    }

    private void setStages(Stage primaryStage) {
        Board gameBoard = new Board();
        Scene scene = gameBoard.createScene();
        this.setAppIcon(primaryStage);
        primaryStage.setTitle("Boulder Dash");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void setAppIcon(Stage stage) {
        try {
            Image ico = new Image("/resources/images/icon.png");
            stage.getIcons().add(ico);
            movesStage.getIcons().add(ico);
        } catch (Exception ignored) {
        }
    }

}
