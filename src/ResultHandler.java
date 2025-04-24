public class ResultHandler {

    public interface PlayerResult {
        void handleResult();
    }

    public static class Winner implements PlayerResult {
        @Override
        public void handleResult() {
            Sound.stopBackgroundMusic();
            Sound.playSoundEffect(Variables.getWinnerSoundPath());
        }
    }

    public static class Loser implements PlayerResult {
        @Override
        public void handleResult() {
            Sound.stopBackgroundMusic();
            Sound.playSoundEffect(Variables.getLoserSoundPath());
        }
    }

    public static void who(int playerWon) {
        PlayerResult result = (playerWon == 1) ? new Winner() : new Loser();
        result.handleResult();
    }
}
