import java.util.Comparator;

public class SimpleCardService implements CardService {
    @Override
    public int getCardRank(String card) {
        String[] parts = card.split("\\s+");
        return switch(parts[0]) {
            case "TWO" -> 2;
            case "THREE" -> 3;
            case "FOUR" -> 4;
            case "FIVE" -> 5;
            case "SIX" -> 6;
            case "SEVEN" -> 7;
            case "EIGHT" -> 8;
            case "NINE" -> 9;
            case "TEN" -> 10;
            case "JACK" -> 11;
            case "QUEEN" -> 12;
            case "KING" -> 13;
            case "ACE" -> 14;
            default -> 0;
        };
    }

    @Override
    public String getCardSuit(String card) {
        return card.split("\\s+")[1];
    }

    @Override
    public boolean canBeat(String attackerCard, String defenderCard, String trumpSuit) {
        String attackerSuit = getCardSuit(attackerCard);
        String defenderSuit = getCardSuit(defenderCard);
        int attackerRank = getCardRank(attackerCard);
        int defenderRank = getCardRank(defenderCard);

        if (defenderSuit.equals(attackerSuit)) {
            return defenderRank > attackerRank;
        }
        return !attackerSuit.equals(trumpSuit) && defenderSuit.equals(trumpSuit);
    }

    @Override
    public Comparator<String> createCardComparator(String trumpSuit) {
        return new CardComparator(trumpSuit, this);
    }
}