package pt.ipbeja.estig.po2.boulderdash.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javafx.scene.control.Alert;

/**
 * @author Tomás Jorge
 * @version 21/05/2021
 * @number 20436
 */

public class ReadFile {


    private final String filename;
    private final String separator;

    public ReadFile(String filename, String separator) {
        this.filename = filename;
        this.separator = separator;
    }

    public static String[][] readFileToStringArray2D(String filename, String separator) {
        try {
            List<String> linesNumber = Files.readAllLines(Paths.get(filename));
            String[][] allData = new String[linesNumber.size()][];

            for(int i = 0; i < linesNumber.size(); ++i) {
                if (i == 0) {
                    allData[i] = ((String)linesNumber.get(i)).split(" ");
                }
                else {
                    allData[i] = ((String) linesNumber.get(i)).split(separator);
                }
            }

            return allData;
        } catch (IOException var5) {
            String errorMessage = "Error reading file " + filename;
            showError(errorMessage);
            System.out.println(errorMessage + " - Exception " + var5.toString());
            return new String[0][];
        }
    }

    private static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public int getLines() {
        String[][] s = readFileToStringArray2D(filename, separator);
        return Integer.getInteger(s[0][0]);
    }


    public int getCols() {
        String[][] s = readFileToStringArray2D(filename, separator);
        return Integer.getInteger(s[0][2]);
    }


}
