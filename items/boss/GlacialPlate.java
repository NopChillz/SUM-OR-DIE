package items.boss;

import GameATK.Entity;
import effects.DamageContext;
import effects.ItemEffect;
import items.AbstractItem;

public class GlacialPlate extends AbstractItem {

    public GlacialPlate() {
        super("Glacial Plate", false, true);
    }

    @Override
    public void onAcquire(Entity owner) {

        effects.add(new ItemEffect() {

            @Override
            public void onDamageTaken(Entity defender,Entity attacker,DamageContext ctx) {

                if(ctx.getTarget() != owner) return;

                if(ctx.getDamage() >= 25){
                    ctx.multiplyDamage(0.5);
                }
            }
        });
    }
}