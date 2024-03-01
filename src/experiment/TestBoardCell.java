/**
 * TestBoardCell Class - Holding many getters/setters for private variables.  This creates cell/room and their aspects
 * different stages. 
 * 
 * @author Hudson Faris
 * @author Sam Bangapadang
 * 
 * Sources: JavaDocs
 * Date: 2/29/2024
 * 
 */




package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	private int row;
	private int col;
	private Set<TestBoardCell> adjList;
	
	private boolean isRoom;
	private boolean isOccupied;
	
	
	
	/**
	 * Creates adjList hashset and constructor for cells. 
	 * @param row
	 * @param col
	 */
	
	public TestBoardCell(int row, int col) {
		this.row = row;
		this.col = col;
		this.adjList = new HashSet<>();
		
		
	}
	
	//All getters/setters
	public void addAdjacency(TestBoardCell cell) {
		adjList.add(cell);
	}
	
	public Set<TestBoardCell> getAdjList() {
		return adjList;
	}
	
	public void setRoom(boolean room) {
        isRoom= room;
       
    }

    public boolean isRoom() {
         return isRoom;
    }
	
	public void setOccupied(boolean occupied) {
		isOccupied = occupied;
	
	}
	
	public boolean isOccupied() {
		return isOccupied; 
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
}



