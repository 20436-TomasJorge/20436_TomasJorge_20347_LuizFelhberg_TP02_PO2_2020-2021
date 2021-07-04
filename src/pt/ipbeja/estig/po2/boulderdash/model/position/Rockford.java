package pt.ipbeja.estig.po2.boulderdash.model.position;

import com.sun.javafx.scene.traversal.Direction;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pt.ipbeja.estig.po2.boulderdash.gui.Board;

/**
 * @author Tomás Jorge
 * @number 20436
 * @version 12/05/2021
 * -------------------
 *
 */

public class Rockford extends ImageView {

    private static Rockford instance = null;

    Board board = new Board();

    private static int line, col;
    private static int oldLine, oldCol;

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
        if(!board.isPositionFree(line, col)) {
            line = oldLine;
            col = oldCol;
        }
        System.out.println("Rockford position {" + line + ", " + col + "}\n");
    }

    public int getLine() {
        return line;
    }

    public int getCol() {
        return col;
    }

    public int getOldLine() {
        return oldLine;
    }

    public int getOldCol() {
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
