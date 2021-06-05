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
    public final static int SIDE_SIZE = 30;

    public ButtonImage() {
        //this.position = position;
        this.setImage();
    }

    private void setImage() {
        String filename = "/resources/images/diamond.png";
        Image img = new Image(filename);
        this.setImage(img);
        this.setFitHeight(SIDE_SIZE);
        this.setFitWidth(SIDE_SIZE);
    }
}

