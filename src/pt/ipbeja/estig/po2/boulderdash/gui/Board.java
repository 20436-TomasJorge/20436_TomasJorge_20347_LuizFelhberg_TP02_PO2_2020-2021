package pt.ipbeja.estig.po2.boulderdash.gui;



/**
 * @author Tomás Jorge
 * @number 20436
 * @version 12/05/2021
 * -------------------
 *
 */

public class Board {

    private AbstractPosition buttons[][];

    public void createBoard() {


    }

    /*public void createBoard() {
        ButtonHandler handler = new ButtonHandler();
        this.buttons = new TicTacToeButton[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                AbstractPosition button = new AbstractPosition();
                button.setOnAction(handler);
                add(button, col, row);
                buttons[row][col] = button;
            }
        }
    }*/

}
