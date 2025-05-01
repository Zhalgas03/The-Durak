import java.util.Comparator;
import java.util.List;

public class HandSorter {
    public void sortHands(List<String> playerHand, List<String> botHand, List<String> durakHand, Comparator<String> cardComparator) {
        playerHand.sort(cardComparator);
        botHand.sort(cardComparator);
        durakHand.sort(cardComparator);
    }
}