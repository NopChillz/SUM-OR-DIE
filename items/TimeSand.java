package items;

import GameATK.Entity;
import effects.TimeSandEffect;
import javax.swing.ImageIcon;

public class TimeSand extends BuffItem {

    public TimeSand(){

        super("Time Sand", true);

        icon = new ImageIcon(getClass().getResource("/Image/Items/TimeSand.png"));

        effects.add(new TimeSandEffect());
    }

    @Override
    public void onUse(Entity user, Entity target){
        this.setVisuallyUsed(true);
    }
}