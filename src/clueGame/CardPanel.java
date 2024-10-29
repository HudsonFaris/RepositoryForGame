package clueGame;

/**
 * CardPanel Class - Component of JPanel, creates the right side card panel
 * 
 * @author Hudson Faris
 * @author Sam Bangapadang
 * 
 * Sources: JavaDocs
 * Date: 4/7/2024
 * 
 */

import java.awt.Color;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class CardPanel extends JPanel{

	// variabels to hold the main panel and the in hand/ seen cards
	private JPanel mainPanel, inHand, seen;
	private JLabel handLabel, seenLabel;


	//just for testing - I hope - huddy
	Player humanPlayer;

	//creates jpanels and titles then adds them to the main panel
	public CardPanel(){
		humanPlayer = Board.getInstance().HumanPlayer;
		mainPanel = new JPanel();
		JPanel weapons = weapons();
		JPanel people = people();
		
		JPanel rooms = rooms();

		Border peopleTitle = BorderFactory.createTitledBorder("People");
		people.setBorder(peopleTitle);
		Border roomTitle = BorderFactory.createTitledBorder("Rooms");
		rooms.setBorder(roomTitle);
		Border weaponTitle = BorderFactory.createTitledBorder("Weapons");
		weapons.setBorder(weaponTitle);

		mainPanel.add(weapons);
		mainPanel.add(people);
		mainPanel.add(rooms);
		mainPanel.setLayout(new GridLayout(3, 1));
		mainPanel.setBorder(new TitledBorder (new EtchedBorder()));
		add(mainPanel);
	}


	//Creates the room part of hte panel.
	public JPanel rooms() {
		JPanel roomCards = new JPanel();
		inHand = new JPanel();
		seen = new JPanel();
		handLabel = new JLabel("In Hand:");
		seenLabel = new JLabel("Seen:");

		ArrayList<Card> hand = new ArrayList<Card>();
		ArrayList<Card> seenList = new ArrayList<Card>();
		hand = Board.getInstance().HumanPlayer.getHand();
		seenList = Board.getInstance().HumanPlayer.getSeen();
		if(hand.size() == 0) {
			JLabel handText = new JLabel("None");  
			inHand.add(handText);
		}
		
		if(seenList.size() == 0){
			JTextField alreadySeen =new JTextField("None");
			seen.add(alreadySeen);
		}
		
		for(Card c: hand){
			if(c.getCardType() == CardType.ROOM) {
				JLabel handText= new JLabel();
				handText.setText(c.getCardName());
				
				inHand.add(handText);
				handText.revalidate();
			}
		}
		if(seenList.size()> 0) {
			for(Card c: seenList) {
				if(c.getCardType().equals(CardType.ROOM)) {
					JLabel alreadySeen = new JLabel();
					alreadySeen.setText(c.getCardName());
					Color color = Board.getInstance().cardColors.get(c);
					alreadySeen.setBackground(color);
					seen.add(alreadySeen);
					seen.revalidate();
				}
			}
		}
		roomCards.add(handLabel); //inline order adding. 
		roomCards.add(inHand);
		roomCards.add(seenLabel);
		roomCards.add(seen);
		seen.revalidate();
		roomCards.setLayout(new GridLayout(0, 1, 0, 0)); //default values??
		inHand.setLayout(new GridLayout(0, 1));
		seen.setLayout(new GridLayout(0, 1));
		roomCards.setBorder(new TitledBorder (new EtchedBorder()));
		roomCards.setBackground(Color.GRAY); //GRAY BEST
		
		return roomCards;
	}
	
	public JPanel people(){
		JPanel knownCards = new JPanel();
		inHand = new JPanel();
		seen = new JPanel();
		handLabel = new JLabel("In Hand:");
		seenLabel = new JLabel("Seen:");

		ArrayList<Card> hand = new ArrayList<Card>();
		ArrayList<Card> seenList = new ArrayList<Card>();
		hand = Board.getInstance().HumanPlayer.getHand();
		seenList =Board.getInstance().HumanPlayer.getSeen();
		if(hand.size() ==0) {
			
			JLabel handText = new JLabel("None");  
			inHand.add(handText);
		}
		if(seenList.size() == 0) {
			JTextField alreadySeen = new JTextField("None");
			seen.add(alreadySeen);
		}
		for(Card c: hand) {
			if(c.getCardType() == CardType.PERSON) {
				JLabel handText = new JLabel(); 
				handText.setText(c.getCardName());
				inHand.add(handText);
				handText.revalidate();
			}
		}
		if(seenList.size()>0){
			for(Card c: seenList){
				if(c.getCardType().equals(CardType.PERSON)) {
					JLabel alreadySeen = new JLabel();
					alreadySeen.setText(c.getCardName());
					Color color = Board.getInstance().cardColors.get(c);
					alreadySeen.setBackground(color);
					seen.revalidate();
				}
				//check razor
			}
		}
		knownCards.add(handLabel);
		knownCards.add(inHand);
		knownCards.add(seenLabel);
		
		knownCards.add(seen);
		seen.revalidate();
		knownCards.setLayout(new GridLayout(0, 1)); // 1 for sure
		inHand.setLayout(new GridLayout(0, 1)); // 2
		seen.setLayout(new GridLayout(0, 1)); // 2
		knownCards.setBorder(new TitledBorder (new EtchedBorder()));
		knownCards.setBackground(Color.GRAY);
		
		return knownCards;
	}

	// Weapons aprt of the panel - check done
	public JPanel weapons() {
		JPanel weaponCards = new JPanel();
		inHand = new JPanel();
		seen = new JPanel();
		handLabel = new JLabel("In Hand:");
		seenLabel = new JLabel("Seen:"); //Messages as needed

		ArrayList<Card> hand = new ArrayList<Card>();
		ArrayList<Card> seenList = new ArrayList<Card>();
		hand = Board.getInstance().HumanPlayer.getHand();
		seenList = Board.getInstance().HumanPlayer.getSeen();
		
		if(hand.size() == 0) {
			JLabel handText = new JLabel("None");// 
			inHand.add(handText);
			//System.out.println("Test");
		}
		if(seenList.size() == 0) {
			JTextField alreadySeen = new JTextField("None");
			seen.add(alreadySeen);
		}
		for(Card c: hand) {
			if(c.getCardType().equals(CardType.WEAPON)) {
				JLabel handText = new JLabel();
				handText.setText(c.getCardName());
				inHand.add(handText);
				handText.revalidate();
			} 
		}
//System.out.println(".");
		if(seenList.size() > 0) {
			for(Card c: seenList){
				if(c.getCardType().equals(CardType.WEAPON)) {
					JLabel alreadySeen = new JLabel();
					alreadySeen.setText(c.getCardName());
					Color color = Board.getInstance().cardColors.get(c);
					alreadySeen.setBackground(color);
					seen.add(alreadySeen);
					
					//Check razor
					seen.revalidate();
				}
			}
		}

		weaponCards.add(handLabel); //inline order
		weaponCards.add(inHand);
		weaponCards.add(seenLabel);
		weaponCards.add(seen);
		seen.revalidate();
		weaponCards.setLayout(new GridLayout(0, 1)); //Value seem to work quite wlel. 
		inHand.setLayout(new GridLayout(0, 1));
		seen.setLayout(new GridLayout(0, 1));
		weaponCards.setBorder(new TitledBorder (new EtchedBorder()));
		
		
		weaponCards.setBackground(Color.GRAY);
		return weaponCards;
	}


	//update display, will be used ALOT!!
	//just removes everything and sets it equal to the new values or it should (works)
	public void updateDisplay() {
		mainPanel.removeAll();
		mainPanel = new CardPanel();
	}

}