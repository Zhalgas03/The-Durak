import java.util.List;
import java.util.Comparator;
public class GameController implements GameStateProvider, TurnManagerCallback {
    private Comparator<String> cardComparator;
    private final CardService cardService;
    private GameUI gameUI;
    private final GameState gameState;
    private final GameSession game;
    private final GameInitializer gameInitializer;
    private final HandSorter handSorter;
    private final SoundService soundService;
    private int currentDeckMode = 36;

    public GameController(
            GameUI gameUI,
            GameState gameState,
            GameSession game,
            CardService cardService,
            GameLogicManager gameLogicManager,
            TurnManager turnManager,
            SoundService soundService
    ) {
        this.gameUI = gameUI;
        this.gameState = gameState;
        this.game = game;
        this.cardService = cardService;
        this.soundService = soundService;
        this.gameInitializer = new GameInitializer(new Dealer(), gameState, game, cardService, turnManager, gameUI, this, gameLogicManager);
        this.handSorter = new HandSorter();
    }
    public void setGameUI(GameUI gameUI) {
        this.gameUI = gameUI;
    }
    @Override
    public List<String> getPlayerHand() { return gameState.getPlayerHand(); }
    @Override
    public List<String> getBotHand() { return gameState.getBotHand(); }
    @Override
    public List<String> getDurakHand() { return gameState.getDurakHand(); }
    @Override
    public String getTrump() { return gameState.getTrump(); }
    @Override
    public Deck getDeck() { return gameState.getDeck(); }
    @Override
    public void setTurn(GameSession.Turn turn) { game.setTurn(turn); }

    public void setDeckMode(int mode) {
        if (mode == 36 || mode == 52) {
            this.currentDeckMode = mode;
        }
    }


    public void initGame(int deckMode) {
        setDeckMode(deckMode);
        gameInitializer.prepareNewGame(this, deckMode);
        updateCardComparator();
        soundService.playGameTheme();
    }


    public void restartGame() {
        gameInitializer.prepareNewGame(this, currentDeckMode);
        gameUI.updateImages();
        soundService.playGameTheme();
    }

    public void  updateCardComparator() {
        cardComparator = cardService.createCardComparator(gameState.getTrump());
    }

    public void sortAllHands() {
        handSorter.sortHands(gameState.getPlayerHand(), gameState.getBotHand(), gameState.getDurakHand(), cardComparator);
    }


}

