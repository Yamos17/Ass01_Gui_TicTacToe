import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeFrame extends JFrame {
    private TicTacToeButton[][] buttons;
    private GameLogic gameLogic;

    public TicTacToeFrame() {
        setTitle("Tic Tac Toe");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridLayout(3, 3));
        buttons = new TicTacToeButton[3][3];
        gameLogic = new GameLogic();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new TicTacToeButton();
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                mainPanel.add(buttons[i][j]);
            }
        }

        add(mainPanel, BorderLayout.CENTER);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        add(quitButton, BorderLayout.SOUTH);
    }

    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (gameLogic.makeMove(row, col)) {
                buttons[row][col].setText(gameLogic.getCurrentPlayerSymbol());
                if (gameLogic.checkForWin()) {
                    JOptionPane.showMessageDialog(null, gameLogic.getCurrentPlayerSymbol() + " wins!");
                    resetGame();
                } else if (gameLogic.isBoardFull()) {
                    JOptionPane.showMessageDialog(null, "It's a tie!");
                    resetGame();
                } else {
                    gameLogic.switchPlayer();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid move. Try again.");
            }
        }
    }

    private void resetGame() {
        gameLogic.resetBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicTacToeFrame frame = new TicTacToeFrame();
            frame.setVisible(true);
        });
    }
}
