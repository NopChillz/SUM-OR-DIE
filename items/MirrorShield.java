package items;

import effects.MirrorShieldEffect;
import javax.swing.ImageIcon;

public class MirrorShield extends DefenseItem {

    public MirrorShield(){

        super("Mirror Shield", true, true);

        icon = new ImageIcon(
            getClass().getResource("/Image/Items/MirrorShield.png")
        );

        effects.add(new MirrorShieldEffect());
    }
}