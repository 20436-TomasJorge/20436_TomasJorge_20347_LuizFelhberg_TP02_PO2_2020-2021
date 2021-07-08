package pt.ipbeja.estig.po2.boulderdash.model;

import pt.ipbeja.estig.po2.boulderdash.gui.Board;
import pt.ipbeja.estig.po2.boulderdash.gui.View;
import pt.ipbeja.estig.po2.boulderdash.model.pieces.Rockford;

/**
 * @author Tomás Jorge
 * @version 04/06/2021
 * @number 20436
 */

public class Model {

    public final static int SIDE_SIZE = 50;

    public int countBoards = 0;
    public int totalDiamonds = 0;
    public int moves = 0;
    public int points = 0;
    public int caughtDiamonds = 0;

    public String filename = "/level1.txt";
    public final String separator = "";

    private final ReadFile file = new ReadFile(getFilename(), separator);
    public final int lines = file.getLines();
    public final int cols = file.getCols();

    public final String[][] fileArray2D = file.readFileToStringArray2D(getFilename(), separator);

    private int posLine = 0;
    private int posCol = 0;

    private final View view;

    public Model(View view) {
        this.view = view;
    }

    public boolean isPositionFree(int line, int col) {
        if(fileArray2D[line + 1][col].equals("F")
                || fileArray2D[line + 1][col].equals("O")
                || fileArray2D[line + 1][col].equals("G")) {
            for (int i = 0; i < Board.boulderLines.size(); i++) {
                if(line == Board.boulderLines.get(i) && col == Board.boulderCols.get(i)) {
                    System.out.println("Boulder. BAD MOVE");
                    return false;
                }
            }
            System.out.println("Free Position. GOOD MOVE");
            return true;
        }
        else {
            System.out.println("Wall. BAD MOVE");
            return false;
        }
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
                    Board.boulderLines.add(posLine);
                    Board.boulderCols.add(posCol);
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
                    Board.diamondsLines.add(posLine);
                    Board.diamondsCols.add(posCol);
                    posLine = 0;
                    posCol = 0;
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
        for (int i = 0; i < Board.diamondsLines.size(); i++) {
            if (diamondCaught(i)) {
                caughtDiamonds++;
                points += (100 * caughtDiamonds) - (5 * moves);
                Board.diamondsLines.remove(i);
                Board.diamondsCols.remove(i);
            }
        }
    }

    public boolean diamondCaught(int i) {
        return Rockford.getLine() == Board.diamondsLines.get(i) && Rockford.getCol() == Board.diamondsCols.get(i);
    }

    public int getLines() {
        return lines;
    }

    public int getCols() {
        return cols;
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

    public View getView() {
        return view;
    }

    public String getSeparator() {
        return separator;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(int level) {
        this.filename = "/level" + level + ".txt";
    }
}
