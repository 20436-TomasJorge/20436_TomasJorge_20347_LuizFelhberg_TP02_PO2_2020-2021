package pt.ipbeja.estig.po2.boulderdash.model;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Tomás Jorge
 * @version 15/05/2021
 * @number 20436
 */

public abstract class AbstractPosition extends ImageView {

    private final Model model = new Model(null);
    public boolean[][] freeTunnels;

    public final static int SIDE_SIZE = 50;
    private final int line, col;

    public AbstractPosition(int line, int col) {
        this.line = line;
        this.col = col;
        freeTunnels = new boolean[model.getLines()][model.getCols()];
    }

    void setFreeTunnels (int line, int col) {
        freeTunnels[line][col] = true;
    }
}

