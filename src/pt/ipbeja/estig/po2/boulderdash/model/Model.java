package pt.ipbeja.estig.po2.boulderdash.model;

import pt.ipbeja.estig.po2.boulderdash.gui.View;
import pt.ipbeja.estig.po2.boulderdash.model.pieces.Rockford;

import java.util.ArrayList;

import static pt.ipbeja.estig.po2.boulderdash.gui.Start.caughtDiamonds;
import static pt.ipbeja.estig.po2.boulderdash.gui.Start.totalDiamonds;

/**
 * @author Tomás Jorge
 * @version 04/06/2021
 * @number 20436
 */

public class Model {

    public final static int SIDE_SIZE = 50;

    public String filename = "level1.txt";
    public final String separator = "";

    private final ReadFile file = new ReadFile(filename, separator);
    private final int lines = file.getLines();
    private final int cols = file.getCols();

    public final String[][] fileArray2D = file.readFileToStringArray2D(getFilename(), separator);

    public static ArrayList<Integer> boulderLines = new ArrayList<>();
    public static ArrayList<Integer> boulderCols = new ArrayList<>();

    public static ArrayList<Integer> diamondsLines = new ArrayList<>();
    public static ArrayList<Integer> diamondsCols = new ArrayList<>();

    private int posLine = 0;
    private int posCol = 0;

    private final View view;

    public Model(View view) {
        this.view = view;
    }

    public void setPositionMovingObjects(int line, int col) {
        rockfordStartPosition(line, col);
        bouldersPositions(line, col);
        diamondsPositions(line, col);
    }

    private void rockfordStartPosition(int line, int col) {
        if(fileArray2D[line][0].equals("J")) {
            if(col % 4 == 0) {
                posCol = col;
            }
            else {
                posLine = col;
            }
            if(posLine != 0 && posCol != 0) {
                posLine = Integer.parseInt(fileArray2D[line][posLine]);
                posCol = Integer.parseInt(fileArray2D[line][posCol]);
                if(!fileArray2D[posLine + 1][posCol].equals("W")) {
                    Rockford.setLine(posLine);
                    Rockford.setCol(posCol);
                    view.onBoardRockfordStart(posLine, posCol);
                }
                else {
                    file.showError("Change Rockford position in file");
                }
                posLine = 0;
                posCol = 0;
            }
        }
    }

    private void bouldersPositions(int line, int col) {
        if(fileArray2D[line][0].equals("P")) {
            if(col % 4 == 0) {
                posCol = col;
            }
            else {
                posLine = col;
            }
            if(posLine != 0 && posCol != 0) {
                posLine = Integer.parseInt(fileArray2D[line][posLine]);
                posCol = Integer.parseInt(fileArray2D[line][posCol]);
                if(!fileArray2D[posLine + 1][posCol].equals("W") && (posLine != Rockford.getLine()
                        || posCol != Rockford.getCol())) {
                    boulderLines.add(posLine);
                    boulderCols.add(posCol);
                }
                else {
                    file.showError("Change boulder position in file");
                }
                posLine = 0;
                posCol = 0;
            }
        }
    }

    private void diamondsPositions(int line, int col) {
        if(fileArray2D[line][0].equals("D")) {
            if(col % 4 == 0) {
                posCol = col;
            }
            else {
                posLine = col;
            }
            if(posLine != 0 && posCol != 0) {
                posLine = Integer.parseInt(fileArray2D[line][posLine]);
                posCol = Integer.parseInt(fileArray2D[line][posCol]);
                if(!fileArray2D[posLine + 1][posCol].equals("W")) {
                    diamondsLines.add(posLine);
                    diamondsCols.add(posCol);
                    totalDiamonds++;
                }
                else {
                    file.showError("Change diamond position in file");
                }
                posLine = 0;
                posCol = 0;
            }
        }
    }

    public void rockfordCatchDiamond() {
        for (int i = 0; i < diamondsLines.size(); i++) {
            if (Rockford.getLine() == diamondsLines.get(i) && Rockford.getCol() == diamondsCols.get(i)) {
                caughtDiamonds += 1;
            }
        }
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
