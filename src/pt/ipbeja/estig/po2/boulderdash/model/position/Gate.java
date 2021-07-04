package pt.ipbeja.estig.po2.boulderdash.model.position;

import javafx.scene.image.Image;
import pt.ipbeja.estig.po2.boulderdash.gui.View;
import pt.ipbeja.estig.po2.boulderdash.model.position.AbstractPosition;

import static pt.ipbeja.estig.po2.boulderdash.model.Model.SIDE_SIZE;

/**
 * @author Tomás Jorge
 * @version 15/05/2021
 * @number 20436
 */

public class Gate extends AbstractPosition {

    private final int line, col;

    public Gate(int line, int col) {
        super(line, col);
        this.line = line;
        this.col = col;
        setImage();
    }

    private void setImage() {
        Image img = new Image("/resources/images/freeTunnel.png");
        this.setImage(img);
        this.setFitHeight(SIDE_SIZE);
        this.setFitWidth(SIDE_SIZE);
    }

    public int getLine() {
        return line;
    }

    public int getCol() {
        return col;
    }

    public Image levelPassedSetImage() {
        return new Image("/resources/images/gate.png");
    }
}
