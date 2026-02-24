package GameATK;

import java.util.*;

public class ComboAnalyzer {

    public static ComboResult analyze(List<Integer> nums){

        int sum = nums.stream().mapToInt(i->i).sum();
        ComboResult result = new ComboResult(sum);

        // =====================
        // SIZE TAG
        // =====================
        if(nums.size()==2)
            result.addTag(ComboTag.TWO_NUMBERS);

        if(nums.size()==3)
            result.addTag(ComboTag.THREE_NUMBERS);

        // =====================
        // EVEN / ODD
        // =====================
        boolean allEven = nums.stream().allMatch(n->n%2==0);
        boolean hasOdd = nums.stream().anyMatch(n->n%2==1);

        if(allEven) result.addTag(ComboTag.ALL_EVEN);
        if(hasOdd) result.addTag(ComboTag.HAS_ODD);

        // =====================
        // DUPLICATE
        // =====================
        Set<Integer> set = new HashSet<>(nums);
        if(set.size() != nums.size())
            result.addTag(ComboTag.HAS_DUPLICATE);

        // =====================
        // SUM RANGE
        // =====================
        if(sum <= 9) result.addTag(ComboTag.SUM_LOW);
        else if(sum <= 15) result.addTag(ComboTag.SUM_MID);
        else result.addTag(ComboTag.SUM_HIGH);

        // =====================
        // HEAL RULE
        // =====================
        if(allEven && nums.size()==3){
            result.setHealPlayer(true);
        }

        // =====================
        // HIT LOGIC
        // =====================
        applyHitRule(result);

        return result;
    }

    private static void applyHitRule(ComboResult r){

        int dmg = r.getDamage();
        double roll = Math.random();

        if(dmg < 5){
            r.setHitBoss(false);
            return;
        }

        if(dmg <= 15){
            r.setHitBoss(true);
        }
        else if(dmg <= 25){
            r.setHitBoss(roll <= 0.5);
        }
        else{
            if(roll <= 0.25)
                r.setHitBoss(true);
            else if(roll <= 0.5)
                r.setHitSelf(true);
        }
    }
}