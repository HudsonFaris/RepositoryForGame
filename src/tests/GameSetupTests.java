package tests;


/**
 * GameSetupTests - Holds tests for skeleton code of weapons, players, and cards
 * 
 * @author Hudson Faris
 * @author Sam Bangapadang
 * 
 * Sources: JavaDocs
 * Date: 3/29/2024
 * 
 */

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

public class GameSetupTests {

    // constants for verification
    public static final int NUM_ROOMS = 9;
    public static final int NUM_PLAYER_CARDS = 6;
    public static final int NUM_WEAPON_CARDS = 6;
    public static final int NUM_DECK_CARDS = 21;
    
    // create board instance
    private static Board board;
    
    @BeforeAll
    public static void setUp() {
        board = Board.getInstance();
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        board.initialize();
    }

    // checks for the correct number of players
    @Test
    public void loadNumPlayers() {
        Map<String, Player> players = board.getPlayers();
        assertEquals(NUM_PLAYER_CARDS, players.size());
    }

    // confirms all players are where they need to be
    @Test
    public void testPlayerNames() {
    	
        Map<String, Player> players = board.getPlayers();
        assertEquals(NUM_PLAYER_CARDS, players.size());
        Assert.assertTrue(players.containsKey("Mike"));
        Assert.assertTrue(players.containsKey("Varz"));
        Assert.assertTrue(players.containsKey("Greif"));
        Assert.assertTrue(players.containsKey("Stoop"));
        Assert.assertTrue(players.containsKey("Owen"));
        Assert.assertTrue(players.containsKey("Chip"));
    }

    @Test
    public void testPlayerColors() {
        Map<String, Player> players = board.getPlayers();
        Assert.assertNotSame(Color.BLACK, players.get("Mike").getColor());
        Assert.assertEquals(Color.BLACK, players.get("Varz").getColor());
        Assert.assertNotSame(Color.BLACK, players.get("Greif").getColor());
        Assert.assertNotSame(Color.BLACK, players.get("Stoop").getColor());
        Assert.assertNotSame(Color.BLACK, players.get("Owen").getColor());
        Assert.assertNotSame(Color.BLACK, players.get("Chip").getColor());
    }

    // tests that the code separates human players from computer players
    @Test
    public void testHumanPlayer() {
        Map<String, Player> players = board.getPlayers();
        Assert.assertEquals(HumanPlayer.class, players.get("Mike").getClass());
        Assert.assertEquals(ComputerPlayer.class, players.get("Varz").getClass());
        Assert.assertEquals(ComputerPlayer.class, players.get("Greif").getClass());
        Assert.assertEquals(ComputerPlayer.class, players.get("Stoop").getClass());
        Assert.assertEquals(ComputerPlayer.class, players.get("Owen").getClass());
        Assert.assertEquals(ComputerPlayer.class, players.get("Chip").getClass());
    }

    // tests that all players are mapped to the correct location
    @Test
    public void testLocations() {
        Map<String, Player> players = board.getPlayers();
        
        Assert.assertEquals(board.getCell(4, 5), players.get("Mike").getLocation()); 
        Assert.assertEquals(board.getCell(1, 14), players.get("Varz").getLocation()); 
        Assert.assertEquals(board.getCell(5, 15), players.get("Greif").getLocation()); 
        Assert.assertEquals(board.getCell(16, 2), players.get("Stoop").getLocation()); 
        Assert.assertEquals(board.getCell(12, 22), players.get("Owen").getLocation()); 
        Assert.assertEquals(board.getCell(24, 14), players.get("Chip").getLocation()); 
    }

    // checks for the correct number of weapons
    @Test
    public void loadNumWeapons() {
        ArrayList<Card> weapons = board.getWeapons();
        assertEquals(NUM_WEAPON_CARDS, weapons.size());
    }

    // test that weapons are populated and of card type weapon
    @Test
    public void testWeapons() {
        ArrayList<Card> weapons = board.getWeapons();
        Assert.assertTrue(weapons != null);
        Assert.assertTrue(weapons.get(1).getCardType() == CardType.WEAPON);
    }

    // checks that the deck is the correct size after removal of solution cards
    @Test
    public void loadNumDeck() {
        ArrayList<Card> deck = board.getDeck();
        assertEquals(NUM_DECK_CARDS, deck.size() + 3);
    }
	
	// test all of the players hands after the cards are dealt
	@Test
	public void testHand() {
		Map<String, Player> players = board.getPlayers();
		ArrayList<Card> hand = players.get("Mike").getHand();
		Assert.assertNotEquals(null, hand);
		ArrayList<Card> handTwo = players.get("Varz").getHand();
		Assert.assertNotEquals(null, handTwo);
		ArrayList<Card> handThree = players.get("Greif").getHand();
		Assert.assertNotEquals(null, handThree);
		ArrayList<Card> handFour = players.get("Stoop").getHand();
		Assert.assertNotEquals(null, handFour);
		ArrayList<Card> handFive = players.get("Owen").getHand();
		Assert.assertNotEquals(null, handFive);
		ArrayList<Card> handSix = players.get("Chip").getHand();
		Assert.assertNotEquals(null, handSix);				
	}
	
	// test that the solution class works
	@Test
	public void testSolution() {
		Card person = new Card("Test", CardType.PERSON);
		Card room = new Card("Room", CardType.ROOM);
		Card weapon = new Card("Weapon", CardType.WEAPON);
		
		Solution answers = new Solution(person, room, weapon);
		
		Assert.assertTrue(answers != null);
		
		Assert.assertTrue(answers.getPerson().getCardType() == CardType.PERSON);
		Assert.assertTrue(answers.getRoom().getCardType() == CardType.ROOM);
		Assert.assertTrue(answers.getWeapon().getCardType() == CardType.WEAPON);
	}

}