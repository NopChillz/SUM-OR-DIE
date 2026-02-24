package items;

import GameATK.Entity;
import effects.VenomEffect;
import javax.swing.ImageIcon;

public class VenomDagger extends AttackItem {

    public VenomDagger() {

        super("Venom Dagger", true);

        icon = new ImageIcon(
            getClass().getResource("/Image/Items/VenomDagger.png")
        );

        effects.add(new VenomEffect());
    }

    @Override
    public void onUse(Entity user, Entity target){}
}