package items.boss;

import GameATK.Entity;
import effects.DamageContext;
import effects.ItemEffect;
import items.AbstractItem;

public class InfernalThornmail extends AbstractItem {

    public InfernalThornmail() {
        super("Infernal Thornmail", false, true);
    }

    @Override
    public void onAcquire(Entity owner) {

        effects.add(new ItemEffect() {

            @Override
            public void onDamageTaken(Entity defender,Entity attacker,DamageContext ctx) {

                if(ctx.getTarget() != owner) return;

                int reflect = (int)(ctx.getDamage() * 0.2);
                ctx.getAttacker().takeDamage(reflect);
            }
        });
    }
}