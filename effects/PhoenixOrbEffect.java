package effects;

import GameATK.Entity;
import log.GameLog;

public class PhoenixOrbEffect implements ItemEffect {

    private boolean used = false;

    @Override
    public void onDamageTaken(Entity defender,
                              Entity attacker,
                              DamageContext ctx){

        if(used) return;

        int incoming = ctx.getDamage();
        int hpAfterHit = defender.getCurrentHp() - incoming;

        // ⭐ จะตาย
        if(hpAfterHit <= 0){

            used = true;

            int reviveHp =
                (int)(defender.getMaxHp() * 0.20);

            // ⭐ กัน damage ฆ่า
            ctx.setDamage(0);

            defender.heal(reviveHp);

            GameLog.add("🔥 Phoenix Orb revived the player!");
            GameLog.add("✨ Restored " + reviveHp + " HP!");

            // ⭐ ลบ item อย่างปลอดภัย
            defender.getItemManager()
                    .expireItemByEffect(this);
        }
    }
}