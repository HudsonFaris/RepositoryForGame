package clueGame;

/**
 * SuggestionPanel - This class holds the structure for the suggestionpanel background work
 * 
 * @author Hudson Faris
 * @author Sam Bangapadang
 * 
 * Sources: JavaDocs
 * Date: 4/22/2024
 * 
 */

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class SuggestionPanel extends JFrame {
    private String currRoomName;
    private Card submitPerson, submitWeapon, submitRoom;
    private JButton submit, cancel;
    private List<String> players;
    private List<String> weapons;
    private JComboBox<String> personCombo, weaponCombo;

    public SuggestionPanel(){
    	//INLINE ORDER
        initializeComponents();
        layoutComponents();
        attachListeners();
    }

    //Initializes card components
    private void initializeComponents(){
        currRoomName = Board.getInstance().roomMap.get(Board.getInstance().HumanPlayer.getLocation().getInitial()).getName();
        players = Board.getInstance().gameDeck.stream()
                        .filter(c -> c.getCardType() == CardType.PERSON) //Looked up filter methods
                        .map(Card::getCardName)
                        .collect(Collectors.toList());
        weapons = Board.getInstance().gameDeck.stream()
                        .filter(c -> c.getCardType() == CardType.WEAPON)
                        .map(Card::getCardName)
                        .collect(Collectors.toList());

        submit = new JButton("Submit");
        cancel = new JButton("Cancel");
        
        personCombo = new JComboBox<>(players.toArray(new String[0]));
        weaponCombo = new JComboBox<>(weapons.toArray(new String[0]));
    }

    
    //Layouts components for the size
    private void layoutComponents() {
        setTitle("Make a suggestion");
        setSize(275, 240); //Made smaller
        setLayout(new GridLayout(4, 2));
        
        JLabel roomLabel = new JLabel("Current room");
        JLabel roomText = new JLabel(currRoomName);
        roomText.setBorder(new TitledBorder(new EtchedBorder()));
        
        //ORDER MATTERS - DONT TOUCH
        add(roomLabel);
        add(roomText);
        add(new JLabel("Person"));
        add(personCombo);
        add(new JLabel("Weapon"));
        add(weaponCombo);
        add(submit);
        add(cancel);
        //System.out.println("Test");
    }

    private void attachListeners(){
        cancel.addActionListener(e -> setVisible(false));
        submit.addActionListener(new ActionListener() {
            @Override //Listener -huddy
            public void actionPerformed(ActionEvent e) {
                handleSubmission();
            }
        });
    }
    
    //Check razor

    private void handleSubmission(){
        String personChoice=(String) personCombo.getSelectedItem();
        String weaponChoice=(String) weaponCombo.getSelectedItem();
        //System.out.println("Test");
        submitRoom = findCardByName(currRoomName, CardType.ROOM);
        submitPerson = findCardByName(personChoice, CardType.PERSON);
        submitWeapon = findCardByName(weaponChoice, CardType.WEAPON);
        
        Board.getInstance().handleSuggestion(submitPerson, submitRoom, submitWeapon, Board.getInstance().HumanPlayer);
        setVisible(false);
    }

    //Done
    private Card findCardByName(String name, CardType type) {
        return Board.getInstance().gameDeck.stream()
                .filter(c -> c.getCardName().equals(name) && c.getCardType() == type)
                .findFirst()
                .orElse(null); //Filter again. 
    }
}

//Class works
