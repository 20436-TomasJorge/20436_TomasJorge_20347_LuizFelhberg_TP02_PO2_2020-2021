package pt.ipbeja.estig.po2.boulderdash;

import com.sun.javafx.scene.traversal.Direction;
import org.junit.jupiter.api.Test;
import pt.ipbeja.estig.po2.boulderdash.gui.Board;
import pt.ipbeja.estig.po2.boulderdash.model.pieces.Rockford;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Tomás Jorge
 * @version 02/07/2021
 * @number 20436
 */

public class GameTest {

    Rockford rockford = Rockford.getInstance();
    Board board = new Board();

    @Test
    void test1() {
        System.out.println("=============\nTest 1 - Move to a Free Position\n");

        int lineBeforeMove = Rockford.getLine();
        int colBeforeMove = Rockford.getCol();
        System.out.println("line -> " + lineBeforeMove + " | col -> " + colBeforeMove);

        boolean move = board.isPositionFree(lineBeforeMove + 1, colBeforeMove);
        assertTrue(move);

        rockford.rockfordMove(Direction.DOWN);

        int lineAfterMove = Rockford.getLine();
        int colAfterMove = Rockford.getCol();
        System.out.println("\nMove to\nline -> " + lineAfterMove + " | col -> " + colAfterMove);

        assertNotEquals(lineBeforeMove, lineAfterMove);
        assertEquals(colBeforeMove, colAfterMove);
    }

    @Test
    void test2() {
        System.out.println("\n=============\nTest 2 - Move to a position occupied by a Wall\n");

        int lineBeforeMove = Rockford.getLine();
        int colBeforeMove = Rockford.getCol();

        System.out.println("line -> " + lineBeforeMove + " | col -> " + colBeforeMove + "\n");

        boolean move = board.isPositionFree(lineBeforeMove - 1, colBeforeMove);
        assertFalse(move);

        rockford.rockfordMove(Direction.UP);

        int lineAfterMove = Rockford.getLine();
        int colAfterMove = Rockford.getCol();
        System.out.println("\nMove to\nline -> " + lineAfterMove + " | col -> " + colAfterMove);

        assertEquals(lineBeforeMove, lineAfterMove);
        assertEquals(colBeforeMove, colAfterMove);
    }

    @Test
    void teste3() {
        System.out.println("\n=============\nTest 3 - Move to a position occupied by a Boulder\n");

        int lineBeforeMove = Rockford.getLine();
        int colBeforeMove = Rockford.getCol();

        System.out.println("line -> " + lineBeforeMove + " | col -> " + colBeforeMove + "\n");

        boolean move = board.isPositionFree(lineBeforeMove, colBeforeMove + 1);
        assertFalse(move);

        rockford.rockfordMove(Direction.RIGHT);

        int lineAfterMove = Rockford.getLine();
        int colAfterMove = Rockford.getCol();
        System.out.println("\nMove to\nline -> " + lineAfterMove + " | col -> " + colAfterMove);

        assertEquals(lineBeforeMove, lineAfterMove);
        assertEquals(colBeforeMove, colAfterMove);
    }
}
