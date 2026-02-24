package GameATK;

public class EasyAI implements BossAI {

    @Override
    public int decideDamage(Boss boss, Entity target) {

        System.out.println("BOSS ATTACK!");
        return 5;
    }
}