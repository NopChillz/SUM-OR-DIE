package effects;

import GameATK.Entity;

public interface ItemEffect {

    default void onApply(Entity owner) {}

    default void onRemove(Entity owner) {}

    default void onActivate(){}

    default void onAttack(Entity attacker, Entity target, DamageContext ctx) {}

    default void onTurnStart(Entity owner) {}

    default void onTurnEnd(Entity owner) {}

    default void onDeath(Entity owner) {}

    default void onDamageTaken(Entity defender,Entity attacker,DamageContext ctx){}

    default StatusInterceptor getStatusInterceptor(){
        return null;
    }
}
