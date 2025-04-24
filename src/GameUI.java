import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameUI {

    private static JFrame frame;
    private static JPanel panel;

    public static class DoubleBufferedPanel extends JPanel {
        public DoubleBufferedPanel(LayoutManager layout) {
            super(layout);
            setDoubleBuffered(true);
        }
    }

    public static void createAndShowGUI() {
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

        panel.setBackground(Variables.getPanelColor());
        botPanel.setBackground(Variables.getPanelColor());
        trump2.setBackground(Variables.getPanelColor());
        info.setBackground(Variables.getPanelColor());
        durakPanel.setBackground(Variables.getPanelColor());
        frame.getContentPane().setBackground(Variables.getPanelColor());

        frame.add(panel, BorderLayout.SOUTH);
        frame.add(botPanel, BorderLayout.NORTH);
        frame.add(durakPanel, BorderLayout.EAST);
        frame.add(trump2, BorderLayout.CENTER);
        frame.add(info, BorderLayout.WEST);

        trump2.setBorder(BorderFactory.createEmptyBorder(200, 75, 200, 200));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        botPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        info.setBorder(BorderFactory.createEmptyBorder(210, 20, 0, 0));

        frame.setVisible(true);

    }
    public static void updateImages() {
        SwingUtilities.invokeLater(() -> {
            updatePlayerPanel();
            updateInfoPanel();
            updateTrumpPanel();
            updateBotPanel();
            frame.revalidate();
            frame.repaint();
        });
    }


    private static void updatePlayerPanel() {
        panel.removeAll();
        addCardImagesToPanel(panel, Variables.getList(), false, true);
    }
    private static void updateInfoPanel() {
        JPanel info = (JPanel) frame.getContentPane().getComponent(4);
        info.removeAll();

        JLabel deckCountLabel = new JLabel("Deck size:");
        JLabel deckCountLabel2 = new JLabel(String.valueOf(Game52.getDeck().size()));
        JLabel tlabel = getjLabel();


        deckCountLabel.setFont(LoadFont.loadGameFont(24f));
        deckCountLabel2.setFont(LoadFont.loadGameFont(48f));

        deckCountLabel.setForeground(Color.WHITE);
        deckCountLabel2.setForeground(Color.WHITE);

        tlabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        deckCountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        deckCountLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);

        info.add(tlabel);
        info.add(deckCountLabel);
        info.add(deckCountLabel2);
    }
    private static void updateTrumpPanel() {
        JPanel trump2 = (JPanel) frame.getContentPane().getComponent(3);
        trump2.removeAll();

        ImageIcon midIcon = getMidIcon();

        JLabel midLabel = new JLabel(midIcon);
        trump2.add(midLabel);
        midLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Sound.playSoundEffect(Variables.getClickSoundPath());
            }
        });
    }


    private static void updateBotPanel() {
        JPanel botPanel = (JPanel) frame.getContentPane().getComponent(1);
        botPanel.removeAll();

        SimpleBot bot = Game52.getBotInstance();
        if (bot != null) {
            addCardImagesToPanel(botPanel, bot.getHand(), true, false);
        }
        botPanel.add(new JLabel(new ImageIcon("cards/separate.png")));
        addCardImagesToPanel(botPanel, Variables.dList(), true, false);


    }
    private static class ImageCache {
        private static final Map<String, ImageIcon> cache = new HashMap<>();

        public static ImageIcon getImage(String path) {
            return cache.computeIfAbsent(path, p -> {
                try {
                    return new ImageIcon(new File(p).getAbsolutePath());
                } catch (Exception e) {
                    System.err.println("Error loading image: " + p);
                    return new ImageIcon();
                }
            });
        }
    }



    private static void addCardImagesToPanel(JPanel targetPanel, List<String> cards,
                                             boolean isHidden, boolean addClickListener) {
        for (int i = 0; i < cards.size(); i++) {
            final int index = i;
            String cardPath = "cards/" + (isHidden ? "BACK.png" : cards.get(index) + ".png");
            ImageIcon icon = ImageCache.getImage(cardPath);

            JPanel cardWrapper = new JPanel(null);
            cardWrapper.setOpaque(false);
            cardWrapper.setPreferredSize(new Dimension(icon.getIconWidth() + 10, icon.getIconHeight() + 20));

            JLabel label = new JLabel(icon);
            label.setBounds(5, 15, icon.getIconWidth(), icon.getIconHeight());

            if (addClickListener) {
                // Настройка анимации и курсора
                CardAnimator.setupCardHoverAnimation(label, 15);

                // Обработчик клика
                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Game52.onImageClick(index);
                        Sound.playSoundEffect(Variables.getClickSoundPath());
                    }
                });
            } else {
                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Sound.playSoundEffect(Variables.getDenySoundPath());
                    }
                });
            }

            cardWrapper.add(label);
            targetPanel.add(cardWrapper);
        }
    }



    private static JLabel getjLabel() {
        ImageIcon trumpik = new ImageIcon(Variables.getTrumpImagePath());
        return new JLabel(trumpik);
    }

    private static ImageIcon getMidIcon() {
        String path = switch (Game52.getTurn()) {
            case PLAYER -> "BACK.png";
            case BOT -> {
                SimpleBot bot = Game52.getBotInstance();
                yield bot != null && !bot.getHand().isEmpty() ? bot.getHand().get(0) + ".png" : "BACK.png";
            }
            case OPPONENT -> Variables.dList().isEmpty() ? "BACK.png" : Variables.dList().get(0) + ".png";
        };
        return new ImageIcon("cards/" + path);
    }

}
