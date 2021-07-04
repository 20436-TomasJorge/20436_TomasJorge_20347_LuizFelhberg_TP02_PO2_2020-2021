package pt.ipbeja.estig.po2.boulderdash.gui;

/**
 * @author Tomás Jorge
 * @version 21/05/2021
 * @number 20436
 */
public interface View {

    boolean allDiamondsCaught();

    void levelPassed();

    void gameLost();

    void onBoardRockfordStart(int line , int col);
}
