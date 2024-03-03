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


public class BoardCell {
	private int row;
	private int column;
	private char initial;
 	private DoorDirection doorDirection;
 	private char secretPassage;
 	private Set<BoardCell> adjList;
 	private boolean isDoorway;
 	private boolean isCenterCell;
 	private boolean isLabelCell;

 	
 	
 	
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
 	public BoardCell(int row, int column, char initial, DoorDirection doorDirection, boolean isDoorway, boolean isCenterCell, boolean isLabelCell, char secretPassage) {
        this.row = row;
        this.column = column;
        this.initial = initial;
        this.doorDirection = doorDirection;
        this.isDoorway = isDoorway;
        this.isCenterCell = isCenterCell;
        this.isLabelCell = isLabelCell;
        this.secretPassage = secretPassage;

        
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
    	System.out.println(secretPassage);
    	return secretPassage;
    }
    
    
    
}