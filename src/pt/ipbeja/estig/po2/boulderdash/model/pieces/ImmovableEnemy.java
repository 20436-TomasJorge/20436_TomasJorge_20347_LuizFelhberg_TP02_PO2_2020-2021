package pt.ipbeja.estig.po2.boulderdash.model.pieces;

import javafx.scene.image.Image;

/**
 * @author Tomás Jorge, 20436
 * @author Luiz Felhberg, 20347
 * @version 13/07/2021
 */

public class ImmovableEnemy {

    public ImmovableEnemy() { }

    /**
     * @return Immovable Enemy image to set in the scene
     */
    public static Image getImoEnemyImage() {
        return new Image("resources/images/imoEnemy.png");
    }
}
