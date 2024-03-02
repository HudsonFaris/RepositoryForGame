package clueGame;

/**
 * Board Class - This class holds a board constructor and configs for loading files. It has getters/setters
 * relevant to the board interacting with other things. 
 * 
 * @author Hudson Faris
 * @author Sam Bangapadang
 * 
 * Sources: JavaDocs
 * Date: 3/1/2024
 * 
 */

import java.util.Map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Board {
	private List<List<BoardCell>> grid;
    private int row;
    private int column;
    private String layoutConfigFile;
    private String setupConfigFile;
    private Map<Character, Room> roomMap;
    private static Board theInstance = new Board();

    
    /**
     * Constructor Singleton Pattern
     */
    private Board() {
        super();
        grid = new ArrayList<>();
        roomMap = new HashMap<>();
    }
    
    public void setLayoutConfigFile(String layoutConfigFile) {
        this.layoutConfigFile = layoutConfigFile;
    }

    public static Board getInstance() {
        return theInstance;
    }

    
    //Calls intialization. 
    public void initialize() {
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
    
    public void setConfigFiles(String layoutConfigFile, String setupConfigFile) {
        this.layoutConfigFile = layoutConfigFile;
        this.setupConfigFile = setupConfigFile;
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
        boolean isCenterCell = cellValue.length() > 1 && cellValue.charAt(1) == '*';
        boolean isLabelCell = cellValue.length() > 1 && cellValue.charAt(1) == '#';
        DoorDirection doorDirection = DoorDirection.NONE;
        boolean isDoorway = false;
        cellValue = cellValue.trim();

        if (cellValue.length() > 1) {
      
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

        return new BoardCell(row, column, initial, doorDirection, isDoorway, isCenterCell, isLabelCell);
    }
    
    
    public BoardCell getCell(int numRows, int numCols) {
    	return grid.get(numRows).get(numCols);
    }
    
    public Room getRoom(BoardCell cell) {
    	return new Room("");
    }
    
    
   

}


