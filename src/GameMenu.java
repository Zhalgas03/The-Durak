import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameMenu extends JFrame {
    private int selectedIndex = 0;
    private JButton[] menuButtons;
    private final SoundService soundService;

    public GameMenu(SoundService soundService) {
        this.soundService = soundService;
        setupFrame();
        addComponents();
        setupKeyBindings();
        soundService.playMenuTheme();
    }


    private void setupFrame() {
        setTitle("DURAK");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK);
        setLayout(new GridBagLayout());
    }



    private void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 20, 5, 20);
        gbc.anchor = GridBagConstraints.CENTER;
        GridBagConstraints logoGbc = (GridBagConstraints) gbc.clone();
        logoGbc.insets = new Insets(5, 20, 40, 20);

        JLabel logo = new JLabel("DURAK");
        logo.setFont(GameStyle.loadGameFont(230f));
        logo.setForeground(new Color(255, 255, 255));
        add(logo, logoGbc);


        String[] buttonLabels = {"TURBO", "CLASSIC", "OPTIONS", "EXIT"};
        menuButtons = new JButton[buttonLabels.length];
        gbc.gridy = 1;
        for (int i = 0; i < buttonLabels.length; i++) {
            menuButtons[i] = createArcadeButton(buttonLabels[i], i);
            add(menuButtons[i], gbc);
            gbc.gridy++;
        }
        updateSelection();
    }


    private JButton createArcadeButton(String text, int index) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;

                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.setFont(GameStyle.loadGameFont(28f));
                g2d.setColor(selectedIndex == index ?
                        new Color(255, 255, 0) : new Color(255, 255, 255));

                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(text)) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2d.drawString(text, x, y);
            }
        };

        btn.setPreferredSize(new Dimension(400, 60));
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setFocusPainted(false);
        return btn;
    }




    private void setupKeyBindings() {
        InputMap im = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getRootPane().getActionMap();

        im.put(KeyStroke.getKeyStroke("UP"), "up");
        im.put(KeyStroke.getKeyStroke("DOWN"), "down");
        im.put(KeyStroke.getKeyStroke("LEFT"), "left");
        im.put(KeyStroke.getKeyStroke("RIGHT"), "right");
        im.put(KeyStroke.getKeyStroke("ENTER"), "enter");

        am.put("up", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (--selectedIndex < 0) selectedIndex = menuButtons.length - 1;
                updateSelection();
            }
        });

        am.put("down", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (++selectedIndex >= menuButtons.length) selectedIndex = 0;
                updateSelection();
            }
        });



        am.put("enter", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                handleSelection();
            }
        });
    }


    private void updateSelection() {
        for (JButton btn : menuButtons) {
            btn.repaint();
        }
        soundService.playMoveSound();
    }

    private void handleSelection() {

        switch(selectedIndex) {
            case 0:

                getRootPane().setEnabled(false);
                soundService.playSelectSound();

                new Timer(1000, e -> {
                    ((Timer)e.getSource()).stop();
                    startGame(36);
                    getRootPane().setEnabled(true);
                }).start();
                break;
            case 1:

                getRootPane().setEnabled(false);
                soundService.playSelectSound();

                new Timer(1000, e -> {
                    ((Timer)e.getSource()).stop();
                    startGame(52);
                    getRootPane().setEnabled(true);
                }).start();
                break;
            case 2: soundService.playCardDenied();; break;
            case 3: System.exit(0);
        }
    }




    private void startGame(int deckMode) {
        soundService.stopBackgroundMusic();
        dispose();
        new Thread(() -> {

            Main.main(new String[]{String.valueOf(deckMode)});
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SoundService sound = new SoundService();
            GameMenu menu = new GameMenu(sound);
            menu.setVisible(true);
        });
    }
}