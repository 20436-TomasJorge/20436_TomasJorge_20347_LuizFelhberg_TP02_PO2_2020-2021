package pt.ipbeja.estig.po2.boulderdash.gui;


import com.sun.javafx.scene.traversal.Direction;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import pt.ipbeja.estig.po2.boulderdash.model.pieces.*;
import pt.ipbeja.estig.po2.boulderdash.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static pt.ipbeja.estig.po2.boulderdash.gui.Start.*;
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

    private ReadFile file;
    private String[][] fileArray2D;
    public AbstractPosition[][] buttons;

    public static ArrayList<Integer> boulderLines;
    public static ArrayList<Integer> boulderCols;

    public static ArrayList<Integer> diamondsLines;
    public static ArrayList<Integer> diamondsCols;


    private static final Map<KeyCode, Direction> directionMap = new HashMap<>();
    static {
        directionMap.put(KeyCode.UP, Direction.UP);
        directionMap.put(KeyCode.DOWN, Direction.DOWN);
        directionMap.put(KeyCode.LEFT, Direction.LEFT);
        directionMap.put(KeyCode.RIGHT, Direction.RIGHT);
    }

    public Board(){
        initializeGlobalVariables();
        if (model.countBoards == 0) {
            positionOfMovingObjects();
            model.totalDiamonds = diamondsLines.size();
            model.countBoards = 1;

            movesStage.setScene(setRockfordMoves());
            movesStage.setTitle("Boulder Dash");
            movesStage.setMinWidth(200);
            movesStage.setMinHeight(400);
            movesStage.show();
        }
    }

    public Board(String text) {
        initializeGlobalVariables();
        System.out.println("\n================\n" +
                    " This is a " + text +
                    "\n================");
        positionOfMovingObjects();
        gatePosition();
        model.totalDiamonds = diamondsLines.size();
    }

    private void initializeGlobalVariables() {
        file = new ReadFile(model.getFilename(), model.getSeparator());
        fileArray2D = file.readFileToStringArray2D(model.getFilename(), model.getSeparator());
        buttons = new AbstractPosition[model.getLines()][model.getCols()];

        boulderLines = new ArrayList<>();
        boulderCols = new ArrayList<>();
        diamondsLines = new ArrayList<>();
        diamondsCols = new ArrayList<>();
    }

    private Scene setRockfordMoves() {
        GridPane pane = new GridPane();
        VBox vBox = new VBox();
        vBox.getChildren().add(textMoves);
        pane.getChildren().add(vBox);
        return new Scene(pane);
    }

    public Scene createScene() {
        btnStart.setOnAction(event -> {
            btnClicked++;
            timer.schedule(task, 0, 1000);
        });

        this.getChildren().addAll(gameInfo(), createBoard());
        Scene scene = new Scene(this);
        this.setKeyHandle(scene);
        return scene;
    }

    private HBox gameInfo() {
        Text timerText = new Text(" Time:");
        Text movesText = new Text("Moves:");
        Text pointsText = new Text("Points:");

        HBox timerBox = new HBox(5);
        timerBox.getChildren().addAll(timerText, textTime);

        HBox movesBox = new HBox(5);
        movesBox.getChildren().addAll(movesText, nMoves);

        HBox pointsBox = new HBox(5);
        pointsBox.getChildren().addAll(pointsText, nPoints);

        HBox hBox = new HBox(20);
        hBox.getChildren().addAll(btnStart, btnNextLevel, timerBox, movesBox, pointsBox);

        return hBox;
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

    public Scene createNextLevelScene() {
        this.getChildren().addAll(gameInfo(), createNewBoard());
        Scene scene = new Scene(this);
        this.setKeyHandle(scene);
        return scene;
    }

    public GridPane createNewBoard() {
        GridPane pane = new GridPane();
        int newLevel = levelPassed + 1;
        if(newLevel <= file.getNumberOfLevels()) {
            model.setFilename(newLevel);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Congratulations!! You finished the game");
            alert.setContentText("Your best score was: " + bestScore);
            alert.showAndWait();
        }
        file = new ReadFile(model.getFilename(), model.getSeparator());
        fileArray2D = file.readFileToStringArray2D(model.getFilename(), model.getSeparator());
        buttons = new AbstractPosition[model.getLines()][model.getCols()];
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
            return new Gate(line, col, "Main");
        }
        return new OccupiedTunnel();
    }

    public void gatePosition() {
        for (int line = 0; line < model.getLines(); line++) {
            for (int col = 0; col < model.getCols(); col++) {
                if (fileArray2D[line + 1][col].equals("G")) {
                    new Gate(line, col, "Test");
                }
            }
        }
    }

    public void positionOfMovingObjects() {
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
            buttons[boulderLines.get(i)][boulderCols.get(i)].
                    setImage(Boulder.getBoulderImage());
        }
        for (int i = 0; i < diamondsCols.size(); i++) {
            buttons[diamondsLines.get(i)][diamondsCols.get(i)].
                    setImage(Diamond.getDiamondImage());
        }
    }

    void setKeyHandle(Scene scnMain) {
        scnMain.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                keyPressed(directionMap.get(event.getCode()));
                rockfordSetImage();
                diamondFallFreePos();
                boulderFallFreePos();
                model.rockfordCatchDiamond();
                nPoints.setText(model.points + "");

                changeLevel();
            };
        });
    }

    public void keyPressed(Direction direction) {
        Rockford rockford = Rockford.getInstance();
        rockford.rockfordMove(direction);
        setMovesAtScene += rockford.rockfordMovementText() + "\n";
        textMoves.setText(setMovesAtScene);
    }

    private void rockfordSetImage() {
        model.moves++;
        nMoves.setText(model.moves + "");
        buttons[Rockford.getOldLine()][Rockford.getOldCol()].
                setImage(FreeTunnel.getFreeTunnelImg());
        buttons[Rockford.getLine()][Rockford.getCol()].
                setImage(Rockford.getRockfordImage());
    }

    private void boulderFallFreePos() {
        for (int i = 0; i < boulderLines.size(); i++) {
            if (boulderBottomLineIsFree(i)) {
                buttons[boulderLines.get(i) + 1][boulderCols.get(i)].
                        setImage(Boulder.getBoulderImage());
                buttons[boulderLines.get(i)][boulderCols.get(i)].
                        setImage(FreeTunnel.getFreeTunnelImg());
                boulderLines.set(i, boulderLines.get(i) + 1);
            }
        }
    }

    private boolean boulderBottomLineIsFree(int i) {
        String imgString = buttons[boulderLines.get(i) + 1][boulderCols.get(i)].
                getImage().toString();
        return Rockford.getOldLine() == boulderLines.get(i) + 1
                && Rockford.getOldCol() == boulderCols.get(i)
                || imgString.equals(FreeTunnel.getFreeTunnelString());
    }

    private void diamondFallFreePos() {
        for (int i = 0; i < diamondsLines.size(); i++) {
            if (diamondBottomLineIsFree(i)) {
                buttons[diamondsLines.get(i) + 1][diamondsCols.get(i)].
                        setImage(Diamond.getDiamondImage());
                buttons[diamondsLines.get(i)][diamondsCols.get(i)].
                        setImage(FreeTunnel.getFreeTunnelImg());
                setDiamondNewPos(i);
            }
        }
    }

    public void setDiamondNewPos(int i) {
        diamondsLines.set(i, diamondsLines.get(i) + 1);
    }

    public boolean diamondBottomLineIsFree(int i) {
        String imgString = "";
        boolean freeLine = Rockford.getOldLine() == diamondsLines.get(i) + 1
                && Rockford.getOldCol() == diamondsCols.get(i) && model.diamondCaught(i);
        if(buttonsHaveImage()) {
            imgString = buttons[diamondsLines.get(i) + 1][diamondsCols.get(i)].
                    getImage().toString();
            return freeLine || imgString.equals(FreeTunnel.getFreeTunnelString());
        }
        return freeLine;
    }

    private boolean buttonsHaveImage() {
        ArrayList<Boolean> buttonsHaveImage = new ArrayList<>();
        for (int line = 0; line < model.getLines(); line++) {
            for (int col = 0; col < model.getCols(); col++) {
                if(buttons[line][col] == null) buttonsHaveImage.add(false);
                else buttonsHaveImage.add(true);
            }
        }
        if(buttonsHaveImage.contains(false)) return false;
        return true;
    }

    public Model getModel() {
        return model;
    }

    private void resetTimers() {
        timerValue = 0;
        timer.cancel();
        task.cancel();
    }

    @Override
    public boolean allDiamondsCaught() {
        if(model.totalDiamonds == model.caughtDiamonds) {
            if(buttonsHaveImage()) {
                buttons[Gate.getLine()][Gate.getCol()].setImage(Gate.setGateImg());
            }
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
    public boolean levelPassed() {
        if(rockfordEntersTheDoor()) {
            int passedLevel = model.getLevel();

            System.out.println("Level " + passedLevel + " was Passed");

            if(buttonsHaveImage()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Level " + passedLevel + " was Passed");
                alert.setContentText("Good Luck on the Next Level");
                alert.showAndWait();
            }

            return true;
        }
        return false;
    }

    @Override
    public void changeLevel() {
        if(levelPassed()) {
            if(model.points > bestScore)  bestScore = model.points;
            resetTimers();
            levelPassed = model.getLevel();
        }
    }

    @Override
    public void gameLost() {

    }

    @Override
    public void onBoardRockfordStart(int line , int col){
        if(model.countBoards == 0) {
            System.out.println("Rockford starting position {" + line + ", " + col + "}");
            System.out.println("=================================\n");
        }
    }
}
