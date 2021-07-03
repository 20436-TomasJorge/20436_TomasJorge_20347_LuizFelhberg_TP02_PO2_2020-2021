package pt.ipbeja.estig.po2.boulderdash.model.position;

import javafx.scene.image.Image;
import pt.ipbeja.estig.po2.boulderdash.model.position.AbstractPosition;

/**
 * @author Tomás Jorge
 * @version 15/05/2021
 * @number 20436
 */

public class FreeTunnel extends AbstractPosition {

    private final int line, col;

    public FreeTunnel(int line, int col) {
        super(line, col);
        this.line = line;
        this.col = col;
        this.setImage();
    }

    public void setImage() {
        Image img = new Image("/resources/images/freeTunnel.png");
        this.setImage(img);
        this.setFitHeight(SIDE_SIZE);
        this.setFitWidth(SIDE_SIZE);
    }
}
