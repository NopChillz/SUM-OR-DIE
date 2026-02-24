package UI;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;

public class WinUi extends JPanel{
    public WinUi(MainFrame Mainframe) {
        ImageIcon bgIcon = new ImageIcon(getClass().getResource("/Image/Background/Win.png"));
        JLabel background = new JLabel(bgIcon);
        background.setBounds(0, 0, 1280, 720);
        background.setLayout(null);

        ImageIcon btnIcon = new ImageIcon(getClass().getResource("/Image/Icon/Restart.png"));
        Image scaledBtnImg = btnIcon.getImage().getScaledInstance(273, 153, Image.SCALE_SMOOTH);
        JButton btnRestart = new JButton(new ImageIcon(scaledBtnImg));
        btnRestart.setBorderPainted(false);
        btnRestart.setContentAreaFilled(false); 
        btnRestart.setFocusPainted(false);     
        btnRestart.setOpaque(false);
        btnRestart.setBounds(460, 459, 273, 153);

        btnRestart.addActionListener(e -> {
            Mainframe.changePage(new LevelUI(Mainframe));
            try {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResource("/Sound/ClickStart.wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                javax.sound.sampled.FloatControl gainControl = (javax.sound.sampled.FloatControl) clip.getControl(javax.sound.sampled.FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-15.0f);
                clip.start();
            } catch (Exception ex) {}
        });

        background.add(btnRestart);
        add(background);
    }
}
