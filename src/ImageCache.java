import javax.swing.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ImageCache {
    private final Map<String, ImageIcon> cache = new HashMap<>();

    public ImageIcon getImage(String path) {
        return cache.computeIfAbsent(path, p -> {
            try {
                return new ImageIcon(new File(p).getAbsolutePath());
            } catch (Exception e) {
                System.err.println("Error loading image: " + p);
                return new ImageIcon();
            }
        });
    }
}