package clueGame;

/**
 * BoardCell Class - This class holds the boardCell constructor and any relevant getters/setters.
 * It also holds any properties a cell could hold. 
 * 
 * @author Hudson Faris
 * @author Sam Bangapadang
 * 
 * Sources: JavaDocs
 * Date: 3/1/2024
 * 
 */

import java.util.Set;


public class BoardCell {
	private int row;
	private int column;
	private char initial;
 	private DoorDirection doorDirection;
 	private boolean roomLabel;
 	private boolean roomCenter;
 	private char secretPassage;
 	private Set<BoardCell> adjList;
 	private boolean isDoorway;

 	
 	/**
 	 * Constructor
 	 * @param row
 	 * @param column
 	 * @param initial
 	 * @param doorDirection
 	 * @param isDoorway
 	 */
 	public BoardCell(int row, int column, char initial, DoorDirection doorDirection, boolean isDoorway) {
        this.row = row;
        this.column = column;
        this.initial = initial;
        this.doorDirection = doorDirection;
        this.isDoorway = isDoorway;
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
    	return false;
    }
    
    public boolean isLabel() {
    	return false;
    }
    
    public char getSecretPassage() {
    	return secretPassage;
    }
    
}