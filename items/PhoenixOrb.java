package items;

import effects.PhoenixOrbEffect;
import javax.swing.ImageIcon;

public class PhoenixOrb extends DefenseItem {

    public PhoenixOrb(){

        // consumable แต่ persistent (รอ trigger)
        super("Phoenix Orb", true, true);

        icon = new ImageIcon(
            getClass().getResource("/Image/Items/PhoenixOrb.png")
        );

        effects.add(new PhoenixOrbEffect());

        // ⭐ passive ทำงานทันที
        arm();
    }
}