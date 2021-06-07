package pt.ipbeja.estig.po2.boulderdash.model;

/**
 * @author Tomás Jorge
 * @version 15/05/2021
 * @number 20436
 */

public class Wall extends AbstractPosition {

    private final int line, col;

    public Wall(int line, int col) {
        super(line, col);
        this.line = line;
        this.col = col;
    }


}
