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
    }
	
	/**
	 * 
	 * @param startCell
	 * @param pathLength
	 */
	public void calcTargets(TestBoardCell startCell, int pathLength) {
        // Stub: No actual calculation logic
        targets.clear(); // Clear previous targets
    }
	
	/**
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	
	public TestBoardCell getCell(int row, int col) {
        return new TestBoardCell(row, col); // Return a new cell for failure
    }
	
	/**
	 * 
	 * @return
	 */
	
	public Set<TestBoardCell> getTargets() {
        return new HashSet<>(); // Return an empty set for failure
    }
}
