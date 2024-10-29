package tests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {

    private static Board board;

    @BeforeAll
    public static void setUp() {
        board = Board.getInstance();
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        board.initialize();
    }

    // Adjacency Tests

    @Test
    public void testAdjacencyWalkways() {
        // Test a walkway that only connects to other walkways
        Set<BoardCell> testList = board.getAdjList(19, 11); 
        assertEquals(3, testList.size());
        assertTrue(testList.contains(board.getCell(19, 12))); // Change to True -- Not connected to doorway//Edge
        assertTrue(testList.contains(board.getCell(19, 10))); //Edge
        assertTrue(testList.contains(board.getCell(18, 11))); 
    }

    @Test
    public void testAdjacencyRooms() {
        // Test a room that is center and find adjacent doorways. 
        Set<BoardCell> testList = board.getAdjList(20, 2);
        assertEquals(2, testList.size()); 
    }

    @Test
    public void testAdjacencyDoorways() {
        // Test a doorway adjacency (doorways should connect to room center and adjacent walkways)
        Set<BoardCell> testList = board.getAdjList(17, 19);
        assertTrue(testList.contains(board.getCell(17, 15)));  //Doorway
        assertTrue(testList.contains(board.getCell(13, 19)));
        testList = board.getAdjList(10, 2);
        assertFalse(testList.contains(board.getCell(9, 15)));  //Secret Passage
        testList = board.getAdjList(2, 3);
        assertFalse(testList.contains(board.getCell(1, 12)));

    }

    // Target Tests
    
    @Test
    public void testTargetsWalkways() {
        // Test targets along walkways
        board.calcTargets(board.getCell(3, 6), 1); 
        Set<BoardCell> targets = board.getTargets(); //Along walkway
        assertEquals(2, targets.size()); 
        board.calcTargets(board.getCell(16, 2), 3);
        assertEquals(2, targets.size());
        assertFalse(targets.contains(board.getCell(20, 2))); //enter room
        assertFalse(targets.contains(board.getCell(15, 3))); //leave room

    }

    @Test
    public void testTargetsEnterRoom() {
        // Test targets that allow a user to enter a room
        board.calcTargets(board.getCell(4, 3), 1); 
        Set<BoardCell> targets = board.getTargets();
        board.getCell(4, 4).setOccupied(true);
        board.getCell(2, 3).setOccupied(true);
        assertTrue(targets.contains(board.getCell(4, 4)));
        assertTrue(targets.contains(board.getCell(2, 3)));  //Enter/leave room
        assertTrue(targets.contains(board.getCell(4, 2))); //blocked by other user
        assertTrue(targets.contains(board.getCell(5, 3)));
        
    }

    
}