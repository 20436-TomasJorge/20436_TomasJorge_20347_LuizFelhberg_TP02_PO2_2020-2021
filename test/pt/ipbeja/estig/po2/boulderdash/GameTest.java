package pt.ipbeja.estig.po2.boulderdash;

import com.sun.javafx.scene.traversal.Direction;
import org.junit.jupiter.api.Test;
import pt.ipbeja.estig.po2.boulderdash.gui.Board;
import pt.ipbeja.estig.po2.boulderdash.model.Model;
import pt.ipbeja.estig.po2.boulderdash.model.position.Rockford;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Tomás Jorge
 * @version 02/07/2021
 * @number 20436
 */

public class GameTest {

    Board board = new Board();
    Rockford rockford = Rockford.getInstance();

    @Test
    void test1() {
        System.out.println("=============\nTest 1\n");

        int lineBeforeMove = rockford.getLine();
        int colBeforeMove = rockford.getCol();
        System.out.println("line -> " + lineBeforeMove + " | col -> " + colBeforeMove);

        boolean move = board.isPositionFree(lineBeforeMove, colBeforeMove + 1);
        assertTrue(move);

        rockford.rockfordMove(Direction.RIGHT);

        int lineAfterMove = rockford.getLine();
        int colAfterMove = rockford.getCol();
        System.out.println("\nMove to\nline -> " + lineAfterMove + " | col -> " + colAfterMove);

        assertEquals(lineBeforeMove, lineAfterMove);
        assertNotEquals(colBeforeMove, colAfterMove);
    }

    @Test
    void test2() {
        System.out.println("\n=============\nTest 2\n");

        int lineBeforeMove = rockford.getLine();
        int colBeforeMove = rockford.getCol();

        System.out.println("line -> " + lineBeforeMove + " | col -> " + colBeforeMove + "\n");

        boolean move = board.isPositionFree(lineBeforeMove - 1, colBeforeMove);
        assertFalse(move);

        rockford.rockfordMove(Direction.UP);

        int lineAfterMove = rockford.getLine();
        int colAfterMove = rockford.getCol();
        System.out.println("\nMove to\nline -> " + lineAfterMove + " | col -> " + colAfterMove);

        assertEquals(lineBeforeMove, lineAfterMove);
        assertEquals(colBeforeMove, colAfterMove);
    }
}
