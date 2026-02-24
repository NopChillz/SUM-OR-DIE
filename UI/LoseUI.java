package UI;

import java.awt.*;
import javax.sound.sampled.*;
import javax.swing.*;

public class LoseUI extends JPanel {

    private Image backgroundImg;

    public LoseUI(MainFrame Mainframe) {

        setLayout(null);
        backgroundImg = new ImageIcon(getClass().getResource("/Image/Background/Lose.png")).getImage();

        ImageIcon btnIcon = new ImageIcon(getClass().getResource("/Image/Icon/Restart.png"));

        Image scaledBtn = btnIcon.getImage().getScaledInstance(273,153,Image.SCALE_SMOOTH);

        JButton btnRestart = new JButton(new ImageIcon(scaledBtn));

        btnRestart.setBorderPainted(false);
        btnRestart.setContentAreaFilled(false);
        btnRestart.setFocusPainted(false);
        btnRestart.setOpaque(false);

        int centerX = (1280 - 273) / 2;
        int centerY = (720 - 153) / 2 + 120;

        btnRestart.setBounds(centerX, centerY, 273, 153);

        btnRestart.addActionListener(e -> {Mainframe.changePage(new LevelUI(Mainframe));
            try {
                AudioInputStream audioIn =
                        AudioSystem.getAudioInputStream(
                                getClass().getResource("/Sound/ClickStart.wav"));

                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                
                FloatControl gain =
                        (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

                gain.setValue(-15.0f);
                clip.start();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        add(btnRestart);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(backgroundImg,
                0, 0,
                getWidth(), getHeight(),   // scale ตามจอ
                this);
    }
}