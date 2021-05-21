package pt.ipbeja.estig.po2.boulderdash;

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

public class LerFicheiro {

        public static String[][] readFileToStringArray2D(String filename, String separator) {
        filename = "files/" + filename;
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            String[][] allData = new String[lines.size()][];

            for(int i = 0; i < lines.size(); ++i) {
                if (i == 0) {
                    allData[i] = ((String)lines.get(i)).split(" ");
                }
                else {
                    allData[i] = ((String) lines.get(i)).split(separator);
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


}
