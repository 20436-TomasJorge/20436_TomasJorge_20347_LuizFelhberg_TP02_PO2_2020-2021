package pt.ipbeja.estig.po2.boulderdash.model.pieces;

import javafx.scene.image.Image;
import pt.ipbeja.estig.po2.boulderdash.model.AbstractPosition;

import static pt.ipbeja.estig.po2.boulderdash.model.Model.SIDE_SIZE;

/**
 * @author Tomás Jorge
 * @version 15/05/2021
 * @number 20436
 */

public class OccupiedTunnel extends AbstractPosition {


    public OccupiedTunnel() {
        setImage();
    }

    public void setImage() {
        Image img = new Image("/resources/images/dirt.png");
        this.setImage(img);
        this.setFitHeight(SIDE_SIZE);
        this.setFitWidth(SIDE_SIZE);
    }
}
