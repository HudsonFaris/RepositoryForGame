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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class GameControlPanel extends JPanel {

	Player player;
	Color color;
	String theGuess, guessResult, turnName;

	private JButton next, accuse;
	public JTextField guess, result, turn, roll;
	
	boolean firstIter = true;
	boolean gameStartDice = true;
	ClueGame game;

	int rollNum;
	int counter = 0;

	
	private static Board board;

	//creates and adds panels
	public GameControlPanel(Board gameBoard) {

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2, 0));

		JPanel nameAndDie = createTurnAndRoll();
		mainPanel.add(nameAndDie);

		JPanel buttons = createButtons();
		mainPanel.add(buttons);

		JPanel guess = createGuess();
		mainPanel.add(guess);

		JPanel result = createResult();
		mainPanel.add(result);

		add(mainPanel);
		board = gameBoard;
		//check done
	}



	//Creates panels and their labels, adds details
	public JPanel createTurnAndRoll() {

		JPanel panel = new JPanel();
		JPanel rollPanel = new JPanel();
		JPanel whoseTurnPanel = new JPanel();

		JLabel whoseTurn = new JLabel("Whose turn?");
		JLabel theRoll = new JLabel("Roll:");

		turn = new JTextField(turnName);
		roll = new JTextField(rollNum);

		rollPanel.setLayout(new GridLayout(1, 2));

		panel.setLayout(new GridLayout(2, 0));
		whoseTurnPanel.setLayout(new GridLayout(1, 2));
		whoseTurnPanel.add(whoseTurn);
		whoseTurnPanel.add(turn);
		rollPanel.add(theRoll);
		rollPanel.add(roll);
		panel.add(whoseTurnPanel, BorderLayout.EAST);
		panel.add(rollPanel, BorderLayout.WEST);
		panel.setBorder(new TitledBorder(new EtchedBorder()));
		panel.setBackground(Color.LIGHT_GRAY); //Check lightGRAY works

		return panel; //Done
	}

	//Creates the pressable buttons -refer to actionListener/mouseListener
	public JPanel createButtons() {

		JPanel panel = new JPanel();
		
		panel.setLayout(new GridLayout(2, 1));

		accuse = new JButton("Make Accusation");
		next = new JButton("NEXT!");
		next.addActionListener(new ButtonListener());
		accuse.addActionListener(new accusationListener());
		
		//Done
		panel.add(accuse);
		panel.add(next);

		panel.setBorder(new TitledBorder(new EtchedBorder()));
		panel.setBackground(Color.LIGHT_GRAY);

		return panel;
	}

	//Listens for button click
	class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			guess.setText("");
			result.setText("");

			if(board.humanPlayerTurn == true &&board.hasMoved ==false) {
				JButton ok = new JButton();
				JOptionPane.showMessageDialog(ok, "Player has not finished turn"); //Make sure works when  in edge cases
				return;
			}


			counter = counter % 6; //Hardcode sontants for player sizes
			player = board.players.get(board.gameCharacters.get(counter).getCardName());
			if(counter!=5 && player == board.players.get(Board.getInstance().HumanPlayer.getName())) {
				player = board.players.get(board.gameCharacters.get(5).getCardName());
			}
			if(counter==5 && player != board.players.get(Board.getInstance().HumanPlayer.getName())) {
				player = board.players.get(Board.getInstance().HumanPlayer.getName());
			}
			
			//Make sure this is true false in that ORDER
			if(player.getName()==board.HumanPlayer.getName()){
				board.humanPlayerTurn = true;
				board.hasMoved = false;
			} 
			
			turn.setText(player.getName());
			
			turn.setBackground(player.getColor());
			counter++; //Adds

			rollTheDice(false);
		}
	}

	
	//Again listens for accustorbutton click
	class accusationListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			if(board.humanPlayerTurn == true && board.hasMoved == false) {
				AccusationPanel panel = new AccusationPanel();
				panel.setVisible(true);
				board.humanPlayerTurn = false;
				
			} else if (board.humanPlayerTurn == false) {
				JButton ok = new JButton();
				JOptionPane.showMessageDialog(ok, "Error: Not your turn!");
			} 
		}
	}

	// creates the needed panels and labels, and passes an instance variable
	// through for updating purposes. then adds everything to the panel.
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

	//Helper method to help determine targets, roll is that
	public void rollTheDice(boolean firstDraw) {
		if(!firstDraw) {
			rollNum = (int) (Math.random()*6) + 1; //6 sides = 6.
		}
		
		//Done. 
		roll.setText(String.valueOf(rollNum));
		board.calcTargets(player.getLocation(), rollNum);
		if (player == board.players.get(Board.getInstance().HumanPlayer.getName())) {
			return;
		} else {
			
			player.setLocation(player.selectTargets(board.targets));
			board.calcTargets(new BoardCell(0, 0), 0);
		}
	}

	//Creates the result box for the gameControl panel
	public JPanel createResult() {

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 0));

		result = new JTextField(guessResult, 25);
		result.setBorder(BorderFactory.createTitledBorder("Guess Result!"));

		//Adds the panel now, make it gray - done huddy
		panel.add(result);
		panel.setBorder(new TitledBorder(new EtchedBorder()));
		panel.setBackground(Color.GRAY);
		return panel;
	}

	//Update display for text boxes, god function //
	public void updateDisplay() {
		result.setText(guessResult);
		guess.setText(theGuess);
		if(firstIter == true){
			turn.setBackground(color);
			turn.setText(turnName);
			roll.setText(Integer.toString(rollNum));
			firstIter = false;
		}
	}

	//Does nothing really..
	void setTurn(Player computerPlayer, int i, boolean drawTargets){
		this.rollNum = i;
		this.color = computerPlayer.getColor();
		this.player = computerPlayer;
		this.turnName = computerPlayer.getName();
		if(drawTargets){
			rollTheDice(drawTargets);
		}
	}
	
	// setters and getters
		public void setGuess(String string) {
			this.theGuess = string;
		}

		public void setGuessResult(String string) {
			this.guessResult = string;
		}

	public int getRoll() {
		return rollNum;
	}

	public Player getPlayer() {
		return player;
	}

	public String getResult() {
		return guessResult;
	}
	
	public String getGuess() {
		return theGuess;
	}
	
}

