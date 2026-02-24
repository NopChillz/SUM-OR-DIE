package items;

public abstract class DefenseItem extends AbstractItem {

    protected double damageReduction = 0;

    public DefenseItem(String name, boolean consumable){
        super(name, consumable);
    }

    public DefenseItem(String name,
                       boolean consumable,
                       boolean persistent){
        super(name, consumable, persistent);
    }
}