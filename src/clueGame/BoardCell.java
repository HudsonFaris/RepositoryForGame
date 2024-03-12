package clueGame;

/**
 * BoardCell Class - This class holds the boardCell constructor and any relevant getters/setters.
 * It also holds any properties a cell could hold. 
 * 
 * @author Hudson Faris
 * @author Sam Bangapadang
 * 
 * Sources: JavaDocs
 * Date: 3/8/2024
 * 
 */

import java.util.Set;
import java.util.HashSet;


public class BoardCell {
    private int row;
    private int column;
    private char initial;
    private DoorDirection doorDirection;
    private char secretPassage;
    private Set<BoardCell> adjList = new HashSet<>();
    private boolean isDoorway, isCenterCell, isLabelCell, occupied, isRoom, isSecretPassage;

    // Main constructor
    public BoardCell(int row, int column, char initial, DoorDirection doorDirection,
                     boolean isDoorway, boolean isCenterCell, boolean isLabelCell, 
                     char secretPassage, boolean isRoom, boolean isSecretPassage) {
        this.row = row;
        this.column = column;
        this.initial = initial;
        this.doorDirection = doorDirection;
        this.isDoorway = isDoorway;
        this.isCenterCell = isCenterCell;
        this.isLabelCell = isLabelCell;
        this.secretPassage = secretPassage;
        this.isRoom = isRoom;
        this.isSecretPassage = isSecretPassage;
    }

    // Simplified constructor for basic cells
    public BoardCell(int row, int column) {
        this(row, column, ' ', DoorDirection.NONE, false, false, false, '0', false, false);
    }

    // Getters and setters
    public int getRow() { return row; }
    
    public int getCol() { return column; }
    
    public char getInitial() { return initial; }
    
    public DoorDirection getDoorDirection() { return doorDirection; }
    
    public char getSecretPassage() { return secretPassage; }
    
    public Set<BoardCell> getAdjList() { return adjList; }
    
    public boolean isDoorway() { return isDoorway; }
    
    public boolean isRoomCenter() { return isCenterCell; }
    
    public boolean isLabel() { return isLabelCell; }
    
    public boolean isOccupied() { return occupied; }
    
    public boolean isRoom() { return isRoom; }
    
    public boolean isSecretPassage() { return isSecretPassage; }

    public void setOccupied(boolean occupied) { this.occupied = occupied; }
    
    public void addAdj(BoardCell adj) { adjList.add(adj); }
}