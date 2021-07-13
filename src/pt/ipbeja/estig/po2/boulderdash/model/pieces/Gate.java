package pt.ipbeja.estig.po2.boulderdash.model.pieces;

import javafx.scene.image.Image;
import pt.ipbeja.estig.po2.boulderdash.model.AbstractPosition;

import static pt.ipbeja.estig.po2.boulderdash.model.Model.SIDE_SIZE;

/**
 * @author Tomás Jorge, 20436
 * @author Luiz Felhberg, 20347
 * @version 15/05/2021
 */

public class Gate extends AbstractPosition {

    private static int line, col;

    /**
     * Constructor to save the gate line and column
     * ----------------------
     * @param gateLine -> line of the Gate
     * @param gateCol -> column of the Gate
     * @param text -> to understand if the class
     *             is being initialized at the Test
     *             class or at a normal class
     */
    public Gate(int gateLine, int gateCol, String text) {
        line = gateLine;
        col = gateCol;
        if(!text.equals("Test")) setImage();
    }

    /**
     * Set a Free Tunnel image at the beginning,
     * it will only appear the real Gate when level
     * is passed
     */
    private void setImage() {
        Image img = new Image("/resources/images/freeTunnel.png");
        this.setImage(img);
        this.setFitHeight(SIDE_SIZE);
        this.setFitWidth(SIDE_SIZE);
    }

    /**
     * @return Gate line
     */
    public static int getLine() {
        return line;
    }

    /**
     * @return Gate column
     */
    public static int getCol() {
        return col;
    }

    /**
     * @return Free Tunnel image to set at scene
     */
    public static Image setGateImg() {
        return new Image("/resources/images/gate.png");
    }
}
