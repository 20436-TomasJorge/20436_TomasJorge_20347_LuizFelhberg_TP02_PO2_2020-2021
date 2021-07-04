package pt.ipbeja.estig.po2.boulderdash.model.position;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static pt.ipbeja.estig.po2.boulderdash.model.Model.SIDE_SIZE;

/**
 * @author Tomás Jorge
 * @version 02/07/2021
 * @number 20436
 */

public class Boulder extends ImageView {

    private final int line, col;

    public Boulder(int line, int col) {
        this.line = line;
        this.col = col;
        this.setImage();
    }

    private void setImage() {
        Image img = new Image("src/resources/images/boulder.png");
        this.setImage(img);
        this.setFitHeight(SIDE_SIZE);
        this.setFitWidth(SIDE_SIZE);
    }
}
