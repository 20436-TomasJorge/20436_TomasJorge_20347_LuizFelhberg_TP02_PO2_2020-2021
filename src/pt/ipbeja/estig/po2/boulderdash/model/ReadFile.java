package pt.ipbeja.estig.po2.boulderdash.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javafx.scene.control.Alert;

/**
 * @author Tomás Jorge
 * @version 21/05/2021
 * @number 20436
 * ---------------------
 * Class that has a method that allows reading a file to
 * a 2D String array, also lets you get information about
 * this is what is in the file, like the number of columns
 * the board will have
 */

public class ReadFile {


    private final String PATH = "src/resources/files";
    private final String filename;
    private final String separator;

    /**
     * Constructor to save the filename and the separator
     * if you need to use the getter's methods
     */
    public ReadFile(String filename, String separator) {
        this.filename = filename;
        this.separator = separator;
    }

    /**
     * Method used in last semester's project in Introdução à Programação
     * --------------
     * @param filename -> mention the location and name of the file to be read
     * @param separator -> how do you want to split the file
     * @return String array in 2D
     */
    public String[][] readFileToStringArray2D(String filename, String separator) {
        filename = PATH + filename;
        try {
            List<String> linesNumber = Files.readAllLines(Paths.get(filename));
            String[][] allData = new String[linesNumber.size()][];

            for(int i = 0; i < linesNumber.size(); ++i) {
                allData[i] = ((String) linesNumber.get(i)).split(separator);
            }

            return allData;
        } catch (IOException var5) {
            String errorMessage = "Error reading file " + filename;
            showError(errorMessage);
            System.out.println(errorMessage + " - Exception " + var5.toString());
            return new String[0][];
        }
    }

    public void createNewFile(String filename) {

    }

    /**
     * Method used in last semester's project in Introdução à Programação
     * --------------
     * @param message -> message to be displayed when path or filename is wrong
     */
    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Method to get the number of lines on the board
     * --------------
     * @return number of lines on the board
     */
    public int getLines() {
        String[][] s = readFileToStringArray2D(filename, separator);
        return Integer.parseInt(s[0][0]);
    }

    /**
     * Method to get the number of columns on the board
     * --------------
     * @return number of columns on the board
     */
    public int getCols() {
        String[][] s = readFileToStringArray2D(filename, separator);
        return Integer.parseInt(s[0][2]);
    }

    public int getNumberOfLevels() {
        File file = new File(PATH);
        File[] files = file.listFiles();
        int count = 0;
        if(files != null) {
            for (File f : files) {
                count++;
            }
        }
        return count;
    }
}
