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
        targets.clear();
        visited.clear();
        visited.add(startCell);
        findAllTargets(startCell, pathLength);
    }

    private void findAllTargets(TestBoardCell thisCell, int numSteps) {
        if (numSteps == 1) {
            // When numSteps is 1, add all valid adjacent cells to targets
            for (TestBoardCell adjCell : thisCell.getAdjList()) {
                if (!adjCell.getOccupied() || adjCell.isRoom()) { // Add if not occupied or is a room
                	 System.out.println("Adding to targets: [" + adjCell.getRow() + "][" + adjCell.getCol() + "]"); // Debug line
                    targets.add(adjCell);
                }
            }
         // Debugging output, if needed
            System.out.println("Current Cell: [" + thisCell.getRow() + "][" + thisCell.getCol() + "]");
            System.out.print("Adjacent Cells: ");
            for (TestBoardCell adj : thisCell.getAdjList()) {
                System.out.print("(" + adj.getRow() + "," + adj.getCol() + ") ");
            }
            System.out.println();
        } else {
            // For numSteps greater than 1, use the existing logic
            for (TestBoardCell adjCell : thisCell.getAdjList()) {
                if (visited.contains(adjCell) || (adjCell.getOccupied() && !adjCell.isRoom())) continue;

                visited.add(adjCell);
                findAllTargets(adjCell, numSteps - 1);
                visited.remove(adjCell);
            }
        }

        
        
        System.out.print("Current Targets: ");
        for (TestBoardCell target : targets) {
            System.out.print("(" + target.getRow() + "," + target.getCol() + ") ");
        }
        System.out.println();
    }

    public Set<TestBoardCell> getTargets() {
        return targets;
    }
}