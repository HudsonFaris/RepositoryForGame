package tests;

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
    private static final String SETUP_FILE = "Data/ClueSetup306.txt";
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
        int expectedRows = 20; // This should be the actual number of rows in your ClueLayout.csv
        int expectedColumns = 20; // This should be the actual number of columns in your ClueLayout.csv
        assertEquals("Incorrect number of rows.", expectedRows, board.getNumRows());
        assertEquals("Incorrect number of columns.", expectedColumns, board.getNumColumns());
    }


    @Test
    public void testDoorways() {
        // This test assumes that you have a way to access the cells and that your cells know if they are doorways.
        assertTrue("Cell at (1,3) should be a doorway.", board.getCellAt(1, 3).isDoorway());
        assertEquals("Door direction at (1,3) should be RIGHT.", DoorDirection.RIGHT, board.getCellAt(1, 3).getDoorDirection());
        // Repeat the above for other directions
        assertFalse("Cell at (0,0) should not be a doorway.", board.getCellAt(0, 0).isDoorway());
    }

    @Test
    public void testCorrectNumberOfDoorsLoaded() {
        int expectedDoors = 12; // This should be the actual number of doors in your ClueLayout.csv
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
        return doorCount;
    }

    @Test
    public void testCellInitials() {
        assertEquals("Cell initial at (0,0) should match.", 'C', board.getCellAt(0, 0).getInitial());
        // Test other cells as needed
    }

    @Test
    public void testRoomCentersAndLabels() {
        Room kitchen = board.getRoom('K'); // Assuming 'K' is the initial for Kitchen
        assertNotNull("Kitchen should have a center cell.", kitchen.getCenterCell());
        assertNotNull("Kitchen should have a label cell.", kitchen.getLabelCell());
    }

    
    
}
