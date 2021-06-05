package pt.ipbeja.estig.po2.boulderdash.view;

import pt.ipbeja.estig.po2.boulderdash.model.AbstractPosition;

import java.util.List;

/**
 * @author Tomás Jorge
 * @version 21/05/2021
 * @number 20436
 */
public interface View {

    void levelPassed();

    void gamelost();

    void onBoardRockfordMove();
}
