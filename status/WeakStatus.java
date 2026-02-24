package status;

import GameATK.Entity;
import effects.DamageContext;
import log.GameLog;

public class WeakStatus extends StatusEffect {

    public WeakStatus(int turns){
        super(turns);
        iconPath = "/Image/Status/weak.png";
    }

    @Override
    public void onAttack(DamageContext ctx){

        ctx.multiplyDamage(0.9); // -10%
    }

    @Override
    public void onApply(Entity target){
        GameLog.add("💀 Attack weakened!");
    }
}