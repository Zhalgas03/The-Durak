import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TurnManager {
    private final CardService cardService;

    public TurnManager(CardService cardService) {
        this.cardService = cardService;
    }
    public GameSession.Turn determineFirstTurn(GameStateProvider gameState) {
        String trump = gameState.getTrump();
        List<Participant> participants = new ArrayList<>();

        addParticipantIfHasTrump(participants, GameSession.Turn.PLAYER, gameState.getPlayerHand(), trump);
        addParticipantIfHasTrump(participants, GameSession.Turn.BOT, gameState.getBotHand(), trump);
        addParticipantIfHasTrump(participants, GameSession.Turn.OPPONENT, gameState.getDurakHand(), trump);

        return participants.stream()
                .min(Comparator.comparingInt(Participant::minTrumpRank))
                .map(Participant::turn)
                .orElse(GameSession.Turn.PLAYER);
    }

    private void addParticipantIfHasTrump(List<Participant> participants,
                                          GameSession.Turn turn,
                                          List<String> hand,
                                          String trump) {
        int minRank = hand.stream()
                .filter(card -> cardService.getCardSuit(card).equals(trump))
                .mapToInt(cardService::getCardRank)
                .min()
                .orElse(Integer.MAX_VALUE);
        if (minRank != Integer.MAX_VALUE) {
            participants.add(new Participant(turn, minRank));
        }
    }

    private record Participant(GameSession.Turn turn, int minTrumpRank) {}
}