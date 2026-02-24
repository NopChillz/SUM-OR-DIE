package Animation;

import java.awt.*;
import javax.swing.*;

public class Battle {
    private JLabel playerLabel;
    private JLabel bossLabel;
    private int level;

    public Battle(JLabel playerLabel, JLabel bossLabel, int level) {
        this.playerLabel = playerLabel;
        this.bossLabel = bossLabel;
        this.level = level;
    }

   public void playerAttack(Runnable onFinish) {

        Image atkImg = new ImageIcon(getClass().getResource("/Image/Character/PlayerAttack.png")).getImage();
        playerLabel.setBounds(80, 200, 222, 113);
        playerLabel.setIcon(new ImageIcon(atkImg.getScaledInstance(222,113,Image.SCALE_SMOOTH)));
        Timer timer = new Timer(400, e -> {Image idleImg = new ImageIcon(getClass().getResource("/Image/Character/Player.png")).getImage();

            playerLabel.setBounds(100, 190, 100, 125);
            playerLabel.setIcon(new ImageIcon(idleImg.getScaledInstance(100,125,Image.SCALE_SMOOTH)));

            // ⭐⭐⭐ สำคัญที่สุด ⭐⭐⭐
            if(onFinish != null)
                onFinish.run();

        });

        timer.setRepeats(false);
        timer.start();
    }

    public void bossTakeDamage() {
        try {
            String hitImagePath = "/Image/Character/Boss" + level + "_Hit.png";
            Image hitImg = new ImageIcon(getClass().getResource(hitImagePath)).getImage();
            Image scaledHit = null;
            if (level == 1) {
                scaledHit = hitImg.getScaledInstance(135, 125, Image.SCALE_SMOOTH);
                bossLabel.setBounds(420, 190, 135, 125); 
            } 
            else if (level == 2) {
                scaledHit = hitImg.getScaledInstance(153, 193, Image.SCALE_SMOOTH);
                bossLabel.setBounds(420, 140, 153, 193);
            } 
            else {
                scaledHit = hitImg.getScaledInstance(160, 160, Image.SCALE_SMOOTH);
                bossLabel.setBounds(430, 160, 160, 160);
            }
            bossLabel.setIcon(new ImageIcon(scaledHit));
            Timer timer = new Timer(300, e -> {
                try {
                    String idleImagePath = "/Image/Character/Boss" + level + ".png";
                    Image idleImg = new ImageIcon(getClass().getResource(idleImagePath)).getImage();
                    Image scaledIdle;
                    if (level == 1) {
                        scaledIdle = idleImg.getScaledInstance(86, 131, Image.SCALE_SMOOTH);
                        bossLabel.setBounds(450, 190, 86, 131); 
                    } 
                    else if (level == 2) {
                        scaledIdle = idleImg.getScaledInstance(86, 165, Image.SCALE_SMOOTH);
                        bossLabel.setBounds(450, 150, 86, 165);
                    } 
                    else {
                        scaledIdle = idleImg.getScaledInstance(120, 149, Image.SCALE_SMOOTH);
                        bossLabel.setBounds(450, 170, 120, 149);
                    }

      
                    bossLabel.setIcon(new ImageIcon(scaledIdle));
                    
                } catch (Exception ex) {
                    System.err.println("หารูปบอสปกติไม่เจอ");
                }
            });
            timer.setRepeats(false);
            timer.start();

        } catch (Exception ex) {
            System.err.println("หารูปบอสโดนตีไม่เจอ! อย่าลืมใส่ไฟล์: Boss" + level + "_Hit.png");
        }
    }

    public void bossAttack() {
        try {
            String atkImagePath = "/Image/Character/Boss" + level + "_Attack.png";
            Image atkImg = new ImageIcon(getClass().getResource(atkImagePath)).getImage();
            Image scaledAtk = null;

            if (level == 1) {
                scaledAtk = atkImg.getScaledInstance(265, 134, Image.SCALE_SMOOTH);
                bossLabel.setBounds(300, 180, 265, 134); 
            } else if (level == 2) {
                scaledAtk = atkImg.getScaledInstance(176, 176, Image.SCALE_SMOOTH);
                bossLabel.setBounds(380, 145, 176, 176);
            } else {
                scaledAtk = atkImg.getScaledInstance(155, 155, Image.SCALE_SMOOTH);
                bossLabel.setBounds(410, 160, 155, 155);
            }

            bossLabel.setIcon(new ImageIcon(scaledAtk));
            
            Timer timer = new Timer(400, e -> {
                try {
                    String idleImagePath = "/Image/Character/Boss" + level + ".png";
                    Image idleImg = new ImageIcon(getClass().getResource(idleImagePath)).getImage();
                    Image scaledIdle;
                    
                    if (level == 1) {
                        scaledIdle = idleImg.getScaledInstance(86, 131, Image.SCALE_SMOOTH);
                        bossLabel.setBounds(450, 190, 86, 131); 
                    } else if (level == 2) {
                        scaledIdle = idleImg.getScaledInstance(86, 165, Image.SCALE_SMOOTH);
                        bossLabel.setBounds(450, 150, 86, 165);
                    } else {
                        scaledIdle = idleImg.getScaledInstance(120, 149, Image.SCALE_SMOOTH);
                        bossLabel.setBounds(450, 170, 120, 149);
                    }
                    bossLabel.setIcon(new ImageIcon(scaledIdle));
                } catch (Exception ex) {}
            });
            timer.setRepeats(false);
            timer.start();
        } catch (Exception ex) {
            System.err.println("หารูปบอสโจมตีไม่เจอ! เช็กไฟล์: Boss" + level + "_Attack.png");
        }
    }

    public void playerTakeDamage() {
        try {
            Image hitImg = new ImageIcon(getClass().getResource("/Image/Character/Player_Hit.png")).getImage();
            Image scaledHit = hitImg.getScaledInstance(110, 125, Image.SCALE_SMOOTH);
            playerLabel.setBounds(90, 195, 110, 125);
            playerLabel.setIcon(new ImageIcon(scaledHit));

            Timer timer = new Timer(400, e -> {
                try {
                    Image idleImg = new ImageIcon(getClass().getResource("/Image/Character/Player.png")).getImage();
                    Image scaledIdle = idleImg.getScaledInstance(100, 125, Image.SCALE_SMOOTH);
                    playerLabel.setBounds(100, 190, 100, 125); 
                    playerLabel.setIcon(new ImageIcon(scaledIdle));
                } catch (Exception ex) {}
            });
            timer.setRepeats(false);
            timer.start();
        } catch (Exception ex) {
            System.err.println("หารูป Player_Hit.png ไม่เจอ!");
        }
    }
}