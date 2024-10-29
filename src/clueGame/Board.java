package clueGame;

/**
 * Board Class - This class holds a board constructor and configs for loading files. It has getters/setters
 * relevant to the board interacting with other things. It does literally everything. 
 * 
 * @author Hudson Faris
 * @author Sam Bangapadang
 * 
 * Sources: JavaDocs
 * Date: 4/22/2024
 * 
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Map.Entry;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Board extends JPanel{

	private int numRows;
	private int numCols;
	private String layoutConfigFile;
	private String setupConfigFile;
	private BoardCell[][] grid;
	Map<String, Player> players;  
	Solution solution;          //Solution 
	Set<BoardCell> targets;
	Set<BoardCell> visited;
	Map<Card, Color> cardColors;
	Map<Character, Room> roomMap;       // Mapping from room initial to Room object
	Map<Character, Card> roomNames;     // Mapping from room initial to corresponding Card
	Map<Character, BoardCell> centerMap;    // Mapping from room initial to center cell of the room
	Map<Character, BoardCell> passageMap;  // Mapping from passage initial to corresponding passage cell

	ArrayList<Card> deck;           // List of all cards
	ArrayList<Card> gameDeck;       // List of cards used in a particular game
	ArrayList<Card> rooms;          // List of all room cards
	ArrayList<Card> weapons;        // List of all weapon cards
	ArrayList<Card> gameCharacters; //


	// sets to hold targets, and vistited cells
	

	// gui objects for easy access in boar

	Player HumanPlayer;
	GameControlPanel controlPanel;
	CardPanel cardPanel;
	boolean humanPlayerTurn = true;
	boolean hasMoved = false;

	// singleton method
	private static Board theInstance = new Board();
	private Board() { //As made in tutorial
		super();
		addMouseListener(new mouseListener()); //Added looked up
	}
	public static Board getInstance() {return theInstance;}

	//function to try and run the config files
	//try/catch for file not found exceptions, or bad format exceptions - iether or
	public void initialize() {
		try {
			//Added config values to account for null errrs
			setConfigValues();
			loadSetupConfig();
			loadLayoutConfig();
			//Added to 
			adjacencies();
			deal();
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	// updates gui and sets panel. 
	public void setPanels(GameControlPanel gc, CardPanel gcp) {
		this.controlPanel = gc;
		this.cardPanel = gcp;
	}

	//Checking an accusation... just seeing if it meets solution
	public boolean checkAccusation(Card person, Card location, Card weapon) {
	    return solution.getPerson().getCardName().equals(person.getCardName()) &&
	           solution.getRoom().getCardName().equals(location.getCardName()) &&
	           solution.getWeapon().getCardName().equals(weapon.getCardName());
	}


	//Handle suggestion by seeing if a player can disprove it. Goes thru all players
	public Card handleSuggestion(Card person, Card room, Card weapon, Player suggestor) {
	    List<Card> suggestionList = List.of(person, room, weapon);
	    List<Card> disprovedCards = new ArrayList<>();
	    List<Card> disprovingPlayers = new ArrayList<>();

	    // Check each player except the suggestor for a disproving card
	    for (Map.Entry<String, Player> entry : players.entrySet()){
	        Player player = entry.getValue();
	        if (player.equals(suggestor)){
	            continue; //Check
	        }

	        // Move the player to the room if they match the person suggested
	        if (person.getCardName().equals(player.getName())) {
	        	//System.out.println("TEst");
	            player.getLocation().setOccupied(false);
	            player.setLocation(roomMap.get(room.getCardName().charAt(0)).getCenterCell());
	            Board.getInstance().repaint();  
	        }

	        // Attempt to disprove the suggestion
	        Card result = player.disproveSuggestion(suggestionList);
	        if (result != null){
	            disprovedCards.add(result);
	            
	            disprovingPlayers.add(new Card(player.getName(), CardType.PERSON)); // Collecting players who can disprove
	        }
	    }

	    //Update control panel display with the guess
	    controlPanel.updateDisplay();
	    String guess = "The guess is "+person.getCardName() + " in " + room.getCardName()+" with " + weapon.getCardName();
	    controlPanel.setGuess(guess);

	    //Handle disproved suggestions
	    if (!disprovedCards.isEmpty()){
	        int index = new Random().nextInt(disprovedCards.size());
	        Card disprovedCard = disprovedCards.get(index);
	        Card disprovingPlayer = disprovingPlayers.get(index);

	        controlPanel.setGuessResult("Suggestion Result: " + disprovedCard.getCardName() + " from " + disprovingPlayer.getCardName());

	        if (suggestor instanceof HumanPlayer) {
	            suggestor.updateSeen(disprovedCard);
	            suggestor.updateSeen(disprovingPlayer);
	            cardPanel.updateDisplay();
	        }

	        Board.getInstance().repaint();
	        return disprovedCard;
	    } else{
	        controlPanel.setGuessResult("No new clue");
	        Board.getInstance().repaint();
	        return null;
	    }
	}

	//calculates the adjacencies
	//checks if door or is cell center roughly1
	public void adjacencies() {
		for(int i=0; i<numRows; i++) {
			for(int j=0; j<numCols; j++) {
				char temp = getCell(i, j).getInitial();
				BoardCell cell = getCell(i, j);
				if(cell.isWalkway()) {
					if(validateBounds(i+1, j) && getCell(i+1, j).isWalkway()) { //Had to move to this method, other thing got messed up
						cell.addAdj(getCell(i+1, j));
					}
					//System.out.println("TEst");
					if(validateBounds(i-1, j) && getCell(i-1, j).isWalkway()) {
						cell.addAdj(getCell(i-1, j));
					}
					if(validateBounds(i, j+1) && getCell(i, j+1).isWalkway()) {
						cell.addAdj(getCell(i, j+1));
					}
					if(validateBounds(i, j-1) && getCell(i, j-1).isWalkway()) {
						cell.addAdj(getCell(i, j-1));
					}
				} 

				if(cell.isDoorway()) {
					DoorDirection tmp = cell.getDoorDirection();
					Character roomLoc ='X';
					if(tmp == DoorDirection.UP) {
						roomLoc = getCell(i-1, j).getInitial();
					}
					if(tmp == DoorDirection.DOWN) {
						roomLoc = getCell(i+1, j).getInitial();
					}
					if(tmp == DoorDirection.RIGHT) {
						roomLoc = getCell(i, j+1).getInitial();
					}
					if(tmp == DoorDirection.LEFT) {
						roomLoc = getCell(i, j-1).getInitial();
					}
					cell.addAdj(centerMap.get(roomLoc));
					centerMap.get(roomLoc).addAdj(cell);
				}

				if(cell.isRoomCenter()) {
					if(passageMap.containsKey(cell.getInitial())) {
						Character tmp = (passageMap.get(cell.getInitial())).getInitial();
						cell.addAdj(centerMap.get(tmp));
					}						
				}				
			}
		}
	}


	//Checks to see if everything is in bounds of board, reassurance. 
	public boolean validateBounds(int row, int col) {
		return (row >=0&&row<numRows&&col>=0 && col < numCols);
	}


	//Creates hashsets and recursively checks targets
	public void calcTargets(BoardCell startCell, int path) {
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		if(path == 0) { //Edge case check
			return;
		}
		visited.add(startCell);
		findTargets(startCell, path);
	}


	//Calcs the target, who would've gussed
	/**
	 * 
	 * @param startCell
	 * @param pathLength
	 */
	public void findTargets(BoardCell startCell, int pathLength) {
	    for (BoardCell cell : startCell.getAdjList()) {
	        //Skip cell if it has already been visited or if it's an occupied non-room cell
	        if (visited.contains(cell)||(cell.isOccupied() && !cell.isRoom())) {
	            continue;
	        }

	        visited.add(cell); // Mark this cell as visited

	        
	        // Determine if the cell should be added to targets
	        boolean isAccessibleRoom = cell.isRoom() && !cell.isOccupied();
	        boolean isFinalStep = pathLength == 1;
	        boolean isUnoccupied = !cell.isOccupied();

	        if (isFinalStep || isAccessibleRoom || (cell.isRoom() && isUnoccupied)) {
	            targets.add(cell);
	        }else{
	        	
	            findTargets(cell, pathLength - 1); // Recur for the next step
	        }

	        visited.remove(cell); // Unmark this cell after recursion
	    }
	}
	
	//System.out.println("Test");


	// sets the file names with proper path "data"
	public void setConfigFiles(String string, String string1) {
		this.layoutConfigFile = "data/" + string;
		this.setupConfigFile = "data/" + string1; //I hate this
	}


	//Sets config values. 
	public void setConfigValues() throws FileNotFoundException, BadConfigFormatException {
	    FileReader reader = new FileReader(layoutConfigFile);
	    try (Scanner scanner = new Scanner(reader)) {  //Switched to scanner for ease 
	        List<String[]> lines = new ArrayList<>();
	        int maxCols = 0;

	        //Reads lines. 
	        while (scanner.hasNext()){
	        	//Didn't work check agian?
	        	//Works now - S
	            String currLine = scanner.nextLine().trim();
	            if (currLine.isEmpty()) continue;  
	            String[] values = currLine.split("[,\\s]+");  
	            maxCols = Math.max(maxCols, values.length); 
	            lines.add(values);  
	        }

	        //Create grid now 
	        
	        numRows = lines.size();
	        numCols = maxCols;
	        grid = new BoardCell[numRows][numCols];

	        //Fill the grid based on lines data
	        for (int i = 0; i < numRows; i++){
	            String[] rowValues = lines.get(i);
	            for (int j = 0; j<rowValues.length; j++) {
	                grid[i][j] =new BoardCell(i, j);
	            }
	            for (int j = rowValues.length; j < numCols; j++) {
	                grid[i][j] = new BoardCell(i, j); //Why?
	            }
	        }
	    } catch (Exception e){ //Check needed, autoGEN
	        throw new BadConfigFormatException("Error processing layout configuration file: " + e.getMessage());
	    }
	    
	    //System.out.println("TEst");
	}


	// loads the given legend, creates the room map, and the cards
	public void loadSetupConfig() throws BadConfigFormatException, FileNotFoundException {
		//Moved to here from constructor, creates localization and less confusion
		//Also don't have to create twice now. 
		weapons = new ArrayList<Card>();
		deck = new ArrayList<Card>();
		roomNames = new HashMap<Character, Card>();
		gameCharacters = new ArrayList<Card>();
		gameDeck = new ArrayList<Card>();
				
		players = new HashMap<String, Player>();
		
		roomMap = new HashMap<Character, Room>();
		rooms = new ArrayList<Card>();
		FileReader reader = new FileReader(setupConfigFile);

		boolean firstPlayer = true;
		String currLine;
		String[] values;	
		Scanner scanner = new Scanner(reader);
		Color color = null;	
		char key;

		while (scanner.hasNext()) {
			currLine = scanner.nextLine();
			values = currLine.split(", "); //Delimitor
			String name = values[0];

			if(name.equals("Room") || name.equals("Space")) {
				key = values[2].charAt(0);
				if (!(Character.isLetter(key))) {
					throw new BadConfigFormatException("Bad format: Inappropriate value for room initial in legend " + setupConfigFile);
				}
				Room room = new Room(values[1]);
				roomMap.put(key, room);
				if(name.equals("Room")) {
					Card newCard;
					newCard = new Card(values[1], CardType.ROOM);
					deck.add(newCard);
					gameDeck.add(newCard);
					rooms.add(newCard);
					roomNames.put(key, newCard);
				}
			}
			if(name.equals("Player")) {		
				switch(values[2]) {
				case "Gray":
					color = Color.GRAY;
					break;
				case "Red":
					color = Color.RED;
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
				case "Black":
					color = Color.BLACK;
					break;
				}

				Card newCard;
				newCard = new Card(values[1], CardType.PERSON);
				deck.add(newCard);
				gameDeck.add(newCard);
				gameCharacters.add(newCard);
				//So done
				if(firstPlayer == true){
					HumanPlayer = new HumanPlayer(values[1], color,Integer.parseInt(values[3]), Integer.parseInt(values[4]));
					players.put(values[1],HumanPlayer);
					firstPlayer = false;
				}else {					
					Player player = new ComputerPlayer(values[1], color, Integer.parseInt(values[3]), Integer.parseInt(values[4]));
					players.put(values[1], player);
				}

				
				
			} else if(name.equals("Weapon")){
				Card newCard;
				newCard = new Card(values[1], CardType.WEAPON);
				deck.add(newCard);
				gameDeck.add(newCard);
				weapons.add(newCard);
			} else{
				continue;
			}
		}
//System.out.println("TEst");

	}

	
	public void shuffle() {
		Collections.shuffle(weapons);
		Collections.shuffle(gameCharacters);
		Collections.shuffle(rooms);
	}
	
	public void deal() throws BadConfigFormatException {
	    shuffle();  // Presumably this shuffles the gameDeck or similar
	    cardColors = new HashMap<>();
	    
	    //Checks it
	    solution = new Solution(gameCharacters.get(0), rooms.get(0), weapons.get(0));
	    deck.remove(weapons.get(0));
	    deck.remove(gameCharacters.get(0));
	    deck.remove(rooms.get(0));

	    Collections.shuffle(deck);

	   //System.out.println("test final");
	    int playerCount = players.size();
	    int counter = 0;
	    for (Map.Entry<String, Player> entry : players.entrySet()) {
	        Player player = entry.getValue();
	        for (int i = 0; i < 3; i++) {  // =Deal 3 cards to each player if possible
	            int cardIndex = counter + i;
	            if (cardIndex >= deck.size()){
	                throw new BadConfigFormatException("Not enough cards to deal equally among players.");
	            }
	            Card cardToDeal = deck.get(cardIndex);
	            player.updateHand(cardToDeal);
	            cardColors.put(cardToDeal, player.getColor());
	        }
	        counter += 3;  //Move the counter by 3 for the next player
	    }

	    if (counter < deck.size()) {
	        throw new BadConfigFormatException("Not all cards have been dealt. Remaining cards in the deck.");
	    }
	}


	// goes through the csv file and checks for doors, door direction
	// label cells, center cells using switch statement
	public void loadLayoutConfig() throws BadConfigFormatException, FileNotFoundException{
		passageMap = new HashMap<Character, BoardCell>();
		centerMap = new HashMap<Character, BoardCell>();
		FileReader reader = new FileReader(layoutConfigFile);
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(reader);
		String currLine;
		String[] values;
		int cols = 0; 
		int rows = 0;
		while(scanner.hasNext()) {
			currLine = scanner.nextLine();
			values = currLine.split(",");
			cols = values.length;
			//throwing badConfigForamtException in case of number columns is inconsistent
			if(numCols!= cols) {
				throw new BadConfigFormatException("Error with config files");
			}
			for(int i = 0; i < cols; ++i) {
				grid[rows][i] = getCell(rows, i);
				char location = values[i].charAt(0);
				if(location == 'X') {
					grid[rows][i].setUnused();
					grid[rows][i].setInitial('X');
					grid[rows][i].setRoom(true);
					continue;
				}
				grid[rows][i].setInitial(location);
				if(location == 'W') {
					grid[rows][i].setWalkway();
				}
				else if(roomMap.containsKey(location)) {
					grid[rows][i].setInitial(location);
					grid[rows][i].setRoom(true);
				}
				if(values[i].length() == 2) {
				
					char tmp = values[i].charAt(1);
					specialCell(tmp, rows, i, location);
				}
			}
			rows++;
		}					
	}
	//Determines special characters ID's
	private void specialCell(char tmp, int rows, int i, char location) {
		switch (tmp) {
		case '>'://Check
			grid[rows][i].setDoorway();
			grid[rows][i].setDoorDirection(DoorDirection.RIGHT);
			return;
		case '<':
			grid[rows][i].setDoorway();
			grid[rows][i].setDoorDirection(DoorDirection.LEFT);
			return;
		case '^':
			grid[rows][i].setDoorway();
			grid[rows][i].setDoorDirection(DoorDirection.UP);
			return;
		case 'v':
			grid[rows][i].setDoorway();
			grid[rows][i].setDoorDirection(DoorDirection.DOWN);
			return;
		case '#': //Works
			grid[rows][i].setRoomLabel();
			roomMap.get(location).setLabel(grid[rows][i]);
			return;
		case '*': //Now works
			grid[rows][i].setRoomCenter();
			roomMap.get(location).setCenter(grid[rows][i]);
			centerMap.put(location, grid[rows][i]);
			return;
		default: //Secret Passage accordingly. 
			grid[rows][i].setSecretPassage(tmp);
			passageMap.put(tmp, grid[rows][i]);
			return;
		}
		//System.out.println("TesT");
	}

	/**
	 * Paints components of board based on type of object, continues to repaint and updates board accordingly
	 * players used.  
	 */
	public void paintComponent(Graphics boardView) {
		super.paintComponent(boardView);
		int xOffset, yOffset;
		int width = this.getWidth()/getNumColumns();
		int height = this.getHeight()/getNumRows();
		ArrayList<BoardCell> currentPlayerLocs = new ArrayList<BoardCell>();
		for(int heightIter = 0; heightIter < getNumRows(); heightIter++) {
			for(int widthIter = 0; widthIter < getNumColumns(); widthIter++) {
				xOffset = widthIter * width;
				yOffset = heightIter * height; //This works, dont touch it
				getCell(heightIter, widthIter).drawCell(boardView, width, height, xOffset, yOffset, this);
			}
			//uh
		}
		Board.getInstance().repaint();
		for(Entry<String, Player> playerIter: players.entrySet()) {
			BoardCell playerLoc = playerIter.getValue().getLocation();
			xOffset = width * playerLoc.getColumn();
			yOffset = height * playerLoc.getRow();
			
			if(playerLoc.isRoom() && currentPlayerLocs.contains(playerLoc)) { //EXTRA Credit to account for overallping
				xOffset += 6 * Collections.frequency(currentPlayerLocs, playerLoc);
			}
			currentPlayerLocs.add(playerLoc);
			boardView.setColor(playerIter.getValue().getColor());
			boardView.fillOval(xOffset, yOffset, width, height);
			boardView.setColor(Color.BLACK);
			boardView.drawOval(xOffset, yOffset, width, height);
			playerLoc.setOccupied(true);
		}
	}

	// determines if the board was clicked as well as if it is valid
	// if not returns a dialouge box with the error.
	public class mouseListener implements MouseListener {

	    int counter = 0;
	    //System.out.println("TEst");
	    @Override
	    public void mouseClicked(MouseEvent e) {
	        int column = e.getX() / (getWidth() / getNumColumns());
	        int row = e.getY() / (getHeight() / getNumRows());
	        BoardCell clickedCell = getCell(row, column);

	        if (!humanPlayerTurn) {
	            showMessage("Error: Not your turn!");
	            return;
	        }

	        if (!targets.contains(clickedCell) || clickedCell.isOccupied() && !clickedCell.isRoom()) {
	            showMessage("Error: Invalid move");
	            return;
	        }
//Check
	        //Move human player to the new location
	        HumanPlayer.getLocation().setOccupied(false);
	        HumanPlayer.setLocation(clickedCell);
	        clickedCell.setOccupied(true);
	        humanPlayerTurn = false;
	        hasMoved = true;
	        counter++;

	        if (clickedCell.isRoom()) {
	            SuggestionPanel panel = new SuggestionPanel();
	            panel.setVisible(true);
	            humanPlayerTurn = false;
	        }
	    }

	    private void showMessage(String message) {
	        JOptionPane.showMessageDialog(new JButton(), message);
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


	// getters and setters
	
	public Map<String, Player> getPlayers(){
		return players;
	}

	public void setPlayers(Map<String, Player> input) {
		players = input;
	}
	

	public Map<Card, Color> getCardColors(){
		return cardColors;
	}

	public void addDeckCards(Card card) {
		deck.add(card);
	}
	// returns the number of columns
		public int getNumColumns() {
			return numCols;
		}

		// returns the number of rows
		public int getNumRows() {
			return numRows;
		}
	
	public ArrayList<Card> getDeck(){
		return deck;
	}
	public ArrayList<Card> getWeapons(){
		return weapons;
	}

	// returns room based on key from room map
	public Room getRoom(char c) {
		return roomMap.get(c);
	}

	
	public BoardCell getCell(int i, int j) {
		return grid[i][j];
	}

	// returns room from the room map
	public Room getRoom(BoardCell cell) {
		return getRoom(cell.getInitial());
	}

	// returns the amount of rooms
	public int getAmountRooms() {
		return roomMap.size();
	}

	// returns a adjacency list for any given cell
	public Set<BoardCell> getAdjList(int i, int j) {
		return getCell(i, j).getAdjList();
	}
	
	public Card getCardFromRoomInitial(Character c) {
		return roomNames.get(c);
	}
	
	// returns set of targets
		public Set<BoardCell> getTargets() {
			return targets;
		}
		//project done!
		
}

//Done!

