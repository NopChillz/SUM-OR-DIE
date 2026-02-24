package items;

import GameATK.Entity;
import effects.WarHornEffect;
import javax.swing.ImageIcon;

public class WarHorn extends BuffItem {

    public WarHorn() {

        // consumable + persistent (อยู่หลายเทิร์น)
        super("War Horn", true, true);

        icon = new ImageIcon(
            getClass().getResource("/Image/Items/WarHorn.png")
        );

        effects.add(new WarHornEffect());
    }

    @Override
    public void onUse(Entity user, Entity target){

        if(this instanceof AbstractItem ai){
            ai.setVisuallyUsed(true);
            ai.disarm();   // ⭐ ทำให้กดซ้ำไม่ได้
        }
    }
}