package quest;

import GameATK.Player;
import java.util.List;

@FunctionalInterface
public interface QuestCondition {

    boolean check(List<Integer> numbers, Player player);
    
}