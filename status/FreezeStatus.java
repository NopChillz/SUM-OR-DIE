package status;

import GameATK.Entity;
import log.GameLog;

public class FreezeStatus extends StatusEffect {

    public FreezeStatus(int turns){
        super(turns);
        iconPath = "/Image/Status/freeze.png";
    }

    @Override
    public int getPriority(){
        return 100;
    }

    @Override
    public void onTurnStart(Entity target){
        GameLog.add("❄ Freeze remains (" + remainingTurns + ")");
    }   

    @Override
    public boolean blocksAttack(){
        return false; // ⭐ Frozen ยังตีได้
    }

    @Override
    public boolean blocksItem(){
        return true; // ⭐ แต่ใช้ item ไม่ได้
    }

    @Override
    public void onTurnEnd(Entity target){
        super.onTurnEnd(target);

        if(isFinished()){
            GameLog.add("❄ Freeze has worn off.");
        }
    }
}