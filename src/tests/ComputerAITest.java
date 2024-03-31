package tests;

import java.awt.Color;
import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;

public class ComputerAITest {

    private static Board board;
    public static Card mikeCard, varzCard, greifCard, stoopCard, owenCard, chipCard, knifeCard, mailroomCard;

    @BeforeAll
    public static void setUp() {
        board = Board.getInstance();
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        board.initialize();

        mikeCard = new Card("Mike", CardType.PERSON);
        varzCard = new Card("Varz", CardType.PERSON);
        greifCard = new Card("Greif", CardType.PERSON);
        stoopCard = new Card("Stoop", CardType.PERSON);
        owenCard = new Card("Owen", CardType.PERSON);
        chipCard = new Card("Chip", CardType.PERSON);
        knifeCard = new Card("Knife", CardType.WEAPON);
        mailroomCard = new Card("Mailroom", CardType.ROOM);
    }

    @Test
    public void testSuggLocation() {
        Player computerPlayerOne = new ComputerPlayer();
        computerPlayerOne.createSuggestion(computerPlayerOne.getLocation());
        String location = computerPlayerOne.getSuggRoom();
        Assert.assertTrue(location == "Walkway");

        Player computerPlayerTwo = new ComputerPlayer("Test", Color.WHITE, 20, 2);
        computerPlayerTwo.createSuggestion(computerPlayerTwo.getLocation());
        String locationTWO = computerPlayerTwo.getSuggRoom();
        Assert.assertTrue(locationTWO == "Pantry");
    }

    @Test
    public void testCreateSuggestion() {
        Player computerPlayerOne = new ComputerPlayer();
        computerPlayerOne.getHand().clear();
        computerPlayerOne.getSeen().clear();
        board.getDeck().clear();
        computerPlayerOne.updateSeen(varzCard);
        computerPlayerOne.updateSeen(greifCard);
        computerPlayerOne.updateSeen(mikeCard);
        computerPlayerOne.updateSeen(stoopCard);
        board.addDeckCards(varzCard);
        board.addDeckCards(greifCard);
        board.addDeckCards(mikeCard);
        board.addDeckCards(stoopCard);
        board.addDeckCards(chipCard);
        board.addDeckCards(knifeCard);
        computerPlayerOne.createSuggestion(computerPlayerOne.getLocation());
        Assert.assertEquals("Chip", computerPlayerOne.getSuggPerson().getCardName());
        Assert.assertEquals("Knife", computerPlayerOne.getSuggWeapon().getCardName());
    }

    @Test
    public void testSelectTargets() {
        Player player = new ComputerPlayer();
        board.calcTargets(player.getLocation(), 1);
        Set<BoardCell> targets = board.getTargets();
        BoardCell initialCell = player.getLocation();
        BoardCell cell = player.selectTargets(targets);
        Assert.assertTrue(cell != initialCell);

        Player playerTwo = new ComputerPlayer("Name", Color.WHITE, 6, 5);
        playerTwo.updateSeen(mailroomCard);
        board.calcTargets(player.getLocation(), 3);
        Set<BoardCell> targetsTwo = board.getTargets();
        BoardCell cellTwo = player.selectTargets(targetsTwo);
        Assert.assertTrue(cellTwo.getInitial() != 'R');
    }
}