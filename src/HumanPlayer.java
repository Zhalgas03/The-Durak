public class HumanPlayer extends GameParticipant<Integer> {
    private final GameState gameState;
    private final GameSession game;
    private final GameUI gameUI;
    private final GameController gameController;
    private final CardService cardService;
    private final GameLogicManager gameLogicManager;
    public HumanPlayer(GameState gameState,
                       GameSession game,
                       GameUI gameUI,
                       GameController gameController,
                       CardService cardService,GameLogicManager gameLogicManager) {
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

        String playerCard = gameState.getPlayerHand().remove(index);


        boolean found = false;

        for (int i = 0; i < game.getBotInstance().getHand().size(); i++) {
            String botCard = game.getBotInstance().getHand().get(i);
            if (cardService.canBeat(playerCard, botCard, gameState.getTrump())) {
                game.getBotInstance().getHand().remove(i);


                gameLogicManager.drawCardIfNeeded(game.getBotInstance().getHand(), gameState.getDeck());
                game.setTurn(GameSession.Turn.BOT);
                found = true;
                break;
            }
        }

        if (!found) {
            game.getBotInstance().getHand().add(playerCard);
            game.setTurn(GameSession.Turn.OPPONENT);

        }

        gameLogicManager.drawCardIfNeeded(gameState.getPlayerHand(), gameState.getDeck());

        gameLogicManager.sortAndCheck(gameState.getPlayerHand(), game.getBotInstance().getHand(), gameState.getDurakHand(),gameController, gameUI, gameState.getTrump());
    }
}
