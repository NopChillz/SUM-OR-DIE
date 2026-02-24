package items.boss;

import GameATK.Entity;
import effects.DamageContext;
import effects.ItemEffect;
import items.AbstractItem;
import log.GameLog;

public class VoidSilence extends AbstractItem {

    public VoidSilence() {
        super("Void Silence", false, true);
    }

    @Override
    public void onAcquire(Entity owner) {

        effects.add(new ItemEffect() {

            @Override
            public void onAttack(Entity attacker, Entity target, DamageContext ctx) {

                if(attacker != owner) return;

                target.addStatus("ITEM_LOCK");
                GameLog.add("🔮 Void Silence disables player items!");
            }
        });
    }
}