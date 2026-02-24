package status;

import GameATK.Entity;
import log.GameLog;

public class ItemLockStatus extends StatusEffect {

    public ItemLockStatus(int turns){
        super(turns);
        iconPath = "/Image/Status/itemlock.png";
    }

    @Override
    public void onApply(Entity target){
        GameLog.add("🔒 Items are sealed!");
    }

    @Override
    public void onTurnStart(Entity target){
        GameLog.add("🔒 Cannot use items ("+remainingTurns+")");
    }

    @Override
    public void onTurnEnd(Entity target){
        super.onTurnEnd(target);

        if(isFinished()){
            GameLog.add("🔓 Item seal broken.");
        }
    }
}