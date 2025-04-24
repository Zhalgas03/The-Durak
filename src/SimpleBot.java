public class SimpleBot extends GameParticipant<Integer> {
    public SimpleBot() {}

    @Override
    public void playCard(Integer card) {
        if (hand.isEmpty()) {
            Game52.setTurn(Game52.Turn.OPPONENT);
            return;
        }

        String botCard = hand.remove(0);
        trimHand();



        boolean match = false;
        String matchedCard = null;

        for (String playerCard : Variables.dList()) {
            if (Functions.canBeat(botCard, playerCard, Variables.getTrump())) {
                matchedCard = playerCard;
                match = true;
                break;
            }
        }

        drawCardFromDeck(Game52.getDeck(), 6);

        if (match) {
            Variables.dList().remove(matchedCard);
            Variables.dList().trimToSize();



            drawCardIfNeeded(Variables.dList(), Game52.deck);
            Game52.setTurn(Game52.Turn.OPPONENT);
        } else {
            Variables.dList().add(botCard);
            Game52.setTurn(Game52.Turn.PLAYER);


        }

        sortAndCheck(Variables.getList(), this.getHand(), Variables.dList());
    }
}
