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
	private static final int MAX_SIZE = 20;
	
	
	/**
	 * Creating TestBoard
	 */
	
	public TestBoard() {
		board = new TestBoardCell[MAX_SIZE][MAX_SIZE];
        targets = new HashSet<>();
        // Initialize the board with cells
        for (int row = 0; row < MAX_SIZE; row++) {
            for (int col = 0; col < MAX_SIZE; col++) {
                board[row][col] = new TestBoardCell(row, col);
            }
        }
        
        calculateAdjacencies();
    }
	
	/**
	 * 
	 * @param startCell
	 * @param pathLength
	 */
	public void calcTargets(TestBoardCell startCell, int pathLength) {
        // Stub: No actual calculation logic
        targets.clear(); // Clear previous targets
        
        Set<TestBoardCell> visited = new HashSet<>();
        calcTargetsHelper(startCell, pathLength, visited);
    }
	
	
	private void calcTargetsHelper(TestBoardCell cell, int pathLength, Set<TestBoardCell> visited) {
		
		visited.add(cell);

	    if (cell.isRoom() && !cell.getOccupied()) {
	        targets.add(cell);
	        System.out.println("Added room cell to targets: " + cell.getRow() + ", " + cell.getCol());
	    } else if (pathLength == 0) {
	        if (!cell.getOccupied()) {
	            targets.add(cell);
	        }
	    } else {
	        for (TestBoardCell adjCell : cell.getAdjList()) {
	            if (!visited.contains(adjCell) && !adjCell.getOccupied()) {
	                calcTargetsHelper(adjCell, pathLength - 1, visited);
	            }
	        }
	    }
	    visited.remove(cell); // Backtrack
	}
	
	/**
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	
	public TestBoardCell getCell(int row, int col) {
		if (row < 0 || row >= MAX_SIZE || col < 0 || col >= MAX_SIZE) {
			return null;
		}
        return board[row][col]; // Return a new cell for failure
    }
	
	/**
	 * 
	 * @return
	 */
	
	public Set<TestBoardCell> getTargets() {
        return targets; 
    }
	
	
	public void calculateAdjacencies() {
		for (int row = 0; row < MAX_SIZE; row++) {
			for (int col = 0; col < MAX_SIZE; col++) {
				TestBoardCell cell = board[row][col];
				if (row > 0) {
					cell.addAdjacency(board[row-1][col]);
				}
				if (row < MAX_SIZE -1 ) {
					cell.addAdjacency(board[row+1][col]);
				}
				if (col > 0) {
					cell.addAdjacency(board[row][col -1]);
				}
				if (col < MAX_SIZE - 1) {
					cell.addAdjacency(board[row][col+1]);
				}
				
			}
		}
	}
}



