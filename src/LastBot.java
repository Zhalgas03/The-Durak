public class LastBot extends GameParticipant<Integer> {
    private final GameState gameState;
    private final GameSession game;
    private final GameUI gameUI;
    private final GameController gameController;
    private final CardService cardService;
    private final GameLogicManager gameLogicManager;
    public LastBot(GameState gameState, GameSession game, GameUI gameUI, GameController gameController,CardService cardService,GameLogicManager gameLogicManager) {
        this.gameState = gameState;
        this.game = game;
        this.gameUI = gameUI;
        this.gameController = gameController;
        this.cardService = cardService;
        this.gameLogicManager = gameLogicManager;
    }
    @Override
    public void playCard(Integer card) {
        int index = game.getClickedImageIndex();

        String botCard = gameState.getDurakHand().remove(0);

        String playerCard = gameState.getPlayerHand().remove(index);

        boolean match = cardService.canBeat(botCard, playerCard, gameState.getTrump());

        gameLogicManager.drawCardIfNeeded(gameState.getDurakHand(), gameState.getDeck());

        if (match) {
            gameLogicManager.drawCardIfNeeded(gameState.getPlayerHand(), gameState.getDeck());
            game.setTurn(GameSession.Turn.PLAYER);
        } else {
            gameState.getPlayerHand().add(playerCard);
            gameState.getPlayerHand().add(botCard);


            GameSession.Turn next = game.getBotInstance().getHand().isEmpty() ? GameSession.Turn.PLAYER : GameSession.Turn.BOT;
            game.setTurn(next);
        }


        gameLogicManager.sortAndCheck(gameState.getPlayerHand(), game.getBotInstance().getHand(), gameState.getDurakHand(),gameController, gameUI, gameState.getTrump());
    }
}
