package clueGame;

/**
 * BoardCell Class - This class holds the boardCell constructor and any relevant getters/setters.
 * It also holds any properties a cell could hold. 
 * 
 * @author Hudson Faris
 * @author Sam Bangapadang
 * 
 * Sources: JavaDocs
 * Date: 3/3/2024
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
 	private boolean isDoorway;
 	private boolean isCenterCell;
 	private boolean isLabelCell;
 	private boolean occupied = false; // New property to track if the cell is occupied
 	private boolean isRoom;
 	private boolean isSecretPassage;
 	
 	
 	
 	/**
 	 * Constructor for each cell. 
 	 * @param row
 	 * @param column
 	 * @param initial
 	 * @param doorDirection
 	 * @param isDoorway
 	 * @param isCenterCell
 	 * @param isLabelCell
 	 * @param secretPassage
 	 */
 	
 	
 	public BoardCell(int row, int column, char initial, DoorDirection doorDirection, boolean isDoorway, boolean isCenterCell, boolean isLabelCell, char secretPassage, boolean isRoom, boolean isSecretPassage) {
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
 	
 	
 	public boolean isCenterCell() {
        return this.isCenterCell;
    }
 	
 	public boolean isLabelCell() {
 		return this.isLabelCell;
 	}
 	
 	public void addAdj(BoardCell adj) {
 		adjList.add(adj);
 	}
 	
 	public BoardCell(int row, int column) {
        this.row = row;
        this.column = column;
    }
 	

 	public boolean isDoorway() {
        return isDoorway;
    }
 	
 // Getters for the door direction and initial
    public DoorDirection getDoorDirection() {
        return doorDirection;
    }

    public char getInitial() {
        return initial;
    }
    
    public boolean isRoomCenter() {
    	return isCenterCell;
    }
    
    public boolean isLabel() {
    	return isLabelCell;
    }
    
    public char getSecretPassage() {
    	return secretPassage;
    }
    
    public boolean isSecretPassage() {
    	return isSecretPassage;
    }


 // Updated to return the Set of adjacent BoardCells
    public Set<BoardCell> getAdjList() {
        return adjList;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
    
    public boolean isOccupied() {
        return occupied;
    }
    
    public boolean isRoom() {
    	return isRoom;
    }
 
    
}