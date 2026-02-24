package status;

import GameATK.Entity;
import log.GameLog;

public class StunStatus extends StatusEffect {

    public StunStatus(int turns){
        super(turns);
        iconPath = "/Image/Status/stun.png";
    }

    @Override
    public void onApply(Entity target){
        GameLog.add("💫 Stunned!");
    }
}