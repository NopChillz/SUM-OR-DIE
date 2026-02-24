package effects;

import GameATK.Entity;
import GameATK.Player;
import GameATK.TurnManager;
import java.util.Random;
import log.GameLog;

public class TimeSandEffect implements ItemEffect {

    private boolean active = false;
    private boolean triggered = false;

    private final Random rand = new Random();

    @Override
    public void onActivate(){
        active = true;
        GameLog.add("⏳ Time Sand activated...");
    }

    @Override
    public void onAttack(Entity attacker,
                         Entity target,
                         DamageContext ctx){

        if(!active || triggered) return;
        if(!(attacker instanceof Player player)) return;

        triggered = true;

        if(rand.nextDouble() <= 1.00){

            TurnManager tm = player.getTurnManager();

            if(tm != null){
                tm.grantExtraTurn();
                GameLog.add("⏳ Time bends! Extra turn granted!");
            }
        }

        // ใช้แล้วหายทันที
        player.getItemManager()
              .expireItemByEffect(this);
    }
}