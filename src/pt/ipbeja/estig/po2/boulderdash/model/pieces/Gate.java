package pt.ipbeja.estig.po2.boulderdash.model.pieces;

import javafx.scene.image.Image;
import pt.ipbeja.estig.po2.boulderdash.model.AbstractPosition;

import static pt.ipbeja.estig.po2.boulderdash.model.Model.SIDE_SIZE;

/**
 * @author Tomás Jorge
 * @version 15/05/2021
 * @number 20436
 */

public class Gate extends AbstractPosition {

    private static int line, col;

    public Gate(int gateLine, int gateCol) {
        line = gateLine;
        col = gateCol;
        setImage();
    }

    private void setImage() {
        Image img = new Image("/resources/images/freeTunnel.png");
        this.setImage(img);
        this.setFitHeight(SIDE_SIZE);
        this.setFitWidth(SIDE_SIZE);
    }

    public static int getLine() {
        return line;
    }

    public static int getCol() {
        return col;
    }

    public static Image levelPassedSetImage() {
        return new Image("/resources/images/gate.png");
    }
}
