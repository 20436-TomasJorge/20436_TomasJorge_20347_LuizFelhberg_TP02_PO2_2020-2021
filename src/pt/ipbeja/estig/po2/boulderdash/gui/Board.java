package pt.ipbeja.estig.po2.boulderdash.gui;


import com.sun.corba.se.impl.orb.ORBConfiguratorImpl;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import pt.ipbeja.estig.po2.boulderdash.model.*;

import java.io.IOException;

/**
 * @author Tomás Jorge
 * @number 20436
 * @version 12/05/2021
 * -------------------
 *
 */

public class Board extends VBox implements View {

    private AbstractPosition buttons[][];

    private Gate gate;
    private Wall  wall;
    private FreeTunnel freeTunnel;
    private OccupiedTunnel occTunnel;

    private final Model model = new Model(this);
    private final ReadFile file = new ReadFile(model.getFilename(), model.getSeparator());

    private final String[][] fileArray2D = file.readFileToStringArray2D(model.getFilename(), model.getSeparator());

    public Board() {
        this.buttons = new AbstractPosition[model.getLines()][model.getCols()];
        GridPane gameBoard = this.createBoard();
        this.getChildren().add(gameBoard);
    }

    private GridPane createBoard() {
        GridPane pane = new GridPane();
        for (int line = 0; line < model.getLines(); line++) {
            for (int col = 0; col < model.getCols(); col++) {
                AbstractPosition objectType = getObjectType(line, col);
                pane.add(objectType, col, line);
                buttons[line][col] = objectType;
            }
        }
        return pane;
    }

    private AbstractPosition getObjectType(int line, int col) {
        if(line == 0 || line == model.getLines() - 1 ||
                col == 0 || col == model.getCols() - 1 ||
                fileArray2D[line + 1][col].equals("W")) {
            wall = new Wall(line, col);
            return wall;
        }
        if(fileArray2D[line + 1][col].equals("F")) {
            freeTunnel = new FreeTunnel(line, col);
            return freeTunnel;
        }
        if(fileArray2D[line + 1][col].equals("G")) {
            gate = new Gate(line, col);
            return gate;
        }
        occTunnel = new OccupiedTunnel(line, col);
        return occTunnel;
    }

    @Override
    public boolean allDiamondsCaught() {
        /*if(getTotalDiamondsInGame ==  caughtdiamonds) {

        }*/
        return false;
    }

    @Override
    public void levelPassed() {
        int passedLevel = model.getLevel();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Level " + passedLevel + "was Passed");
        alert.setContentText("Good Luck on the Next Level");
        alert.showAndWait();

        model.setFilename(passedLevel + 1);
    }

    @Override
    public void gameLost() {

    }

    @Override
    public void onBoardRockfordMove() {

    }
}
