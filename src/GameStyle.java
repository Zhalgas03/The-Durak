import java.awt.*;
import java.io.File;

public class GameStyle {
    public static final Color PANEL_COLOR = new Color(0x496f93);

    public static Color getPanelColor() {
        return PANEL_COLOR;
    }
    public static Font loadGameFont(float size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT,
                    new File("style/font/font.otf")).deriveFont(size);
        } catch (Exception e) {
            return new Font("Arial", Font.BOLD, (int) size);
        }
    }
}
