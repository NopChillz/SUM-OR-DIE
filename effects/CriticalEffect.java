package effects;

import GameATK.Entity;
import java.util.Random;
import log.GameLog;

public class CriticalEffect implements ItemEffect {

    private final double chance;
    private final double multiplier;
    private final Random rng = new Random();

    public CriticalEffect(double chance, double multiplier){
        this.chance = chance;
        this.multiplier = multiplier;
    }

    @Override
    public void onAttack(Entity attacker, Entity target, DamageContext ctx){

        if(rng.nextDouble() <= chance){
            ctx.multiplyDamage(multiplier);
            GameLog.add("💥 CRITICAL HIT! (Breaker Axe)");
        }
    }
}