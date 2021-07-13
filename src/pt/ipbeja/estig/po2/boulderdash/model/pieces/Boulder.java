package pt.ipbeja.estig.po2.boulderdash.model.pieces;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Tomás Jorge, 20436
 * @author Luiz Felhberg, 20347
 * @version 02/07/2021
 */

public class Boulder extends ImageView {

    public Boulder() { }

    /**
     * @return Boulder image to set in the scene
     */
    public static Image getBoulderImage() {
        return new Image("resources/images/boulder.png");
    }
}
