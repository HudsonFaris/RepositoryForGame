/**
 * TestBoardCell Class - This class holds many tests/getters/setters functions that don't do anything at the moment. 
 * 
 * @author Hudson Faris
 * @author Sam Bangapadang
 * 
 * Sources: JavaDocs
 * Date: 2/24/2024
 * 
 */




package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	private int row;
	private int col;
	private Set<TestBoardCell> adjList;
	private boolean isRoom = false;
	private boolean isOccupied = false;
	
	/**
	 * 
	 * @param row
	 * @param col
	 */
	
	
	public TestBoardCell(int row, int col) {
		this.row = row;
		this.col = col;
		this.adjList = new HashSet<>();
		
		// just initialization for now
	}
	
	public void addAdjacency(TestBoardCell cell) {
		adjList.add(cell);
	}
	
	public Set<TestBoardCell> getAdjList() {
		return adjList;
	}
	
	public void setRoom(boolean room) {
		//System.out.println("Cell at (" + row + "," + col + ") set as Room: " + room);
        isRoom = room;
        
    }

    public boolean isRoom() {
        
        return isRoom;
    }
	
	public void setOccupied(boolean occupied) {
		isOccupied = occupied;
		//System.out.println("Cell at (" + row + "," + col + ") set as Occupied: " + occupied);
	}
	
	public boolean getOccupied() {
		return isOccupied; 
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
}



