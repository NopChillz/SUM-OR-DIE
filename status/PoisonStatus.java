package status;

import GameATK.Entity;
import log.GameLog;

public class PoisonStatus extends StatusEffect {

    private final double percent;

    public PoisonStatus(int turns, double percent) {
        super(turns);
        this.percent = percent;
        iconPath = "/Image/Status/poison.png";
    }

    @Override
    public void onTurnStart(Entity target) {

        int dmg = (int)(target.getMaxHp() * percent);
        System.out.println("POISON TRIGGERED");
        target.takeDamage(dmg);
        GameLog.add("Poison tick!");
    }
    
    
}