package tests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {

    private static Board board;

    @BeforeAll
    public static void setUp() {
        board = Board.getInstance();
        board.setConfigFiles("Data/ClueLayout.csv", "Data/ClueSetup.txt");
        board.initialize();
    }

    // Adjacency Tests

    //@Test
    public void testAdjacencyWalkways() {
        // Test a walkway that only connects to other walkways
        Set<BoardCell> testList = board.getAdjList(3, 3); // Replace with actual cell
        assertEquals(4, testList.size()); // Replace with expected number of adjacent cells
        assertTrue(testList.contains(board.getCell(3, 4)));
        assertTrue(testList.contains(board.getCell(3, 2)));
        // Add more assertions as needed
    }

    //@Test
    public void testAdjacencyRooms() {
        // Test a room that is not a center and should have an empty adjacency list
        Set<BoardCell> testList = board.getAdjList(1, 1); // Replace with actual cell
        assertEquals(0, testList.size());
    }

    //@Test
    public void testAdjacencyDoorways() {
        // Test a doorway adjacency (doorways should connect to room center and adjacent walkways)
        Set<BoardCell> testList = board.getAdjList(4, 4); // Replace with actual cell
        assertTrue(testList.contains(board.getCell(4, 5))); // Replace with room center cell
        // Add more assertions as needed
    }

    // Target Tests

    @Test
    public void testTargetsWalkways() {
        // Test targets along walkways
        board.calcTargets(board.getCell(5, 5), 2); 
        Set<BoardCell> targets = board.getTargets();
        assertEquals(8, targets.size()); 
        assertTrue(targets.contains(board.getCell(5, 7)));
        // Add more assertions as needed
    }

    @Test
    public void testTargetsEnterRoom() {
        // Test targets that allow a user to enter a room
        board.calcTargets(board.getCell(6, 6), 3); // Replace with actual cell and distance
        Set<BoardCell> targets = board.getTargets();
        assertTrue(targets.contains(board.getCell(7, 8))); // Replace with room center or doorway cell
        // Add more assertions as needed
    }

    // Add more tests for other scenarios, like exiting a room, secret passages, and blocking by other players
}