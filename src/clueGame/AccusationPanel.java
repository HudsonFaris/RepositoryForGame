package clueGame;

/**
 * Accusation Panel - This class holds the structure for the accusation panel pop up, and handles accusation for some
 * circumstances. 
 * 
 * @author Hudson Faris
 * @author Sam Bangapadang
 * 
 * Sources: JavaDocs
 * Date: 4/22/2024
 * 
 */

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class AccusationPanel extends JFrame {
	
	private JComboBox<String> personCombo;
    private JComboBox<String> weaponCombo;
    private JComboBox<String> roomCombo;

    private List<String> players = new ArrayList<>();
    private List<String> weapons = new ArrayList<>();
    private List<String> rooms = new ArrayList<>();


    private JButton submit = new JButton("Submit");
    private JButton cancel = new JButton("Cancel");

    public AccusationPanel(){ //IN ORDER
        initializeComponents();
        populateComboBoxes();
        setupLayout();
    }

    private void initializeComponents(){
        cancel.addActionListener(e -> setVisible(false));
        submit.addActionListener(e -> handleSubmit());
    }

    private void populateComboBoxes() {
        Board.getInstance().gameDeck.forEach(card -> {
            switch (card.getCardType()){
                case PERSON: //In order
                    players.add(card.getCardName());
                    break;
                case WEAPON:
                    weapons.add(card.getCardName());
                    break;
                case ROOM:
                    rooms.add(card.getCardName());
                    break;
            }
        });
//Combos
        weaponCombo = new JComboBox<>(weapons.toArray(new String[0]));
        personCombo = new JComboBox<>(players.toArray(new String[0]));
        roomCombo = new JComboBox<>(rooms.toArray(new String[0]));
    }

    private void setupLayout() { //Creates splash layout
        setTitle("Make an accusation");
        setSize(251, 251);
        setLayout(new GridLayout(3, 2));
        add(new JLabel("Current room"));
        add(roomCombo);
        add(new JLabel("Person"));
        add(personCombo);
        add(new JLabel("Weapon"));
        add(weaponCombo);
        add(submit);
        add(cancel);
    }

    
    //Handles sumbissions
    private void handleSubmit() {
        String roomChoice = roomCombo.getSelectedItem().toString();
        String personChoice = personCombo.getSelectedItem().toString();
        String weaponChoice = weaponCombo.getSelectedItem().toString();

        Card submitRoom = findCardByName(roomChoice);
        Card submitWeapon = findCardByName(weaponChoice);
        Card submitPerson = findCardByName(personChoice);

        boolean resultOfAccusation = Board.getInstance().checkAccusation(submitPerson, submitRoom, submitWeapon);
        displayResult(resultOfAccusation);
    }

    //Finds the card by string name accordingly. 
    private Card findCardByName(String cardName) {
        return Board.getInstance().gameDeck.stream()
                .filter(card -> card.getCardName().equals(cardName))
                .findFirst()
                .orElse(null);
    }

    
    //Displays the result of the accusation accordingly, lose or win. 
    private void displayResult(boolean resultOfAccusation) {
        setVisible(false);
        String message = resultOfAccusation ? "You win!" : "You lose."; //That would suck if you lost
        JOptionPane.showMessageDialog(this, message);
        //System.out.println("Working?");
        System.exit(0);
    }
}

//Done!