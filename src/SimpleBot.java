public class SimpleBot extends GameParticipant<Integer> {
    private final GameState gameState;
    private final GameSession game;
    private final GameUI gameUI;
    private final GameController gameController;
    private final CardService cardService;
    private final GameLogicManager gameLogicManager;
    public SimpleBot(GameState gameState, GameSession game, GameUI gameUI, GameController gameController,CardService cardService,GameLogicManager gameLogicManager) {
        this.gameState = gameState;
        this.game = game;
        this.gameUI = gameUI;
        this.gameController = gameController;
        this.cardService = cardService;
        this.gameLogicManager = gameLogicManager;
    }
    @Override
    public void playCard(Integer card) {
        if (hand.isEmpty()) {
            game.setTurn(GameSession.Turn.OPPONENT);
            return;
        }

        String botCard = hand.remove(0);
        trimHand();



        boolean match = false;
        String matchedCard = null;

        for (String playerCard : gameState.getDurakHand()) {
            if (cardService.canBeat(botCard, playerCard,gameState.getTrump())) {
                matchedCard = playerCard;
                match = true;
                break;
            }
        }

        drawCardFromDeck(gameState.getDeck(), 6);

        if (match) {
            gameState.getDurakHand().remove(matchedCard);

            gameLogicManager.drawCardIfNeeded(gameState.getDurakHand(), gameState.getDeck());
            game.setTurn(GameSession.Turn.OPPONENT);
        } else {
            gameState.getDurakHand().add(botCard);
            game.setTurn(GameSession.Turn.PLAYER);


        }
        gameLogicManager.drawCardIfNeeded(this.getHand(), gameState.getDeck());
        gameLogicManager.sortAndCheck(gameState.getPlayerHand(), game.getBotInstance().getHand(), gameState.getDurakHand(),gameController, gameUI, gameState.getTrump());
    }
}
