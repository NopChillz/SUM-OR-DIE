package effects;

import GameATK.Entity;
import log.GameLog;

public class SoulHunterEffect implements ItemEffect {

    private int stacks = 0;
    private boolean empowered = false;

    @Override
    public void onAttack(Entity attacker, Entity target, DamageContext ctx) {

        // ⭐ ถ้ากำลังใช้ empowered hit
        if(empowered){
            ctx.multiplyDamage(2.0);

            GameLog.add("🔥 Soul Hunter unleashed! x2 DAMAGE");

            // ใช้แล้วหมดทันที
            attacker.getItemManager().expireItemByEffect(this);

            return;
        }

        // ⭐ stack เฉพาะตอนตีโดน
        if(ctx.getDamage() > 0){

            stacks++;

            GameLog.add("Soul Stack: " + stacks + "/3");

            if(stacks >= 3){
                empowered = true;
                GameLog.add("Soul Hunter READY!");
            }
        }
    }
}