package pt.ipbeja.estig.po2.boulderdash.gui;


import com.sun.javafx.scene.traversal.Direction;
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import pt.ipbeja.estig.po2.boulderdash.model.pieces.*;
import pt.ipbeja.estig.po2.boulderdash.model.*;

import javax.swing.text.html.ImageView;
import java.util.HashMap;
import java.util.Map;

import static pt.ipbeja.estig.po2.boulderdash.gui.Start.caughtDiamonds;
import static pt.ipbeja.estig.po2.boulderdash.gui.Start.totalDiamonds;
import static pt.ipbeja.estig.po2.boulderdash.model.Model.*;

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

    public final Model model = new Model(this);
    private final ReadFile file = new ReadFile(model.getFilename(), model.getSeparator());

    public AbstractPosition[][] buttons = new AbstractPosition[model.getLines()][model.getCols()];

    private String[][] fileArray2D = file.readFileToStringArray2D(model.getFilename(), model.getSeparator());

    private static final Map<KeyCode, Direction> directionMap = new HashMap<>();
    static {
        directionMap.put(KeyCode.UP, Direction.UP);
        directionMap.put(KeyCode.DOWN, Direction.DOWN);
        directionMap.put(KeyCode.LEFT, Direction.LEFT);
        directionMap.put(KeyCode.RIGHT, Direction.RIGHT);
    }

    public Board() {
        positionOfRockford();
    }

    public Scene createScene() {
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
        ImageOfMovingObjects();
        buttons[Rockford.getLine()][Rockford.getCol()].setImage(Rockford.getRockfordImage());
        return pane;
    }

    private AbstractPosition getObjectType(int line, int col) {
        if(line == 0 || line == model.getLines() - 1 || col == 0 || col == model.getCols() - 1
                || fileArray2D[line + 1][col].equals("W")) {
            return new Wall();
        }
        if(fileArray2D[line + 1][col].equals("F")) {
            return new FreeTunnel();
        }
        if(fileArray2D[line + 1][col].equals("G")) {
            return new Gate(line, col);
        }
        return new OccupiedTunnel();
    }

    public void positionOfRockford() {
        int starterLine = Integer.parseInt(fileArray2D[0][0]) + 1;
        for (int line = starterLine; line < fileArray2D.length; line++) {
            for (int col = 0; col < fileArray2D[line].length; col++) {
                if(col % 2 == 0 && fileArray2D[line][col].matches("-?\\d+")) {
                    model.setPositionMovingObjects(line, col);
                }
            }
        }
    }

    public void ImageOfMovingObjects() {
        for (int i = 0; i < boulderLines.size(); i++) {
            buttons[boulderLines.get(i)][boulderCols.get(i)].setImage(Boulder.getBoulderImage());
            buttons[diamondsLines.get(i)][diamondsCols.get(i)].setImage(Diamond.getBoulderImage());
        }
    }


    public boolean isPositionFree(int line, int col) {
        if(fileArray2D[line + 1][col].equals("F")
                || fileArray2D[line + 1][col].equals("O") || fileArray2D[line + 1][col].equals("G")) {
            for (int i = 0; i < boulderLines.size(); i++) {
                if(line == boulderLines.get(i) && col == boulderCols.get(i)) {
                    System.out.println("Boulder, BAD MOVE");
                    return false;
                }
            }
            System.out.println("Free Position. GOOD MOVE");
            return true;
        }
        else {
            System.out.println("Wall, BAD MOVE");
            return false;
        }
    }

    void setKeyHandle(Scene scnMain) {
        scnMain.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                keyPressed(directionMap.get(event.getCode()));
                rockfordSetImage();
                boulderBotPosFree();
                model.rockfordCatchDiamond();
                levelPassed();
            };
        });
    }

    private void keyPressed(Direction direction) {
        Rockford rockford = Rockford.getInstance();
        rockford.rockfordMove(direction);
    }

    private void rockfordSetImage() {
        buttons[Rockford.getOldLine()][Rockford.getOldCol()].setImage(FreeTunnel.getFreeTunnelImg());
        buttons[Rockford.getLine()][Rockford.getCol()].setImage(Rockford.getRockfordImage());
    }

    private void boulderBotPosFree() {
        for (int i = 0; i < boulderLines.size(); i++) {
            for (int j = 0; j < boulderCols.size(); j++) {
                if(String.valueOf(buttons[boulderLines.get(i) + 1][boulderCols.get(j)]).contains("FreeTunnel")){
                    buttons[boulderLines.get(i) + 1][boulderCols.get(j)].setImage(Boulder.getBoulderImage());
                    buttons[boulderLines.get(i)][boulderCols.get(j)] = new FreeTunnel();
                    boulderLines.set(i, boulderLines.get(i) + 1);
                }
            }
        }
    }

    @Override
    public boolean allDiamondsCaught() {
        System.out.println(caughtDiamonds);
        if(totalDiamonds == caughtDiamonds) {
            buttons[Gate.getLine()][Gate.getCol()].setImage(Gate.levelPassedSetImage());
            FadeTransition blinkGate = new FadeTransition(Duration.seconds(1.5), buttons[Gate.getLine()][Gate.getCol()]);
            blinkGate.play();
            return true;
        }
        return false;
    }

    @Override
    public boolean rockfordEntersTheDoor() {
        return allDiamondsCaught() && Rockford.getLine() == Gate.getLine()
                && Rockford.getCol() == Gate.getCol();
    }


    @Override
    public void levelPassed() {
        if(rockfordEntersTheDoor()) {
            int passedLevel = model.getLevel();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Level " + passedLevel + " was Passed");
            alert.setContentText("Good Luck on the Next Level");
            alert.showAndWait();

            model.setFilename(passedLevel + 1);
        }
    }

    @Override
    public void gameLost() {

    }

    @Override
    public void onBoardRockfordStart(int line , int col){
        if(Start.countBoards == 0) {
            System.out.println("Rockford starting position {" + line + ", " + col + "}");
            System.out.println("=================================\n");
        }
        Start.countBoards++;
    }
}
