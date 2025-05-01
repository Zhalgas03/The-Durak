public class ResultHandler {
    private final SoundService soundService;

    public ResultHandler(SoundService soundService) {
        this.soundService = soundService;
    }

    public interface PlayerResult {
        void handleResult();
    }

    public class Winner implements PlayerResult {
        @Override
        public void handleResult() {
            soundService.stopBackgroundMusic();
            soundService.playWinSound();
        }
    }

    public class Loser implements PlayerResult {
        @Override
        public void handleResult() {
            soundService.stopBackgroundMusic();
            soundService.playLoseSound();
        }
    }

    public void winner(int playerWon) {
        PlayerResult result = (playerWon == 1) ? new Winner() : new Loser();
        result.handleResult();
    }
}
