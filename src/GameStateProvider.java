import java.util.List;

public interface GameStateProvider {
    List<String> getPlayerHand();
    List<String> getBotHand();
    List<String> getDurakHand();
    String getTrump();
    Deck getDeck();
}

