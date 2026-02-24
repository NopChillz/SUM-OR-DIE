package items;

public abstract class BuffItem extends AbstractItem {

    public BuffItem(String name, boolean consumable) {
        super(name, consumable);
    }

    public BuffItem(String name,
                       boolean consumable,
                       boolean persistent){
        super(name, consumable, persistent);
    }
}