package clueGame;

/**
 * Board Class - This class holds a board constructor and configs for loading files. It has getters/setters
 * relevant to the board interacting with other things. 
 * 
 * @author Hudson Faris
 * @author Sam Bangapadang
 * 
 * Sources: JavaDocs
 * Date: 3/3/2024
 * 
 */

import java.util.Map;
import java.util.Set;

import experiment.TestBoardCell;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;



public class Board {
	private List<List<BoardCell>> grid;
    private int row;
    private int column;
    private String layoutConfigFile;
    private String setupConfigFile;
    private Set<BoardCell> targets;
    private Set<BoardCell> visited;
    private Map<Character, Room> roomMap;
    private static Board theInstance = new Board();

    public void reset() {
        grid.clear(); // Clear the existing grid
        roomMap.clear(); // Clear the existing rooms
     
        this.row = 0;
        this.column = 0;
    }
    /**
     * Constructor Singleton Pattern
     */
    private Board() {
        super();
        grid = new ArrayList<>();
        roomMap = new HashMap<>();
        targets = new HashSet<>();
        visited = new HashSet<>();
    }
    
        
    
    public void setLayoutConfigFile(String layoutConfigFile) {
        this.layoutConfigFile = layoutConfigFile;
    }

    public static Board getInstance() {
        return theInstance;
    }
    
    
    //Calls intialization. 
    public void initialize() {
    	reset();
        try {
            loadSetupConfig();
            loadLayoutConfig();
        } catch (BadConfigFormatException e) {
            System.out.println("Test");
            e.printStackTrace();
        }
    }
    
 // Method to get the entire grid
    public List<List<BoardCell>> getGrid() {
        return grid;
    }

    // Method to get a single cell from the grid
    public BoardCell getCellAt(int row, int col) {
        return grid.get(row).get(col);
    }

    // Method to get the room map
    public Map<Character, Room> getRoomMap() {
        return roomMap;
    }

    
    
    // Method to get a room by initial
    public Room getRoom(char initial) {
        return roomMap.get(initial);
    }
    

    // Method to get the number of rows
    public int getNumRows() {
    	return row;
        
    }

    public int getNumColumns() {
        // Assumes that the grid is not jagged and all rows have the same number of columns
    	//System.out.println(grid.isEmpty() ? 0 : grid.get(0).size());
        return grid.isEmpty() ? 0 : grid.get(0).size();
    }
    
    public void setConfigFiles(String layoutConfigFileName, String setupConfigFileName) {
        // Assuming the files are in the project root directory
        layoutConfigFile = "data/" + layoutConfigFileName;
        setupConfigFile = "data/" + setupConfigFileName;
    }


    
    /**
     * Takes in reader and splits lines. Basically creates the roomMap. 
     * @throws BadConfigFormatException
     */
    public void loadSetupConfig() throws BadConfigFormatException {
        // Initialize roomMap or other necessary fields
        try (BufferedReader reader = new BufferedReader(new FileReader(setupConfigFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(", ");
                if (tokens.length >= 3) { // Make sure there are at least 3 tokens
                    char key = tokens[2].charAt(0);
                    if (tokens[0].equals("Room") || tokens[0].equals("Space")) {
                        roomMap.put(key, new Room(tokens[1]));
                    } else {
                        // Handle other types of lines or throw an exception if the format is unrecognized
                    }
                }
            }
        } catch (IOException e) {
            throw new BadConfigFormatException("Cannot read setup config file: " + e.getMessage());
        }
        
    }

    
    /**
     * Handles the layout by splitting and adding each cell to it's appropriate location. 
     * @throws BadConfigFormatException
     */
    public void loadLayoutConfig() throws BadConfigFormatException {
        try (BufferedReader br = new BufferedReader(new FileReader(layoutConfigFile))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }
                String[] cellValues = line.split(",");
                List<BoardCell> boardRow = new ArrayList<>();
                for (int column = 0; column < cellValues.length; column++) {
                    if (cellValues[column].trim().isEmpty()) {
                        // Handle empty cells appropriately, perhaps with a default cell value
                        boardRow.add(createBoardCell(row, column, "defaultCellValue"));
                    } else {
                        boardRow.add(createBoardCell(row, column, cellValues[column].trim()));
                    }
                }
                grid.add(boardRow);
                row++;
            }
            
            this.row = row;
         
        } catch (IOException e) {
            throw new BadConfigFormatException("Error reading layout configuration file.");
        }
        /*
         * Added loops to check for initial to set center and label. Not previously made. 
         */
        for (List<BoardCell> row : grid) {
        	for (BoardCell cell : row) {
        		if (cell.isCenterCell()) {
        			Room room = roomMap.get(cell.getInitial());
        			if (room != null) {
        				room.setCenterCell(cell);
        			}
        		}
        		if (cell.isLabelCell()) {
        			Room room = roomMap.get(cell.getInitial());
        			if (room != null) {
        				room.setLabelCell(cell);
        			}
        		}
        	}
        }
        calculateAdj(); //Calculate adjacencies
        
    }
    
    
    /**
     * Creates the boardCell and holds DoorDirections. 
     * @param row
     * @param column
     * @param cellValue
     * @return
     */
    private BoardCell createBoardCell(int row, int column, String cellValue) {
        char initial = cellValue.charAt(0);
        char secretPassage = '\0';
        
        boolean isCenterCell = cellValue.length() > 1 && cellValue.charAt(1) == '*';
        boolean isLabelCell = cellValue.length() > 1 && cellValue.charAt(1) == '#';
        DoorDirection doorDirection = DoorDirection.NONE;
        boolean isDoorway = false;
        cellValue = cellValue.trim();
        boolean isRoom = false;
        
        if (cellValue.charAt(0) != 'W' || cellValue.charAt(0) != 'X') {
        	isRoom = true;
        }
        
        if (cellValue.length() > 1) {
        	char secondChar = cellValue.charAt(1);
        	if (Character.isLetter(secondChar)) {
                secretPassage = secondChar;
        	}
        	
            // This assumes the second character indicates the door direction
            isDoorway = true;
            switch (cellValue.charAt(1)) {
                case '^':
                    doorDirection = DoorDirection.UP;
                    break;
                case 'v':
                    doorDirection = DoorDirection.DOWN;
                    break;
                case '<':
                    doorDirection = DoorDirection.LEFT;
                    break;
                case '>':
                    doorDirection = DoorDirection.RIGHT;
                    break;
                default:
                    isDoorway = false; // Invalid direction, not a doorway
                    break;
            }
        }

        return new BoardCell(row, column, initial, doorDirection, isDoorway, isCenterCell, isLabelCell, secretPassage, isRoom);
    }
    
    //Other get cell for updated test methods
    public BoardCell getCell(int numRows, int numCols) {
    	return grid.get(numRows).get(numCols);
    }
    
    
    //Gets initial and uses map to get room
    public Room getRoom(BoardCell cell) {
        if (cell == null) {
            return null;
        }
        char initial = cell.getInitial();
        return roomMap.get(initial);
    }
    
    public void calcTargets(BoardCell startCell, int pathLength) {
        targets.clear();
        visited.clear();
        visited.add(startCell);
        findAllTargets(startCell, pathLength);
    }

    
    /**
     * Calculates adjacent targets for path == 0. Acts as helper function to method above. 
     */
    private void calculateAdj() {
        for (int row = 0; row < getNumRows(); row++) {
            for (int col = 0; col < getNumColumns(); col++) {
                BoardCell cell = getCellAt(row, col);
                // Check if the cell is not null before proceeding
                if (cell != null) {
                	
                	//if (cell.isRoomCenter()) {
                      //  if (cell.getDoorDirection() == DoorDirection.DOWN && (row+1) ==  {
                        	
                    //    }
                    //}

                    // Add adjacent cells, checking bounds
                    if (row > 0) cell.addAdj(getCellAt(row - 1, col));
                    if (row < getNumRows() - 1) cell.addAdj(getCellAt(row + 1, col));
                    if (col > 0) cell.addAdj(getCellAt(row, col - 1));
                    if (col < getNumColumns() - 1) cell.addAdj(getCellAt(row, col + 1));
                }
            }
        }
    }
    
    /**
    private void findDoorwaysIntoRoom(BoardCell roomCenter, int roomRow, int roomCol) {
        // Check all cells to see if they are doorways that lead into this room
        for (int row = 0; row < getNumRows(); row++) {
            for (int col = 0; col < getNumColumns(); col++) {
                BoardCell cell = getCellAt(row, col);
                if (cell.isDoorway()) {
                	System.out.println(cell);
                    // Check if the door direction points to the room center
                    if ((cell.getDoorDirection() == DoorDirection.DOWN) ||
                        (cell.getDoorDirection() == DoorDirection.UP && roomRow == row + 1 && roomCol == col) ||
                        (cell.getDoorDirection() == DoorDirection.RIGHT && roomRow == row && roomCol == col - 1) ||
                        (cell.getDoorDirection() == DoorDirection.LEFT && roomRow == row && roomCol == col + 1)) {
                        roomCenter.addAdj(cell); // Add the doorway as an adjacency to the room center
                        System.out.println(cell);
                    }
                }
            }
        }
    }
    
    **/
    
   
    
    // Recursive method to find all targets
    private void findAllTargets(BoardCell thisCell, int numSteps) {
        if (numSteps == 0) {
            targets.add(thisCell);
            return;
        }

        for (BoardCell adjCell : thisCell.getAdjList()) { //Not implemented
            if (visited.contains(adjCell)) continue;

            visited.add(adjCell);
            findAllTargets(adjCell, numSteps - 1);
            visited.remove(adjCell);
        }
    }

    public Set<BoardCell> getTargets() {
        return targets;
    }
    
    
    /**
     * Returns set of adjList. 
     * @param row
     * @param col
     * @return
     */
    public Set<BoardCell> getAdjList(int row, int col) {
        // Retrieve the cell at the specified row and column
        BoardCell cell = getCellAt(row, col);

        // Return the adjacency list of the cell
        if (cell != null) {
            return cell.getAdjList();
        } else {
            // If the cell is not found, return an empty set
            return new HashSet<>();
        }
    }
    
    

}