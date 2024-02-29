package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {
    private TestBoardCell[][] grid;
    private Set<TestBoardCell> targets;
    private Set<TestBoardCell> visited;
    private static final int ROWS = 4;
    private static final int COLS = 4;


    public TestBoard() {
        grid = new TestBoardCell[ROWS][COLS];
        targets = new HashSet<>();
        visited = new HashSet<>();
        initBoard();
    }

    private void initBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                grid[row][col] = new TestBoardCell(row, col);
            }
        }
        calculateAdjacencies();
    }

    private void calculateAdjacencies() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                TestBoardCell cell = grid[row][col];
                if (row > 0) cell.addAdjacency(grid[row - 1][col]);
                if (row < ROWS - 1) cell.addAdjacency(grid[row + 1][col]);
                if (col > 0) cell.addAdjacency(grid[row][col - 1]);
                if (col < COLS - 1) cell.addAdjacency(grid[row][col + 1]);
            }
        }
    }

    public TestBoardCell getCell(int row, int col) {
        return grid[row][col];
    }
    
    public void calcTargets(TestBoardCell startCell, int pathLength) {

    	
    	targets.clear(); // Clear targets only at the beginning of the calculation
        visited.clear();
        visited.add(startCell);
        findAllTargets(startCell, pathLength);
     
    }

    private void findAllTargets(TestBoardCell thisCell, int numSteps) {
        // If only one step left, add the adjacent cells to targets and return
    	
    	 if (numSteps == 0) {
    		 System.out.println(thisCell.isRoom());
    		 System.out.println(thisCell.isOccupied());
    	        // Add this cell to targets if it's not the start cell, not occupied, and either a room or a regular cell
    		 if ((thisCell.isRoom() || !thisCell.isOccupied())) {
                 targets.add(thisCell);
                 
    	        }
    	        return;
    	    }

        // Recursively search for targets in adjacent cells
        for (TestBoardCell adjCell : thisCell.getAdjList()) {
            // Skip if the cell has already been visited or is occupied
            if (visited.contains(adjCell) || adjCell.isOccupied()) continue;

            // Add the cell to the visited set and perform the recursive call
            visited.add(adjCell);
            findAllTargets(adjCell, numSteps - 1);
            visited.remove(adjCell); // Remove the cell from visited set after the recursive call
        }
    }

    public Set<TestBoardCell> getTargets() {
        return targets;
    }
}