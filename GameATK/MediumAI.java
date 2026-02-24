package GameATK;

import java.util.Random;

public class MediumAI implements BossAI {

    private Random rand = new Random();

    @Override
    public int decideDamage(Boss self, Entity target) {

        if (rand.nextBoolean()) {
            System.out.println(self.getName()+" strikes hard!");
            return 8;
        } else {
            self.heal(5);
            System.out.println(self.getName()+" heals!");
            return 0; // ไม่โจมตี
        }
    }
}