import java.util.Comparator;

public class SimpleCardService implements CardService {
    @Override
    public int getCardRank(String card) {
        String[] parts = card.split("\\s+");
        int value = Rank.fromString(parts[0]).getValue();
        return value;
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