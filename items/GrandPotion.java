package items;

import GameATK.Entity;
import javax.swing.ImageIcon;
import log.GameLog;

public class GrandPotion extends HealItem {

    public GrandPotion(){

        // ⭐ persistent = true (ไม่หาย)
        super("Grand Potion", true, true);

        icon = new ImageIcon(
            getClass().getResource("/Image/Items/GrandPotion.png")
        );

        setCooldown(4); // ⭐ 4 TURN COOLDOWN
    }

    @Override
    public void onUse(Entity user, Entity target){

        int heal = (int)(user.getMaxHp() * 0.5);

        user.heal(heal);

        GameLog.add("🧪 Grand Potion heals " + heal + " HP!");

        startCooldown(); // ⭐ เริ่ม cooldown
    }
}