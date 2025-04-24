import javax.swing.*;

public class CustomDialogExample {
    public CustomDialogExample(int gameType) {
        SwingUtilities.invokeLater(() -> showDialog(gameType));
    }

    private void showDialog(int gameType) {
        String[] options = {"Replay", "Exit"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Game Over. Play again?",
                "Game Over",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) {
            GameController.restartGame();
        } else {
            System.exit(0);
        }
    }
}
