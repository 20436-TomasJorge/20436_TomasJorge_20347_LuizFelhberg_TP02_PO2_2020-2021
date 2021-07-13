package pt.ipbeja.estig.po2.boulderdash.gui;

/**
 * @author Tomás Jorge, 20436
 * @author Luiz Felhberg, 20347
 * @version 21/05/2021
 */
public interface View {

    boolean allDiamondsCaught();

    boolean rockfordEntersTheDoor();

    boolean levelPassed();

    void changeLevel();

    void gameWon();

    void gameLost();

    void onBoardRockfordStart(int line , int col);
}
