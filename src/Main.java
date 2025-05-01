public class Main {
    public static void main(String[] args) {
        int deckMode = 36;
        if (args.length > 0) {
            try {
                deckMode = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                deckMode = 36;
            }
        }


        SoundService soundService = new SoundService();
        CardService cardService = new SimpleCardService();
        TurnManager turnManager = new TurnManager(cardService);
        GameLogicManager gameLogicManager = new GameLogicManager(cardService, soundService);


        GameSession gameSession = new GameSession(
                soundService,
                cardService,
                gameLogicManager,
                turnManager,
                deckMode
        );

        gameSession.startGame();
    }
}