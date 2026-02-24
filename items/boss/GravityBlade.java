package items.boss;

import GameATK.Entity;
import effects.DamageContext;
import effects.ItemEffect;
import items.AbstractItem;
import log.GameLog;

public class GravityBlade extends AbstractItem {

    public GravityBlade() {
        super("Gravity Blade", false, true);
    }

    @Override
    public void onAcquire(Entity owner) {

        effects.add(new ItemEffect() {

            @Override
            public void onAttack(Entity attacker, Entity target, DamageContext ctx) {

                if (attacker != owner)
                    return;

                target.addStatus("WEAK_2");
                GameLog.add("🌑 Player weakened by gravity!");
            }
        });
    }
}