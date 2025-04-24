import java.awt.*;
import java.io.File;

public class LoadFont {
    public static Font loadGameFont(float size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT,
                    new File("cards/font.otf")).deriveFont(size);
        } catch (Exception e) {
            return new Font("Arial", Font.BOLD, (int) size);
        }
    }
}
