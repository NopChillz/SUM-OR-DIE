package items;

import effects.SoulHunterEffect;
import javax.swing.ImageIcon;

public class SoulHunter extends AttackItem {

    public SoulHunter() {

        super("Soul Hunter", false, true);

        icon = new ImageIcon(
            getClass().getResource("/Image/Items/SoulHunter.png")
        );

        effects.add(new SoulHunterEffect());
    }
}