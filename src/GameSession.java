import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.util.List;

public class Game52 {
    private  GameState gameState = new GameState(); // теперь
    private  SimpleBot botInstance;

    public  void setBotInstance(SimpleBot bot) {
        botInstance = bot;
    }


    public  SimpleBot getBotInstance() {
        return botInstance;
    }





    // ========== GLOBAL STATE ==========
    private  boolean imageClicked = false;
    public  int clickedImageIndex = -1;



    // ========== TURN ENUM ==========
    public enum Turn {
        PLAYER,
        BOT,
        OPPONENT;
    }
    public  final int PLAYER_TURN = 1;
    public  final int BOT_TURN = 2;
    public  final int OPPONENT_TURN = 3;

    // ========== GAME INIT / FLOW ==========
    private  GameUI gameUI;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Game52 game = new Game52();
            game.gameUI = new GameUI(game.gameState, game); // передаем game
            game.gameUI.createAndShowGUI();
            game.gameUI.updateImages();
            GameController controller = new GameController(game.gameUI, game.gameState,game);
            controller.initGame();
        });
    }


    public void setTurn(Game52.Turn t) {
        gameState.setTurn(t);
        if (t == Turn.BOT && botInstance != null) {
            SwingUtilities.invokeLater(() -> {
                botInstance.playCard(null);
                UIUtils.refresh(false, gameUI, this);
            });
        }
    }
    public  void waitForNextClick() {//makes imageclicked false to wait for opponent's move
        imageClicked = false;
        clickedImageIndex = -1;
    }

    public void onImageClick(int index) {
        GameParticipant<Integer> player = new HumanPlayer(gameState, this);
        GameParticipant<Integer> bot = this.getBotInstance();
        GameParticipant<Integer> durak = new LastBot(gameState,this);
        if (!imageClicked) {
            clickedImageIndex = index;
            imageClicked = true;
            switch (gameState.getTurn()) {
                case PLAYER -> {
                    player.playCard(null);
                    UIUtils.refresh(false, gameUI, this);
                }
                case BOT -> {
                    bot.playCard(null);
                    UIUtils.refresh(false, gameUI, this);
                }
                case OPPONENT -> {
                    durak.playCard(null);
                    UIUtils.refresh(false, gameUI, this);
                }
            }
        }
    }

}
