import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.File;

public class Sound {
    private static Clip bgClip;
    private static void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.ERROR_MESSAGE);
    }
    public static void playBackgroundMusic(String path) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(path));
            bgClip = AudioSystem.getClip();
            bgClip.open(audioStream);
            bgClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            showError("Music error: " + e.getMessage());
        }
    }

    public static void stopBackgroundMusic() {
        if (bgClip != null && bgClip.isRunning()) {
            bgClip.stop();
            bgClip.close();
        }
    }

    public static void playSoundEffect(String path) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(path)));
            clip.start();
        } catch (Exception e) {
            showError("Sound error: " + e.getMessage());
        }
    }

}
