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
import java.util.HashSet;
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
	
	
	// Computer player creates a suggestion includes room, a weapon and a player
		// Room must match location of player, pass board cell as a parameter
		@Override
		public void createSuggestion(BoardCell boardCell) {

			ArrayList<Card> peoples = new ArrayList<Card>();
			ArrayList<Card> weapons = new ArrayList<Card>();

			for(Card c: Board.getInstance().getDeck()) {
				if( !seen.contains(c) && !getHand().contains(c)) {
					if(c.getCardType().equals(CardType.PERSON)) {
						peoples.add(c);
					} else if (c.getCardType() == CardType.WEAPON){
						weapons.add(c);
					}
				}
			}

			Random randNum = new Random();
			 if (!peoples.isEmpty()) {
			        this.setSuggPerson(peoples.get(randNum.nextInt(peoples.size())));
			    }
			    // Setting a suggestion for a weapon if the weapons list is not empty
			 if (!weapons.isEmpty()) {
			        this.setSuggWeapon(weapons.get(randNum.nextInt(weapons.size())));
			    }
			 
			char initialRoom = (boardCell.getInitial());
			// switch statement to aid in getting location.
			switch(initialRoom) {
			case 'M':
				System.out.println(1);
				this.suggRoom = "Mailroom";
				break;
			case 'S':
				this.suggRoom = "Supper Room";
				break;
			case 'B':
				this.suggRoom = "Break Room";
				break;
			case 'L':
				this.suggRoom = "Lobby";
				break;
			case 'G':
				this.suggRoom = "Garden";
				break;
			case 'P':
				this.suggRoom = "Pantry";
				break;
			case 'J':
				this.suggRoom = "Jocket Room";
				break;
			case 'H':
				this.suggRoom = "Hidden Compartment";
				break;
			case 'A':
				this.suggRoom = "Auditorium";
				break;
			case 'W':
				this.suggRoom = "Walkway";
				break;
			default:
				this.suggRoom = "Unused";
				break;
			}

		}


	//Comp selects target.  
	// Computer player selcts a move target 
		@Override
		public BoardCell selectTargets(Set<BoardCell> targets) {
			for(BoardCell targetCheck: targets) {
				if(targetCheck.isRoom() && !getSeen().toString().contains(Board.getInstance().getRoom(targetCheck.getInitial()).getName())) {
					return targetCheck;
				}
			}
			Random rngen = new Random();
			int randTarget = rngen.nextInt(targets.size());
			BoardCell[] targetArray = targets.toArray(new BoardCell[targets.size()]); // since sets are not indexed, it must be converted into array, which adds linear complexity.
			return targetArray[randTarget];
			
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