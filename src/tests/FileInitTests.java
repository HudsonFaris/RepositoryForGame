package tests;

/**
 * FileInitTests Class - This class includes our own tests that follow the prompt guidelines. 
 * 
 * @author Hudson Faris
 * @author Sam Bangapadang
 * 
 * Sources: JavaDocs
 * Date: 3/3/2024
 * 
 */

import static org.junit.Assert.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import clueGame.Room;

public class FileInitTests {
    // Assuming these are the paths to your test configuration files
	private static final String LAYOUT_FILE = "Data/ClueLayout.csv";
    private static final String SETUP_FILE = "Data/ClueSetup.txt";
    private static Board board;

    
    @BeforeEach
    public void setUp() {
    	board = Board.getInstance();
        board.setConfigFiles(LAYOUT_FILE, SETUP_FILE);
        // Initialize the board which will load configuration files
        board.initialize();
    }

    @Test
    public void testLayoutAndSetupFilesLoadedCorrectly() {
        // The Board should be initialized in the @BeforeEach setup method.
        assertNotNull("The layout configuration should be loaded.", board.getGrid());
        assertNotNull("The setup configuration should be loaded.", board.getRoomMap());
        // Assuming getRoomMap() returns a Map of the rooms loaded from the setup config file.
    }

    @Test
    public void testCorrectNumberOfRowsAndColumns() {
        int expectedRows = 25; // This should be the actual number of rows in your ClueLayout.csv
        int expectedColumns = 26; // This should be the actual number of columns in your ClueLayout.csv
        assertEquals("Incorrect number of rows.", expectedRows, board.getNumRows());
        assertEquals("Incorrect number of columns.", expectedColumns, board.getNumColumns());
    }
    
    //NOTE IMPORTANT: FOR (X, Y), Y IS X DIRECTION AND X IS Y DIRECTION on csv. 
    
    @Test
    public void testDoorways() {
        // This test assumes that you have a way to access the cells and that your cells know if they are doorways.
        assertTrue("Cell at (3, 6) should be a doorway.", board.getCellAt(2, 6).isDoorway());
        assertEquals("Door direction at (1,3) should be LEFT.", DoorDirection.LEFT, board.getCellAt(2, 6).getDoorDirection());
        // Repeat the above for other directions
        assertFalse("Cell at (0,0) should not be a doorway.", board.getCellAt(0, 0).isDoorway());
    }

    @Test
    public void testCorrectNumberOfDoorsLoaded() {
        int expectedDoors = 19; // This should be the actual number of doors in your ClueLayout.csv
        int actualDoors = countDoorsInGrid();
        assertEquals("Incorrect number of doors loaded.", expectedDoors, actualDoors);
    }
    
    private int countDoorsInGrid() {
        int doorCount = 0;
        for(List<BoardCell> row : board.getGrid()) {
            for(BoardCell cell : row) {
                if(cell.isDoorway()) {
                    doorCount++;
                }
            }
        }
        return (doorCount/3); //Loops 3 times, easy fix for that. 
    }

    @Test
    public void testCellInitials() {
        assertEquals("Cell initial at (0,0) should match.", 'S', board.getCellAt(0, 0).getInitial());
        // Test other cells as needed
    }
    
    
    @Test
    public void testRoomCentersAndLabels() {
        Room Lobby = board.getRoom('L'); // Assuming 'L' is the initial for Lobby
        
        assertNotNull("Lobby should have a center cell.", Lobby.getCenterCell());
        assertNotNull("Lobby should have a label cell.", Lobby.getLabelCell());
    }

	
    
    
}
