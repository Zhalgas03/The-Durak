/*


import java.awt.*;

public static void onImageClick(int index) {
    GameParticipant<Integer> player = new Player52();
    if (!imageClicked && getTurn() == Game52.Turn.PLAYER) {
        clickedImageIndex = index;
        imageClicked = true;
        player.playCard(null);
        Functions.refresh(false);
    }
}


public static void performBotTurn() {
    GameParticipant<Integer> bot = getBotInstance();
    GameParticipant<Integer> durak = new Opponent();

    switch (getTurn()) {
        case BOT -> {
            bot.playCard(null);
            Functions.refresh(false);
        }
        case OPPONENT -> {
            durak.playCard(null);
            Functions.refresh(false);
        }
    }
}



/// это
        if (Game52.getTurn() != Game52.Turn.PLAYER && !Game52.autoClickPerformed) {
        SwingUtilities.invokeLater(() -> {
        if (panel.getComponentCount() > 0) {
Component componentToClick = panel.getComponent(0);
                    Game52.AutoClicker.performAutoClick(componentToClick);
Game52.autoClickPerformed = true;
        }
        });
        /// на это
        if (Game52.getTurn() != Game52.Turn.PLAYER) {
        SwingUtilities.invokeLater(() -> Game52.performBotTurn());
        }


 */

