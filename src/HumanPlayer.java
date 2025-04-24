public class HumanPlayer extends GameParticipant<Integer> {
    @Override
    public void playCard(Integer card) {
        int index = Game52.clickedImageIndex;

        String playerCard = Variables.getList().remove(index);
        Variables.getList().trimToSize();


        boolean found = false;

        for (int i = 0; i < Game52.getBotInstance().getHand().size(); i++) {
            String botCard = Game52.getBotInstance().getHand().get(i);
            if (Functions.canBeat(playerCard, botCard, Variables.getTrump())) {
                Game52.getBotInstance().getHand().remove(i);


                drawCardIfNeeded(Game52.getBotInstance().getHand(), Game52.deck);
                Game52.setTurn(Game52.Turn.BOT);
                found = true;
                break;
            }
        }

        if (!found) {
            Game52.getBotInstance().getHand().add(playerCard);
            Game52.setTurn(Game52.Turn.OPPONENT);

        }

        drawCardIfNeeded(Variables.getList(), Game52.deck);
        sortAndCheck(Variables.getList(), Game52.getBotInstance().getHand(), Variables.dList());
    }
}
