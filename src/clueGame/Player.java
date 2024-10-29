package clueGame;

/**
 * Player Class - This class holds the constructor for a player, and holds suggestion methods and asbtract methods for the 
 * player class. 
 * 
 * @author Hudson Faris
 * @author Sam Bangapadang
 * 
 * Sources: JavaDocs
 * Date: 4/22/2024
 * 
 */

import java.awt.Color;

import java.util.Random;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;


public abstract class Player {

	//variables to hold name of the player, corresponding color,
	//location and said players hand
	private String name;
	private Color color;
	private int row, column;
	private BoardCell location;
	private ArrayList<Card> hand = new ArrayList<Card>();
	protected ArrayList<Card> seen = new ArrayList<Card>();
	

	// constructor to set correct variables
	public Player(String name, Color color, int row, int column) {
		this.setName(name);
		this.setColor(color);
		this.setRow(row);
		this.setColumn(column);
		this.location = Board.getInstance().getCell(row, column);
	}

	// Player disproves a suggestion
	public Card disproveSuggestion(List<Card> suggestion) {
        List<Card> disproveCards = new ArrayList<>();
        for (Card card : hand) {
            if (suggestion.contains(card)) {
                disproveCards.add(card);
            }
        }

        // Randomly select a card to return if there are any candidates
        if (!disproveCards.isEmpty()) {
            Random random = new Random();
            Card disprovedCard = disproveCards.get(random.nextInt(disproveCards.size()));
            seen.add(disprovedCard); // Update seen list with the card returned
            //System.out.println("TEST");
            return disprovedCard;
        }

        return null; //Return null if no cards can disprove the suggestion
    }

	// simply...updates seen //check
	public void updateSeen(Card seenCard) {
		if(!seen.contains(seenCard)) {
			seen.add(seenCard);
		}
	}

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

	public BoardCell getLocation() {
		return location;
	}

	public void setLocation(BoardCell location) {
		this.location = location;
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
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	//Abstract methods
	public abstract Card getSuggWeapon();
	public abstract String getSuggRoom();
	public abstract BoardCell selectTargets(Set<BoardCell> set);
	public abstract Card getSuggPerson();
	public void createSuggestion(BoardCell boardCell) {}

}