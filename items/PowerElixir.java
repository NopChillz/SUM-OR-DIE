package items;

import effects.DamageBuffEffect;
import javax.swing.ImageIcon;

public class PowerElixir extends BuffItem {

    public PowerElixir() {

        super("Power Elixir", true); // ⭐ สำคัญมาก

        icon = new ImageIcon(
            getClass().getResource("/Image/Items/PowerElixir.png")
        );

        effects.add(new DamageBuffEffect(1.3, 2));
    }
}