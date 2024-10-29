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
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class ClueGame extends JFrame {

    private boolean firstTurn = true;
    private GameControlPanel controlPanel;
    private CardPanel cardPanel;

    //Constructor, dimension and adds all panels, runs game well. 
    public ClueGame(Board board) {
        setTitle("Clue Game - CSCI306");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(700, 750));

        controlPanel = new GameControlPanel(board);
        cardPanel = new CardPanel();

        add(board, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        add(cardPanel, BorderLayout.EAST);

        showSplash();
        nextTurn(); //Turn after splash all
        board.setPanels(controlPanel, cardPanel);

        pack(); //Use pack here
        setVisible(true);
    }

    //Handles the logic for the next turn, specifically the first turn
    private void nextTurn() {
        if (firstTurn) {
            int dieRoll = (int) (Math.random() * 6)+1;
            controlPanel.setTurn((HumanPlayer) Board.getInstance().HumanPlayer, dieRoll, true);
            controlPanel.updateDisplay();
            firstTurn = false;
        }
    }

    //Displays initial splash and is centered text thanks to html
    private void showSplash() {
        String message = "<html><div style='text-align: center;'>You are " + Board.getInstance().HumanPlayer.getName() + 
                         "<br>Can you find the solution<br>before the computer players?</div></html>";
        JOptionPane.showMessageDialog(this, message, "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
    }

    //Starts game, don't try sound
    public static void main(String[] args){
        Board board = Board.getInstance();
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        board.initialize();
        new ClueGame(board);
        //playMusic();
    }
}