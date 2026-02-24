package effects;

import GameATK.Entity;
import log.GameLog;

public class WarHornEffect implements ItemEffect {

    private int remainingTurns = 2;
    private boolean active = false;

    private static final double BONUS = 1.3;

    @Override
    public void onActivate(){
        active = true;
        GameLog.add("📯 War Horn echoes! ATK increased!");
    }

    @Override
    public void onAttack(Entity attacker,
                         Entity target,
                         DamageContext ctx){

        if(!active) return;

        if(ctx.isModifiedBy(this)) return;
        ctx.markModified(this);

        ctx.multiplyDamage(BONUS);

        GameLog.add("📯 War Horn boosts attack!");
    }

    // ⭐ ลดเฉพาะ Player Turn
    @Override
    public void onTurnStart(Entity owner){

        if(!active) return;

        remainingTurns--;

        if(remainingTurns <= 0){
            owner.getItemManager()
                 .expireItemByEffect(this);

            GameLog.add("📯 War Horn faded.");
        }
    }
}