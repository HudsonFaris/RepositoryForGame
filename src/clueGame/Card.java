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

import java.util.Objects;

public class Card {

    private String cardName;
    private CardType cardType;

    /**
     * Constructs a new Card with the specified name and type.
     *
     * @param name the name of the card
     * @param cardType the type of the card
     */
    public Card(String name, CardType cardType) {
        this.cardName = name;
        this.cardType = cardType;
    }


     //Checks if this card is equal to another card based on card name and type.

    @Override
    public boolean equals(Object obj) {
        if (this == obj){ //???
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Card other =(Card) obj;
        return Objects.equals(cardName, other.cardName) && cardType == other.cardType;
    }


    //Returns a hash code

    @Override
    public int hashCode() {
        return Objects.hash(cardName, cardType);
    }

    // Returns the card type.
    public CardType getCardType() {
        return cardType;
    }
    //Sets the card name.

    public void setCardName(String name) {
        this.cardName = name;
    }
    
    //Returns card name
    public String getCardName() {
        return cardName;
    }

    //Sets card type

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }
}

//easy