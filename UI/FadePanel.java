package UI;

import java.awt.*;
import javax.swing.*;

public class FadePanel extends JPanel {

    private float alpha = 0f;
    private Timer timer;

    public FadePanel() {
        setOpaque(false);
    }

    // ===============================
    // FADE OUT (ดำขึ้น)
    // ===============================
    public void fadeOut(Runnable onFinish) {

        alpha = 0f;

        timer = new Timer(16, e -> {
            alpha += 0.05f;

            if (alpha >= 1f) {
                alpha = 1f;
                timer.stop();

                if (onFinish != null)
                    onFinish.run();
            }

            repaint();
        });

        timer.start();
    }

    // ===============================
    // FADE IN (สว่างกลับ)
    // ===============================
    public void fadeIn(Runnable onFinish) {

        alpha = 1f;

        timer = new Timer(16, e -> {
            alpha -= 0.05f;

            if (alpha <= 0f) {
                alpha = 0f;
                timer.stop();

                if (onFinish != null)
                    onFinish.run();
            }

            repaint();
        });

        timer.start();
    }

    // ===============================
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, alpha));

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }
}