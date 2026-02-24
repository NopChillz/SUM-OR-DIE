package effects;

import GameATK.Entity;
import java.util.Random;
import log.GameLog;

public class MirrorShieldEffect implements ItemEffect {

    private Random rand = new Random();
    private boolean triggered = false;   // ⭐ NEW

    @Override
    public void onDamageTaken(Entity defender,
                              Entity attacker,
                              DamageContext ctx){

        if(triggered) return; // กันยิงซ้ำ
        triggered = true; // ⭐ ใช้แล้วทันที

        if(rand.nextDouble() <= 0.99){

            int reflect = (int)(ctx.getDamage() * 0.5);

            attacker.takeDamage(reflect);

            GameLog.add("🛡 Mirror Shield reflected " + reflect + " damage!");
        }

        // ⭐⭐ สำคัญที่สุด ⭐⭐
        defender.getItemManager().expireItemByEffect(this);
    }
}