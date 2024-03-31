package clueGame;

/**
 * Player Class - This class holds the constructor for a player, and holds suggestion methods and asbtract methods for the 
 * player class. 
 * 
 * @author Hudson Faris
 * @author Sam Bangapadang
 * 
 * Sources: JavaDocs
 * Date: 3/29/2024
 * 
 */



import java.awt.Color;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public abstract class Player {

	// basic variables...
	private ArrayList<Card> hand = new ArrayList<Card>();
	protected ArrayList<Card> seen = new ArrayList<Card>();
	private int row, column;
	private BoardCell location;
	private String name;
	private Color color;
	

	// constructor to set correct variables
	public Player(String name, Color color, int row, int column) {
		
		this.setName(name);
		this.setColor(color);
		this.setColumn(column);
		this.setRow(row);
		
		this.location = Board.getInstance().getCell(row, column);
	}

	// player disprove suggestion
	public Card disproveSuggestion(ArrayList<Card> suggestion) {
	    List<Card> disproveCards = hand.stream()
	                                   .filter(suggestion::contains)
	                                   .collect(Collectors.toList());

	    return disproveCards.isEmpty() ? null : disproveCards.get(new Random().nextInt(disproveCards.size()));
	}
	
	
	//check if card is seen
	public void updateSeen(Card seenCard) {
		if(!seen.contains(seenCard)) {
			seen.add(seenCard);
		}
	}
	
	
	public abstract BoardCell selectTargets(Set<BoardCell> set);
	
	public abstract Card getSuggPerson();
	
	public abstract Card getSuggWeapon();
	
	public abstract String getSuggRoom();
	

	public void createSuggestion(BoardCell boardCell) {}

	
	// adds cards to array list hand of players
	public void updateHand(Card card) {
		hand.add(card);
	}
	
	public ArrayList<Card> getSeen() {
		return seen;
	}

	// returns each player's hand
	public ArrayList<Card> getHand() {
		return hand;
	}

	// corresponding setters and getters
	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public BoardCell getLocation() {
		return location;
	}

	public void setLocation(BoardCell location) {
		this.location = location;
	}

}