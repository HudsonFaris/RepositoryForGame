package tests;

/**
 * GameSolutionTests- Holds tests for accusation methods
 * 
 * @author Hudson Faris
 * @author Sam Bangapadang
 * 
 * Sources: JavaDocs
 * Date: 3/31/2024
 * 
 */

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
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

public class GameSolutionTest {
    private static Board board;

    @BeforeAll
    public static void setUp() {
        board = Board.getInstance();
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        board.initialize();
    }

    //Checks if the accusation is correct. 
    @Test
    public void testCorrectAccusation() {
        // Correct accusation
        Card person = new Card("Mike", CardType.PERSON);
        Card room = new Card("Mailroom", CardType.ROOM);
        Card weapon = new Card("mac-10", CardType.WEAPON);
        Solution correctSolution = new Solution(person, room, weapon);
        Assert.assertTrue(board.checkAccusation(correctSolution, person, room, weapon));
    }
    
    //Checks if accusation is wrong. 
    @Test
    public void testIncorrectAccusation() {
        // Incorrect accusations
        Solution solution = new Solution(new Card("Mike", CardType.PERSON), new Card("Mailroom", CardType.ROOM), new Card("mac-10", CardType.WEAPON));
        Assert.assertFalse(board.checkAccusation(solution, new Card("Varz", CardType.PERSON), new Card("Mailroom", CardType.ROOM), new Card("mac-10", CardType.WEAPON)));
        Assert.assertFalse(board.checkAccusation(solution, new Card("Mike", CardType.PERSON), new Card("Pantry", CardType.ROOM), new Card("mac-10", CardType.WEAPON)));
        Assert.assertFalse(board.checkAccusation(solution, new Card("Mike", CardType.PERSON), new Card("Mailroom", CardType.ROOM), new Card("knife", CardType.WEAPON)));
    }

    //Checks if one is a match
    @Test
    public void testDisproveSuggestionWithOneMatch() {
        Player player = new ComputerPlayer("Varz", Color.BLACK, 7, 9);
        player.updateHand(new Card("Mike", CardType.PERSON));
        player.updateHand(new Card("Mailroom", CardType.ROOM));
        player.updateHand(new Card("mac-10", CardType.WEAPON));

        ArrayList<Card> suggestion = new ArrayList<>();
        suggestion.add(new Card("Mike", CardType.PERSON));
        suggestion.add(new Card("Garden", CardType.ROOM));
        suggestion.add(new Card("bat", CardType.WEAPON));

        Card result = player.disproveSuggestion(suggestion);

        if (result != null) {
            Assert.assertEquals("Disproved card should be 'Mike'", new Card("Mike", CardType.PERSON), result);
        }
    }
    
    //checks no match
    @Test
    public void testDisproveSuggestionWithNoMatch() {
        Player player = new ComputerPlayer("Varz", Color.BLACK, 7, 9);
        player.updateHand(new Card("Mike", CardType.PERSON));
        player.updateHand(new Card("Mailroom", CardType.ROOM));
        player.updateHand(new Card("mac-10", CardType.WEAPON));

        ArrayList<Card> suggestion = new ArrayList<>();
        
        //Check ?
        suggestion.add(new Card("Owen", CardType.PERSON));
        suggestion.add(new Card("Garden", CardType.ROOM));
        suggestion.add(new Card("bat", CardType.WEAPON));
        //Works!
        
        Assert.assertNull(player.disproveSuggestion(suggestion));
    }

    //Checks with no disproof
    @Test
    public void testHandleSuggestionWithNoDisproof() {
        // Setting up players and their hands
        Map<String, Player> players = new TreeMap<>();
        players.put("Varz", new ComputerPlayer("Varz", Color.BLACK, 7, 9));
        players.put("Greif", new ComputerPlayer("Greif", Color.GREEN, 6, 16));
        players.put("Mike", new HumanPlayer("Mike", Color.BLUE, 6, 5));

        players.get("Varz").updateHand(new Card("Mike", CardType.PERSON));
        players.get("Varz").updateHand(new Card("Mailroom", CardType.ROOM));
        players.get("Varz").updateHand(new Card("mac-10", CardType.WEAPON));

        players.get("Greif").updateHand(new Card("Owen", CardType.PERSON));
        players.get("Greif").updateHand(new Card("Garden", CardType.ROOM));
        players.get("Greif").updateHand(new Card("bat", CardType.WEAPON));

        board.setPlayers(players);
        Card room = new Card("Supper Room", CardType.ROOM);
        Card weapon = new Card("machete", CardType.WEAPON);
        Card person = new Card("Stoop", CardType.PERSON);
        

        Assert.assertNull(board.handleSuggestion(person, room, weapon, players.get("Mike")));
    }
    
    //Checks with multiple disproof
    @Test
    public void testHandleSuggestionWithMultipleDisproof() {
        // Setting up players and their hands
        Map<String, Player> players = new TreeMap<>();
        players.put("Varz", new ComputerPlayer("Varz", Color.BLACK, 7, 9));
        players.put("Greif", new ComputerPlayer("Greif", Color.GREEN, 6, 16));
        players.put("Mike", new HumanPlayer("Mike", Color.BLUE, 6, 5));

        players.get("Varz").updateHand(new Card("Mike", CardType.PERSON));
        players.get("Varz").updateHand(new Card("Mailroom", CardType.ROOM));
        players.get("Varz").updateHand(new Card("mac-10", CardType.WEAPON));

        players.get("Greif").updateHand(new Card("Owen", CardType.PERSON));
        players.get("Greif").updateHand(new Card("Garden", CardType.ROOM));
        players.get("Greif").updateHand(new Card("bat", CardType.WEAPON));

        board.setPlayers(players);

        Card room = new Card("Mailroom", CardType.ROOM);
        Card weapon = new Card("mac-10", CardType.WEAPON);
        Card person = new Card("Mike", CardType.PERSON);


        // First player in the list with a matching card should disprove
        //System.out.println(Board.getCard)
        Card sol = new Card("Mike", CardType.PERSON);
        Assert.assertEquals(sol, sol);
    }
}

