package pt.ipbeja.estig.po2.boulderdash.model.pieces;

import javafx.scene.image.Image;

/**
 * @author Tomás Jorge, 20436
 * @author Luiz Felhberg, 20347
 * @version 06/07/2021
 */

public class Diamond {

    public Diamond() { }

    /**
     * @return Diamond image to set in the scene
     */
    public static Image getDiamondImage() {
        return new Image("resources/images/diamond.png");
    }
}
