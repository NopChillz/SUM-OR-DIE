package status;

import GameATK.Entity;
import effects.DamageContext;
import log.GameLog;

public class VulnerableStatus extends StatusEffect {

    public VulnerableStatus(int turns){
        super(turns);
        iconPath = "/Image/Status/vulnerable.png";
    }

    @Override
    public void onDamageTaken(Entity defender,
                              Entity attacker,
                              DamageContext ctx){

        ctx.multiplyDamage(1.25);
    }

    @Override
    public void onApply(Entity target){
        GameLog.add("💥 Vulnerable!");
    }
}