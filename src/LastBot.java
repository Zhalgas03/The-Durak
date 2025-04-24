public class LastBot extends GameParticipant<Integer> {
    @Override
    public void playCard(Integer card) {
        int index = Game52.clickedImageIndex;

        String botCard = Variables.dList().remove(0);
        Variables.dList().trimToSize();

        String playerCard = Variables.getList().remove(index);
        Variables.getList().trimToSize();

        boolean match = Functions.canBeat(botCard, playerCard, Variables.getTrump());

        drawCardIfNeeded(Variables.dList(), Game52.deck);

        if (match) {
            drawCardIfNeeded(Variables.getList(), Game52.deck);
            Game52.setTurn(Game52.Turn.PLAYER);
        } else {
            Variables.getList().add(playerCard);
            Variables.getList().add(botCard);


            Game52.Turn next = Game52.getBotInstance().getHand().isEmpty() ? Game52.Turn.PLAYER : Game52.Turn.BOT;
            Game52.setTurn(next);
        }

        sortAndCheck(Variables.getList(), Game52.getBotInstance().getHand(), Variables.dList());
    }
}
