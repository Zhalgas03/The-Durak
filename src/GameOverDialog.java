import javax.swing.*;
import java.awt.*;

public class GameOverDialog {
    private final GameController gameController;


    public GameOverDialog(GameController gameController) {
        this.gameController = gameController;

        SwingUtilities.invokeLater(this::showDialog);
    }

    private void showDialog() {
        UIManager.put("OptionPane.background", new Color(30, 30, 30));
        UIManager.put("Panel.background", new Color(30, 30, 30));
        UIManager.put("OptionPane.messageForeground", Color.WHITE);
        UIManager.put("Button.background", new Color(60, 60, 60));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.focus", new Color(80, 80, 80));

        String[] options = {"Replay", "Exit"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "<html><h2 style='color:white;'>Game Over</h2><p style='color:white;'>Play again?</p></html>",
                "Game Over",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) {
            Timer timer = new Timer(500, e -> gameController.restartGame());
            timer.setRepeats(false);
            timer.start();
        } else {
            System.exit(0);
        }
    }
}