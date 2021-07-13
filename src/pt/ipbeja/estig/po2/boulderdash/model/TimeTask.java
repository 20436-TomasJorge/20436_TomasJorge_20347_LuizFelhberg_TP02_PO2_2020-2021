package pt.ipbeja.estig.po2.boulderdash.model;

import java.util.TimerTask;

import static pt.ipbeja.estig.po2.boulderdash.gui.Start.textTime;
import static pt.ipbeja.estig.po2.boulderdash.gui.Start.timerValue;

/**
 * @author Tomás Jorge, 20436
 * @author Luiz Felhberg, 20347
 * @version 08/07/2021
 * ---------------------
 * Class defines a task that can be scheduled to run,
 * in this case, was used to run time
 */

public class TimeTask extends TimerTask {

    @Override
    public void run() {
        timerValue++;
        textTime.setText(timerValue + "");
    }

}
