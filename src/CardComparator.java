import java.util.Comparator;

public class CardComparator implements Comparator<String> {
    private final String trumpSuit;
    private final CardService cardService;

    public CardComparator(String trumpSuit, CardService cardService) {
        this.trumpSuit = trumpSuit;
        this.cardService = cardService;
    }

    @Override
    public int compare(String card1, String card2) {
        boolean isTrump1 = cardService.getCardSuit(card1).equals(trumpSuit);
        boolean isTrump2 = cardService.getCardSuit(card2).equals(trumpSuit);

        if (isTrump1 && isTrump2) {
            return Integer.compare(
                    cardService.getCardRank(card1),
                    cardService.getCardRank(card2)
            );
        }
        if (isTrump1) return 1;
        if (isTrump2) return -1;
        return Integer.compare(
                cardService.getCardRank(card1),
                cardService.getCardRank(card2)
        );
    }
}