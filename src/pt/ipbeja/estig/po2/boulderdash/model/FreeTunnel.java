package pt.ipbeja.estig.po2.boulderdash.model;

import javafx.scene.image.Image;
import pt.ipbeja.estig.po2.boulderdash.gui.Board;
import pt.ipbeja.estig.po2.boulderdash.gui.View;

import java.util.List;

/**
 * @author Tomás Jorge
 * @version 15/05/2021
 * @number 20436
 */

public class FreeTunnel extends AbstractPosition {

    private final int line, col;

    public FreeTunnel(int line, int col) {
        super(line, col);
        this.line = line;
        this.col = col;
        this.setImage();
        this.setFreeTunnels(line, col);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.println("{"+i+", "+j+"} = " + freeTunnels[i][j]);
            }
        }
        System.out.println("---------");
    }

    public void setImage() {
        Image img = new Image("/resources/images/freeTunnel.png");
        this.setImage(img);
        this.setFitHeight(SIDE_SIZE);
        this.setFitWidth(SIDE_SIZE);
    }
}
