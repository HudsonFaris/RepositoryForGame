package clueGame;

/**
 * ClueGame Class - Component of JPanel, creates the board of the game. 
 * 
 * @author Hudson Faris
 * @author Sam Bangapadang
 * 
 * Sources: JavaDocs
 * Date: 4/7/2024
 * 
 */

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame {
    
    private boolean firstTurn = true;
    private GameControlPanel controlPanel;
    private CardPanel cardPanel;
    private Board board;

    
    //Creates room map and other panels/components
    public ClueGame() {
        initializeGame();
        initializeUI();
    }
    
    //Sets up game
    private void initializeGame() {
        board = Board.getInstance();
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        board.initialize();
    }
    
    /**
     * Creates dimensions and adds components and organizes it
     */
    private void initializeUI() {
        setTitle("Clue Game - CSCI306");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(700, 720));
        
        controlPanel = new GameControlPanel(board);
        cardPanel = new CardPanel();
        
        add(board, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        add(cardPanel, BorderLayout.EAST);
        
        nextTurn();
        
        showSplash();
        pack();
        setVisible(true);
    }

    
    //Controls turn - starts with human player
    private void nextTurn() {
        if (firstTurn) {
            controlPanel.setTurn((HumanPlayer) board.HumanPlayer, (int) (Math.random() * 6) + 1, true);
            controlPanel.updateDisplay();
            firstTurn = false;
        }
    }
    
    public void showSplash() {
		JButton ok = new JButton();
		JOptionPane.showMessageDialog(ok, "<html><center>You are " + Board.getInstance().HumanPlayer.getName() + 
				 "<br>Can you find the solution"+ "<br>before the computer players?");
	}

    public static void main(String[] args) {
        new ClueGame();
    }
}