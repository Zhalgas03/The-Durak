public class Bot extends GameParticipant<Card> {// The Player class extends the GameParticipant class
    @Override// Override the abstract playCard method from GameParticipant
    public void playCard(Card card) {
    int index = MainBeta2.clickedImageIndex;//Get the index of the clicked image
    String p = Variables.getBot().remove(0);//bot throw the smallest card
    Variables.getBot().trimToSize();
    String a = Variables.getList().remove(index);
    Variables.getList().trimToSize();

    //splits bots card
    String[] words = p.split("\\s+");
    boolean match = false;
    String w = words[1];
    String n = words[0];
    int n1 = rankValue(n);//rank into int

    //player have trump and bot dots not
    if (a.contains(Variables.getTrump()) && !p.contains(Variables.getTrump())) {
        match = true;
    }

    //check if player has higher rank
    if (a.contains(w)) {
        String[] Bwords = a.split("\\s+");
        String v = Bwords[0];
        int nv = rankValue(v);
        if (nv > n1) {
            match = true;
        }
    }

    //gives card to bot
    if (MainBeta2.deck.size() > 0 && Variables.getBot().size() < 6) {
        give(MainBeta2.deck, Variables.getBot());
    }

    if (match) {//if player can beat bots card
        if (Variables.getList().size() < 6 && MainBeta2.deck.size() > 0) {//gives card to player
            Card card1 = MainBeta2.deck.drawCard();
            Variables.getList().add(String.valueOf(card1));
        }
        MainBeta2.setTurn(true);//players turn
    } else {//othewise takes card and bot moves again
        Variables.getList().add(a);
        Variables.getList().add(p);
        MainBeta2.setTurn(false);
    }

    //sorts cards
    Variables.getList().sort(new CardComparator());
    Variables.getBot().sort(new CardComparator());

    endGame(Variables.getList(), Variables.getBot(), null, true);



}

}

