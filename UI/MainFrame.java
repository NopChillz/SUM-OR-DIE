package UI;

import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {

    public static int gameVolume = 70;

    // ===== Fade System =====
    private FadePanel fadePanel = new FadePanel();
    private boolean isTransitioning = false;

    // =================================================
    // CONSTRUCTOR
    // =================================================
    public MainFrame() {

        setTitle("Game");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        ImageIcon gameIcon =
                new ImageIcon(getClass().getResource("/Image/icon/icon1.png"));
        setIconImage(gameIcon.getImage());

        // ⭐ ต้องตั้งก่อนใช้ glasspane
        setLayout(new BorderLayout());

        // ===== Fade layer =====
        setGlassPane(fadePanel);
        fadePanel.setVisible(false);

        // หน้าแรก
        setContentPane(new StartUI(this));

        setVisible(true);
    }

    // =================================================
    // CHANGE PAGE WITH SAFE FADE
    // =================================================
    public void changePage(JPanel newPanel) {

        // ⭐ กัน transition ซ้อน (ตัวแก้จอดำ)
        if (isTransitioning) return;

        isTransitioning = true;
        fadePanel.setVisible(true);

        fadePanel.fadeOut(() -> {

            setContentPane(newPanel);
            revalidate();
            repaint();

            fadePanel.fadeIn(() -> {
                fadePanel.setVisible(false);
                isTransitioning = false; // ปลดล็อค
            });
        });
    }
}
