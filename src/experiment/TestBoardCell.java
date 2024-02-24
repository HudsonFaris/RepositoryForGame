package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	private int row;
	private int col;
	private Set<TestBoardCell> adjList;
	private boolean isRoom;
	private boolean isOccupied;
	
	
	public TestBoardCell(int row, int col) {
		this.row = row;
		this.col = col;
		this.adjList = new HashSet<>();
		// just initialization for now
	}
	
	public void addAdjanecy(TestBoardCell cell) {
		//Blank
	}
	
	public Set<TestBoardCell> getAdjList() {
		return new HashSet<>(); // return empty set for now
	}
	
	public void setRoom(boolean room) {
		//Blank
	}
	
	public boolean isRoom() {
		return false; // default to false for failure
	}
	
	public void setOccupied(boolean occupied) {
		//Blank
	}
	
	public boolean getOccupied() {
		return false; // default to false for failure
	}
}



