import java.util.List;

public class Dealer {
    private static final int INITIAL_CARDS_COUNT = 6;

    @SafeVarargs
    public final void dealInitialCards(Deck deck, List<String>... hands) {
        for (List<String> hand : hands) {
            dealCards(deck, hand);
        }
    }

    private void dealCards(Deck deck, List<String> hand) {
        for (int i = 0; i < INITIAL_CARDS_COUNT && !deck.isEmpty(); i++) {
            hand.add(String.valueOf(deck.drawCard()));
        }
    }
}