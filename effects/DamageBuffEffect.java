package effects;

import GameATK.Entity;

public class DamageBuffEffect implements ItemEffect {

    private double multiplier;
    private int turnsLeft;

    public DamageBuffEffect(double multiplier, int duration){
        this.multiplier = multiplier;
        this.turnsLeft = duration;
    }

    @Override
    public void onAttack(Entity attacker, Entity target, DamageContext ctx){

        if(turnsLeft <= 0) return;

        int newDamage = (int)(ctx.getDamage() * multiplier);
        ctx.setDamage(newDamage);

        turnsLeft--;
    }

    public boolean isExpired(){
        return turnsLeft <= 0;
    }
}