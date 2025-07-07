import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameSession {
    private final GameState gameState;
    private final GameUI gameUI;
    private final GameController controller;
    private SimpleBot botInstance;
    private boolean imageClicked = false;
    private int clickedImageIndex = -1;
    private final CardService cardService;
    private final GameLogicManager gameLogicManager;
    private final SoundService soundService;
    private final TurnManager turnManager;
    private final int deckMode;

    private final List<GameParticipant<?>> participants = new ArrayList<>();
    private final Map<Turn, GameParticipant<?>> turnToParticipant = new HashMap<>();

    public GameSession(
            SoundService soundService,
            CardService cardService,
            GameLogicManager gameLogicManager,
            TurnManager turnManager,
            int deckMode
    ) {
        this.soundService = soundService;
        this.cardService = cardService;
        this.gameLogicManager = gameLogicManager;
        this.turnManager = turnManager;

        this.gameState = new GameState();
        ImageCache imageCache = new ImageCache();
        this.controller = new GameController(
                null,
                gameState,
                this,
                cardService,
                gameLogicManager,
                turnManager,
                soundService
        );
        this.deckMode = deckMode;

        this.gameUI = new GameUI(gameState, this, soundService, imageCache, controller);
        this.controller.setGameUI(gameUI);
    }

    public void startGame() {
        gameState.setDeck(new Deck(deckMode));
        participants.clear();


        HumanPlayer player = new HumanPlayer(gameState, this, gameUI, controller, cardService, gameLogicManager);
        SimpleBot bot = new SimpleBot(gameState, this, gameUI, controller, cardService, gameLogicManager);
        LastBot opponent = new LastBot(gameState, this, gameUI, controller, cardService, gameLogicManager);

        participants.add(player);
        participants.add(bot);
        participants.add(opponent);


        turnToParticipant.put(Turn.PLAYER, player);
        turnToParticipant.put(Turn.BOT, bot);
        turnToParticipant.put(Turn.OPPONENT, opponent);

        this.botInstance = bot;

        SwingUtilities.invokeLater(() -> {
            gameUI.createAndShowGUI();
            gameUI.updateImages();
            controller.initGame(deckMode);
        });
    }

    public void handleCardClick(int index) {
        if (!imageClicked) {
            clickedImageIndex = index;
            imageClicked = true;


            turnToParticipant.get(gameState.getTurn()).playCard(null);

            gameUI.updateImages();
            resetForNextMove();
        }
    }

    public void resetForNextMove() {
        imageClicked = false;
        clickedImageIndex = -1;
    }

    public void setBotInstance(SimpleBot bot) {
        botInstance = bot;
    }

    public SimpleBot getBotInstance() {
        return botInstance;
    }

    public int getClickedImageIndex() {
        return clickedImageIndex;
    }

    public enum Turn {
        PLAYER, BOT, OPPONENT
    }

    public void setTurn(GameSession.Turn t) {
        gameState.setTurn(t);

        // 🔹 Ход бота автоматом
        if (t == Turn.BOT && botInstance != null) {
            SwingUtilities.invokeLater(() -> {
                botInstance.playCard(null);
                gameUI.updateImages();
                resetForNextMove();
            });
        }
    }
}
