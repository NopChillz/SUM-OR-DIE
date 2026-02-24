package GameATK;

import java.util.Random;
import status.FreezeStatus;

public class BossBrain {

    private Boss boss;
    private Random rand = new Random();
    // ⭐ MEMORY AI (จำท่าล่าสุด)
    private BossIntentType lastIntent = null;

    public BossBrain(Boss boss){
        this.boss = boss;
    }

    // =====================================
    // WEIGHTED PICK SYSTEM
    // =====================================
    private BossIntentType pickWeighted(int attack,int freeze,int gravity,int heal,int defend) {

        int total = attack + freeze + gravity + heal + defend;
        int roll = rand.nextInt(total);

        if((roll -= attack) < 0) return BossIntentType.ATTACK;
        if((roll -= freeze) < 0) return BossIntentType.FREEZE_STRIKE;
        if((roll -= gravity) < 0) return BossIntentType.GRAVITY_SMASH;
        if((roll -= heal) < 0) return BossIntentType.HEAL;

        return BossIntentType.DEFEND;
    }

    // =====================================
    // MAIN DECISION
    // =====================================
    public BossIntent decide(Player player){

        int hp = boss.getHpPercent();
        int itemCount = player.getItemManager().getItems().size();

        boolean playerFrozen =
            player.getStatusManager().has(FreezeStatus.class);

        BossIntentType choice;

        // ===== PHASE 2 =====
        if(boss.getPhase() == 2){

            // ⭐ ถ้าผู้เล่น frozen แล้ว → ลด freeze weight
            if(playerFrozen)
                choice = pickWeighted(30,0,60,5,5);
            else
                choice = pickWeighted(20,15,55,5,5);

            return finalizeChoice(choice, player);
        }

        // ===== LOW HP =====
        if(hp < 30){
            choice = pickWeighted(10,0,70,10,10);
        }

        // ===== ITEM HOARD =====
        else if(itemCount >= 3){
            choice = playerFrozen
                ? pickWeighted(50,0,30,0,20)
                : pickWeighted(30,50,10,0,10);
        }

        // ===== NORMAL =====
        else{
            choice = playerFrozen
                ? pickWeighted(70,0,15,10,5)
                : pickWeighted(60,15,10,10,5);
        }

        return finalizeChoice(choice, player);
    }

    // =====================================
    // BUILD INTENT OBJECT
    // =====================================
    private BossIntent buildIntent(
            BossIntentType type,
            Player player){

        switch(type){

            case ATTACK:
                int dmg = boss.getAI()
                        .decideDamage(boss, player);
                return new BossIntent(type, dmg);

            case FREEZE_STRIKE:
                return new BossIntent(type, 14);

            case GRAVITY_SMASH:
                return new BossIntent(type, 22);

            case HEAL:
                return new BossIntent(type, 15);

            case DEFEND:
                return new BossIntent(type, 12);
        }

        return new BossIntent(BossIntentType.ATTACK, 5);
    }


    private BossIntent finalizeChoice(
            BossIntentType choice,
            Player player){

        // ⭐ MEMORY AI
        if(choice == lastIntent){
            choice = BossIntentType.ATTACK;
        }

        lastIntent = choice;
        return buildIntent(choice, player);
    }

}