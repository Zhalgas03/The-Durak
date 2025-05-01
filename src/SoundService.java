import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    public enum SoundTrack {
        MENU_THEME("menu.wav"),
        GAME_THEME("game.wav"),
        CARD_CLICK("click.wav"),
        CARD_DENY("deny.wav"),
        CARD_MOVE("move.wav"),
        CARD_SELECT("handle.wav"),
        WIN_FANFARE("winner.wav"),
        LOSE_SOUND("loser.wav");

        private final String path;

        SoundTrack(String fileName) {
            this.path = "style/audio/" + fileName;
        }
    }

    private  Clip bgClip;

    private  void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    // ===== Фоновая музыка =====
    public  void playMenuTheme() {
        playBackgroundMusic(SoundTrack.MENU_THEME.path);
    }

    public  void playGameTheme() {
        playBackgroundMusic(SoundTrack.GAME_THEME.path);
    }

    public  void playBackgroundMusic(String path) {
        stopBackgroundMusic();
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(path));
            bgClip = AudioSystem.getClip();
            bgClip.open(audioStream);
            bgClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException e) {
            showError("Unsupported audio file: " + path);
        } catch (IOException e) {
            showError("File not found: " + path);
        } catch (Exception e) {
            showError("Music error: " + e.getMessage());
        }
    }

    public  void stopBackgroundMusic() {
        if (bgClip != null && bgClip.isRunning()) {
            bgClip.stop();
            bgClip.close();
        }
    }

    // ===== Звуковые эффекты =====
    public  void playCardClick() {
        playSoundEffect(SoundTrack.CARD_CLICK.path);
    }

    public  void playCardDenied() {
        playSoundEffect(SoundTrack.CARD_DENY.path);
    }

    public  void playMoveSound() {
        playSoundEffect(SoundTrack.CARD_MOVE.path);
    }

    public  void playSelectSound() {
        playSoundEffect(SoundTrack.CARD_SELECT.path);
    }

    public  void playWinSound() {
        playSoundEffect(SoundTrack.WIN_FANFARE.path);
    }

    public  void playLoseSound() {
        playSoundEffect(SoundTrack.LOSE_SOUND.path);
    }

    public  void playSoundEffect(String path) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(path)));
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            showError("Unsupported audio file: " + path);
        } catch (IOException e) {
            showError("File not found: " + path);
        } catch (Exception e) {
            showError("Sound error: " + e.getMessage());
        }
    }
}