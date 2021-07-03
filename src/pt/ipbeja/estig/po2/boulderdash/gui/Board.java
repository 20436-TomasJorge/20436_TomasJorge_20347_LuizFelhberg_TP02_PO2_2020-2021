package pt.ipbeja.estig.po2.boulderdash.gui;


import com.sun.javafx.scene.traversal.Direction;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.ipbeja.estig.po2.boulderdash.model.position.*;
import pt.ipbeja.estig.po2.boulderdash.model.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tomás Jorge
 * @number 20436
 * @version 12/05/2021
 * -------------------
 *
 */

public class Board extends VBox implements View {


    private Gate gate;
    private Wall wall;
    private FreeTunnel freeTunnel;
    private OccupiedTunnel occTunnel;

    private int posLine = 0;
    private int posCol = 0;


    private final Model model = new Model(this);
    private final ReadFile file = new ReadFile(model.getFilename(), model.getSeparator());

    public AbstractPosition[][] buttons = new AbstractPosition[model.getLines()][model.getCols()];

    public final String[][] fileArray2D = file.readFileToStringArray2D(model.getFilename(), model.getSeparator());

    private static final Map<KeyCode, Direction> directionMap = new HashMap<>();
    static {
        directionMap.put(KeyCode.UP, Direction.UP);
        directionMap.put(KeyCode.DOWN, Direction.DOWN);
        directionMap.put(KeyCode.LEFT, Direction.LEFT);
        directionMap.put(KeyCode.RIGHT, Direction.RIGHT);
    }

    public Board() {
        positionOfMovingObjects();
    }

    public Scene createScene() {
        VBox box = new VBox();
        this.getChildren().add(createBoard());
        Scene scene = new Scene(this);
        this.setKeyHandle(scene);
        return scene;
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

    public void positionOfMovingObjects() {
        int starterLine = Integer.parseInt(fileArray2D[0][0]) + 1;
        for (int line = starterLine; line < fileArray2D.length; line++) {
            for (int col = 0; col < fileArray2D[line].length; col++) {
                if(col % 2 == 0 && fileArray2D[line][col].matches("-?\\d+")) {
                    rockfordStartPosition(line, col);
                }
            }
        }
    }

    /*private void setPositionMovingObjects(int line, int col) {
        rockfordStartPosition(line, col);
        bouldersPositions(line, col);
    }*/

    private void rockfordStartPosition(int line, int col) {
        if(fileArray2D[line][0].equals("J")) {
            if(col % 4 == 0) {
                posCol = col;
            }
            else {
                posLine = col;
            }
            if(posLine != 0 && posCol != 0) {
                Rockford.setLine(Integer.parseInt(fileArray2D[line][posLine]));
                Rockford.setCol(Integer.parseInt(fileArray2D[line][posCol]));
                onBoardRockfordMove(Integer.parseInt(fileArray2D[line][posLine]), Integer.parseInt(fileArray2D[line][posCol]));
                posLine = 0;
                posCol = 0;
            }
        }
    }

    private void bouldersPositions(int line, int col) {
        if(fileArray2D[line][0].equals("J")) {}
    }

    public boolean isPositionFree(int line, int col) {
        boolean position = fileArray2D[line + 1][col].equals("F") ||
                fileArray2D[line + 1][col].equals("O") || fileArray2D[line + 1][col].equals("G");
        if(fileArray2D[line + 1][col].equals("F") ||
                fileArray2D[line + 1][col].equals("O") || fileArray2D[line + 1][col].equals("G")) {
            System.out.println("Free Position. GOOD MOVE");
        }
        if(fileArray2D[line + 1][col].equals("W")) {
            System.out.println("Wall, BAD MOVE");
        }
        return position;
    }

    void setKeyHandle(Scene scnMain) {
        scnMain.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                model.keyPressed(directionMap.get(event.getCode()));
            };
        });
    }

    @Override
    public boolean allDiamondsCaught() {
        /*if(getTotalDiamondsInGame ==  caughtdiamonds) {

        }*/
        return true;
    }

    @Override
    public void levelPassed() {
        if(allDiamondsCaught()) {
            System.out.println(gate.getLine());
            System.out.println(gate.getCol());
            int passedLevel = model.getLevel();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Level " + passedLevel + "was Passed");
            alert.setContentText("Good Luck on the Next Level");
            alert.showAndWait();

            model.setFilename(passedLevel + 1);
        }
    }

    @Override
    public void gameLost() {

    }

    @Override
    public void onBoardRockfordMove(int line , int col){
        if(Start.countBoards == 0) {
            System.out.println("Rockford starting position {" + line + ", " + col + "}");
            System.out.println("==========================\n");
        }
        Start.countBoards++;
    }
}
