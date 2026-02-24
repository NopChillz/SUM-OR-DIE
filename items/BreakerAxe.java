package items;

import effects.CriticalEffect;
import javax.swing.ImageIcon;

public class BreakerAxe extends AttackItem {

    public BreakerAxe(){

        super("Breaker Axe", true);

        icon = new ImageIcon(getClass().getResource("/Image/Items/BreakerAxe.png"));

        effects.add(new CriticalEffect(0.20, 1.5));
    }
}