package GameATK;

public class Player extends Entity {

    public Player() {
        super("Player", 100);
    }

    private TurnManager turnManager;

    public void setTurnManager(TurnManager tm){
        this.turnManager = tm;
    }

    public TurnManager getTurnManager(){
        return turnManager;
    }

}