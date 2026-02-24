package status;

import GameATK.Entity;
import effects.DamageContext;

public abstract class StatusEffect {

    protected int remainingTurns;
    protected String iconPath;//เพิ่มใหม่
    
    public String getIconPath(){
        return iconPath;
    }
    public StatusEffect(int turns) {
        this.remainingTurns = turns;
    }

    public int getPriority(){//เพิ่มใหม่
        return 0;
    }

    public int getRemainingTurns() {//เพิ่มใหม่
        return remainingTurns;
    }

    public void reduceTurns(int amount) {//เพิ่มใหม่
        remainingTurns = Math.max(0, remainingTurns - amount);
    }


    public boolean blocksAttack(){//เพิ่มใหม่
        return false;
    }

    public boolean blocksItem(){//เพิ่มใหม่
        return false;
    }

    public void onApply(Entity target) {}

    public void onAttack(DamageContext ctx){
        // default: do nothing
    }

    public void onDamageTaken(Entity defender,Entity attacker,DamageContext ctx){}

    public void onTurnStart(Entity target) {}

    public void onTurnEnd(Entity target) {
        remainingTurns--;
    }

    public boolean isFinished() {
        return remainingTurns <= 0;
    }
}