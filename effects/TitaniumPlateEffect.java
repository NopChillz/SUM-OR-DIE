package effects;

import GameATK.Entity;
import log.GameLog;

public class TitaniumPlateEffect implements ItemEffect {

    private int remainingHits = 2;
    private boolean active = false;
    private boolean visualMarked = false;

    @Override
    public void onActivate(){
        active = true;
        GameLog.add("🛡 Titanium Plate activated!");
    }

    @Override
    public void onDamageTaken(Entity defender,
                            Entity attacker,
                            DamageContext ctx){

        if(!active) return;
        if(remainingHits <= 0) return;

        // ⭐ MARK VISUAL USED (ครั้งแรกที่ทำงาน)
        if(!visualMarked){

            for(var item : defender.getItemManager().getItems()){
                if(item.getEffects().contains(this)){
                    ((items.AbstractItem)item).setVisuallyUsed(true);
                    break;
                }
            }

            GameLog.add("🛡 Titanium Plate activated!");
            visualMarked = true;
        }

        int original = ctx.getDamage();
        int reduced  = (int)Math.round(original * 0.8);

        ctx.setDamage(reduced);

        if(remainingHits == 2){
            GameLog.add("🛡 Titanium Plate absorbs the hit!");
        }
        else if(remainingHits == 1){
            GameLog.add("🛡 Armor is cracking!");
        }

        remainingHits--;

        if(remainingHits == 0){
            defender.getItemManager().expireItemByEffect(this);
        }
    }

}