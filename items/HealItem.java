package items;

public abstract class HealItem extends AbstractItem {

    protected double healAmount = 0;

    public HealItem(String name, boolean consumable){
        super(name, consumable);
    }

    public HealItem(String name,
                       boolean consumable,
                       boolean persistent){
        super(name, consumable, persistent);
    }
}