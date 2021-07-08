package pt.ipbeja.estig.po2.boulderdash.model;

import java.util.TimerTask;

import static pt.ipbeja.estig.po2.boulderdash.gui.Start.timerValue;
import static pt.ipbeja.estig.po2.boulderdash.gui.Start.textTime;

/**
 * @author Tomás Jorge
 * @version 08/07/2021
 * @number 20436
 */

public class TimeTask extends TimerTask {
    @Override
    public void run() {
        timerValue++;
        textTime.setText(timerValue + "");
    }
}
