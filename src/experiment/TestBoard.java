package experiment;

import java.util.Set;

public class TestBoard {
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets;
	
	
	
	public TestBoard() {
		//Blank
	}
	
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		//Blank
	}
	
	public TestBoardCell getCell(int row, int col) {
		return null; //Not entirely sure what to return here. 
	}
	
	public Set<TestBoardCell> getTargets() {
		return targets;
		//maybe hashtable return new HashSet<>();
	}
}
