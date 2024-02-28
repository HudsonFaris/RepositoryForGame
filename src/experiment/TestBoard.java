package experiment;

import java.util.Set;
import java.util.HashSet;

/**
 * TestBoard Class - This class creates a testboard cell and targets, it holds getters and a calculate target function
 * that will be utilized later in the class
 * 
 * @author Hudson Faris
 * @author Sam Bangapadang
 * 
 * Sources: JavaDocs
 * Date: 2/24/2024
 * 
 */


public class TestBoard {
	private TestBoardCell[][] board;
	private Set<TestBoardCell> targets;
	final static int COLS = 4;
	final static int ROWS = 4;
	
	
	
	/**
	 * Creating TestBoard
	 */
	
	public TestBoard() {
		board = new TestBoardCell[ROWS][COLS];
        targets = new HashSet<>();
        // Initialize the board with cells
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                board[row][col] = new TestBoardCell(row, col);
            }
        }
        
        calculateAdjacencies(); //recursive definition
    }
	
	/**
	 * 
	 * @param startCell
	 * @param pathLength
	 */
	public void calcTargets(TestBoardCell startCell, int pathLength) {
		targets.clear(); // Clear previous targets
	    Set<TestBoardCell> visited = new HashSet<>();
	    visited.add(startCell); //Add start cell to visited
	    calcTargetsHelper(startCell, pathLength, visited); //Easier to seperate
	}
	
	
	private void calcTargetsHelper(TestBoardCell cell, int pathLength, Set<TestBoardCell> visited) {
		if (pathLength == 0 || cell.getOccupied()) { //Odd case if already occupied or length 0 to add
            targets.add(cell);
            return;
        }

        for (TestBoardCell adjCell : cell.getAdjList()) { //Looping through adjacent list
            if (!visited.contains(adjCell)) {
                visited.add(adjCell); //If visited list aligns with adjacent cell add it
                calcTargetsHelper(adjCell, pathLength - 1, new HashSet<>(visited)); //Recursive
                visited.remove(adjCell);
            }
        }
    }

	
	/**
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	
	public TestBoardCell getCell(int row, int col) {
		if (row < 0 || row >= ROWS || col < 0 || col >= COLS) { //Check to avoid error
			return null;
		}
        return board[row][col];
    }
	
	/**
	 * 
	 * @return
	 */
	
	public Set<TestBoardCell> getTargets() {
        return targets; 
    }
	
	
	public void calculateAdjacencies() { //Adjacent calculator loops through rows/cols for full board and adds to list
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				TestBoardCell cell = board[row][col];
				if (row > 0) {
					cell.addAdjacency(board[row-1][col]);
				}
				if (row < ROWS - 1 ) {
					cell.addAdjacency(board[row+1][col]);
				}
				if (col > 0) {
					cell.addAdjacency(board[row][col -1]);
				}
				if (col < COLS - 1) {
					cell.addAdjacency(board[row][col+1]);
				}
				
			}
		}
	}
}



