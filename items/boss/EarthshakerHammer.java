package items.boss;

import GameATK.Entity;
import effects.DamageContext;
import effects.ItemEffect;
import items.AbstractItem;

public class EarthshakerHammer extends AbstractItem {

    public EarthshakerHammer() {
        super("Earthshaker Hammer", false, true);
    }

    @Override
    public void onAcquire(Entity owner) {

        effects.add(new ItemEffect() {

            @Override
            public void onAttack(Entity attacker, Entity target, DamageContext ctx) {

                if(attacker != owner) return;

                int items =
                    target.getItemManager().getItems().size();

                ctx.setDamage(ctx.getDamage() + items * 2);
            }
        });
    }
}