package experiment;

import java.util.Set;

public class TestBoardCell {
	private int row;
	private int col;
	Set<TestBoardCell> adjacencyList;
	private boolean isRoom;
	private boolean isOccupied;
	
	
	
	
	public TestBoardCell(int row, int col) {
		this.row = row;
		this.col = col;
		
	}
	
	public void addAdjanecy(TestBoardCell cell) {
		//Blank
	}
	
	public Set<TestBoardCell> getAdjList() {
		return adjacencyList;
	}
	
	public void setRoom(boolean room) {
		//Blank
	}
	
	public boolean getRoom() {
		return true;
	}
	
	public void setOccupied(boolean occupied) {
		//Blank
	}
	
	public boolean getOccupied() {
		return isOccupied;
	}
}



