package items.boss;

import GameATK.Entity;
import effects.DamageContext;
import effects.ItemEffect;
import items.AbstractItem;
import java.util.Random;
import log.GameLog;
import status.FreezeStatus;

public class FrostbiteAxe extends AbstractItem {

    public FrostbiteAxe() {
        super("Frostbite Axe", false, true); // passive boss item
    }

    @Override
    public void onAcquire(Entity owner) {

        Random rand = new Random();

        effects.add(new ItemEffect() {

            @Override
            public void onAttack(Entity attacker,
                                 Entity target,
                                 DamageContext ctx) {

                if(attacker != owner)
                    return;

                if(rand.nextDouble() < 0.20){
                // ⭐ อย่ายิงถ้ายัง frozen
                if(target.getStatusManager().has(FreezeStatus.class))
                    return;

                target.getStatusManager()
                    .add(new FreezeStatus(2), target);

                GameLog.add("❄ Frostbite Axe freezes the player!");
            }
            }
        });
    }
}