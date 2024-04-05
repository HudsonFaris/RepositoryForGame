package clueGame;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class GameControlPanel extends JPanel {
    private static final int TEXT_FIELD_LENGTH = 25; //Constanf ro text length

    //Instance variables for GUI components...
    private JButton nextButton, accuseButton;
    private JTextField guessTextField, resultTextField, turnTextField, rollTextField;
    private String theGuess, guessResult, turnName;
    private int rollNum;
    private Color playerColor;
//Test.

	private Player player;

    private static Board board;

    // Constructor
    public GameControlPanel() {
        setLayout(new GridLayout(2, 2));
        add(createTurnAndRollPanel());
        add(createButtonsPanel());
        add(createGuessPanel());
        add(createResultPanel());
    }
    
    /**
     * Creates turn/roll
     * @return
     */
    private JPanel createTurnAndRollPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(createLabelledField("Whose turn?", turnTextField = new JTextField(turnName)));
        panel.add(createLabelledField("Roll:", rollTextField = new JTextField(Integer.toString(rollNum))));
        stylePanel(panel);
        return panel;
    }

    /*
     * Buttons
     */
    private JPanel createButtonsPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        accuseButton = new JButton("Make Accusation");
        nextButton = new JButton("NEXT!");
        panel.add(accuseButton);
        panel.add(nextButton);
        stylePanel(panel);
        return panel;
    }

    //Everything else..
    private JPanel createGuessPanel() {
        return createSingleFieldPanel("Guess", guessTextField = new JTextField(theGuess, TEXT_FIELD_LENGTH));
    }

    private JPanel createResultPanel() {
        return createSingleFieldPanel("Guess Result!", resultTextField = new JTextField(guessResult, TEXT_FIELD_LENGTH));
    }

    private JPanel createLabelledField(String label, JTextField textField) {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(new JLabel(label));
        panel.add(textField);
        return panel;
    }

    private JPanel createSingleFieldPanel(String title, JTextField textField) {
        JPanel panel = new JPanel(new GridLayout(1, 1));
        textField.setBorder(BorderFactory.createTitledBorder(title));
        panel.add(textField);
        stylePanel(panel);
        return panel;
    }

    private void stylePanel(JPanel panel) {
        panel.setBorder(new TitledBorder(new EtchedBorder()));
        panel.setBackground(Color.LIGHT_GRAY);
    }

    public void updateDisplay() {
        guessTextField.setText(theGuess);
        resultTextField.setText(guessResult);
        turnTextField.setText(turnName);
        turnTextField.setBackground(playerColor);
        rollTextField.setText(Integer.toString(rollNum));
    }

    //Setters and Getters precisely. 
    public void setGuess(String guess) {
        this.theGuess = guess;
    }

    public void setGuessResult(String result) {
        this.guessResult = result;
    }

    public void setTurn(Player player, int rollNum) {
        this.player = player;
        this.turnName = player.getName();
        this.rollNum = rollNum;
        this.playerColor = player.getColor();
    }

    public String getGuess() {
        return theGuess;
    }

    public String getResult() {
        return guessResult;
    }

    public int getRoll() {
        return rollNum;
    }

    public static void main(String[] args) {
        board = Board.getInstance();
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        board.initialize();
        GameControlPanel panel = new GameControlPanel();  //create the panel
        JFrame frame = new JFrame();  //create the frame
        frame.setContentPane(panel); 
        frame.setSize(750, 180);  //size the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true); // make it visible

        //Test ish to match example stuff. 
        panel.setTurn(new ComputerPlayer("Col. Mustard", Color.ORANGE, 6, 5), 5);
        panel.setGuess("I have no guess!");
        panel.setGuessResult("So you have nothing?");
        panel.updateDisplay();
    }
}