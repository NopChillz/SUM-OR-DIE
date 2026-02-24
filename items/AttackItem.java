package items;

public abstract class AttackItem extends AbstractItem {

    protected int attackBonus = 0;

    public AttackItem(String name, boolean consumable){
        super(name, consumable);
    }

    public AttackItem(String name,
                       boolean consumable,
                       boolean persistent){
        super(name, consumable, persistent);
    }

}