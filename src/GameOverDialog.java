import javax.swing.*;

public class CustomDialogExample {
    private final GameController gameController;
    private final GameUI gameUI;

    public CustomDialogExample(GameController gameController, GameUI gameUI) {
        this.gameController = gameController;
        this.gameUI = gameUI;
        SwingUtilities.invokeLater(this::showDialog);
    }

    private void showDialog() {
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
            gameController.restartGame(gameUI); // RESTART
        } else {
            System.exit(0); // EXIT
        }
    }
}
