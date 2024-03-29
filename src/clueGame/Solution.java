package clueGame;

/**
 * Solution Class - Holds the solution cards, that's really it... Doesn't do much 
 * 
 * @author Hudson Faris
 * @author Sam Bangapadang
 * 
 * Sources: JavaDocs
 * Date: 3/29/2024
 * 
 */

public class Solution {

	Card room;
	Card person;
	Card Weapon;
	
	
	
	
	//construct
	public Solution(Card person, Card room, Card weapon) {
		this.person = person;
		this.room = room;
		this.Weapon = weapon;
	}
	
	public Card getRoom() {
		return room;
	}

	public void setRoom(Card room) {
		this.room = room;
	}
	
	public Card getPerson() {
		return person;
	}

	public void setPerson(Card person) {
		this.person = person;
	}

	public void setWeapon(Card weapon) {
		Weapon = weapon;
	}

	public Card getWeapon() {
		return Weapon;
	}

}