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
import java.awt.Color;
import java.awt.Graphics;
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
 	private boolean isWalkway;
 	
 	
 	
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
 	
 	
 	public BoardCell(int row, int column, char initial, DoorDirection doorDirection, boolean isDoorway, boolean isCenterCell, boolean isLabelCell, char secretPassage, boolean isRoom, boolean isSecretPassage, boolean isWalkway) {
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
        this.isWalkway = isWalkway;

        
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

    public int getCol() {
		// TODO Auto-generated method stub
		return column;
	}


	public int getRow() {
	// TODO Auto-generated method stub
	return row;
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
    
    public boolean isWalkway() {
    	return isWalkway;
    }
    
    public void drawCell(Graphics boardView,  int width,  int height, int xOffset, int yOffset, Board board) {
		if(this.isWalkway()) {
			boardView.setColor(Color.BLACK);
			boardView.drawRect(xOffset, yOffset, width, height);
			boardView.setColor(Color.YELLOW);
			boardView.fillRect(xOffset + 1, yOffset + 1, width - 1, height - 1);
		}
		if(this.isRoom()) {
			boardView.setColor(Color.GRAY);
			boardView.fillRect(xOffset, yOffset, width, height);
			
		}
		if(this.isDoorway()) {
			boardView.setColor(Color.BLUE);
			switch (this.getDoorDirection()) {
			case UP:
				boardView.fillRect(xOffset, yOffset, width, 5);
				break;
			case LEFT:
				boardView.fillRect(xOffset, yOffset, 5, height);
				break;
			case DOWN:
				boardView.fillRect(xOffset, yOffset + height - 5, width, 5);
				break;
			default:
				boardView.fillRect(xOffset + width - 5, yOffset, 5, height);
				break;
			}
		}
		if(this.getInitial() == 'X') {
			boardView.setColor(Color.BLACK);
			boardView.fillRect(xOffset, yOffset, width, height);
		}
		if(this.isLabel()) {
			boardView.setColor(Color.BLUE);
			boardView.drawString(board.getRoom(this.getInitial()).getName(), xOffset, yOffset);
		}
		if(board.getTargets().contains(this) && board.humanPlayerTurn) {
			boardView.setColor(Color.DARK_GRAY);
			boardView.fillRect(xOffset, yOffset, width, height);
		}
	}


	public int getCol1() {
		return column;
	}
 
    
}