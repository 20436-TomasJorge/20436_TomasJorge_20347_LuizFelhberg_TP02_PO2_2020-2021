package pt.ipbeja.estig.po2.boulderdash.model;

import java.io.File;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.scene.control.Alert;

/**
 * @author Tomás Jorge, 20436
 * @author Luiz Felhberg, 20347
 * @version 21/05/2021
 * ---------------------
 * Class that has a method that allows reading a file to
 * a 2D String array, also lets you get information about
 * this is what is in the file, like the number of columns
 * the board will have
 */

public class ReadFile {

    // Global Variables
    private final String PATH = "src/resources/files";
    private final String filename;
    private final String separator;
    // End of the Global Variables

    /**
     * Constructor to save the filename and the separator
     * ---------------------------------
     * @param filename -> file level name to be read
     * @param separator -> how do you want to split the file
     */
    public ReadFile(String filename, String separator) {
        this.filename = filename;
        this.separator = separator;
    }

    /**
     * Method used and given by professors in the last semester's project in
     * "Introdução à Programação"
     * ---------------------------------
     * @param filename -> file level name to be read
     * @param separator -> how do you want to split the file
     * @return String array in 2D
     */
    public String[][] readFileToStringArray2D(String filename, String separator) {
        filename = this.PATH + filename;
        try {
            List<String> linesNumber = Files.readAllLines(Paths.get(filename));
            String[][] allData = new String[linesNumber.size()][];

            for(int i = 0; i < linesNumber.size(); ++i) {
                allData[i] = ((String) linesNumber.get(i)).split(separator);
            }

            return allData;
        } catch (IOException exception) {
            String errorMessage = "Error reading file " + filename;
            showError(errorMessage);
            System.out.println(errorMessage + " - Exception " + exception.toString());
            return new String[0][];
        }
    }

    /**
     * Method used and given by professors in the last semester's project in
     * "Introdução à Programação"
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
        String[][] s = this.readFileToStringArray2D(this.filename, " ");
        return Integer.parseInt(s[0][0]);
    }

    /**
     * Method to get the number of columns on the board
     * --------------
     * @return number of columns on the board
     */
    public int getCols() {
        String[][] s = this.readFileToStringArray2D(this.filename, " ");
        return Integer.parseInt(s[0][1]);
    }

    /**
     *
     * get the number of levels in the path
     * --------------
     * @return number of levels
     */
    public int getNumberOfLevels() {
        File file = new File(this.PATH);
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
