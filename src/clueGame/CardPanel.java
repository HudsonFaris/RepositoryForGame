

package clueGame;

/**
 * CardPanel - Class for control card GUI in ClueGame
 * @author Hudson Faris
 * @author Sam Bangapadang
 * 
 * Sources: JavaDocs
 * Date: 4/5/2024
 * 
 */


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class CardPanel extends JPanel {

    private JPanel mainPanel;
    private static Board board;
    private Player humanPlayer = Board.getInstance().HumanPlayer;

    
    //Panel constructor, sets with constants. 
    public CardPanel() {
        mainPanel = new JPanel(new GridLayout(3, 1));
        mainPanel.setBorder(new TitledBorder(new EtchedBorder()));

        JPanel people = people();
        JPanel rooms = rooms();
        JPanel weapons = weapons();

        mainPanel.add(people);
        mainPanel.add(rooms);
        mainPanel.add(weapons);
        
        add(mainPanel);
    }

    /**
     * Creates the people part of GUI
     * @return
     */
    public JPanel people() {
        JPanel knownCards = new JPanel(new GridLayout(0, 1));
        knownCards.setBorder(new TitledBorder(new EtchedBorder()));
        knownCards.setBackground(Color.LIGHT_GRAY);
        
        JLabel handLabel = new JLabel("In Hand:");
        JLabel seenLabel = new JLabel("Seen:");
        JPanel inHand = new JPanel(new GridLayout(0, 1));
        JPanel seen = new JPanel(new GridLayout(0, 1));

        populateCardPanels(humanPlayer.getHand(), inHand, CardType.PERSON);
        populateCardPanels(humanPlayer.getSeen(), seen, CardType.PERSON);
        
        knownCards.add(handLabel);
        knownCards.add(inHand);
        knownCards.add(seenLabel);
        knownCards.add(seen);

        return knownCards;
    }

    /**
     * Creates room part of GUI
     * @return
     */
    public JPanel rooms() {
        JPanel roomCards = new JPanel(new GridLayout(0, 1, 0, 0));
        roomCards.setBorder(new TitledBorder(new EtchedBorder()));
        roomCards.setBackground(Color.LIGHT_GRAY);

        JLabel handLabel = new JLabel("In Hand:");
        JLabel seenLabel = new JLabel("Seen:");
        JPanel inHand = new JPanel(new GridLayout(0, 1));
        JPanel seen = new JPanel(new GridLayout(0, 1));

        populateCardPanels(humanPlayer.getHand(), inHand, CardType.ROOM);
        populateCardPanels(humanPlayer.getSeen(), seen, CardType.ROOM);

        roomCards.add(handLabel);
        roomCards.add(inHand);
        roomCards.add(seenLabel);
        roomCards.add(seen);

        return roomCards;
    }

    /**
     * Creates weapon part of GUI
     * @return
     */
    public JPanel weapons() {
        JPanel weaponCards = new JPanel(new GridLayout(0, 1));
        weaponCards.setBorder(new TitledBorder(new EtchedBorder()));
        weaponCards.setBackground(Color.LIGHT_GRAY);

        JLabel handLabel = new JLabel("In Hand:");
        JLabel seenLabel = new JLabel("Seen:");
        JPanel inHand = new JPanel(new GridLayout(0, 1));
        JPanel seen = new JPanel(new GridLayout(0, 1));

        populateCardPanels(humanPlayer.getHand(), inHand, CardType.WEAPON);
        populateCardPanels(humanPlayer.getSeen(), seen, CardType.WEAPON);

        weaponCards.add(handLabel);
        weaponCards.add(inHand);
        weaponCards.add(seenLabel);
        weaponCards.add(seen);

        return weaponCards;
    }

    
    //Populates with colors
    private void populateCardPanels(ArrayList<Card> cards, JPanel panel, CardType type) {
        for(Card c: cards) {
            if(c.getCardType() == type) {
                JTextField cardText = new JTextField(10);
                cardText.setText(c.getCardName());
                //Color color = board.getCardColor().get(c); //CHECK
                cardText.setBackground(Color.WHITE); //White for now, will match cards. 
                panel.add(cardText);
            }
        }
        if(panel.getComponentCount() == 0) {
            panel.add(new JTextField("None", 10));
        }
    }

    //Updates the display. 
    public void updateDisplay() {
        mainPanel.removeAll();
        JPanel people = people();
        JPanel rooms = rooms();
        JPanel weapons = weapons();

        mainPanel.add(people);
        mainPanel.add(rooms);
        mainPanel.add(weapons);
    }

    //Testing main method basically. 
    public static void main(String[] args) {
        board = Board.getInstance();
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        board.initialize();

        CardPanel cardPanel = new CardPanel();
        JFrame frame = new JFrame();
        frame.setContentPane(cardPanel);
        frame.setSize(new Dimension(200, 900));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}