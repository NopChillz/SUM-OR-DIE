package UI;
import java.awt.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class StartUI extends JPanel{
    
    private MainFrame mainFrame; 

    public StartUI(MainFrame mainFrame) {

        this.mainFrame = mainFrame; 

        ImageIcon bgIcon = new ImageIcon(getClass().getResource("/Image/Background/StartGame.png"));
        JLabel background = new JLabel(bgIcon);
        background.setBounds(0, 0, 1280, 720);
        background.setLayout(null);

        ImageIcon btnIcon = new ImageIcon(getClass().getResource("/Image/Icon/PlayBtn.png"));
        Image scaledBtnImg = btnIcon.getImage().getScaledInstance(317, 114, Image.SCALE_SMOOTH);
        JButton btnStart = new JButton(new ImageIcon(scaledBtnImg));
        btnStart.setBorderPainted(false);
        btnStart.setContentAreaFilled(false); 
        btnStart.setFocusPainted(false);     
        btnStart.setOpaque(false);
        btnStart.setBounds(430, 405, 317, 114);

        btnIcon = new ImageIcon(getClass().getResource("/Image/Icon/Setting.png"));
        scaledBtnImg = btnIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        JButton SettingBtn = new JButton(new ImageIcon(scaledBtnImg));
        SettingBtn.setBorderPainted(false);
        SettingBtn.setContentAreaFilled(false); 
        SettingBtn.setFocusPainted(false);     
        SettingBtn.setOpaque(false);
        SettingBtn.setBounds(700, 405, 317, 114);
        
        btnStart.addActionListener(e -> {
            this.mainFrame.changePage(new LevelUI(this.mainFrame));
            try {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResource("/Sound/ClickStart.wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                javax.sound.sampled.FloatControl gainControl = (javax.sound.sampled.FloatControl) clip.getControl(javax.sound.sampled.FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-15.0f);
                clip.start();
            } catch (Exception ex) {}
        });

        // eventปุ่มตั้งค่า
        SettingBtn.addActionListener(e -> {
           showSettingsDialog();
        });

        background.add(btnStart);
        background.add(SettingBtn);
        add(background);
    }

    private void showSettingsDialog() {
        JDialog dialog = new JDialog(mainFrame, "SETTINGS", true);
        dialog.setSize(300, 350);
        dialog.setLocationRelativeTo(this); 
        dialog.setUndecorated(true); 

        JPanel panel = new JPanel(new GridLayout(5, 1, 15, 15));
        panel.setBackground(new Color(43, 45, 48)); 
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(184, 134, 11), 4), 
            BorderFactory.createEmptyBorder(20, 20, 20, 20) 
        ));

        JLabel titleLabel = new JLabel("SETTINGS", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 26));
        titleLabel.setForeground(new Color(255, 215, 0)); 

        JButton btnHowToPlay = createSettingButton("HOW TO PLAY");
        JButton btnCredits = createSettingButton("CREDITS");
        JButton btnClose = createSettingButton("CLOSE");

        btnHowToPlay.addActionListener(e -> {
            String howToPlayText = "HOW TO PLAY:\n1. Select 2-3 tiles\n2. Use item in Inventory\n3. Attack the Boss!";
            JOptionPane.showMessageDialog(dialog, howToPlayText, "HOW TO PLAY", JOptionPane.INFORMATION_MESSAGE);
        });

        btnCredits.addActionListener(e -> {
            String creditsText = "DEVELOPER:\n-68050062\n-68050074\n-68050092\n-68050164\n-68050367";
            JOptionPane.showMessageDialog(dialog, creditsText, "CREDITS", JOptionPane.INFORMATION_MESSAGE);
        });

        btnClose.addActionListener(e -> dialog.dispose()); 

        panel.add(titleLabel);
        panel.add(btnHowToPlay);
        panel.add(btnCredits);
        panel.add(new JLabel()); 
        panel.add(btnClose);

        dialog.add(panel);
        dialog.setVisible(true); 
    }

    private JButton createSettingButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Monospaced", Font.BOLD, 18));
        btn.setBackground(new Color(80, 75, 70));
        btn.setForeground(new Color(245, 245, 245));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createLineBorder(new Color(184, 134, 11), 2));
        return btn;
    }
}