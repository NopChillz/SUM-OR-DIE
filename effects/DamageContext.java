package effects;

import GameATK.Entity;
import java.util.HashSet;
import java.util.Set;

public class DamageContext {

    private Entity attacker;
    private Entity target;
    private int damage;
    private Set<Object> modifiers = new HashSet<>();

    public DamageContext(Entity attacker, Entity target, int damage){
        this.attacker = attacker;
        this.target = target;
        this.damage = damage;
    }

    public Entity getAttacker(){
        return attacker;
    }

    public Entity getTarget(){
        return target;
    }

    public int getDamage(){
        return damage;
    }

    public void setDamage(int damage){
        this.damage = damage;
    }

    public void addDamage(int value){
        this.damage += value;
    }

    public void multiplyDamage(double multiplier){
        this.damage = (int)(damage * multiplier);
    }

    public boolean isModifiedBy(Object source){
        return modifiers.contains(source);
    }

    public void markModified(Object source){
        modifiers.add(source);
    }
}