import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class GameLogicManager {

    private final CardService cardService;
    private final SoundService soundService;

    public GameLogicManager(CardService cardService, SoundService soundService) {
        this.cardService = cardService;
        this.soundService = soundService;
    }

    public void sortHands(List<List<String>> hands, String trumpSuit) {
        Comparator<String> comparator = cardService.createCardComparator(trumpSuit);
        for (List<String> hand : hands) {
            hand.sort(comparator);
        }
    }

    public void sortAndCheck(List<String> playerList,
                             List<String> botList,
                             List<String> durakList,
                             GameController controller,
                             GameUI gameUI,
                             String trumpSuit) {
        sortHands(Arrays.asList(playerList, botList, durakList), trumpSuit);
        checkWinner(playerList, botList, durakList, controller, gameUI);
    }

    private void checkWinner(List<String> playerList,
                             List<String> botList,
                             List<String> durakList,
                             GameController controller,
                             GameUI gameUI) {
        boolean playerEmpty = playerList.isEmpty();
        boolean botEmpty = botList.isEmpty();
        boolean durakEmpty = durakList.isEmpty();

        int emptyCount = 0;
        int winnerCode = 0;

        if (playerEmpty) {
            emptyCount++;
            winnerCode = 1;
        }
        if (botEmpty) {
            emptyCount++;
            winnerCode = 2;
        }
        if (durakEmpty) {
            emptyCount++;
            winnerCode = 3;
        }

        if (emptyCount == 1) {
            handleWin(winnerCode, controller);
        }
    }



    private void handleWin(int winnerCode, GameController controller) {
        ResultHandler resultHandler = new ResultHandler(soundService);
        resultHandler.winner(winnerCode);
        new GameOverDialog(controller);
    }

    public void drawCardIfNeeded(List<String> hand, Deck deck) {
        if (hand.size() < 6 && !deck.isEmpty()) {
            hand.add(String.valueOf(deck.drawCard()));
        }
    }

}