package clueGame;


/**
 * HumanPlayer - Child class of Player, creates the abstract methods for the player if the player is a human.
 * 
 * @author Hudson Faris
 * @author Sam Bangapadang
 * 
 * Sources: JavaDocs
 * Date: 3/29/2024
 * 
 */

import java.awt.Color;
import java.util.Set;

public class HumanPlayer extends Player{

	public HumanPlayer(String name, Color color, int row, int col) {
		super(name, color, row, col);
	}
	//Creates the asbtract methods...
	@Override
	public String getSuggRoom() {
		return null;
	}

	@Override
	public BoardCell selectTargets(Set<BoardCell> set) {
		return null;
	}

	@Override
	public Card getSuggWeapon() {
		return null;
	}
	
	@Override
	public void createSuggestion(BoardCell boardCell) {
		
	}

	@Override
	public Card getSuggPerson() {
		// TODO Auto-generated method stub
		return null;
	}



}