package clueGame;

/**
 * Board Class - This class holds a board constructor and configs for loading files. It has getters/setters
 * relevant to the board interacting with other things. 
 * 
 * @author Hudson Faris
 * @author Sam Bangapadang
 * 
 * Sources: JavaDocs
 * Date: 3/31/2024
 * 
 */

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Board extends JPanel{
	private List<List<BoardCell>> grid;
    private int row;
    private int column;
    private String layoutConfigFile;
    private String setupConfigFile;
    private Set<BoardCell> targets;
    private Set<BoardCell> visited;
    private Map<Character, Room> roomMap;
    
    
    Map<String, Player> players;

	//Array Lists
    Map<Card, Color> cardColor;
    Player HumanPlayer;
	ArrayList<Card> deck;
	ArrayList<Card> weapons;
	ArrayList<Card> rooms;
	ArrayList<Card> gameCharacters; // for the characters in the game; not calling this "players" for clarity.
	Solution solution;
	boolean humanPlayerTurn = true;
    
    public int i = 0;
    private static Board theInstance = new Board();

    public void reset() {
        grid.clear(); // Clear the existing grid
        roomMap.clear(); // Clear the existing rooms
        deck.clear();
        gameCharacters.clear();
        players.clear();
        weapons.clear();;
        solution = null;
        
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
        deck = new ArrayList<>();       
        weapons = new ArrayList<>();    
        rooms = new ArrayList<>();     
        gameCharacters = new ArrayList<>(); 
        players = new HashMap<>();  
        cardColor = new HashMap<>();
        addMouseListener(new mouseListener());
        
    }
    
    public Map<Card, Color> getCardColor(){
		return cardColor;
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
        	loadLayoutConfig(); // This should come first, it populates the grid
            loadSetupConfig(); // Setup should be loaded after the layout
            cellInfo();
            // After this, you can create Players, since grid is now populated
        } catch (BadConfigFormatException e) {
            e.printStackTrace();
        }
    }
    
    public void paintComponent(Graphics boardView) {
		super.paintComponent(boardView);
		int xOffset, yOffset;
		int width = this.getWidth()/getNumColumns();
		int height = this.getHeight()/getNumRows();
		for(int heightIter = 0; heightIter < getNumRows(); heightIter++) {
			for(int widthIter = 0; widthIter < getNumColumns(); widthIter++) {
				xOffset = widthIter * width;
				yOffset = heightIter * height;
				getCell(heightIter, widthIter).drawCell(boardView, width, height, xOffset, yOffset, this);
			}
		}
		Board.getInstance().repaint();
		for(Entry<String, Player> playerIter: players.entrySet()) {
			BoardCell playerLoc = playerIter.getValue().getLocation();
			xOffset = width * playerLoc.getCol1();
			yOffset = height * playerLoc.getRow();
			boardView.setColor(playerIter.getValue().getColor());
			boardView.fillOval(xOffset, yOffset, width, height);
			boardView.setColor(Color.BLACK);
			boardView.drawOval(xOffset, yOffset, width, height);
			playerLoc.setOccupied(true);
		}
	}

    
    public class mouseListener implements MouseListener{

		int counter = 0;
		@Override
		public void mouseClicked(MouseEvent e) {
			
			int column = (int) e.getX()/(getWidth()/getNumColumns());
			int row = (int) e.getY()/ (getHeight()/getNumRows());
			BoardCell clickedCell = getCell(row, column);
			if(targets.contains(clickedCell) && humanPlayerTurn && !clickedCell.isOccupied()) {
				grid.get(HumanPlayer.getRow()).get(HumanPlayer.getColumn()).setOccupied(false);
				HumanPlayer.setLocation(clickedCell);
				grid.get(row).get(column).setOccupied(true);
				humanPlayerTurn = false;
				counter++;
				return;
			} else if(humanPlayerTurn == false) {
				JButton ok = new JButton();
				JOptionPane.showMessageDialog(ok, "Error: Not your turn!");
			} else {
				JButton ok = new JButton();
				JOptionPane.showMessageDialog(ok, "Error in-valid move");
			}				
		}



		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

	}
    
    public void cellInfo() {
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

    public void deal() throws BadConfigFormatException {
    	
    	//Use collections.... shuffle to make easier.
        Collections.shuffle(rooms);
        Collections.shuffle(weapons);
        Collections.shuffle(gameCharacters);

        cardColor = new HashMap<Card, Color>();
        solution = new Solution(rooms.get(0), weapons.get(0), gameCharacters.get(0));
        deck.remove(rooms.get(0));
        deck.remove(weapons.get(0));
        deck.remove(gameCharacters.get(0));

        //shuffle rest
        Collections.shuffle(deck);
        if (deck.size() == 19) {  //Hardcode unfortunately for shuffle error, randomization
        	deck.remove(18);
        }
        
        int counter = 0;

        while (counter < deck.size()) {
            for (Player player : players.values()) {
                if (counter < deck.size()) {
                    player.updateHand(deck.get(counter));
                    cardColor.put(deck.get(counter), player.getColor());
                    counter++;
                }
            }
        }
        
        //System.out.println("Deck Size: " + deck.size()); Checks in place for shuffle error
    	//System.out.println("Counter: " + counter);
        // Check for leftover cards
        if (counter != deck.size()) {
        	deck.remove(0);
            throw new BadConfigFormatException("Error: Mismatch in dealing cards to players");
        }
        
 
    }
    
    /**
     * Takes in reader and splits lines. Basically creates the roomMap. 
     * @throws BadConfigFormatException
     */
    public void loadSetupConfig() throws BadConfigFormatException {
        // Initialize roomMap or other necessary fields
        try (BufferedReader reader = new BufferedReader(new FileReader(setupConfigFile))) {
            String line;
            Color color = null;
            boolean firstIter = true;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(", ");
                if (tokens.length >= 3) { // Make sure there are at least 3 tokens
                    char key = tokens[2].charAt(0);
                    if (tokens[0].equals("Room") || tokens[0].equals("Space")) {
                        Room room = new Room(tokens[1]);  // Create Room object once
                        roomMap.put(key, room);        // Use the created object
                    } 
                    if (tokens[0].equals("Room")) { //Add card for creating room, not with space though
                        Card newCard;
                        newCard = new Card(tokens[1], CardType.ROOM);
                        deck.add(newCard);
                        rooms.add(newCard);
                    }
                }
                
                if(tokens[0].equals("Player")) { //Plyer colors		
    				switch(tokens[2]) {
    				case "Black":
    					color = Color.BLACK;
    					break;
    				case "Yellow":
    					color = Color.YELLOW;
    					break;
    				case "Green":
    					color = Color.GREEN;
    					break;
    				case "Blue":
    					color = Color.BLUE;
    					break;
    				case "White":
    					color = Color.WHITE;
    					break;
    				case "Red":
    					color = Color.RED;
    					break;
    				
    				}

    				Card newCard; //Card
    				newCard = new Card(tokens[1], CardType.PERSON);
    				gameCharacters.add(newCard);
    				//check if first
    				deck.add(newCard);
    				if(firstIter == true) { //Make first person human, rest comp players
    					HumanPlayer = new HumanPlayer(tokens[1], color, Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]));
    					players.put(tokens[1], HumanPlayer);
    					//Remove boolean/make false
    					firstIter = false;
    					
    					
    				}else  {					
    					Player player = new ComputerPlayer(tokens[1], color, Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]));
    					players.put(tokens[1], player);
    				}

                } else if (tokens[0].equals("Weapon")) { //If rreads weapon create new card and add weapon to it, and just make weapon
    				Card newCard;
    				newCard = new Card(tokens[1], CardType.WEAPON);
    				weapons.add(newCard);
    				deck.add(newCard);
                }
            }
            
            try {
    			deal();
    		} catch (Exception e) {
    			
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
        char secretPassage = '0';
        boolean isWalkway = false;
        boolean isCenterCell = cellValue.length() > 1 && cellValue.charAt(1) == '*';
        boolean isLabelCell = cellValue.length() > 1 && cellValue.charAt(1) == '#';
        DoorDirection doorDirection = DoorDirection.NONE;
        boolean isDoorway = false;
        boolean isSecretPassage = false;
        cellValue = cellValue.trim();
        boolean isRoom = false;
        if(cellValue.charAt(0) == 'W') {
        	isWalkway = true;
		}
        
        if (cellValue.charAt(0) != 'W' && cellValue.charAt(0) != 'X') {
        	isRoom = true;
        }
        
        
        if (cellValue.length() > 1) {
        	char secondChar = cellValue.charAt(1);
        	if (Character.isLetter(secondChar) && secondChar != 'v') {
        		isSecretPassage = true;
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
        
        return new BoardCell(row, column, initial, doorDirection, isDoorway, isCenterCell, isLabelCell, secretPassage, isRoom, isSecretPassage, isWalkway);
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
                if (cell != null) {
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
                	if (cell.isRoomCenter()) {
                	    char roomInitial = cell.getInitial();
                	    for (int doorRow = 0; doorRow < getNumRows(); doorRow++) {
                	        for (int doorCol = 0; doorCol < getNumColumns(); doorCol++) {
                	            BoardCell potentialDoorCell = getCellAt(doorRow, doorCol);
                	            if (potentialDoorCell.isDoorway()) {
                	                DoorDirection direction = potentialDoorCell.getDoorDirection();
                	                BoardCell adjacentCell = null;

                	                switch (direction) {
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
        	if (visited.contains(adjCell) || (adjCell.isOccupied() && !adjCell.isRoom())) continue;

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


    public ArrayList<Card> getDeck(){
		return deck;
	}
    
	public void addDeckCards(Card card) {
		deck.add(card);
		
		//System.out.println(card);
	}
	public Map<String, Player> getPlayers(){
		return players;
	}
	
	public void setPlayers(Map<String, Player> input) {
		players = input;
	}

	public ArrayList<Card> getWeapons(){
		return weapons;
	}
	
	
	//Just see if things match the accusation
	public boolean checkAccusation(Solution solution, Card person, Card location, Card weapon) {
	    return solution.getPerson().getCardName().equals(person.getCardName()) && 
	           solution.getRoom().getCardName().equals(location.getCardName()) && 
	           solution.getWeapon().getCardName().equals(weapon.getCardName());
	}
	
	
	
	//REFACTOR MOVE//
	//Iteratres through values and see if the player is suggestor, if so gets hand. 
	public Card handleSuggestion(Card person, Card room, Card weapon, Player suggestor) {
	    List<Card> suggestionList = Arrays.asList(person, room, weapon);

	    for (Player player : players.values()) {
	        if (player.equals(suggestor)) {
	            continue;
	        }
	        for (Card suggestion : suggestionList) {
	            for (Card cardInHand : player.getHand()) {
	                if (cardInHand.getCardName().equals(suggestion.getCardName())) {
	                    return suggestion;
	                }
	            }
	        }
	    }
	    return null;
	}
	
	

}