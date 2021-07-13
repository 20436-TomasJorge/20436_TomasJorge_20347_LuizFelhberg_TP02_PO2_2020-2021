package pt.ipbeja.estig.po2.boulderdash.model;

import jdk.internal.dynalink.beans.StaticClass;
import pt.ipbeja.estig.po2.boulderdash.gui.Board;
import pt.ipbeja.estig.po2.boulderdash.gui.View;
import pt.ipbeja.estig.po2.boulderdash.model.pieces.Rockford;

/**
 * @author Tomás Jorge, 20436
 * @author Luiz Felhberg, 20347
 * @version 04/06/2021
 */

public class Model {

    // Global Variables
    public final static int SIDE_SIZE = 35;

    public int countBoards = 0;
    public int totalDiamonds = 0;
    public int moves = 0;
    public int points = 0;
    public int caughtDiamonds = 0;

    public String filename = "/level3.txt";
    public final String separator = "";

    private final ReadFile file = new ReadFile(this.getFilename(), this.getSeparator());
    public final int lines = this.file.getLines();
    public final int cols = this.file.getCols();


    public final String[][] fileArray2D = this.file.readFileToStringArray2D(this.getFilename(), this.separator);

    public final String[][] fileToMovingObjs = this.file.readFileToStringArray2D(this.getFilename(), " ");

    private int posLine = 0;
    private int posCol = 0;

    private final View view;
    // End of the Global Variables

    /**
     * Model  constructor
     * -------------------
     * @param view -> initialize the interface View
     */
    public Model(View view) {
        this.view = view;
    }

    /**
     * Verify if the position at {line, col} is free
     * -------------------
     * @param line -> line to verify
     * @param col -> column to verify
     * @return true if the position at {line, col} is free,
     * false if the position is occupied
     */
    public boolean isPositionFree(int line, int col) {
        if(this.fileArray2D[line + 1][col].equals("F")
                || this.fileArray2D[line + 1][col].equals("O")
                || this.fileArray2D[line + 1][col].equals("G")) {
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

    /**
     * Call all methods that save moving objects position
     * --------------------
     * @param line -> line that is being read from the file
     * @param col -> column that is being read from the file
     */
    public void setPositionMovingObjects(int line, int col) {
        this.rockfordStartPosition(line, col);
        this.bouldersPositions(line, col);
        this.diamondsPositions(line, col);
        this.immovableEnemiesPosition(line, col);
    }

    /**
     * Find the position where Rockford will start
     * --------------------
     * @param line -> line that is being read from the file
     * @param col -> column that is being read from the file
     */
    private void rockfordStartPosition(int line, int col) {
        if(this.fileToMovingObjs[line][0].equals("J") ) {
            if(col % 2 == 0) {
                this.posCol = col;
            }
            else {
                this.posLine = col;
            }
            if(this.posLine != 0 && this.posCol != 0) {
                this.posLine = Integer.parseInt(this.fileToMovingObjs[line][this.posLine]);
                this.posCol = Integer.parseInt(this.fileToMovingObjs[line][this.posCol]);
                if(!this.fileArray2D[this.posLine + 1][this.posCol].equals("W")) {
                    Rockford.setLine(this.posLine);
                    Rockford.setCol(this.posCol);
                    this.view.onBoardRockfordStart(this.posLine, this.posCol);
                }
                else {
                    this.file.showError("Change Rockford position in file");
                }
                this.posLine = 0;
                this.posCol = 0;
            }
        }
    }

    /**
     * Find the positions where Boulders will start and
     * save in a ArrayList
     * --------------------
     * @param line -> line that is being read from the file
     * @param col -> column that is being read from the file
     */
    private void bouldersPositions(int line, int col) {
        if(this.fileToMovingObjs[line][0].equals("P") && col > 0) {
            if(col % 2 == 0) {
                this.posCol = col;
            }
            else {
                this.posLine = col;
            }
            if(this.posLine != 0 && this.posCol != 0) {
                this.posLine = Integer.parseInt(this.fileToMovingObjs[line][this.posLine]);
                this.posCol = Integer.parseInt(this.fileToMovingObjs[line][this.posCol]);
                if(!this.fileArray2D[this.posLine + 1][this.posCol].equals("W")
                        && (this.posLine != Rockford.getLine()
                        || this.posCol != Rockford.getCol())) {
                    Board.boulderLines.add(this.posLine);
                    Board.boulderCols.add(this.posCol);
                }
                else {
                    this.file.showError("Change boulder position in file");
                }
                this.posLine = 0;
                this.posCol = 0;
            }
        }
    }

    /**
     * Find the positions where Diamonds will start and
     * save in a ArrayList
     * --------------------
     * @param line -> line that is being read from the file
     * @param col -> column that is being read from the file
     */
    private void diamondsPositions(int line, int col) {
        if(this.fileToMovingObjs[line][0].equals("D") && col > 0) {
            if(col % 2 == 0) {
                this.posCol = col;
            }
            else {
                this.posLine = col;
            }
            if(this.posLine != 0 && this.posCol != 0) {
                this.posLine = Integer.parseInt(this.fileToMovingObjs[line][this.posLine]);
                this.posCol = Integer.parseInt(this.fileToMovingObjs[line][this.posCol]);
                if(!this.fileArray2D[posLine + 1][posCol].equals("W")) {
                    Board.diamondsLines.add(posLine);
                    Board.diamondsCols.add(posCol);
                    this.posLine = 0;
                    this.posCol = 0;
                }
                else {
                    this.file.showError("Change diamond position in file");
                }
                this.posLine = 0;
                this.posCol = 0;
            }
        }
    }

    /**
     * Find the positions where Immovable enemies will start and
     * save in a ArrayList
     * --------------------
     * @param line -> line that is being read from the file
     * @param col -> column that is being read from the file
     */
    private void immovableEnemiesPosition(int line, int col) {
        if(this.fileToMovingObjs[line][0].equals("I") && col > 0) {
            if(col % 2 == 0) {
                this.posCol = col;
            }
            else {
                this.posLine = col;
            }
            if(this.posLine != 0 && this.posCol != 0) {
                this.posLine = Integer.parseInt(this.fileToMovingObjs[line][this.posLine]);
                this.posCol = Integer.parseInt(this.fileToMovingObjs[line][this.posCol]);
                if(!this.fileArray2D[this.posLine + 1][this.posCol].equals("W")) {
                    Board.imoEnemiesLines.add(this.posLine);
                    Board.imoEnemiesCols.add(this.posCol);
                    this.posLine = 0;
                    this.posCol = 0;
                }
                else {
                    this.file.showError("Change Immovable Enemy position in file");
                }
                this.posLine = 0;
                this.posCol = 0;
            }
        }
    }

    /**
     * If Rockford catch a diamond, this diamond will be removed
     * from the ArrayList and the number of the variable "caughtDiamonds"
     * will increase
     */
    public void rockfordCatchDiamond() {
        for (int i = 0; i < Board.diamondsLines.size(); i++) {
            if (diamondCaught(i)) {
                this.caughtDiamonds++;
                this.points += (100 * this.caughtDiamonds) - (5 * this.moves);
                Board.diamondsLines.remove(i);
                Board.diamondsCols.remove(i);
            }
        }
    }

    /**
     * Verify if Rockford is at the same position at one of
     * diamonds position
     * --------------------
     * @param i -> index of the ArrayList
     * @return true if the Rockford and diamond position is the same,
     * false if is the positions are not the same
     */
    public boolean diamondCaught(int i) {
        return Rockford.getLine() == Board.diamondsLines.get(i)
                && Rockford.getCol() == Board.diamondsCols.get(i);
    }

    /**
     * Verify if Rockford is at the same position at one of
     * the immovable enemies position
     * --------------------
     * @param i -> index of the ArrayList
     * @return true if the Rockford and enemy position is the same,
     * false if is the positions are not the same
     */
    public boolean rockfordMoveToImoEnemy(int i) {
        return Rockford.getLine() == Board.imoEnemiesLines.get(i)
                && Rockford.getCol() == Board.imoEnemiesCols.get(i);
    }

    /**
     * Get the file name and search for the number
     * of the level
     * --------------------
     * @return number of the level
     */
    public int getLevel() {
        int level = 0;
        for (int i = 0; i < this.filename.length(); i++) {
            for (int numbers = 1; numbers < 10; numbers++) {
                if(String.valueOf(this.filename.charAt(i)).equals(String.valueOf(numbers))) {
                    level = Integer.parseInt(String.valueOf(this.filename.charAt(i)));
                }
            }
        }
        if(level <= 0) this.file.showError("This Level doesn´t Exist: Level " + level);
        return level;
    }

    /**
     * @return number of the lines that the board have
     */
    public int getBoardLines() {
        return this.lines;
    }

    /**
     * @return number of the columns that the board have
     */
    public int getBoardCols() {
        return this.cols;
    }

    /**
     * @return the view, initialized at the beginning
     */
    public View getView() {
        return this.view;
    }

    /**
     * @return the separator, initialized at the beginning
     */
    public String getSeparator() {
        return this.separator;
    }

    /**
     * @return file name
     */
    public String getFilename() {
        return this.filename;
    }

    /**
     * Change the file name to change the level
     * of the game
     * -----------------
     * @param level -> next level to play
     */
    public void setFilename(int level) {
        this.filename = "/level" + level + ".txt";
    }
}
