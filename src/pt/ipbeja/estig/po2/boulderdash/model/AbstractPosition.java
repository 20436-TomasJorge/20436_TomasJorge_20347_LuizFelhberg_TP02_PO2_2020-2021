package pt.ipbeja.estig.po2.boulderdash.model;

import javafx.scene.image.ImageView;
import pt.ipbeja.estig.po2.boulderdash.gui.View;
import pt.ipbeja.estig.po2.boulderdash.model.Model;

/**
 * @author Tomás Jorge
 * @version 15/05/2021
 * @number 20436
 */

public abstract class AbstractPosition extends ImageView {

    private final Model model = new Model(null);
    View view;


    public AbstractPosition() {
    }
}

