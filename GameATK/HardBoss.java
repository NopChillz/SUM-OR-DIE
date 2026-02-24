package GameATK;

import items.boss.DemonBloodGrail;
import items.boss.FrostbiteAxe;
import items.boss.InfernalThornmail;
import items.boss.VoidSilence;
import items.boss.WraithCloak;
import log.GameLog;

public class HardBoss extends Boss {

    public HardBoss() {
        super("Hard Boss", 180, new HardAI());

        getItemManager().addItem(new FrostbiteAxe());
        getItemManager().addItem(new InfernalThornmail());
        getItemManager().addItem(new WraithCloak());
        getItemManager().addItem(new VoidSilence());
    }

    @Override
    protected void onPhaseTwoStart(){

        GameLog.add("🔥 Hard Boss enters PHASE 2!");

        // ได้โล่ทันที
        addShield(20);

        // เพิ่ม relic ใหม่ตอน phase 2
        getItemManager().addItem(new DemonBloodGrail());
    }
}