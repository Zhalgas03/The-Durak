import java.util.List;

public class GameController {
    private static final int INITIAL_CARDS_COUNT = 6;
    private static final Functions.CardComparator CARD_COMPARATOR = new Functions.CardComparator();
    public static void initGame() {
        resetGameState();
        initializeNewGame();
        Sound.playBackgroundMusic(Variables.getGameSoundPath());
    }


    public static void restartGame() {
        resetGameState();
        initializeNewGame();
        GameUI.updateImages();
        Sound.playBackgroundMusic(Variables.getGameSoundPath());
    }


    private static abstract class StartGame<T> {
        public abstract void startGame(Deck deck, List<T> list, List<T> bot, List<T> durak, String trump);
    }

    private static class Game extends StartGame<String> {
        @Override
        public void startGame(Deck deck,
                              List<String> playerHand,  // Переименовано из list
                              List<String> botHand,      // Переименовано из bot
                              List<String> durak,
                              String trump) {
            dealCards(deck, playerHand);
            dealCards(deck, botHand);
            dealCards(deck, durak);

        }

        private void dealCards(Deck deck, List<String> target) {
            for (int i = 0; i < INITIAL_CARDS_COUNT && !deck.isEmpty(); i++) {
                target.add(String.valueOf(deck.drawCard()));
            }
        }
    }

    private static void resetGameState() {
        // Очистка данных
        Variables.getList().clear();
        Variables.dList().clear();

        // Сброс состояния игры
        Game52.setTurn(Game52.Turn.PLAYER);
        Game52.deck = new Deck(52);
        Game52.setBotInstance(new SimpleBot());
    }

    private static void initializeNewGame() {
        // Инициализация бота
        SimpleBot bot = Game52.getBotInstance();

        // Запуск игры
        Game game = new Game();
        game.startGame(
                Game52.deck,
                Variables.getList(),
                bot.getHand(),
                Variables.dList(),
                Variables.getTrump()
        );

        // Сортировка карт
        sortAllHands();
    }

    private static void sortAllHands() {
        Variables.getList().sort(CARD_COMPARATOR);
        Game52.getBotInstance().getHand().sort(CARD_COMPARATOR);
        Variables.dList().sort(CARD_COMPARATOR);
    }
}
