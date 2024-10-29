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

public class HumanPlayer extends Player {

	//Constructor for the human player, including name, color, and starting location
    public HumanPlayer(String name, Color color, int row, int col) {
        super(name, color, row, col);
    }
    
    @Override
    public Card getSuggWeapon() {
        return null; //Ignore
    }

    @Override
    public String getSuggRoom() {
        return null; //Ignore
    }

    @Override
    public BoardCell selectTargets(Set<BoardCell> set) {
        return null; //Ignore
    }
    
    @Override
    public Card getSuggPerson() {
        return null; //Ignore
    }

    @Override
    public void createSuggestion(BoardCell boardCell) {
        //Ignore
    }


}