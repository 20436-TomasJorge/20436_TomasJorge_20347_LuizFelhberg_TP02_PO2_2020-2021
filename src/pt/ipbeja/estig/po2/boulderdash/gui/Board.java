package pt.ipbeja.estig.po2.boulderdash.gui;

import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import com.sun.javafx.scene.traversal.Direction;

import pt.ipbeja.estig.po2.boulderdash.model.*;
import pt.ipbeja.estig.po2.boulderdash.model.pieces.*;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import static pt.ipbeja.estig.po2.boulderdash.gui.Start.*;

/**
 * @author Tomás Jorge, 20436
 * @author Luiz Felhberg, 20347
 * @version 12/05/2021
 * -------------------
 */

public class Board extends VBox implements View {

    // Global Variables
    public final Model model = new Model(this);

    private ReadFile file;
    private String[][] fileArray2D;
    private String[][] fileToMovingObjs;
    public AbstractPosition[][] buttons;

    public static ArrayList<Integer> boulderLines;
    public static ArrayList<Integer> boulderCols;

    public static ArrayList<Integer> diamondsLines;
    public static ArrayList<Integer> diamondsCols;

    public static ArrayList<Integer> imoEnemiesLines;
    public static ArrayList<Integer> imoEnemiesCols;

    private static final Map<KeyCode, Direction> directionMap = new HashMap<>();
    static {
        directionMap.put(KeyCode.UP, Direction.UP);
        directionMap.put(KeyCode.DOWN, Direction.DOWN);
        directionMap.put(KeyCode.LEFT, Direction.LEFT);
        directionMap.put(KeyCode.RIGHT, Direction.RIGHT);
    }
    // End of the Global Variables

    /**
     * Constructor to initialize a graphic board
     */
    public Board(){
        this.initializeGlobalVariables();
        if (this.model.countBoards == 0) {
            this.positionOfMovingObjects();
            this.model.totalDiamonds = diamondsLines.size();
            this.model.countBoards = 1;

            movesStage.setScene(setRockfordMoves());
            movesStage.setTitle("Boulder Dash - Moves");
            movesStage.setMinWidth(200);
            movesStage.setMinHeight(400);
            movesStage.show();
        }
    }

    /**
     * Constructor that allows us to run the tests
     * -----------------------
     * @param text -> insert some text to mention that we
     *             are going to run the Tests
     */
    public Board(String text) {
        this.initializeGlobalVariables();
        System.out.println("\n================\n" +
                    " This is a " + text +
                    "\n================");
        this.positionOfMovingObjects();
        this.gatePosition();
        this.model.totalDiamonds = diamondsLines.size();
    }

    /**
     * Initialize all the global variables
     */
    private void initializeGlobalVariables() {
        this.file = new ReadFile(this.model.getFilename(), this.model.getSeparator());
        this.fileArray2D = this.file.readFileToStringArray2D(
                this.model.getFilename(), this.model.getSeparator());
        this.fileToMovingObjs = this.file.readFileToStringArray2D(
                this.model.getFilename(), " ");
        this.buttons = new AbstractPosition[this.file.getLines()][this.file.getCols()];

        boulderLines = new ArrayList<>();
        boulderCols = new ArrayList<>();
        diamondsLines = new ArrayList<>();
        diamondsCols = new ArrayList<>();
        imoEnemiesLines = new ArrayList<>();
        imoEnemiesCols = new ArrayList<>();
    }

    /**
     * Creates a scene to record all of rockford's movements
     * -----------------------
     * @return scene with the text in "textMoves"
     */
    private Scene setRockfordMoves() {
        GridPane pane = new GridPane();
        VBox vBox = new VBox();
        vBox.getChildren().add(textMoves);
        pane.getChildren().add(vBox);
        return new Scene(pane);
    }

    /**
     * Create the main scene with a graphic objects
     * -----------------------
     * @return scene with objects
     */
    public Scene createScene() {
        btnStart.setOnAction(event -> {
            btnClicked++;
            timer.schedule(task, 0, 1000);
        });

        this.getChildren().addAll(this.gameInfo(), this.createBoard());
        Scene scene = new Scene(this);
        this.setKeyHandle(scene);
        return scene;
    }

    /**
     * Create a horizontal box to show some data like,
     * the time in the game, the number of the movements, etc.
     * -----------------------
     * @return HBox that contains the necessary data to complete
     * the game
     */
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

    /**
     * Create a grid that contains all the board information
     * like, the position of the objects
     * -----------------------
     * @return GridPane that contains all objetcs in the right
     * position
     */
    private GridPane createBoard() {
        GridPane pane = new GridPane();
        for (int line = 0; line < this.model.getBoardLines(); line++) {
            for (int col = 0; col < this.model.getBoardCols(); col++) {
                AbstractPosition objectType = this.getObjectType(line, col);
                pane.add(objectType, col, line);
                this.buttons[line][col] = objectType;
            }
        }
        this.ImageOfMovingObjects();
        this.buttons[Rockford.getLine()][Rockford.getCol()].setImage(Rockford.getRockfordImage());
        return pane;
    }

    /**
     * Create a new scene to show the new level
     * -----------------------
     * @return scene with the new graphic level
     */
    public Scene createNextLevelScene() {
        this.getChildren().addAll(this.gameInfo(), this.createNewBoard());
        Scene scene = new Scene(this);
        this.setKeyHandle(scene);
        return scene;
    }

    /**
     * Create a grid that contains all the board information
     * of the new level
     * -----------------------
     * @return new board with the information of the
     * new level
     */
    public GridPane createNewBoard() {
        int newLevel = levelPassed + 1;
        if(newLevel <= this.file.getNumberOfLevels()) {
            this.model.setFilename(newLevel);
            setMovesAtScene = "";
            textMoves.setText("Rockford Moves:\n\nLevel " + newLevel + ":\n");
            setMovesAtScene = textMoves.getText();
        }
        else {
            gameWon();
        }
        this.file = new ReadFile(this.model.getFilename(),this.model.getSeparator());
        this.fileArray2D = file.readFileToStringArray2D(
                this.model.getFilename(), this.model.getSeparator());
        this.fileToMovingObjs = file.readFileToStringArray2D(
                this.model.getFilename(), " ");
        this.buttons = new AbstractPosition[this.file.getLines()][this.file.getCols()];
        return createBoard();
    }

    /**
     * Get the abstract position read in the file
     * -----------------------
     * @param line ->  line that is being read from the file
     * @param col -> column that is being read from the file
     * @return AbstractPosition which represents the read line and column
     */
    private AbstractPosition getObjectType(int line, int col) {
        if(line == 0 || line == this.model.getBoardLines() - 1 || col == 0
                || col == this.model.getBoardCols() - 1
                || this.fileArray2D[line + 1][col].equals("W")) {
            return new Wall();
        }
        if(this.fileArray2D[line + 1][col].equals("F")) {
            return new FreeTunnel();
        }
        if(this.fileArray2D[line + 1][col].equals("G")) {
            return new Gate(line, col, "Main");
        }
        return new OccupiedTunnel();
    }

    /**
     * Obtain the position of the gate to be used in Tests
     */
    public void gatePosition() {
        for (int line = 0; line < this.model.getBoardLines(); line++) {
            for (int col = 0; col < this.model.getBoardCols(); col++) {
                if (this.fileArray2D[line + 1][col].equals("G")) {
                    new Gate(line, col, "Test");
                }
            }
        }
    }

    /**
     * Get the position of the moving objects
     */
    public void positionOfMovingObjects() {
        int starterLine = file.getLines() + 1;
        for (int line = starterLine; line < this.fileArray2D.length; line++) {
            for (int col = 1; col < this.fileToMovingObjs[line].length; col++) {
                if(this.fileToMovingObjs[line][col].matches("-?\\d+")) {
                    this.model.setPositionMovingObjects(line, col);
                }
            }
        }
    }

    /**
     * set the scene image for the moving objects
     */
    public void ImageOfMovingObjects() {
        for (int i = 0; i < boulderLines.size(); i++) {
            this.buttons[boulderLines.get(i)][boulderCols.get(i)].
                    setImage(Boulder.getBoulderImage());
        }
        for (int i = 0; i < diamondsCols.size(); i++) {
            this.buttons[diamondsLines.get(i)][diamondsCols.get(i)].
                    setImage(Diamond.getDiamondImage());
        }
        for (int i = 0; i < imoEnemiesCols.size(); i++) {
            this.buttons[imoEnemiesLines.get(i)][imoEnemiesCols.get(i)].
                    setImage(ImmovableEnemy.getImoEnemyImage());
        }
    }

    /**
     * Wait for the keyboard key click
     * -------------------
     * @param scnMain -> scene being shown
     */
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
                gameLost();
            };
        });
    }

    /**
     * When the key is pressed send the information
     * to the class Rockford for update his position
     * if possible
     * --------------------------
     * @param direction -> arrow of the keyboard that was pressed
     */
    public void keyPressed(Direction direction) {
        Rockford rockford = Rockford.getInstance();
        rockford.rockfordMove(direction);
        setMovesAtScene += rockford.rockfordMovementText() + "\n";
        textMoves.setText(setMovesAtScene);
    }

    /**
     * Update the scene, changing the position of the objects,
     * depending on the Rockford movements
     */
    private void rockfordSetImage() {
        this.model.moves++;
        nMoves.setText(this.model.moves + "");
        this.buttons[Rockford.getOldLine()][Rockford.getOldCol()].
                setImage(FreeTunnel.getFreeTunnelImg());
        this.buttons[Rockford.getLine()][Rockford.getCol()].
                setImage(Rockford.getRockfordImage());
    }

    /**
     * Boulder fall if there is a free tunnel under it
     */
    private void boulderFallFreePos() {
        for (int i = 0; i < boulderLines.size(); i++) {
            if (this.boulderBottomLineIsFree(i)) {
                this.buttons[boulderLines.get(i) + 1][boulderCols.get(i)].
                        setImage(Boulder.getBoulderImage());
                this.buttons[boulderLines.get(i)][boulderCols.get(i)].
                        setImage(FreeTunnel.getFreeTunnelImg());
                boulderLines.set(i, boulderLines.get(i) + 1);
            }
        }
    }

    /**
     * verify if the position under the Boulder is a free tunnel
     * ----------------------------
     * @param i -> Boulders position values saved by ArrayList
     * @return true if the position under the boulder is free,
     * false if the position under the boulder is not free
     */
    private boolean boulderBottomLineIsFree(int i) {
        String imgString = this.buttons[boulderLines.get(i) + 1][boulderCols.get(i)].
                getImage().toString();
        return Rockford.getOldLine() == boulderLines.get(i) + 1
                && Rockford.getOldCol() == boulderCols.get(i)
                || imgString.equals(FreeTunnel.getFreeTunnelString());
    }

    /**
     * Diamond fall if there is a free tunnel under it
     */
    private void diamondFallFreePos() {
        for (int i = 0; i < diamondsLines.size(); i++) {
            if (this.diamondBottomLineIsFree(i)) {
                this.buttons[diamondsLines.get(i) + 1][diamondsCols.get(i)].
                        setImage(Diamond.getDiamondImage());
                this.buttons[diamondsLines.get(i)][diamondsCols.get(i)].
                        setImage(FreeTunnel.getFreeTunnelImg());
                this.setDiamondNewPos(i);
            }
        }
    }

    /**
     * update the diamond position if he falls
     * @param i -> diamond position values saved by ArrayList
     */
    public void setDiamondNewPos(int i) {
        diamondsLines.set(i, diamondsLines.get(i) + 1);
    }

    /**
     * verify if the position under the diamond is a free tunnel
     * ----------------------------
     * @param i -> diamond position values saved by ArrayList
     * @return true if the position under the diamond is free,
     * false if the position under the diamond is not free
     */
    public boolean diamondBottomLineIsFree(int i) {
        String imgString = "";
        boolean freeLine = Rockford.getOldLine() == diamondsLines.get(i) + 1
                && Rockford.getOldCol() == diamondsCols.get(i) && this.model.diamondCaught(i);
        if(this.buttonsHaveImage()) {
            imgString = this.buttons[diamondsLines.get(i) + 1][diamondsCols.get(i)].
                    getImage().toString();
            return freeLine || imgString.equals(FreeTunnel.getFreeTunnelString());
        }
        return freeLine;
    }

    /**
     * Verify if buttons scene was created
     * !! This method was created because the Tests
     * do not support graphical objects !!
     * -------------------------
     * @return true if was not created, false if was created
     */
    private boolean buttonsHaveImage() {
        ArrayList<Boolean> buttonsHaveImage = new ArrayList<>();
        for (int line = 0; line < this.model.getBoardLines(); line++) {
            for (int col = 0; col < this.model.getBoardCols(); col++) {
                if(this.buttons[line][col] == null) buttonsHaveImage.add(false);
                else buttonsHaveImage.add(true);
            }
        }
        if(buttonsHaveImage.contains(false)) return false;
        return true;
    }

    /**
     * Created for get the model at other class
     * where we initialize the Board
     * --------------------
     * @return model initialized at the beginning
     */
    public Model getModel() {
        return this.model;
    }

    /**
     * reset the timer and the objects used by the timer
     */
    private void resetTimers() {
        timerValue = 0;
        timer.cancel();
        task.cancel();
    }

    /**
     * Verify if all diamonds were caught
     * ----------------------
     * @return true if all diamonds were caught,
     * false if not all diamonds were caught
     */
    @Override
    public boolean allDiamondsCaught() {
        if(this.model.totalDiamonds == this.model.caughtDiamonds) {
            if(this.buttonsHaveImage()) {
                buttons[Gate.getLine()][Gate.getCol()].setImage(Gate.setGateImg());
            }
            return true;
        }
        return false;
    }

    /**
     * Verify if Rockford position is the same as the
     * Gate position
     * ------------------
     * @return true if Rockford and Gate position is the same,
     * false if the position is diferent
     */
    @Override
    public boolean rockfordEntersTheDoor() {
        return this.allDiamondsCaught() && Rockford.getLine() == Gate.getLine()
                && Rockford.getCol() == Gate.getCol();
    }

    /**
     * Verify if the level was passed
     * ---------------
     * @return true if level was passed,
     * false if wasn't passed
     */
    @Override
    public boolean levelPassed() {
        if(this.rockfordEntersTheDoor()) {
            int passedLevel = this.model.getLevel();
            System.out.println("Level " + passedLevel + " was Passed");
            if(this.buttonsHaveImage()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Level " + passedLevel + " was Passed");
                alert.setContentText("Good Luck on the Next Level");
                alert.showAndWait();
            }
            return true;
        }
        return false;
    }

    /**
     * if the method "levelPassed()" returns true,
     * the level will be changed and the timers
     * will reset
     */
    @Override
    public void changeLevel() {
        if(this.levelPassed()) {
            if(this.model.points > bestScore)  bestScore = this.model.points;
            this.resetTimers();
            levelPassed = this.model.getLevel();
        }
    }

    /**
     * Show an alert saying that the player XX has won the game
     */
    @Override
    public void gameWon() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Congratulations " + playerName + "!! You finished the game");
        alert.setContentText("Your best score was: " + bestScore);
        alert.showAndWait();
    }

    /**
     * If Rockford and the immovable enemy are at the
     * same position the game is lost and a alert is
     * shown and the scene is closed
     */
    @Override
    public void gameLost() {
        for (int i = 0; i < imoEnemiesLines.size(); i++) {
            if(this.model.rockfordMoveToImoEnemy(i)) {
                this.resetTimers();
                System.out.println("You have lost the game");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(playerName + " you died :(\nThere was a enemy");
                alert.setContentText("Your best score was: " + bestScore);
                alert.showAndWait();
                mainStage.close();
            }
        }
    }

    /**
     * Print of the Rockford initial position
     * ---------------
     * @param line -> line were rockford starts
     * @param col -> column were rockford starts
     */
    @Override
    public void onBoardRockfordStart(int line , int col){
        if(this.model.countBoards == 0) {
            System.out.println("Rockford starting position {" + line + ", " + col + "}");
            System.out.println("=================================\n");
        }
    }
}
