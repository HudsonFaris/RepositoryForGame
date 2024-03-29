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

	}


	//Comp selects target.  
	@Override
	public BoardCell selectTargets(Set<BoardCell> targets) {
		return null;
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