import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.util.List;

public class Game52 {

    private static SimpleBot botInstance;

    public static void setBotInstance(SimpleBot bot) {
        botInstance = bot;
    }


    public static SimpleBot getBotInstance() {
        if (botInstance == null) {
            botInstance = new SimpleBot(); // Инициализация объекта
        }
        return botInstance;
    }


    public static Deck getDeck() {
        return deck;
    }


    // ========== GLOBAL STATE ==========
    private static boolean imageClicked = false;
    public static int clickedImageIndex = -1;
    public static Deck deck = new Deck(52);
    private static Turn turn = Turn.PLAYER;


    // ========== TURN ENUM ==========
    public enum Turn {
        PLAYER,
        BOT,
        OPPONENT;
    }
    public static final int PLAYER_TURN = 1;
    public static final int BOT_TURN = 2;
    public static final int OPPONENT_TURN = 3;

    // ========== GAME INIT / FLOW ==========
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameUI.createAndShowGUI();
            GameUI.updateImages();
        });
        GameController.initGame();
    }

    public static Turn getTurn() {
        return turn;
    }

    public static void setTurn(Turn t) {
        turn = t;
        if (turn == Turn.BOT && botInstance != null) {
            SwingUtilities.invokeLater(() -> {
                botInstance.playCard(null);
                Functions.refresh(false);
            });
        }
    }

    public static void waitForNextClick() {//makes imageclicked false to wait for opponent's move
        imageClicked = false;
        clickedImageIndex = -1;
    }

    // ========== GAME LOGIC ==========
    public static void onImageClick(int index) {//checks whether the image was clicked and make move accordingly to turn
        //instances of player and bot
        GameParticipant<Integer> player = new HumanPlayer();
        GameParticipant<Integer> bot = Game52.getBotInstance();
        GameParticipant<Integer> durak = new LastBot();
        if (!imageClicked) {
            clickedImageIndex = index;
            imageClicked = true;
            switch (getTurn()) {
                case PLAYER -> {
                    player.playCard(null);
                    Functions.refresh(false);
                }
                case BOT -> {
                    bot.playCard(null);
                    Functions.refresh(false);
                }
                case OPPONENT -> {
                    durak.playCard(null);
                    Functions.refresh(false);
                }
            }

        }
    }

    private static void dealCards(Deck deck, List<String> target, int count) {
        for (int i = 0; i < count; i++) {
            Card card = deck.drawCard();
            target.add(String.valueOf(card));
        }
    }

    // ========== TECHNICAL: AUTOBOT ==========



}
