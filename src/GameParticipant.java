import java.util.List;
import java.util.ArrayList;

public abstract class GameParticipant<T>{
    protected List<String> hand = new ArrayList<>();

    public List<String> getHand() {
        return hand;
    }

    public void drawCardFromDeck(Deck deck, int maxCards) {
        while (hand.size() < maxCards && !deck.isEmpty()) {
            hand.add(String.valueOf(deck.drawCard()));
        }
    }

    public void trimHand() {
        ((ArrayList<String>) hand).trimToSize();
    }
    public boolean isAuto() {
        return false;
    }
    public abstract void playCard(T context);
}

