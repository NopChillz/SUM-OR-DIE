package quest;

import GameATK.Player;
import items.Item;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

public class Quest {

        private final String name;
        private final String descriptionTH;
        private final String requirementTH;
        private boolean repeatable;
        

        // เงื่อนไขสำเร็จ
        private final BiPredicate<List<Integer>, Player> condition;

        // reward factory
        private final Supplier<Item> rewardFactory;

        private boolean completed = false;

        public Quest(
                String name,
                String descriptionTH,
                String requirementTH,
                BiPredicate<List<Integer>, Player> condition,
                Supplier<Item> rewardFactory,
                boolean repeatable
        ){
            this.name = name;
            this.descriptionTH = descriptionTH;
            this.requirementTH = requirementTH;
            this.condition = condition;
            this.rewardFactory = rewardFactory;
            this.repeatable = repeatable;
        }

        // =========================
        // CHECK QUEST
        // =========================
        public boolean tryComplete(List<Integer> numbers, Player player){

            if(completed && !repeatable)
                return false;

            if(condition.test(numbers, player)){
                completed = true;

                if(repeatable)
                    completed = false;

                return true;
            }

            return false;
        }

        // =========================
        // REWARD
        // =========================
        public Item createReward(){
            return rewardFactory.get();
        }

        // =========================
        // GETTERS
        // =========================
        public String getName(){
            return name;
        }

        public boolean isCompleted(){
            return completed;
        }

        // =========================
        // UI TEXT
        // =========================
        public String getFullText(){
            return """
        %s
        %s

        เงื่อนไข:
        %s
        """.formatted(name,descriptionTH,requirementTH);
        }
    }