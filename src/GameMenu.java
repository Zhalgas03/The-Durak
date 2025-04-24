import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameMenu extends JFrame {

    private int selectedIndex = 0;
    private JButton[] menuButtons;
    private LoadFont pixelFont;

    public GameMenu() {
        setupFrame();
        addComponents();
        setupKeyBindings();
        Sound.playBackgroundMusic(Variables.getMenuSoundPath());
    }

    // Настройка окна
    private void setupFrame() {
        setTitle("DURAK");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK);
        setLayout(new GridBagLayout());
    }



    // Добавление компонентов
    private void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 20, 5, 20);
        gbc.anchor = GridBagConstraints.CENTER;

        GridBagConstraints logoGbc = (GridBagConstraints) gbc.clone();
        logoGbc.insets = new Insets(5, 20, 40, 20);
        // Логотип
        JLabel logo = new JLabel("DURAK");

        logo.setFont(LoadFont.loadGameFont(230f));
        logo.setForeground(new Color(255, 255, 255));
        add(logo, logoGbc);

        // Кнопки меню
        String[] buttonLabels = {"Classic", "Triad", "OPTIONS", "EXIT"};
        menuButtons = new JButton[buttonLabels.length];

        gbc.gridy = 1;
        for (int i = 0; i < buttonLabels.length; i++) {
            menuButtons[i] = createArcadeButton(buttonLabels[i], i);
            add(menuButtons[i], gbc);
            gbc.gridy++;
        }

        updateSelection();
    }

    // Создание аркадных кнопок
    private JButton createArcadeButton(String text, int index) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;

                // Фон
                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Текст
                g2d.setFont(LoadFont.loadGameFont(28f));
                g2d.setColor(selectedIndex == index ?
                        new Color(255, 255, 0) : new Color(255, 255, 255));

                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(text)) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2d.drawString(text, x, y);
            }
        };

        btn.setPreferredSize(new Dimension(400, 60));
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setFocusPainted(false);
        return btn;
    }



    // Настройка управления
    private void setupKeyBindings() {
        InputMap im = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getRootPane().getActionMap();

        im.put(KeyStroke.getKeyStroke("UP"), "up");
        im.put(KeyStroke.getKeyStroke("DOWN"), "down");
        im.put(KeyStroke.getKeyStroke("LEFT"), "left");
        im.put(KeyStroke.getKeyStroke("RIGHT"), "right");
        im.put(KeyStroke.getKeyStroke("ENTER"), "enter");

        am.put("up", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (--selectedIndex < 0) selectedIndex = menuButtons.length - 1;
                updateSelection();
            }
        });

        am.put("down", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (++selectedIndex >= menuButtons.length) selectedIndex = 0;
                updateSelection();
            }
        });



        am.put("enter", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                handleSelection();
            }
        });
    }

    // Обновление интерфейса
    private void updateSelection() {
        for (JButton btn : menuButtons) {
            btn.repaint();
        }
        Sound.playSoundEffect(Variables.getMoveSoundPath());
    }

    private void handleSelection() {

        switch(selectedIndex) {
            case 0:
            case 1:
                // Блокируем ввод на время анимации
                getRootPane().setEnabled(false);
                Sound.playSoundEffect(Variables.getSelectSoundPath());
                // Запускаем таймер на 1 секунду
                new Timer(1000, e -> {
                    ((Timer)e.getSource()).stop();
                    startGame(selectedIndex == 0 ? 2 : 1);
                    getRootPane().setEnabled(true);
                }).start();
                break;
            case 2: Sound.playSoundEffect(Variables.getDenySoundPath());; break;
            case 3: System.exit(0);
        }
    }



    // Запуск игры
    private void startGame(int players) {
        Sound.stopBackgroundMusic();
        dispose();
        new Thread(() -> {
            if (players == 1) Game52.main(new String[]{});
            else MainBeta2.main(new String[]{});
        }).start();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameMenu().setVisible(true));
    }
}