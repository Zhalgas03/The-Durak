public class GameInitializer {
    private final Dealer dealer;
    private final GameState gameState;
    private final GameSession game;
    private final CardService cardService;
    private final TurnManager turnManager;
    private final GameUI gameUI;
    private final GameController controller;
    private final GameLogicManager gameLogicManager;

    public GameInitializer(Dealer dealer, GameState gameState, GameSession game, CardService cardService, TurnManager turnManager, GameUI gameUI, GameController controller, GameLogicManager gameLogicManager) {
        this.dealer = dealer;
        this.gameState = gameState;
        this.game = game;
        this.cardService = cardService;
        this.turnManager = turnManager;
        this.gameUI = gameUI;
        this.controller = controller;
        this.gameLogicManager = gameLogicManager;
    }

    public void prepareNewGame(GameController gameController,int deckMode) {
        resetGameState(deckMode);
        dealer.dealInitialCards(gameState.getDeck(), gameState.getPlayerHand(), game.getBotInstance().getHand(), gameState.getDurakHand());
        gameState.assignRandomTrumpSuit();
        gameController.updateCardComparator();
        gameController.sortAllHands();
        GameSession.Turn firstTurn = turnManager.determineFirstTurn(gameController);
        game.setTurn(firstTurn);
    }

    private void resetGameState(int mode) {
        gameState.setDeck(new Deck(mode));
        gameState.getPlayerHand().clear();
        gameState.getBotHand().clear();
        gameState.getDurakHand().clear();
        game.setBotInstance(new SimpleBot(gameState, game, gameUI, controller, cardService, gameLogicManager));
    }
}