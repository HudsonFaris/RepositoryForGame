package experiment;

import java.util.Set;
import java.util.HashSet;

public class TestBoard {
	private TestBoardCell[][] board;
	private Set<TestBoardCell> targets;
	private static final int MAX_SIZE = 20;
	
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
	
	public void calcTargets(TestBoardCell startCell, int pathLength) {
        // Stub: No actual calculation logic
        targets.clear(); // Clear previous targets
    }
	
	public TestBoardCell getCell(int row, int col) {
        return new TestBoardCell(row, col); // Return a new cell for failure
    }
	
	public Set<TestBoardCell> getTargets() {
        return new HashSet<>(); // Return an empty set for failure
    }
}
