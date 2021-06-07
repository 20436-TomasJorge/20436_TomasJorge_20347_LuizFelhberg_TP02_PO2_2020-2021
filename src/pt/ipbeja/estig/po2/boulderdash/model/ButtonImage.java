package pt.ipbeja.estig.po2.boulderdash.model;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Tomás Jorge
 * @version 05/06/2021
 * @number 20436
 */

public class ButtonImage extends ImageView {

    //private AbstractPosition position;
    public final static int SIDE_SIZE = 50;
    private final String image;

    public ButtonImage(String image) {
        this.image = image;
        this.setImage();
    }

    private void setImage() {
        Image img = new Image(this.image);
        this.setImage(img);
        this.setFitHeight(SIDE_SIZE);
        this.setFitWidth(SIDE_SIZE);
    }
}

