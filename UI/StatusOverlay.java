package UI;

import GameATK.Entity;
import java.awt.*;
import javax.swing.*;
import status.StatusEffect;

public class StatusOverlay extends JPanel {

    private Entity entity;

    public StatusOverlay(Entity entity){
        this.entity = entity;
        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.CENTER, 2, 0));
    }

    public void refresh(){

        removeAll();

        for(StatusEffect s :
            entity.getStatusManager().getStatuses()){

            if(s.getIconPath() == null) continue;

            ImageIcon icon = new ImageIcon(
                getClass().getResource(s.getIconPath())
            );

            Image scaled =
            icon.getImage().getScaledInstance(24,24,Image.SCALE_SMOOTH);
            JLabel label = new JLabel(new ImageIcon(scaled));
            add(label);
        }

        revalidate();
        repaint();
    }
}