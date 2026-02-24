package GameATK;

import items.boss.FrostbiteAxe;

public class EasyBoss extends Boss {

    public EasyBoss() {
        super("Easy Boss", 100, new EasyAI());

        getItemManager().addItem(new FrostbiteAxe());
    }
}