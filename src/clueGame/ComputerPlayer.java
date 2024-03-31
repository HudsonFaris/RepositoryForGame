package clueGame;



/**
 * ComputerPlayer Class - Child class of player, creates comp player and uses abstract methods if
 * and only if the player is a computer. 5/6. 
 * 
 * @author Hudson Faris
 * @author Sam Bangapadang
 * 
 * Sources: JavaDocs
 * Date: 3/29/2024
 * 
 */


import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;


public class ComputerPlayer extends Player{


	private Card suggPerson;
	private Card suggWeapon;
	private String suggRoom;
	
	
	public ComputerPlayer(String name, Color color, int row, int col) {
		super(name, color, row, col);
	}

	//Color...
	public ComputerPlayer() {
		this("Test", Color.WHITE, 6, 9); //Funny number. .
	}
	
	
	@Override
	public void createSuggestion(BoardCell boardCell) {
	    List<Card> peoples = new ArrayList<>();
	    List<Card> weapons = new ArrayList<>();
	    Random randNum = new Random();

	    for (Card c : Board.getInstance().getDeck()) {
	        if (!seen.contains(c) && !getHand().contains(c)) {
	            if (c.getCardType() == CardType.PERSON) {
	                peoples.add(c);
	            } else if (c.getCardType() == CardType.WEAPON) {
	                weapons.add(c);
	            }
	        }
	    }

	    if (!peoples.isEmpty()) {
	        this.setSuggPerson(peoples.get(randNum.nextInt(peoples.size())));
	    }

	    if (!weapons.isEmpty()) {
	        this.setSuggWeapon(weapons.get(randNum.nextInt(weapons.size())));
	    }

	    Map<Character, String> roomMap = new HashMap<>();
	    roomMap.put('M', "Mailroom");
	    roomMap.put('S', "Supper Room");
	    roomMap.put('B', "Break Room");
	    roomMap.put('L', "Lobby");
	    roomMap.put('G', "Garden");
	    roomMap.put('P', "Pantry");
	    roomMap.put('J', "Jocket Room");
	    roomMap.put('H', "Hidden Compartment");
	    roomMap.put('A', "Auditorium");
	    roomMap.put('W', "Walkway");

	    this.suggRoom = roomMap.getOrDefault(boardCell.getInitial(), "Unused");
	}


	@Override
	public BoardCell selectTargets(Set<BoardCell> targets) {
	    //Use a stream to find an unseen room cell, if available.
	    Optional<BoardCell> unseenRoomCell = targets.stream()
	            .filter(BoardCell::isRoom)
	            .filter(target -> !getSeen().toString().contains(Board.getInstance().getRoom(target.getInitial()).getName()))
	            .findFirst();

	    // If an unseen room cell is found, return it.
	    if (unseenRoomCell.isPresent()) {
	        return unseenRoomCell.get();
	    }

	    // If no unseen room cell is found, select a random target.
	    int randTargetIndex = new Random().nextInt(targets.size());
	    return new ArrayList<>(targets).get(randTargetIndex); // Convert to list for random access
	}


	public Card getSuggPerson() {
		return suggPerson;
	}


	public void setSuggWeapon(Card suggWeapon) {
		this.suggWeapon = suggWeapon;
	}

	public void setSuggRoom(String suggRoom) {
		this.suggRoom = suggRoom;
	}
	
	public void setSuggPerson(Card suggPerson) {
		this.suggPerson = suggPerson;
	}


	public Card getSuggWeapon() {
		return suggWeapon;
	}

	
	public void createSuggestion() {
		// TODO Auto-generated method stub
		
	}
	
	public String getSuggRoom() {
		return suggRoom;
	}


	




}