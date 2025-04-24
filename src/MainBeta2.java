import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MainBeta2 {

    private static String[] args;
    private static JFrame frame;
    private static JPanel panel;
    private static boolean imageClicked = false;
    public static int clickedImageIndex = -1;
    public static Deck deck = new Deck(36);
    private static boolean Turn = true;


    public static void restartGame() {
        Variables.getBot().clear();
        Variables.getList().clear();
        Variables.dList().clear();

        MainBeta2.deck = new Deck(36);
        Game start = new Game();
        start.startGame(deck, Variables.getList(), Variables.getBot(), Variables.getTrump());
        Variables.getList().sort(new Bot.CardComparator());
        Variables.getBot().sort(new Bot.CardComparator());
        updateImages();
        Sound.playBackgroundMusic(Variables.getGameSoundPath());
    }
    private static void onImageClick(int index) {//checks whether the image was clicked and make move accordingly to turn
        //instances of player and bot
        GameParticipant<Card> player = new Player();
        GameParticipant<Card> bot = new Bot();
        if (!imageClicked) {
            clickedImageIndex = index;
            // q(clickedImageIndex);
            imageClicked = true;
            if (isTurn()) {
                player.playCard(null);
                Functions.refresh(true);
            } else {
                bot.playCard(null);
                Functions.refresh(true);
            }
        }
    }



    public static void who(boolean playerWon) {
        Sound.stopBackgroundMusic();
        if (playerWon) {
            Sound.playSoundEffect(Variables.getWinnerSoundPath());
        } else {
            Sound.playSoundEffect(Variables.getLoserSoundPath());
        }
    }
    private static boolean isTurn() {
        return Turn;
    }

    public static void setTurn(boolean turn) {
        Turn = turn;
    }

    private static abstract class start<zxc> {// Abstract method to start the game, to be implemented by subclasses
        public abstract void startGame(Deck deck, List<zxc> list, List<zxc> bot, String trump);
    }

    private static class Game extends start<String> {
        // Implementation of the abstract method to start the game
        // Draw 6 cards from the deck for each player
        public void startGame(Deck deck, List<String> list, List<String> bot, String trump) {
            for (int i = 0; i < 6; i++) {
                Card card = deck.drawCard();
                list.add(String.valueOf(card));
            }

            for (int i = 0; i < 6; i++) {
                Card card = deck.drawCard();
                bot.add(String.valueOf(card));
            }

        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
            updateImages();
        });
        Game start = new Game();//Create an instance of the Game class
        start.startGame(deck, Variables.getList(), Variables.getBot(), Variables.getTrump());// Start the game with the provided deck, player list, bot list, and trump card
        System.out.println("Player's cards: " + Variables.getList());
        System.out.println("Bot's cards: " + Variables.getBot());
        //sorts cards
        Variables.getList().sort(new Bot.CardComparator());
        Variables.getBot().sort(new Bot.CardComparator());

        Sound.playBackgroundMusic(Variables.getGameSoundPath());//plays background music
    }

    private static void createAndShowGUI() {
        //interface
        frame = new JFrame("Durak");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1800, 1000));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        //adding panels
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel botPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel trump = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel trump2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

        //set color
        panel.setBackground(Variables.getPanelColor());
        botPanel.setBackground(Variables.getPanelColor());
        trump.setBackground(Variables.getPanelColor());
        trump2.setBackground(Variables.getPanelColor());
        info.setBackground(Variables.getPanelColor());
        frame.getContentPane().setBackground(Variables.getPanelColor());

        //adding components
        frame.add(panel, BorderLayout.SOUTH);
        frame.add(botPanel, BorderLayout.NORTH);
        frame.add(trump, BorderLayout.EAST);
        frame.add(trump2, BorderLayout.CENTER);
        frame.add(info, BorderLayout.WEST);

        //set margin
        trump.setBorder(BorderFactory.createEmptyBorder(200, 0, 0, 20));
        trump2.setBorder(BorderFactory.createEmptyBorder(200, 200, 200, 200));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        botPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        info.setBorder(BorderFactory.createEmptyBorder(250, 20, 0, 0));

        //creation of label for trump
        JLabel tlabel = getjLabel();
        trump.add(tlabel);

        frame.setVisible(true);
    }

    @NotNull
    private static JLabel getjLabel() {//set image of trump
        ImageIcon trumpik = switch (Variables.getTrump()) {
            case "HEARTS" -> new ImageIcon("C:\\Users\\falco\\Desktop\\debil2\\cards\\ACE HEARTS.png");
            case "DIAMONDS" -> new ImageIcon("C:\\Users\\falco\\Desktop\\debil2\\cards\\ACE DIAMONDS.png");
            case "SPADES" -> new ImageIcon("C:\\Users\\falco\\Desktop\\debil2\\cards\\ACE SPADES.png");
            case "CLUBS" -> new ImageIcon("C:\\Users\\falco\\Desktop\\debil2\\cards\\ACE CLUBS.png");
            default -> new ImageIcon();
        };
        return new JLabel(trumpik);
    }

    public static void updateImages() {//update images
        panel.removeAll();//removes all elements in panel

        //adding images connected to player list
        List<String> list = Variables.getList();
        ImageIcon[] icons = new ImageIcon[list.size()];
        for (int i = 0; i < list.size(); i++) {
            String a = list.get(i);
            icons[i] = new ImageIcon("C:\\Users\\falco\\Desktop\\debil2\\cards\\" + a + ".png");
        }

        //creation of labels and adding click events
        for (int i = 0; i < icons.length; i++) {
            JLabel label = new JLabel(icons[i]);
            int index = i;
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    onImageClick(index);
                    Sound.playSoundEffect(Variables.getClickSoundPath());
                }
            });
            panel.add(label);
        }



        JPanel info = (JPanel) frame.getContentPane().getComponent(4); // adds panel of deck size info
        info.removeAll();

        //creation and customization of labels
        JLabel deckCountLabel = new JLabel("Deck size:");
        JLabel deckCountLabel2 = new JLabel(String.valueOf(deck.size()));
        deckCountLabel.setForeground(Color.WHITE);

        deckCountLabel2.setForeground(Color.WHITE);
        deckCountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        deckCountLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        info.add(deckCountLabel);
        info.add(deckCountLabel2);


        JPanel trump2 = (JPanel) frame.getContentPane().getComponent(3); //panel in center
        trump2.removeAll();
        String m = Variables.getBot().get(0);//first element of bot list

        //image of the center card
        ImageIcon midIcon = new ImageIcon();
        if(Turn){
            midIcon= new ImageIcon("C:\\Users\\falco\\Desktop\\debil2\\cards\\BACK.png");
        }
        else{
            midIcon= new ImageIcon("C:\\Users\\falco\\Desktop\\debil2\\cards\\" + m + ".png");
        }

        //label and click event
        JLabel midLabel = new JLabel(midIcon);
        trump2.add(midLabel);
        midLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Sound.playSoundEffect(Variables.getClickSoundPath());

            }
        });




        JPanel botPanel = (JPanel) frame.getContentPane().getComponent(1);//top panel
        botPanel.removeAll();

        //the same as in player list
        List<String> botList = Variables.getBot();
        ImageIcon[] botIcons = new ImageIcon[botList.size()];
        for (int i = 0; i < botList.size(); i++) {
            botIcons[i] = new ImageIcon("C:\\Users\\falco\\Desktop\\debil2\\cards\\BACK.png");
        }
        for (ImageIcon botIcon : botIcons) {
            JLabel label = new JLabel(botIcon);
            botPanel.add(label);
        }
        frame.revalidate();// Recalculate the layout to include the new button
        frame.repaint(); // Refresh the display to show the new button
    }

    public static void waitForNextClick() {//makes imageclicked false to wait for opponent's move
        imageClicked = false;
        clickedImageIndex = -1;
    }


}
