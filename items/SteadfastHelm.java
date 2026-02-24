package items;

import GameATK.Entity;
import javax.swing.ImageIcon;
import log.GameLog;
import status.FreezeStatus;

public class SteadfastHelm extends DefenseItem {

    public SteadfastHelm(){

        super("Steadfast Helm", true); // consumable

        icon = new ImageIcon(
            getClass().getResource("/Image/Items/SteadfastHelm.png")
        );
    }

    @Override
    public void onUse(Entity user, Entity target){

        user.getStatusManager().reduceAllDurations(1);
        user.addStatusResistance(0.15);
        user.getStatusManager().remove(FreezeStatus.class);
        GameLog.add("🪖 Steadfast Helm cleansed statuses!");

        expire(); // ใช้แล้วหาย
    }
}