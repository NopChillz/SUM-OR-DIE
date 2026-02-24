package quest;

import GameATK.Player;
import items.Item;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import log.GameLog;

public class QuestManager {

    private List<Quest> quests = new ArrayList<>();

    public void addQuest(Quest quest){
        quests.add(quest);
    }

    public List<Quest> getQuests(){
    return Collections.unmodifiableList(quests);
}
    
    public void checkQuests(List<Integer> numbers, Player player){

        for(Quest quest : quests){

            if(quest.tryComplete(numbers, player)){

                Item reward = quest.createReward();
                player.getItemManager().addItem(reward);

                GameLog.add("Quest Complete: " + quest.getName());
            }
        }
    }

    public List<Item> checkQuestsAndCollect(List<Integer> numbers, Player player){ //เพิ่มใหม่
        List<Item> rewards = new ArrayList<>();
        for(Quest q : quests){

            if(q.tryComplete(numbers, player)){

                Item reward = q.createReward();
                rewards.add(reward);

                GameLog.add("Quest Complete: " + reward.getName());
            }
        }

        return rewards;
    }
}