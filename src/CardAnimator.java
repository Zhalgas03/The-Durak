import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CardAnimator {
    private static final int ANIMATION_DELAY = 5;
    private static final int STEP = 1;
    private static final Cursor HAND_CURSOR = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    private static final Cursor DEFAULT_CURSOR = Cursor.getDefaultCursor();

    public static void setupCardHoverAnimation(JComponent component, int startY) {
        final int[] currentY = {startY};
        final Timer timer = new Timer(ANIMATION_DELAY, null);

        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                component.setCursor(HAND_CURSOR);
                startAnimation(component, currentY, 0, timer);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                component.setCursor(DEFAULT_CURSOR);
                startAnimation(component, currentY, startY, timer);
            }
        });
    }

    private static void startAnimation(JComponent comp, int[] currentY, int targetY, Timer timer) {
        timer.stop();
        timer.removeActionListener(timer.getActionListeners().length > 0 ? timer.getActionListeners()[0] : null);

        timer.addActionListener(e -> {
            if (currentY[0] == targetY) {
                timer.stop();
                return;
            }
            currentY[0] += (targetY > currentY[0]) ? STEP : -STEP;
            comp.setLocation(comp.getX(), currentY[0]);
        });
        timer.start();
    }
}