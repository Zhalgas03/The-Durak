import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> cards;// Declare a final list to hold the cards in the deck

    public Deck(int mode) {
        cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.getRanks(mode)) {
                cards.add(new Card(suit, rank));
            }
        }
        shuffle();
    }




    public void shuffle() {
        Collections.shuffle(cards);//Use the Collections.shuffle method to shuffle the list of cards
    }

    public Card drawCard() {// Method to draw a card from the deck
        return cards.isEmpty() ? null : cards.remove(0);
    }

    public int size() {
        return cards.size();// Return the number of cards remaining in the deck
    }
    public List<Card> getCards() {
        return cards;
    }
    public boolean isEmpty() {
        return cards.isEmpty();
    }
}