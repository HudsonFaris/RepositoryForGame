package clueGame;


/**
 * ComputerPlayer Class - Child class of player, creates comp player and uses abstract methods if
 * and only if the player is a computer. 5/6. 
 * 
 * @author Hudson Faris
 * @author Sam Bangapadang
 * 
 * Sources: JavaDocs
 * Date: 3/31/2024
 * 
 */

import java.awt.Color;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import javax.swing.JOptionPane;


public class ComputerPlayer extends Player{


	private Card suggPerson;
	private Card suggWeapon;
	private Card suggRoom;
	Card disprove;
	private String roomString;

	boolean hasAccusation = false;

	public ComputerPlayer(String name, Color color, int row, int col) {
		super(name, color, row, col);
	}

	// default
	public ComputerPlayer() {
		this("TestName", Color.YELLOW, 6, 9); //Test value
	}



	// Computer player creates a suggestion includes room, a weapon and a player
	//Room must match location of player, pass board cell as a parameter
	@Override
	public void createSuggestion(BoardCell boardCell) {
	    ArrayList<Card> peoples = new ArrayList<>();
	    ArrayList<Card> weapons = new ArrayList<>();

	    // Filter out seen and held cards, and categorize people and weapons
	    for (Card c : Board.getInstance().getDeck()) {
	        if (!seen.contains(c) && !getHand().contains(c)) { //CHECK RAZOR
	            if (c.getCardType() == CardType.PERSON && !c.getCardName().equals(this.getName())) {
	                peoples.add(c);
	            } else if (c.getCardType() == CardType.WEAPON) {
	                weapons.add(c);
	            }
	        }
	    }

	    // Select random person and weapon from available cards
	    Random rand = new Random();
	    suggPerson = !peoples.isEmpty() ? peoples.get(rand.nextInt(peoples.size())) : null;
	    suggWeapon = !weapons.isEmpty() ? weapons.get(rand.nextInt(weapons.size())) : null;

	    // Determine the room based on the player's current location
	    roomString = switch (boardCell.getInitial()) {
	        case 'M' -> "Mailroom";
	        case 'S' -> "Supper Room";
	        case 'B' -> "Break Room";
	        case 'L' -> "Lobby";
	        case 'G' -> "Garden";
	        case 'P' -> "Pantry";
	        case 'J' -> "Jockey Room";
	        case 'H' -> "Hidden Compartment"; //Hardcoded for this game, could be changed for whatever it reads (fix?)
	        case 'A' -> "Auditorium";
	        case 'W' -> "Walkway";
	        default -> "Unused";
	    };

	    //Find the matching card for the room
	    suggRoom = null;
	    for (Card c : Board.getInstance().rooms){
	        if (c.getCardName().equals(roomString)){
	            suggRoom = c;
	            break;
	        }
	    }
	}

	// Computer player selcts a move target 
	@Override
	public BoardCell selectTargets(Set<BoardCell> targets) {
	    if (targets.isEmpty()){
	        return getLocation();
	    }
	    
	    getLocation().setOccupied(false);

	    //Check for an accusation
	    if (hasAccusation) {
	        boolean result=Board.getInstance().checkAccusation(suggPerson,suggRoom, suggWeapon);
	        if (result) {
	            JOptionPane.showMessageDialog(null, "Computer Player wins!");
	            System.exit(0);
	        }
	    }

	    // Iterate through potential targets, pick non-seen first
	    BoardCell strategicTarget = null;
	    for (BoardCell target : targets) {
	        if (target.isRoom()) {
	            Card roomCard = Board.getInstance().getCardFromRoomInitial(target.getInitial());
	            if (roomCard != null && !getSeen().contains(roomCard)) {
	                //Update seen cards and create suggestion if moving to a new room
	                updateSeen(roomCard);
	                createSuggestion(target);
	                Card disproofCard = Board.getInstance().handleSuggestion(suggPerson, suggRoom, suggWeapon, this);
	                updateSeen(disproofCard);

	                if (disproofCard == null) {
	                    hasAccusation = true;
	                }

	                strategicTarget = target;
	                break; //Exit after selecting a strategic target
	            }
	        }
	    }

	    // If a strategic target was found, return it
	    if (strategicTarget != null) {
	        return strategicTarget;
	    }

	    // Otherwise, select a random target
	    int idx = new Random().nextInt(targets.size());
	    return targets.stream().skip(idx).findFirst().orElse(null);
	    //cardPanel.updateDisplay(); ignore
	}

	// getters and Setters for Suggesetions
	public Card getSuggPerson() {
		return suggPerson;
	}

	public void setSuggPerson(Card suggPerson) {
		this.suggPerson = suggPerson;
	}

	public Card getSuggWeapon() {
		return suggWeapon;
	}

	public void setSuggWeapon(Card suggWeapon) {
		this.suggWeapon = suggWeapon;
	}

	public String getSuggRoom() {
		return roomString;
	}

	public void setSuggRoom(String suggRoom) {
		this.roomString = suggRoom;
	}

	public void createSuggestion() {
///nothing111111
	}
}