package clueGame;


/**
 * GameControlPanel Class - Component of JPanel, creates the bottom side game panel
 * 
 * @author Hudson Faris
 * @author Sam Bangapadang
 * 
 * Sources: JavaDocs
 * Date: 4/7/2024
 * 
 */

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class GameControlPanel extends JPanel {

	// variables for the UI components
    private JButton next, accuse;
    private JTextField guess, result, turn, roll;

    // strings to hold value names
    String theGuess, guessResult, turnName;

    // game-related variables
    int rollNum;
    int counter = 0;
    Player player;
    Color color;
    private static Board board;

	//Sets control panel
    public GameControlPanel(Board gameBoard) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 0));

        JPanel nameAndDie = createTurnAndRoll();
        JPanel buttons = createButtons();
        JPanel guessPanel = createGuess();
        JPanel resultPanel = createResult();

        mainPanel.add(nameAndDie);
        mainPanel.add(buttons);
        mainPanel.add(guessPanel);
        mainPanel.add(resultPanel);

        add(mainPanel);
        board = gameBoard;
    }
    
 // setters and getters first ones arent being used right now
 	private void setGuess(String string) {
 		this.theGuess = string;
 	}

 	private void setGuessResult(String string) {
 		this.guessResult = string;
 	}

 	void setTurn(Player computerPlayer, int i, boolean drawTargets) {
 		this.rollNum = i;
 		this.player = computerPlayer;
 		this.color = computerPlayer.getColor();
 		this.turnName = computerPlayer.getName();
 		if(drawTargets) {
 			rollTheDice(drawTargets);
 		}
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

 	public Player getPlayer() {
 		return player;
 	}

	//Next, works/ DONE
	public JPanel createTurnAndRoll() {

		JPanel panel = new JPanel();
		JPanel whoseTurnPanel = new JPanel();
		JPanel rollPanel = new JPanel();

		JLabel whoseTurn = new JLabel("Whose turn?");
		JLabel theRoll = new JLabel("Roll:");

		turn = new JTextField(turnName);
		roll = new JTextField(rollNum);

		panel.setLayout(new GridLayout(2, 0));
		whoseTurnPanel.setLayout(new GridLayout(1, 2));
		rollPanel.setLayout(new GridLayout(1, 2));

		whoseTurnPanel.add(whoseTurn);
		whoseTurnPanel.add(turn);
		rollPanel.add(theRoll);
		rollPanel.add(roll);

		panel.add(whoseTurnPanel, BorderLayout.EAST);
		panel.add(rollPanel, BorderLayout.WEST);
		panel.setBorder(new TitledBorder(new EtchedBorder()));
		panel.setBackground(Color.LIGHT_GRAY);

		return panel;
	}

	//creates buttons when needed. 
	public JPanel createButtons() {

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));

		accuse = new JButton("Make Accusation");
		next = new JButton("NEXT!");
		next.addActionListener(new ButtonListener());
		panel.add(accuse);
		panel.add(next);

		panel.setBorder(new TitledBorder(new EtchedBorder()));
		panel.setBackground(Color.LIGHT_GRAY);

		return panel;
	}

	

	//creates guess panel, naturally...
	public JPanel createGuess() {

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 0));

		guess = new JTextField(theGuess, 25);
		guess.setBorder(BorderFactory.createTitledBorder("Guess"));

		panel.add(guess, BorderLayout.WEST);
		panel.setBorder(new TitledBorder(new EtchedBorder()));
		panel.setBackground(Color.GRAY);
		return panel;

	}

	//finds targets when asked after roll dice
	public void rollTheDice(boolean firstDraw) {
		if(!firstDraw) {
			rollNum = (int) (Math.random() * 6) + 1;
		}
		roll.setText(String.valueOf(rollNum));
		board.calcTargets(player.getLocation(), rollNum);
		if (player == board.players.get(Board.getInstance().HumanPlayer.getName())) {
			return;
		} else {
			player.setLocation(player.selectTargets(board.getTargets()));
			board.calcTargets(new BoardCell(0, 0), 0);
		}
	}

	//creates panels
	//rests then adds everything tot he paenl
	public JPanel createResult() {

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 0));

		result = new JTextField(guessResult, 25);
		result.setBorder(BorderFactory.createTitledBorder("Guess Result!"));

		panel.add(result);
		panel.setBorder(new TitledBorder(new EtchedBorder()));
		panel.setBackground(Color.GRAY);
		return panel;
	}

	// update display function that updates the text boxes
	public void updateDisplay() {
		guess.setText(getGuess());
		result.setText(getResult());
		turn.setText(turnName);
		turn.setBackground(color);
		roll.setText(Integer.toString(rollNum));
	}
	
	//listener for certain buttons being hit (listens for humana actions)
		class ButtonListener implements ActionListener {

			public void actionPerformed(ActionEvent e) {
				
				if(player.getName() == board.HumanPlayer.getName()) {
					board.humanPlayerTurn = true;
				} 
				
				counter = counter % 6;
				
				
				player = board.players.get(board.gameCharacters.get(counter).getCardName());
				if(counter == 5 && player != board.players.get(Board.getInstance().HumanPlayer.getName())) {
					player = board.players.get(Board.getInstance().HumanPlayer.getName());
				}
				if(counter != 5 && player == board.players.get(Board.getInstance().HumanPlayer.getName())) {
					player = board.players.get(board.gameCharacters.get(5).getCardName());
				}
				turn.setText(player.getName());
				turn.setBackground(player.getColor());
				counter++;

				rollTheDice(false);
			}
			//Test?
		}

	

	/*
		public static void main(String[] args) {
		board = Board.getInstance();
			board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
			board.initialize();
		
			// test filling in the data
	
			panel.setTurn(new ComputerPlayer("Col. Mustard", Color.ORANGE, 6, 5), 5);
			panel.setGuess("I have no guess!");
			panel.setGuessResult("So you have nothing?");
			panel.updateDisplay();
		}
		*/
}