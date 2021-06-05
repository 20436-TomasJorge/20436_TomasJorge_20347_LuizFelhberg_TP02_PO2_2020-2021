package pt.ipbeja.estig.po2.boulderdash.model;


import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


/**
 * @author Tomás Jorge
 * @number 20436
 * @version 12/05/2021
 * -------------------
 *
 */

public class Board  extends VBox{ // implements View

    private AbstractPosition buttons[][];

    String filename = "/resources/files/level1.txt";
    ReadFile file = new ReadFile(filename, "");
    private final int lines = file.getLines();
    private final int cols = file.getCols();
    private GridPane pane;


    public Board() {
        this.buttons = new AbstractPosition[lines][cols];

        GridPane board = this.createBoard();
        this.getChildren().add(board);
    }

    private GridPane createBoard() {
        GridPane pane = new GridPane();
        for (int line = 0; line < lines; line++) {
            for(int col = 0; col < cols; col++) {
                ButtonImage button = new ButtonImage();
                pane.add(button, col, line);
            }
        }
        return pane;
    }
}
