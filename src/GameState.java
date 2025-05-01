import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameState {
    private List<String> playerHand = new ArrayList<>();
    private List<String> botHand = new ArrayList<>();
    private List<String> durakHand = new ArrayList<>();
    private Deck deck;
    private GameSession.Turn turn = GameSession.Turn.PLAYER;
    private String trump;
    private final List<String> suits = List.of("HEARTS", "DIAMONDS", "CLUBS", "SPADES");

    public List<String> getPlayerHand() {
        return playerHand;
    }

    public void setPlayerHand(List<String> playerHand) {
        this.playerHand = playerHand;
    }

    public List<String> getBotHand() {
        return botHand;
    }


    public void setBotHand(List<String> botHand) {
        this.botHand = botHand;
    }

    public List<String> getDurakHand() {
        return durakHand;
    }

    public void setDurakHand(List<String> durakHand) {
        this.durakHand = durakHand;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public GameSession.Turn getTurn() {
        return turn;
    }

    public void setTurn(GameSession.Turn turn) {
        this.turn = turn;
    }

    public void assignRandomTrumpSuit() {
        Random random = new Random();
        trump = suits.get(random.nextInt(suits.size()));
    }

    public String getTrump() {
        return trump;
    }
    public String getTrumpImagePath() {
        return "style/cards/ACE " + getTrump() + ".png";
    }


}