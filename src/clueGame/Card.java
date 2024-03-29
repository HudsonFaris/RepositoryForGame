package clueGame;

/**
 * Card - Child class of player, creates comp player and uses abstract methods if
 * and only if the player is a computer. 5/6. 
 * 
 * @author Hudson Faris
 * @author Sam Bangapadang
 * 
 * Sources: JavaDocs
 * Date: 3/29/2024
 * 
 */

public class Card{

	//Card variables
	private String cardName;
	private CardType cardType;


	//Construct
	public Card(String name, CardType cardType) {
		super();
		this.cardType = cardType;
		this.cardName = name;
	}
	
	
	// equals method as described....
	public boolean equals(Card target) {
		if(target.cardName == cardName && target.getCardType() == cardType) {
			if(getClass() == target.getClass()) {
				return true;
			}
		} else {
			return false;
		}
		return false;
	}
	
	public void setCardName(String name) {
		this.cardName = name;
	}
	public String getCardName() {
		return cardName;
	}
	
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}
	
	public CardType getCardType() {
		return cardType;
	}

	
}