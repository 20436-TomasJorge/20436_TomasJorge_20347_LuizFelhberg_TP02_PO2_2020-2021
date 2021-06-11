package pt.ipbeja.estig.po2.boulderdash.model;

import javafx.scene.image.Image;

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

    public void setImage() {
        Image img = new Image("/resources/images/gate.png");
        this.setImage(img);
        this.setFitHeight(SIDE_SIZE);
        this.setFitWidth(SIDE_SIZE);
    }
}
