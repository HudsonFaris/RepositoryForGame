package clueGame;

/**
 * Solution Class - Holds the solution variables, that's really it... Doesn't do much 
 * 
 * @author Hudson Faris
 * @author Sam Bangapadang
 * 
 * Sources: JavaDocs
 * Date: 3/29/2024
 * 
 */

//Pretty straightforward
public class Solution {

	Card room;
	Card person;
	Card Weapon;

	//construct
	public Solution(Card person, Card room, Card weapon) {
		this.Weapon = weapon;
		this.room = room;
		this.person = person;
	
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