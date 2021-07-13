package pt.ipbeja.estig.po2.boulderdash.model.pieces;

import javafx.scene.image.Image;

import pt.ipbeja.estig.po2.boulderdash.model.AbstractPosition;

import static pt.ipbeja.estig.po2.boulderdash.model.Model.SIDE_SIZE;

/**
 * @author Tomás Jorge, 20436
 * @author Luiz Felhberg, 20347
 * @version 15/05/2021
 */

public class FreeTunnel extends AbstractPosition {

    private static final Image img = new Image("resources/images/freeTunnel.png");

    public FreeTunnel() {
        this.setImage();
    }

    /**
     * Set the Free Tunnel image at the scene
     */
    public void setImage() {
        this.setImage(img);
        this.setFitHeight(SIDE_SIZE);
        this.setFitWidth(SIDE_SIZE);
    }

    /**
     * @return Free Tunnel image to set at scene
     */
    public static Image getFreeTunnelImg() {
        return img;
    }

    /**
     * @return Image as a String, to compare the
     * name of the object
     */
    public static String getFreeTunnelString() {
        return img.toString();
    }
}
