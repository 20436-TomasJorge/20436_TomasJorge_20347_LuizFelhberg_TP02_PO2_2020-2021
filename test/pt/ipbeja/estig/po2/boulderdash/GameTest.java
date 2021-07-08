package pt.ipbeja.estig.po2.boulderdash;

import com.sun.javafx.scene.traversal.Direction;
import org.junit.jupiter.api.Test;
import pt.ipbeja.estig.po2.boulderdash.gui.Board;
import pt.ipbeja.estig.po2.boulderdash.gui.Start;
import pt.ipbeja.estig.po2.boulderdash.model.Model;
import pt.ipbeja.estig.po2.boulderdash.model.pieces.Gate;
import pt.ipbeja.estig.po2.boulderdash.model.pieces.Rockford;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Tomás Jorge
 * @version 02/07/2021
 * @number 20436
 */

public class GameTest {
    Rockford rockford = Rockford.getInstance();
    Board board = new Board("Test");
    Model model = board.getModel();

    @Test
    void test1() {
        System.out.println("Test 1 - Movement to a Free Position\n");

        int lineBeforeMove = Rockford.getLine();
        int colBeforeMove = Rockford.getCol();
        System.out.println("line -> " + lineBeforeMove + " | col -> " + colBeforeMove);

        boolean move = model.isPositionFree(lineBeforeMove + 1, colBeforeMove);
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
        System.out.println("Test 2 - Movement to a position occupied by a Wall\n");

        int lineBeforeMove = Rockford.getLine();
        int colBeforeMove = Rockford.getCol();
        System.out.println("line -> " + lineBeforeMove + " | col -> " + colBeforeMove + "\n");

        boolean move = model.isPositionFree(lineBeforeMove - 1, colBeforeMove);
        assertFalse(move);

        rockford.rockfordMove(Direction.UP);
        int lineAfterMove = Rockford.getLine();
        int colAfterMove = Rockford.getCol();
        System.out.println("\nMove to\nline -> " + lineAfterMove + " | col -> " + colAfterMove);

        assertEquals(lineBeforeMove, lineAfterMove);
        assertEquals(colBeforeMove, colAfterMove);
    }

    @Test
    void test3() {
        System.out.println("Test 3 - Movement to a position occupied by a Boulder\n");

        int lineBeforeMove = Rockford.getLine();
        int colBeforeMove = Rockford.getCol();
        System.out.println("line -> " + lineBeforeMove + " | col -> " + colBeforeMove + "\n");

        boolean move = model.isPositionFree(lineBeforeMove, colBeforeMove + 1);
        assertFalse(move);

        rockford.rockfordMove(Direction.RIGHT);
        int lineAfterMove = Rockford.getLine();
        int colAfterMove = Rockford.getCol();
        System.out.println("\nMove to\nline -> " + lineAfterMove + " | col -> " + colAfterMove);

        assertEquals(lineBeforeMove, lineAfterMove);
        assertEquals(colBeforeMove, colAfterMove);
    }

    @Test
    void test4() {
        System.out.println("Test 4 - Movement to a position occupied by a Diamond\n");

        int diamondLine = Board.diamondsLines.get(1);
        int diamondCol = Board.diamondsCols.get(1);
        int initialPoints = model.points;
        System.out.println("Diamond Position:  line -> " + diamondLine + " | col -> " + diamondCol);

        Rockford.setLine(diamondLine);
        Rockford.setCol(diamondCol - 1);
        int lineBeforeMove = Rockford.getLine();
        int colBeforeMove = Rockford.getCol();
        System.out.println("Rockford Position: line -> " + lineBeforeMove + " | col -> " + colBeforeMove + "\n");

        boolean move = model.isPositionFree(diamondLine, diamondCol);
        assertTrue(move);

        rockford.rockfordMove(Direction.RIGHT);
        int lineAfterMove = Rockford.getLine();
        int colAfterMove = Rockford.getCol();
        System.out.println("\nRockford move to: line -> " + lineAfterMove + " | col -> " + colAfterMove);

        assertEquals(diamondLine, lineAfterMove);
        assertEquals(diamondCol, colAfterMove);

        model.rockfordCatchDiamond();
        System.out.println("Initial Points -> " + initialPoints);
        System.out.println("Actual Points  -> " + model.points);
        assertNotEquals(initialPoints, model.points);
    }

    @Test
    void test5() {
        System.out.println("Test 5 - Movement to a position that makes a diamond fall\n");
        int i = 0;
        int diamondLine = Board.diamondsLines.get(i);
        int diamondCol = Board.diamondsCols.get(i);
        System.out.println("Diamond Position:  line -> " + diamondLine + " | col -> " + diamondCol);

        Rockford.setLine(diamondLine + 1);
        Rockford.setCol(diamondCol);
        int lineBeforeMove = Rockford.getLine();
        int colBeforeMove = Rockford.getCol();
        System.out.println("Rockford Position: line -> " + lineBeforeMove + " | col -> " + colBeforeMove + "\n");

        boolean move = model.isPositionFree(lineBeforeMove, colBeforeMove + 1);
        assertTrue(move);

        rockford.rockfordMove(Direction.RIGHT);
        int lineAfterMove = Rockford.getLine();
        int colAfterMove = Rockford.getCol();
        System.out.println("\nRockford move to: line -> " + lineAfterMove + " | col -> " + colAfterMove);

        board.setDiamondNewPos(i);

        diamondLine = Board.diamondsLines.get(i);
        diamondCol = Board.diamondsCols.get(i);
        System.out.println("Diamond fall to:  line -> " + diamondLine + " | col -> " + diamondCol);

        assertEquals(lineBeforeMove, diamondLine);
        assertEquals(colBeforeMove, diamondCol);
    }

    @Test
    void test6() {
        System.out.println("Test 6 - Movement to the position occupied by the gate\n");

        int gateLine = Gate.getLine();
        int gateCol = Gate.getCol();
        System.out.println("Gate Position:  line -> " + gateLine + " | col -> " + gateCol);

        Rockford.setLine(gateLine - 1);
        Rockford.setCol(gateCol);
        int lineBeforeMove = Rockford.getLine();
        int colBeforeMove = Rockford.getCol();
        System.out.println("Rockford Position: line -> " + lineBeforeMove + " | col -> " + colBeforeMove + "\n");

        boolean move = model.isPositionFree(gateLine, gateCol);
        assertTrue(move);


        rockford.rockfordMove(Direction.DOWN);
        int lineAfterMove = Rockford.getLine();
        int colAfterMove = Rockford.getCol();
        System.out.println("\nRockford move to: line -> " + lineAfterMove + " | col -> " + colAfterMove);

        model.caughtDiamonds = model.totalDiamonds;
        boolean passed = model.getView().levelPassed();
        assertTrue(passed);
    }
}
