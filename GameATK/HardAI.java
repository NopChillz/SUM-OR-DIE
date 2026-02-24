package GameATK;

public class HardAI implements BossAI {

    @Override
    public int decideDamage(Boss self, Entity target) {

        if (self.getCurrentHp() < self.getMaxHp()/2) {
            System.out.println(self.getName()+" enters RAGE!");
            return 15;
        }

        return 10;
    }
}