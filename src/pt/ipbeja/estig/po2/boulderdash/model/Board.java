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

public class Board extends VBox{ // implements View

    private AbstractPosition buttons[][];

    public final String filename = "C:\\Users\\Utilizador\\Desktop\\IPB\\1º Ano\\2º Semestre\\Programação Objetos\\20436_TomasJorge_TP02_PO2_2020-2021\\src\\resources\\files\\level1.txt";
    public final String separator = "";

    private final ReadFile file = new ReadFile(filename, separator);
    private final int lines = file.getLines();
    private final int cols = file.getCols();

    public Board() {
        System.out.println(lines + " " + cols);
        GridPane board = this.createBoard();
        this.getChildren().add(board);
    }

    private GridPane createBoard() {
        GridPane pane = new GridPane();
        this.buttons = new AbstractPosition[this.lines][this.cols];
        for (int line = 0; line < this.lines; line++) {
            for(int col = 0; col < this.cols; col++) {
                String image = this.getImage(line , col);
                //this.buttons[line][col] = new Wall(line, col);
                ButtonImage button = new ButtonImage(image);
                pane.add(button, col, line);
            }
        }
        return pane;
    }

    private String getImage(int line, int col) {
        // Mudei o separator
        String image = "/resources/images/dirt.png";
        String[][] fileAsArray2D = file.readFileToStringArray2D(this.filename, separator);
        if(line == 0 || line == this.lines - 1  || col == 0 || col == this.cols - 1) {
            return "/resources/images/wall.png";
        }

        if(fileAsArray2D[line+1][col].equals("J")){
            System.out.println("rockford");
            return "/resources/images/rockford.png";
        }
        if(fileAsArray2D[line+1][col].equals("D")){
            System.out.println("diamond");
            return "/resources/images/diamond.png";
        }
        if(fileAsArray2D[line+1][col].equals("E")){
            System.out.println("ghost");
            return "/resources/images/ghost.png";
        }
        return "/resources/images/dirt.png";
    }
}
