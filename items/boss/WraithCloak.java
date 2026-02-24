package items.boss;

import GameATK.Entity;
import effects.DamageContext;
import effects.ItemEffect;
import items.AbstractItem;
import log.GameLog;

public class WraithCloak extends AbstractItem {

    private boolean used = false;

    public WraithCloak() {
        super("Wraith Cloak", false, true);
    }

    @Override
    public void onAcquire(Entity owner) {

        effects.add(new ItemEffect() {

            @Override
            public void onDamageTaken(Entity defender,Entity attacker,DamageContext ctx) {

                if(defender != owner || used)
                    return;

                if(owner.getHpPercent() <= 20 &&
                ctx.getDamage() >= owner.getCurrentHp()){

                    ctx.setDamage(owner.getCurrentHp() - 1);
                    used = true;

                    GameLog.add("👻 Wraith Cloak prevents death!");
                }
            }
        });
    }
}