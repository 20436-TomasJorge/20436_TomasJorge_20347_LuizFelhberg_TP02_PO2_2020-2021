package pt.ipbeja.estig.po2.boulderdash.model.pieces;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Tomás Jorge
 * @version 02/07/2021
 * @number 20436
 */

public class Boulder extends ImageView {

    private int line, col;

    public Boulder(int line, int col) {
        this.line = line;
        this.col = col;
    }

    public static Image getBoulderImage() {
        return new Image("resources/images/boulder.png");
    }

    public int getLine() {
        return line;
    }

    public int getCol() {
        return col;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
