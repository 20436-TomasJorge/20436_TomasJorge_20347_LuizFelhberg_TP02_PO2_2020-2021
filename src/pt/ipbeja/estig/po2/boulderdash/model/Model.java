package pt.ipbeja.estig.po2.boulderdash.model;

import pt.ipbeja.estig.po2.boulderdash.gui.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Tomás Jorge
 * @version 04/06/2021
 * @number 20436
 */

public class Model {

    public String filename = "level1.txt";
    public final String separator = "";

    private final ReadFile file = new ReadFile(filename, separator);
    private final int lines = file.getLines();
    private final int cols = file.getCols();

    private View view;

    public Model(View view) {
        this.view = view;

    }

    public int getLines() {
        return lines;
    }

    public int getCols() {
        return cols;
    }

    public String getSeparator() {
        return separator;
    }

    public String getFilename() {
        return filename;
    }

    public int getLevel() {
        int level = 0;
        for (int i = 0; i < filename.length(); i++) {
            for (int numbers = 1; numbers < 10; numbers++) {
                if(String.valueOf(filename.charAt(i)).equals(String.valueOf(numbers))) {
                    level = Integer.parseInt(String.valueOf(filename.charAt(i)));
                }
            }
        }
        if(level <= 0) file.showError("This Level doesn´t Exist: Level " + level);
        return level;
    }

    public void setFilename(int level) {
        this.filename = "level" + level + ".txt";
    }

}
