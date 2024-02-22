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
		adjacencyList.add(cell);
	}
	
	public Set<TestBoardCell> getAdjList() {
		return adjacencyList;
	}
	
	public void setRoom(boolean room) {
		this.isRoom = room;
	}
	
	public boolean getRoom() {
		return isRoom;
	}
	
	public void setOccupied(boolean occupied) {
		this.isOccupied = occupied;
	}
	
	public boolean getOccupied() {
		return isOccupied;
	}
}



