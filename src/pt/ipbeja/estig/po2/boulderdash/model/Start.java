package pt.ipbeja.estig.po2.boulderdash.model;

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

    private final String ICON_FILE = "/resources/images/icon.png";

    //private Model model;

    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Board gameBoard = new Board();

        Scene scene = new Scene(gameBoard);
        this.setAppIcon(primaryStage, ICON_FILE);
        primaryStage.setTitle("Boulder Dash");
        primaryStage.setScene(scene);
        primaryStage.show();

        //this.model.startTimer();
    }

    private void setAppIcon(Stage stage, String filename) {
        try {
            Image ico = new Image(filename);
            stage.getIcons().add(ico);
        } catch (Exception ex) {
        }
    }

}
