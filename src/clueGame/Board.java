package clueGame;

/**
 * Board Class - This class holds a board constructor and configs for loading files. It has getters/setters
 * relevant to the board interacting with other things. 
 * 
 * @author Hudson Faris
 * @author Sam Bangapadang
 * 
 * Sources: JavaDocs
 * Date: 3/8/2024
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
    public int i = 0;
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
        
    	
    	//Getters for each cell
    }
    public int getRow() {
    	return row;
        
    }
    
    public int getCol() {
    	return column;
        
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
                processLine(line, row++);
            }
            this.row = row;
        } catch (IOException e) {
            throw new BadConfigFormatException("Error reading layout configuration file.");
        }
        setCentersAndLabels();
        calculateAdj(); //Calculate adjacencies
    }

    private void processLine(String line, int row) {
        String[] cellValues = line.split(",");
        List<BoardCell> boardRow = new ArrayList<>();
        for (int column = 0; column < cellValues.length; column++) {
            String cellValue = cellValues[column].trim();
            boardRow.add(createBoardCell(row, column, cellValue.isEmpty() ? "defaultCellValue" : cellValue));
        }
        grid.add(boardRow);
    }

    private void setCentersAndLabels() {
        for (List<BoardCell> row : grid) {
            for (BoardCell cell : row) {
                if (cell.isRoomCenter()) {
                    Room room = roomMap.get(cell.getInitial());
                    if (room != null) {
                        room.setCenterCell(cell);
                    }
                }
                if (cell.isLabel()) {
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
        cellValue = cellValue.trim();
        char initial = cellValue.charAt(0);
        char secondChar = cellValue.length() > 1 ? cellValue.charAt(1) : '0';
        boolean isRoom = initial != 'W' && initial != 'X';
        boolean isCenterCell = secondChar == '*';
        boolean isLabelCell = secondChar == '#';
        boolean isSecretPassage = Character.isLetter(secondChar) && secondChar != 'v';
        char secretPassage = isSecretPassage ? secondChar : '0';
        
        DoorDirection doorDirection = getDoorDirection(secondChar);
        boolean isDoorway = doorDirection != DoorDirection.NONE;

        return new BoardCell(row, column, initial, doorDirection, isDoorway, isCenterCell, isLabelCell, secretPassage, isRoom, isSecretPassage);
    }

    private DoorDirection getDoorDirection(char secondChar) {
        switch (secondChar) {
            case '^': return DoorDirection.UP;
            case 'v': return DoorDirection.DOWN;
            case '<': return DoorDirection.LEFT;
            case '>': return DoorDirection.RIGHT;
            default:  return DoorDirection.NONE;
        }
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
    
    
    //Initial calctargets method/reset with helper method
    public void calcTargets(BoardCell startCell, int pathLength) {
        targets.clear();
        visited.clear();
        
        visited.add(startCell);
        boolean startInRoom = startCell.isRoomCenter();
        findAllTargets(startCell, pathLength, startInRoom);
        
    }

    
    /**
     * Calculates adjacent targets for path == 0. Acts as helper function to method above. 
     */
    private void calculateAdj() {
        for (int row = 0; row < getNumRows(); row++) {
            for (int col = 0; col < getNumColumns(); col++) {
                BoardCell cell = getCellAt(row, col);
                // Check if the cell is not null before proceeding
                if (cell != null) { //If cell exists, mark it as adjacent cell as long as not 'X' or not room
                	if ((row > 0)) {
                	    BoardCell adjacentCell = getCellAt(row-1, col);
                	    if (adjacentCell != null && !adjacentCell.isRoom() && adjacentCell.getInitial() != 'X') {  // Check that the adjacent cell is not null
                	    	cell.addAdj(adjacentCell);
                	    }
                	}
                	if ((row < getNumRows()-1)) {
                	    BoardCell adjacentCell = getCellAt(row+1, col);
                	    if (adjacentCell != null  && !adjacentCell.isRoom()&& adjacentCell.getInitial() != 'X') {  // Check that the adjacent cell is not null
                	    	cell.addAdj(adjacentCell);
                	    }
                	}
                	
                	if (col > 0) {
                	    BoardCell adjacentCell = getCellAt(row, col - 1);
                	    if (adjacentCell != null && !adjacentCell.isRoom()&& adjacentCell.getInitial() != 'X') { // Check if the cell is not null
                	    	cell.addAdj(adjacentCell);
                	    }
                	}

                	if (col < getNumColumns() - 1) {
                	    BoardCell adjacentCell = getCellAt(row, col + 1);
                	    if (adjacentCell != null && !adjacentCell.isRoom()&& adjacentCell.getInitial() != 'X') { // Check if the cell is not null
                	    	cell.addAdj(adjacentCell);
                	    }
                	}
                	
                	
                	
                	
                	
                	   
                    // If the cell is a room center, check the whole board for doors pointing to it
                	//This acts as the room adjacent targets calculator
                	if (cell.isRoomCenter()) {
                	    char roomInitial = cell.getInitial();
                	    for (int doorRow = 0; doorRow < getNumRows(); doorRow++) {
                	        for (int doorCol = 0; doorCol < getNumColumns(); doorCol++) {
                	            BoardCell potentialDoorCell = getCellAt(doorRow, doorCol);
                	            if (potentialDoorCell.isDoorway()) {
                	                DoorDirection direction = potentialDoorCell.getDoorDirection();
                	                BoardCell adjacentCell = null;

                	                switch (direction) { //Check door direction
                	                    case UP:
                	                        if (doorRow > 0) {
                	                            adjacentCell = getCellAt(doorRow - 1, doorCol);
                	                        }
                	                        break;
                	                    case DOWN:
                	                        if (doorRow < getNumRows() - 1) {
                	                            adjacentCell = getCellAt(doorRow + 1, doorCol);
                	                        }
                	                        break;
                	                    case RIGHT:
                	                        if (doorCol < getNumColumns() - 1) {
                	                            adjacentCell = getCellAt(doorRow, doorCol + 1);
                	                        }
                	                        break;
                	                    case LEFT:
                	                        if (doorCol > 0) {
                	                            adjacentCell = getCellAt(doorRow, doorCol - 1);
                	                        }
                	                        break;
                	                }

                	                if (adjacentCell != null && adjacentCell.getInitial() == roomInitial) {
                	                    cell.addAdj(potentialDoorCell);  // Add the doorway as adjacent
                	                  
                	                } 
                	            }
                            }
                        }
                    }
                    }
                
                //If case of secret passage, use helper methods to find corresponding room
                if (cell.isSecretPassage()) {
            		BoardCell origCell = findRoomCenterByInitial(cell.getInitial());
                    char secretPassageInitial = cell.getSecretPassage(); 
                    if (secretPassageInitial != '0') { // Check if the initial is valid
                        BoardCell secretPassageTarget = findRoomCenterByInitial(secretPassageInitial);
                        if (secretPassageTarget != null) {
                            origCell.addAdj(secretPassageTarget); // Add the center of the target room as adjacent
                        }
                    }
                }
                    //If doorway, consider direction of doorway and how that affects going into rooms
                if (cell != null && cell.isDoorway()) {        
                    DoorDirection direction = cell.getDoorDirection();
                    char initial;

                    if (direction == DoorDirection.UP && row > 0) {
                        initial = getCellAt(row-1, col).getInitial();
                    } else if (direction == DoorDirection.DOWN && row < getNumRows() - 1) {
                        initial = getCellAt(row+1, col).getInitial();
                    } else if (direction == DoorDirection.RIGHT && col < getNumColumns() - 1) {
                        initial = getCellAt(row, col+1).getInitial();
                    } else if (direction == DoorDirection.LEFT && col > 0) {
                        initial = getCellAt(row, col-1).getInitial();
                    } else {
                        continue;  // Skip to the next iteration if none of the conditions are met
                    }

                    BoardCell newCell = findRoomCenterByInitial(initial);
                    if (newCell != null) {
                        cell.addAdj(newCell);
                    }
                }   
            }
            }
        }
    
    //Finds room by first initial
    public BoardCell findRoomCenterByInitial(char roomInitial) {
        for (int row = 0; row < getNumRows(); row++) {
            for (int col = 0; col < getNumColumns(); col++) {
                BoardCell cell = getCellAt(row, col);
                if (cell != null && cell.isRoomCenter() && cell.getInitial() == roomInitial) {
                	
                    return cell; // Return the room center cell with the matching initial
                }
            }
        }
        return null; // Return null if no matching room center is found
    }
    
    //Gets second char of secret passage to find corresponding cell. 
    public char getSecondCharOfSecretPassage(String secretPassage) {
        // Check if the secret passage string is not null and has at least two characters
        if (secretPassage != null && secretPassage.length() > 1) {
            return secretPassage.charAt(1); // Return the second character
        }
        return '0'; // Return a null character if the string is too short or null
    }
    
    
   
    
    // Recursive method to find all targets
    private void findAllTargets(BoardCell thisCell, int numSteps, boolean startInRoom) {
        if (numSteps == 0 || (thisCell.isRoomCenter() && !startInRoom)) {
            targets.add(thisCell);
            return;
        }

        for (BoardCell adjCell : thisCell.getAdjList()) {
            if (visited.contains(adjCell) || (adjCell.isOccupied() && !adjCell.isRoom())) {
                continue; // Skip the cell if it's already visited or occupied and not a room
            }

            visited.add(adjCell);
            findAllTargets(adjCell, numSteps - 1, false);
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
        BoardCell cell = getCellAt(row, col);
        return cell != null ? cell.getAdjList() : new HashSet<>();
    }

}