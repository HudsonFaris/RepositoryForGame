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

import java.util.HashSet;
import java.util.Set;
import java.awt.Color;
import java.awt.Graphics;

public class BoardCell {

    // Property declarations
    private int row, col;
    private char initial;
    private boolean roomLabel, roomCenter, isRoom, isDoorway, isOccupied, isUnused, isPassage, walkway;
    private char secretPassage;
    private Set<BoardCell> adjList = new HashSet<>();
    private DoorDirection doorDirection;

    // Constructor
    public BoardCell(int row, int col) {
        this.row = row;
        this.col = col;
    }

//Getters and setters
    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public void setDoorDirection(DoorDirection dd) {
        doorDirection = dd;
    }

    public void addAdj(BoardCell cell) {
        adjList.add(cell);
    }

    // Setters
    public void setInitial(char initial) {
        this.initial = initial;
    }
    
    public void setRoom(boolean room) {
        isRoom = room;
    }

    public void setRoomLabel() {
        this.roomLabel = true;
    }

    public void setDoorway() {
        this.isDoorway = true;
    }

    public void setSecretPassage(char passage) {
        secretPassage = passage;
        isPassage = true;
    }

    public void setUnused() {
        isUnused = true;
    }
    
    public void setRoomCenter() {
        this.roomCenter = true;
    }

    public void setWalkway() {
        this.walkway = true;
    }

    // Getters
    public int getRow() {
        return row;
    }

    public int getColumn() {
        return col;
    }

    public boolean isRoomLabel() {
        return roomLabel;
    }
    
    public char getInitial() {
        return initial;
    }

    public boolean isRoom() {
        return isRoom;
    }

    public boolean isRoomCenter() {
        return roomCenter;
    }

    public boolean isDoorway() {
        return isDoorway;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public boolean isWalkway() {
        return walkway;
    }

    public boolean isPassage() {
        return isPassage;
    }

    public boolean isUnused() {
        return isUnused;
    }

    public DoorDirection getDoorDirection() {
        return doorDirection;
    }

    public Set<BoardCell> getAdjList() {
        return adjList;
    }
    
    public char getSecretPassage() {
        return secretPassage;
    }

	
    //Draws cell method for each kind of cell. 
    public void drawCell(Graphics boardView, int width, int height, int xOffset, int yOffset, Board board) {
        drawWalkwayIfNeeded(boardView, width, height, xOffset, yOffset);
        drawRoomIfNeeded(boardView, width, height, xOffset, yOffset);
        drawDoorwayIfNeeded(boardView, width, height, xOffset, yOffset);
        drawUnusedCellIfNeeded(boardView, width, height, xOffset, yOffset);
        drawRoomLabelIfNeeded(boardView, xOffset, yOffset, board);
        highlightCellIfNeeded(boardView, width, height, xOffset, yOffset, board);
    }

    
    //Helper method. 
    private void drawWalkwayIfNeeded(Graphics boardView, int width, int height, int xOffset, int yOffset) {
        if (isWalkway()) {
            boardView.setColor(Color.BLACK);
            boardView.drawRect(xOffset, yOffset, width, height);
            boardView.setColor(Color.YELLOW);
            boardView.fillRect(xOffset + 1, yOffset + 1, width - 1, height - 1);
        }
    }

    
    //Helper method
    
    private void drawRoomIfNeeded(Graphics boardView, int width, int height, int xOffset, int yOffset) {
        if (isRoom()) {
            boardView.setColor(Color.GRAY);
            boardView.fillRect(xOffset, yOffset, width, height);
        }
    }

    
    //Helper method
    private void drawDoorwayIfNeeded(Graphics boardView, int width, int height, int xOffset, int yOffset) {
        if (isDoorway()) {
            boardView.setColor(Color.BLUE);
            switch (getDoorDirection()) { //Ignore
                case UP:
                    boardView.fillRect(xOffset, yOffset, width, 5);
                    break;
                case LEFT:
                    boardView.fillRect(xOffset, yOffset, 5, height);
                    break;
                case DOWN:
                    boardView.fillRect(xOffset, yOffset + height - 5, width, 5);
                    break;
                case RIGHT:
                    boardView.fillRect(xOffset + width - 5, yOffset, 5, height);
                    break;
            }
        }
    }

    
    //Helper method
    private void drawUnusedCellIfNeeded(Graphics boardView, int width, int height, int xOffset, int yOffset) {
        if (getInitial() == 'X') {
            boardView.setColor(Color.BLACK);
            boardView.fillRect(xOffset, yOffset, width, height);
        }
    }

    
    //Helper method
    private void drawRoomLabelIfNeeded(Graphics boardView, int xOffset, int yOffset, Board board) {
        if (isRoomLabel() && isRoom()) {
            boardView.setColor(Color.BLUE);
            boardView.drawString(board.getRoom(getInitial()).getName(), xOffset, yOffset);
        }
    }

    
    //Helper method
    private void highlightCellIfNeeded(Graphics boardView, int width, int height, int xOffset, int yOffset, Board board) {
        if (board.getTargets().contains(this) && board.humanPlayerTurn) {
            boardView.setColor(Color.DARK_GRAY);
            boardView.fillRect(xOffset, yOffset, width, height);
        }
    }

	//Check? done. -hudson
	

}