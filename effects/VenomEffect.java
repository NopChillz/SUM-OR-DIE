package effects;

import GameATK.Boss;
import GameATK.Entity;
import log.GameLog;
import status.PoisonStatus;

public class VenomEffect implements ItemEffect {

    @Override
    public void onAttack(Entity attacker, Entity target, DamageContext ctx) {

        if(!(target instanceof Boss boss))
            return;

        boss.getStatusManager().add(
            new PoisonStatus(3, 0.05),
            boss
        );

        GameLog.add("Poison applied to Boss!");
    }
}