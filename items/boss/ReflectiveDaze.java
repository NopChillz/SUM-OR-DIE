package items.boss;

import GameATK.Entity;
import effects.ItemEffect;
import items.AbstractItem;
import log.GameLog;

import java.util.Random;

public class ReflectiveDaze extends AbstractItem {

    public ReflectiveDaze() {
        super("Reflective Daze", false, true);
    }

    @Override
    public void onAcquire(Entity owner) {

        Random rand = new Random();

        effects.add(new ItemEffect() {

            @Override
            public void onTurnEnd(Entity self) {

                if(rand.nextDouble() < 0.3){
                    self.addStatus("STUN_PLAYER");
                    GameLog.add("✨ Reflective Daze stuns player!");
                }
            }
        });
    }
}