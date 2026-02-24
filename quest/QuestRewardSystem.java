package quest;

import GameATK.Player;
import items.Item;
import java.util.*;
import manager.ItemManager;

public class QuestRewardSystem {

    // =============================
    // INNER CLASS
    // =============================
    private static class RewardEntry {

        QuestCondition condition;
        Item reward;

        RewardEntry(QuestCondition c, Item r) {
            this.condition = c;
            this.reward = r;
        }
    }

    // =============================
    // DATA
    // =============================
    private final List<RewardEntry> rewards = new ArrayList<>();


    // =============================
    // REGISTER REWARD
    // =============================
    public void register(QuestCondition condition, Item item) {
        rewards.add(new RewardEntry(condition, item));
    }


    // =============================
    // CHECK REWARD
    // =============================
    public void checkRewards(List<Integer> selected, ItemManager inventory, Player player) {
        for (RewardEntry entry : rewards) {
            if (entry.condition.check(selected, player)) {
                inventory.addItem(entry.reward);
                System.out.println("Reward granted: " + entry.reward.getName());
            }
        }
    }
}