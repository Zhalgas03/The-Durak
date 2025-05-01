import java.util.Comparator;

public interface CardService {
    int getCardRank(String card);
    String getCardSuit(String card);
    boolean canBeat(String attackerCard, String defenderCard, String trumpSuit);
    Comparator<String> createCardComparator(String trumpSuit);
}