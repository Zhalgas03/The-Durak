import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
public class PauseOverlay extends JPanel {
    private final SoundService soundService;
    private final GameController controller;
    private final JFrame frame;

    public PauseOverlay(JFrame frame, SoundService soundService, GameController controller) {
        this.soundService = soundService;
        this.controller = controller;
        this.frame = frame;

        initOverlay();
        enableEventBlocking();
    }
    @Override
    protected void processMouseEvent(MouseEvent e) {
        e.consume();
    }

    @Override
    protected void processKeyEvent(KeyEvent e) {
        e.consume();
    }

    private void initOverlay() {
        setOpaque(false);
        setLayout(new GridBagLayout());
        setBounds(0, 0, frame.getWidth(), frame.getHeight());

        JPanel menuPanel = createMenuPanel();
        add(menuPanel);
        setVisible(false);
    }

    private void enableEventBlocking() {
        addMouseListener(new MouseAdapter() {});
        addKeyListener(new KeyAdapter() {});
        setFocusable(true);
        requestFocusInWindow();
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(0, 0, 0, 200));
        panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));
        panel.setPreferredSize(new Dimension(580, 250));

        JLabel pausedLabel = new JLabel("Paused");
        pausedLabel.setFont(GameStyle.loadGameFont(75f));
        pausedLabel.setForeground(Color.WHITE);
        pausedLabel.setAlignmentX(CENTER_ALIGNMENT);

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        buttonRow.setOpaque(false);

        buttonRow.add(createTextLabel("Resume", this::resumeGame));
        buttonRow.add(createTextLabel("Restart", this::restartGame));
        buttonRow.add(createTextLabel("Menu", this::goToMenu));

        panel.add(Box.createVerticalStrut(50));
        panel.add(pausedLabel);
        panel.add(Box.createVerticalStrut(30));
        panel.add(buttonRow);

        return panel;
    }

    private JLabel createTextLabel(String text, Runnable action) {
        JLabel label = new JLabel(text);
        label.setFont(GameStyle.loadGameFont(30f));
        label.setForeground(Color.WHITE);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                soundService.playMoveSound();
                action.run();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                label.setForeground(new Color(255, 255,0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setForeground(Color.WHITE);
            }
        });

        return label;
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
