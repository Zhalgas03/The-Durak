import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class GameUI {
    private final CardAnimator cardAnimator = new CardAnimator();
    private JFrame frame;
    private JPanel panel;
    private final GameState gameState;
    private final GameSession game;
    private final SoundService soundService;
    private final ImageCache imageCache;
    private final GameController controller;


    public GameUI(GameState gameState, GameSession game, SoundService soundService, ImageCache imageCache,GameController controller) {
        this.gameState = gameState;
        this.game = game;
        this.soundService = soundService;
        this.imageCache = imageCache;
        this.controller = controller;
    }

    private static class DoubleBufferedPanel extends JPanel {
        public DoubleBufferedPanel(LayoutManager layout) {
            super(layout);
            setDoubleBuffered(true);
        }
    }

    public void createAndShowGUI() {
        frame = new JFrame("Durak");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1800, 1000));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        panel = new DoubleBufferedPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel botPanel = new DoubleBufferedPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel durakPanel = new DoubleBufferedPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel trump2 = new DoubleBufferedPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

        Color bgColor = Color.BLACK;
        Border whiteBorder = BorderFactory.createLineBorder(Color.WHITE, 4);

        panel.setBackground(bgColor);
        botPanel.setBackground(bgColor);
        trump2.setBackground(bgColor);
        info.setBackground(bgColor);
        durakPanel.setBackground(bgColor);
        frame.getContentPane().setBackground(bgColor);

        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), whiteBorder));
        botPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), whiteBorder));
        trump2.setBorder(BorderFactory.createEmptyBorder(200, 75, 200, 200));
        info.setBorder(BorderFactory.createEmptyBorder(210, 20, 0, 0));

        frame.add(panel, BorderLayout.SOUTH);
        frame.add(botPanel, BorderLayout.NORTH);
        frame.add(durakPanel, BorderLayout.EAST);
        frame.add(trump2, BorderLayout.CENTER);
        frame.add(info, BorderLayout.WEST);


        ImageIcon originalIcon = new ImageIcon("style/cards/burger.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JButton burgerButton = new JButton(scaledIcon);
        burgerButton.setFocusPainted(false);
        burgerButton.setContentAreaFilled(false);
        burgerButton.setBorderPainted(false);
        burgerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        burgerButton.setToolTipText("Menu");

        burgerButton.setBounds(frame.getWidth() - 60, 10, 40, 40);
        JLayeredPane layeredPane = frame.getLayeredPane();
        layeredPane.add(burgerButton, JLayeredPane.POPUP_LAYER);


        PauseOverlay pauseOverlay = new PauseOverlay(frame, soundService, controller);
        frame.setGlassPane(pauseOverlay);
        pauseOverlay.setVisible(false);

        burgerButton.addActionListener(e -> {
            soundService.playMoveSound();
            boolean overlayVisible = !pauseOverlay.isVisible();
            pauseOverlay.setVisible(overlayVisible);
            if (overlayVisible) {
                soundService.pauseBackgroundMusic();
            } else {
                soundService.resumeBackgroundMusic();
                frame.requestFocusInWindow();
            }
        });

        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                burgerButton.setBounds(frame.getWidth() - 60, 10, 40, 40);
                pauseOverlay.setSize(frame.getWidth(), frame.getHeight());
            }
        });

        frame.setVisible(true);
    }


    public void updateImages() {
        SwingUtilities.invokeLater(() -> {
            updatePlayerPanel();
            updateInfoPanel();
            updateTrumpPanel();
            updateBotPanel();
            frame.revalidate();
            frame.repaint();
        });
    }

    private void updatePlayerPanel() {
        panel.removeAll();
        addCardImagesToPanel(panel, gameState.getPlayerHand(), false, true);
    }

    private void updateInfoPanel() {
        JPanel info = (JPanel) frame.getContentPane().getComponent(4);
        info.removeAll();

        JLabel deckCountLabel = new JLabel("Deck size:");
        JLabel deckCountLabel2 = new JLabel(String.valueOf(gameState.getDeck().size()));
        JLabel textLabel = getjLabel();

        Font pixelFont = GameStyle.loadGameFont(24f);
        deckCountLabel.setFont(pixelFont);
        deckCountLabel2.setFont(GameStyle.loadGameFont(48f));
        textLabel.setFont(pixelFont);

        deckCountLabel.setForeground(Color.WHITE);
        deckCountLabel2.setForeground(Color.WHITE);
        textLabel.setForeground(Color.WHITE);

        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        deckCountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        deckCountLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);

        info.add(textLabel);
        info.add(Box.createRigidArea(new Dimension(0, 20)));
        info.add(deckCountLabel);
        info.add(deckCountLabel2);
    }

    private void updateTrumpPanel() {
        JPanel trump2 = (JPanel) frame.getContentPane().getComponent(3);
        trump2.removeAll();

        ImageIcon midIcon = getMidIcon();
        JLabel midLabel = new JLabel(midIcon);
        trump2.add(midLabel);

        midLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                soundService.playCardClick();
            }
        });
    }

    private void updateBotPanel() {
        JPanel botPanel = (JPanel) frame.getContentPane().getComponent(1);
        botPanel.removeAll();

        SimpleBot bot = game.getBotInstance();
        if (bot != null) {
            addCardImagesToPanel(botPanel, bot.getHand(), true, false);
        }


        JPanel separator = new JPanel();
        separator.setPreferredSize(new Dimension(5, 150));
        separator.setBackground(Color.WHITE);
        botPanel.add(separator);

        addCardImagesToPanel(botPanel, gameState.getDurakHand(), true, false);
    }

    private void addCardImagesToPanel(JPanel targetPanel, List<String> cards, boolean isHidden, boolean addClickListener) {
        for (int i = 0; i < cards.size(); i++) {
            final int index = i;
            String cardPath = "style/cards/" + (isHidden ? "BACK.png" : cards.get(index) + ".png");
            ImageIcon icon = imageCache.getImage(cardPath);

            JPanel cardWrapper = new JPanel(null);
            cardWrapper.setOpaque(false);
            cardWrapper.setPreferredSize(new Dimension(icon.getIconWidth() + 10, icon.getIconHeight() + 20));

            JLabel label = new JLabel(icon);
            label.setBounds(5, 15, icon.getIconWidth(), icon.getIconHeight());

            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            if (addClickListener) {
                cardAnimator.setupCardHoverAnimation(label, 15);
                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        game.handleCardClick(index);
                        soundService.playCardClick();
                    }
                });
            } else {
                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        soundService.playCardDenied();
                    }
                });
            }

            cardWrapper.add(label);
            targetPanel.add(cardWrapper);
        }
    }

    private JLabel getjLabel() {
        ImageIcon trumpIcon = new ImageIcon(gameState.getTrumpImagePath());
        return new JLabel(trumpIcon);
    }

    private ImageIcon getMidIcon() {
        String path = switch (gameState.getTurn()) {
            case PLAYER -> "BACK.png";
            case BOT -> {
                SimpleBot bot = game.getBotInstance();
                yield bot != null && !bot.getHand().isEmpty() ? bot.getHand().get(0) + ".png" : "BACK.png";
            }
            case OPPONENT -> gameState.getDurakHand().isEmpty() ? "BACK.png" : gameState.getDurakHand().get(0) + ".png";
        };
        return new ImageIcon("style/cards/" + path);
    }
}
