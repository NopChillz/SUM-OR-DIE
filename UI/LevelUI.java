package UI;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;

public class LevelUI extends JPanel {
    public LevelUI(MainFrame Mainframe) {
        ImageIcon bgIcon = new ImageIcon(getClass().getResource("/Image/Background/LevelBackground.png"));
        JLabel background = new JLabel(bgIcon);
        background.setBounds(0, 0, 1280, 720);
        background.setLayout(null);
        ImageIcon backIcon = new ImageIcon(getClass().getResource("/Image/Icon/BackBtn.png"));
        Image scaledBackImg = backIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        JButton btnBack = new JButton(new ImageIcon(scaledBackImg));
        btnBack.setBorderPainted(false);
        btnBack.setContentAreaFilled(false);
        btnBack.setFocusPainted(false);
        btnBack.setOpaque(false);

        btnBack.setBounds(30, 30, 80, 80);

        btnBack.addActionListener(e -> {
            Mainframe.changePage(new StartUI(Mainframe));

            try {
                AudioInputStream audioIn = AudioSystem
                        .getAudioInputStream(getClass().getResource("/Sound/ClickStart.wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                javax.sound.sampled.FloatControl gainControl = (javax.sound.sampled.FloatControl) clip
                        .getControl(javax.sound.sampled.FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-15.0f);
                clip.start();
            } catch (Exception ex) {
            }
        });
        background.add(btnBack);
        background.add(SelectBtn(39, 563, 1, Mainframe));
        background.add(SelectBtn(505, 563, 2, Mainframe));
        background.add(SelectBtn(971, 563, 3, Mainframe));
        add(background);
    }

    public JButton SelectBtn(int x, int y, int level, MainFrame Mainframe) {
        ImageIcon btnIcon = new ImageIcon(getClass().getResource("/Image/Icon/SelectBtn.png"));
        Image scaledBtnImg = btnIcon.getImage().getScaledInstance(269, 75, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(scaledBtnImg));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setBounds(x, y, 269, 75);

        button.addActionListener(e -> {
            Mainframe.changePage(new GameUI(Mainframe, level));
            try {
                AudioInputStream audioIn = AudioSystem
                        .getAudioInputStream(getClass().getResource("/Sound/ClickStart.wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                javax.sound.sampled.FloatControl gainControl = (javax.sound.sampled.FloatControl) clip
                        .getControl(javax.sound.sampled.FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-15.0f);
                clip.start();
            } catch (Exception ex) {
            }
        });

        return button;
    }
}