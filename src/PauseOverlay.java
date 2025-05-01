import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PauseOverlay extends JPanel {
    private final SoundService soundService;
    private final GameController controller;
    private final JFrame frame;

    public PauseOverlay(JFrame frame, SoundService soundService, GameController controller) {
        this.soundService = soundService;
        this.controller = controller;
        this.frame = frame;

        initOverlay();
    }

    private void initOverlay() {
        setOpaque(false);
        setLayout(new GridBagLayout());
        setBounds(0, 0, frame.getWidth(), frame.getHeight());

        JPanel menuPanel = createMenuPanel();
        add(menuPanel);
        setVisible(false);
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(0, 0, 0, 200));
        panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));
        panel.setPreferredSize(new Dimension(600, 200));

        JLabel pausedLabel = new JLabel("Paused");
        pausedLabel.setFont(GameStyle.loadGameFont(64f));
        pausedLabel.setForeground(Color.WHITE);
        pausedLabel.setAlignmentX(CENTER_ALIGNMENT);

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        buttonRow.setOpaque(false);

        buttonRow.add(createIconButton("style/cards/resume.png", this::resumeGame));
        buttonRow.add(createIconButton("style/cards/restart.png", this::restartGame));
        buttonRow.add(createIconButton("style/cards/home.png", this::goToMenu));

        panel.add(Box.createVerticalStrut(20));
        panel.add(pausedLabel);
        panel.add(Box.createVerticalStrut(30));
        panel.add(buttonRow);

        return panel;
    }

    private JButton createIconButton(String path, Runnable action) {
        ImageIcon icon = new ImageIcon(path);
        Image scaledImage = icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(scaledImage));
        button.setPreferredSize(new Dimension(64, 64));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                soundService.playMoveSound();
            }
        });
        button.addActionListener(e -> action.run());
        return button;
    }

    private void resumeGame() {
        setVisible(false);
        soundService.resumeBackgroundMusic();
        frame.requestFocusInWindow();
    }

    private void restartGame() {
        setVisible(false);
        controller.restartGame();
        soundService.resumeBackgroundMusic();
        frame.requestFocusInWindow();
    }

    private void goToMenu() {
        soundService.stopBackgroundMusic();
        frame.dispose();
        SwingUtilities.invokeLater(() -> new GameMenu(new SoundService()).setVisible(true));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.SrcOver.derive(0.7f));
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
        super.paintComponent(g);
    }
}
