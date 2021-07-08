package pt.ipbeja.estig.po2.boulderdash.model.pieces;

import com.sun.javafx.scene.traversal.Direction;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pt.ipbeja.estig.po2.boulderdash.model.Model;

/**
 * @author Tomás Jorge
 * @number 20436
 * @version 12/05/2021
 * -------------------
 */

public class Rockford extends ImageView {

    private static Rockford instance = null;
    Model model = new Model(null);

    private static int line, col;
    private static int oldLine, oldCol;

    private static String[] alphabet = new String[] {"A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
            "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    protected Rockford(int line, int col) {
        Rockford.line = line;
        Rockford.col = col;
    }

    public static Rockford getInstance() {
        if(instance == null) {
            instance = new Rockford(line, col);
        }
        return instance;
    }

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

    public void goToPosition() {
        if(!model.isPositionFree(line, col)) {
            line = oldLine;
            col = oldCol;
        }
        System.out.println(rockfordMovementText() + "\n");
    }

    public String rockfordMovementText() {
        String moves = "";
        for (int i = 0; i < alphabet.length; i++) {
            if(oldLine == i) moves = "rockford " + alphabet[i] + oldCol + " -> ";
            if(line == i) moves += alphabet[i] + col;
        }
        return moves;
    }

    public static int getLine() {
        return line;
    }

    public static int getCol() {
        return col;
    }

    public static int getOldLine() {
        return oldLine;
    }

    public static int getOldCol() {
        return oldCol;
    }

    public static Image getRockfordImage() {
        return new Image("resources/images/rockford.png");
    }

    public static void setLine(int newLine) {
        line = newLine;
    }

    public static void setCol(int newCol) {
        col = newCol;
    }
}
