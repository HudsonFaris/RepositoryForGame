/**
 * BoardTestsExp Class - This class includes all the tests for a 4x4 made. Testing normal
 * cells and edge cases
 * 
 * @author Hudson Faris
 * @author Sam Bangapadang
 * 
 * Sources: JavaDocs
 * Date: 2/24/2024
 * 
 */


package tests;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import experiment.TestBoard;
import experiment.TestBoardCell;

public class BoardTestsExp {
    private TestBoard board;

    @BeforeEach
    public void setUp() {
        board = new TestBoard(); // Assumes a 4x4 grid
    }

    @Test
    public void testAdjacency() {
        // Top left corner (0,0)
        TestBoardCell cell = board.getCell(0, 0);
        Set<TestBoardCell> testList = cell.getAdjList();
        assertEquals(2, testList.size());
        assertTrue(testList.contains(board.getCell(1, 0)));
        assertTrue(testList.contains(board.getCell(0, 1)));

        // Bottom right corner (3,3)
        cell = board.getCell(3, 3);
        testList = cell.getAdjList();
        assertEquals(2, testList.size());
        assertTrue(testList.contains(board.getCell(2, 3)));
        assertTrue(testList.contains(board.getCell(3, 2)));

        // A right edge (1,3)
        cell = board.getCell(1, 3);
        testList = cell.getAdjList();
        assertEquals(3, testList.size());
        assertTrue(testList.contains(board.getCell(0, 3)));
        assertTrue(testList.contains(board.getCell(1, 2)));
        assertTrue(testList.contains(board.getCell(2, 3)));

        // A left edge (3,0)
        cell = board.getCell(3, 0);
        testList = cell.getAdjList();
        assertEquals(2, testList.size());
        assertTrue(testList.contains(board.getCell(2, 0)));
        assertTrue(testList.contains(board.getCell(3, 1)));

        // Middle of grid (2,2)
        cell = board.getCell(2, 2);
        testList = cell.getAdjList();
        assertEquals(4, testList.size());
        assertTrue(testList.contains(board.getCell(1, 2)));
        assertTrue(testList.contains(board.getCell(2, 1)));
        assertTrue(testList.contains(board.getCell(3, 2)));
        assertTrue(testList.contains(board.getCell(2, 3)));
    }

    //@Test
    public void testTargetsNormal() {
        // Test with 1 step from (0,0)
        TestBoardCell startCell = board.getCell(0, 0);
        board.calcTargets(startCell, 1);
        Set<TestBoardCell> targets = board.getTargets();
        assertEquals(2, targets.size());

        // Test with 2 steps from (0,0)
        board.calcTargets(startCell, 2);
        targets = board.getTargets();
        assertEquals(3, targets.size());

        // Test with 3 steps from (0,0), testing different starting locations
        board.calcTargets(startCell, 3);
        targets = board.getTargets();
        assertTrue(targets.contains(board.getCell(3, 0)));
        assertTrue(targets.contains(board.getCell(2, 1)));
        assertTrue(targets.contains(board.getCell(0, 1)));
        assertTrue(targets.contains(board.getCell(1, 2)));
        assertTrue(targets.contains(board.getCell(0, 3)));
        assertTrue(targets.contains(board.getCell(1, 0)));
        assertEquals(6, targets.size());

        // Test with 6 steps from (1,1), testing max die roll
        startCell = board.getCell(1, 1);
        board.calcTargets(startCell, 6);
        targets = board.getTargets();
        assertEquals(10, targets.size()); // Assuming all cells are reachable with 6 steps from (1,1)
    }

    @Test
    public void testTargetsOccupied() {
        // Assuming (1,1) is occupied
        board.getCell(1, 1).setOccupied(true);
        TestBoardCell startCell = board.getCell(0, 0);
        board.calcTargets(startCell, 2);
        Set<TestBoardCell> targets = board.getTargets();
        assertFalse(targets.contains(board.getCell(1, 1)));
        assertEquals(2, targets.size()); // Adjust based on the board logic

        board.getCell(1, 1).setOccupied(false); // Reset for next tests
    }

    //@Test
    public void testTargetsRoom() {
        // Assuming (1,1) is a room
        board.getCell(1, 1).setRoom(true);
        TestBoardCell startCell = board.getCell(0, 0);
        board.calcTargets(startCell, 2);
        Set<TestBoardCell> targets = board.getTargets();
        assertTrue(targets.contains(board.getCell(1, 1)), "Includes room as target");

        // Starting from a room, assuming (2,2) is a room
        board.getCell(2, 2).setRoom(true);
        startCell = board.getCell(2, 2);
        board.calcTargets(startCell, 1);
        targets = board.getTargets();
        assertEquals(4, targets.size(), "Starting from room targets adjacent cells");
        assertFalse(targets.contains(startCell), "Does not include the room itself");
    }

    //@Test
    public void testTargetsMixed() {
        // Mixed scenario with (1,1) as occupied and (2,2) as a room
        board.getCell(1, 1).setOccupied(true);
        board.getCell(2, 2).setRoom(true);
        TestBoardCell startCell = board.getCell(0, 0);
        board.calcTargets(startCell, 3);
        Set<TestBoardCell> targets = board.getTargets();
        assertTrue(targets.contains(board.getCell(2, 2)), "Includes room in targets");
        assertFalse(targets.contains(board.getCell(1, 1)), "Excludes occupied cell");
    }
}
