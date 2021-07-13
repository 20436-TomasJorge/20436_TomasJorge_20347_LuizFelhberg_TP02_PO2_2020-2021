package pt.ipbeja.estig.po2.boulderdash.gui;

import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.application.Application;

import pt.ipbeja.estig.po2.boulderdash.model.TimeTask;

import java.util.Timer;
import java.util.TimerTask;


/**
 * @author Tomás Jorge, 20436
 * @author Luiz Felhberg, 20347
 * @version 21/05/2021
 */

public class Start extends Application {

    // Global Variables
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
    public static String playerName = "";

    public static Stage mainStage;

    public static Stage movesStage = new Stage();
    public static Text textMoves = new Text("Rockford Moves:\n\nLevel 1:\n");
    public static String setMovesAtScene = textMoves.getText();
    // End of the Global Variables

    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) {

        TextInputDialog inputName = new TextInputDialog();
        inputName.setTitle("Player Name");
        inputName.setHeaderText("What is nickname?");
        inputName.setContentText("* max of 8 characters");
        inputName.showAndWait();

        playerName = inputName.getResult();

        getStage(primaryStage);
        mainStage = primaryStage;
        btnNextLevel.setOnAction(event -> {
            Board board = new Board();
            primaryStage.setScene(board.createNextLevelScene());
        });

    }

    /**
     * get the stage to show at a window
     * -----------------
     * @param primaryStage -> stage to be show
     */
    private void getStage(Stage primaryStage) {
        Board gameBoard = new Board();
        Scene scene = gameBoard.createScene();
        this.setAppIcon(primaryStage);
        primaryStage.setTitle("Boulder Dash");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Set the Icon of the application
     * -----------------
     * @param stage -> application to set the icon
     */
    public void setAppIcon(Stage stage) {
        try {
            Image ico = new Image("/resources/images/icon.png");
            stage.getIcons().add(ico);
            movesStage.getIcons().add(ico);
        } catch (Exception ignored) {
        }
    }

}
