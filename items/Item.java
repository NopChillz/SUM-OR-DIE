package items;

import GameATK.Entity;
import effects.ItemEffect;
import java.util.List;
import javax.swing.ImageIcon;

public interface Item {
    boolean isArmed();

    void arm();
    
    void disarm();
    String getName();

    ImageIcon getIcon();

    boolean isConsumable();

    void onUse(Entity user, Entity target);

    void onAcquire(Entity owner);

    void onRemove(Entity owner);

    List<ItemEffect> getEffects();

    boolean isExpired();

    // ⭐ NEW : activation system
    boolean isActivated();

    void activate();

    void deactivate();
}