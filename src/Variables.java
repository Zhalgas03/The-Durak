import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Variables{
    private static final ArrayList<String> bot=new ArrayList<>(); //the bot's cards
    private static final ArrayList<String> list=new ArrayList<>();//the player's cards
    private static final ArrayList<String> durak=new ArrayList<>();//durak cards

    //choosing trump suit randomly
    private static final Random random = new Random();
    private static final int r = random.nextInt(4);
    private static final String[] suit = {"HEARTS", "DIAMONDS", "CLUBS", "SPADES"};
    private static String trump=suit[r];

    //color
    private static final Color panelColor = new Color(0x496f93);

    //paths
    private static final String buttonSoundPath = "cards/button.wav";
    private static final String menuSoundPath = "cards/menu.wav";
    private static final String gameSoundPath = "cards/game.wav";
    private static final String clickSoundPath = "cards/click.wav";
    private static final String denySoundPath = "cards/deny.wav";
    private static final String loserSoundPath = "cards/loser.wav";
    private static final String winnerSoundPath = "cards/winner.wav";
    private static final String hoverSoundPath = "cards/sound5.wav";
    private static final String moveSoundPath = "cards/move.wav";
    private static final String selectSoundPath = "cards/handle.wav";



    //getters for variables
    public static ArrayList<String> getBot() {
        return bot;
    }
    public static ArrayList<String> getList() {
        return list;
    }
    public static ArrayList<String> dList() {
        return durak;
    }
    public static String getTrump() {
        return trump;
    }

    public static String getMoveSoundPath() {
        return moveSoundPath;
    }
    public static String getSelectSoundPath() {
        return selectSoundPath;
    }
    public static String getLoserSoundPath() {
        return loserSoundPath;
    }
    public static String getWinnerSoundPath() {
        return winnerSoundPath;
    }

    public static Color getPanelColor() {
        return panelColor;
    }

    public static String getButtonSoundPath() {
        return hoverSoundPath;
    }

    public static String getMenuSoundPath() {
        return menuSoundPath;
    }

    public static String getGameSoundPath() {
        return gameSoundPath;
    }


    public static String getHoverSoundPath() {
        return hoverSoundPath;
    }
    public static String getClickSoundPath() {
        return clickSoundPath;
    }
    public static String getDenySoundPath() {
        return denySoundPath;
    }

    public static String getTrumpImagePath() {
        return "cards/ACE " + Variables.getTrump() + ".png";
    }

    public static final String CARD_PATH = "cards/";
}

