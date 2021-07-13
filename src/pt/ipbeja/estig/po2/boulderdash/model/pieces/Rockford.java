package pt.ipbeja.estig.po2.boulderdash.model.pieces;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.sun.javafx.scene.traversal.Direction;

import pt.ipbeja.estig.po2.boulderdash.model.Model;

/**
 * @author Tomás Jorge, 20436
 * @author Luiz Felhberg, 20347
 * @version 12/05/2021
 * -------------------
 */

public class Rockford extends ImageView {

    private static Rockford instance = null;
    Model model = new Model(null);

    private static int line, col;
    private static int oldLine, oldCol;

    private static final String[] alphabet = new String[] {
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"
    };

    /**
     * Constructor that save Rockford position
     * ---------------
     * @param line -> Rockford line
     * @param col -> Rockford column
     */
    protected Rockford(int line, int col) {
        Rockford.line = line;
        Rockford.col = col;
    }

    /**
     * Instantiate the class Rockford
     * ---------------
     * @return Rockford instance
     */
    public static Rockford getInstance() {
        if(instance == null) {
            instance = new Rockford(line, col);
        }
        return instance;
    }

    /**
     * Instantiate the class Rockford
     * ---------------
     * @param direction -> keyboard arrow direction
     */
    public void rockfordMove(Direction direction) {
        oldLine = line;
        oldCol = col;
        switch (direction) {
            case UP:
                line = line - 1;
                break;
            case DOWN:
                line = line + 1;
                break;
            case LEFT:
                col = col - 1;
                break;
            case RIGHT:
                col = col + 1;
                break;
        }
        goToPosition();
    }

    /**
     * Update Rockford position
     */
    public void goToPosition() {
        if(!this.model.isPositionFree(line, col)) {
            line = oldLine;
            col = oldCol;
        }
        System.out.println(rockfordMovementText() + "\n");
    }

    /**
     * Set the movements into to a String,
     * to write in a scene
     * ---------------
     * @return text to write in the scene
     */
    public String rockfordMovementText() {
        String moves = "";
        for (int i = 0; i < alphabet.length; i++) {
            if(oldLine == i) moves = "rockford " + alphabet[i] + oldCol + " -> ";
            if(line == i) moves += alphabet[i] + col;
        }
        return moves;
    }

    /**
     * @return Rockford line after a move
     */
    public static int getLine() {
        return line;
    }

    /**
     * @return Rockford column after a move
     */
    public static int getCol() {
        return col;
    }

    /**
     * @return Rockford old line after a move
     */
    public static int getOldLine() {
        return oldLine;
    }

    /**
     * @return Rockford old column after a move
     */
    public static int getOldCol() {
        return oldCol;
    }

    /**
     * @return Rockford image to set in the scene
     */
    public static Image getRockfordImage() {
        return new Image("resources/images/rockford.png");
    }

    /**
     * Set the Rockford line
     * @param newLine -> line to set Rockford at
     */
    public static void setLine(int newLine) {
        line = newLine;
    }

    /**
     * Set the Rockford column
     * @param newCol -> column to set Rockford at
     */
    public static void setCol(int newCol) {
        col = newCol;
    }
}
