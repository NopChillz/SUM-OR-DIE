package GameATK;

import items.boss.EarthshakerHammer;
import items.boss.FrostbiteAxe;

public class MediumBoss extends Boss {

    public MediumBoss() {
        super("Medium Boss", 120, new MediumAI());

        getItemManager().addItem(new FrostbiteAxe());
        getItemManager().addItem(new EarthshakerHammer());
    }
}