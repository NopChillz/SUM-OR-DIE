package items.boss;

import GameATK.Entity;
import effects.ItemEffect;
import items.AbstractItem;
import log.GameLog;

public class DemonBloodGrail extends AbstractItem {

    public DemonBloodGrail() {
        super("Demon Blood Grail", false, true);
    }

    @Override
    public void onAcquire(Entity owner) {

        effects.add(new ItemEffect() {

            @Override
            public void onTurnStart(Entity self) {

                if(self.hasStatus("POISON")){
                    self.removeStatus("POISON");
                    self.heal(5);

                    GameLog.add("🩸 Demon Blood converts poison into healing!");
                }
            }
        });
    }
}