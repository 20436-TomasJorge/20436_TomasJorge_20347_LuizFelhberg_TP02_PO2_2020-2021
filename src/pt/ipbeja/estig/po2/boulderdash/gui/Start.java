package pt.ipbeja.estig.po2.boulderdash.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import pt.ipbeja.estig.po2.boulderdash.model.Model;

/**
 * @author Tomás Jorge
 * @version 21/05/2021
 * @number 20436
 */

public class Start extends Application {

    private final String ICON_FILE = "/resources/images/icon.png";

    public static void main(String[] args) {
        Application.launch();
    }

    Board gameBoard = new Board();

    public static int countBoards = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene scene = gameBoard.createScene();
        this.setAppIcon(primaryStage, ICON_FILE);
        primaryStage.setTitle("Boulder Dash");
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    private void setAppIcon(Stage stage, String filename) {
        try {
            Image ico = new Image(filename);
            stage.getIcons().add(ico);
        } catch (Exception ex) {
        }
    }

}
