package status;

import GameATK.Entity;
import log.GameLog;

public class RegenStatus extends StatusEffect {

    public RegenStatus(int turns){
        super(turns);
        iconPath = "/Image/Status/regen.png";
    }

    @Override
    public void onApply(Entity target){
        GameLog.add("🌿 Regeneration started!");
    }

    @Override
    public void onTurnStart(Entity target){

        int healAmount =
            (int)Math.round(target.getMaxHp() * 0.10);

        target.heal(healAmount);

        GameLog.add("🌿 Regeneration heals "
            + healAmount + " HP");

    }

    @Override
    public void onTurnEnd(Entity target){

        super.onTurnEnd(target);

        if(isFinished()){
            GameLog.add("🌿 Regeneration faded.");
        }
    }
}