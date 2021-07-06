package pt.ipbeja.estig.po2.boulderdash.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author Tomás Jorge
 * @version 21/05/2021
 * @number 20436
 */

public class Start extends Application {

    public Board gameBoard = new Board();

    public static int countBoards = 0;
    public static int totalDiamonds = 0;
    public static int caughtDiamonds = 0;

    public static Stage stage;

    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
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
        } catch (Exception ex) {
        }
    }

}
