package GameATK;

public class BossIntent {

    private BossIntentType type;
    private int value;

    public BossIntent(BossIntentType type, int value){
        this.type = type;
        this.value = value;
    }

    public BossIntentType getType(){ return type; }
    public int getValue(){ return value; }
}