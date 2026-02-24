package items;

import GameATK.Entity;
import effects.ItemEffect;
import java.util.*;
import javax.swing.ImageIcon;

public abstract class AbstractItem implements Item {

    protected String name;
    protected boolean consumable;
    protected boolean armed = false;
    protected boolean expired = false;
    protected boolean visuallyUsed = false;
    protected boolean persistent = false;
    protected int cooldown = 0;
    protected int cooldownRemaining = 0;

    // ⭐ NEW
    protected boolean activated = false;
    protected ImageIcon icon;

    protected List<ItemEffect> effects = new ArrayList<>();

    public AbstractItem(String name, boolean consumable){
    this(name, consumable, false);
    }

    // constructor สำหรับ item ที่ต้องอยู่หลายเทิร์น
    public AbstractItem(String name,
                        boolean consumable,
                        boolean persistent){

        this.name = name;
        this.consumable = consumable;
        this.persistent = persistent;
    }

    // ======================
    // BASIC INFO
    // ======================

    public void setVisuallyUsed(boolean value){
        this.visuallyUsed = value;
    }

    public boolean isPersistent(){
        return persistent;
    }

    public boolean isVisuallyUsed(){
        return visuallyUsed;
    }

    public void setCooldown(int turns){
        this.cooldown = turns;
    }

    public boolean isOnCooldown(){
        return cooldownRemaining > 0;
    }

    public void startCooldown(){
        cooldownRemaining = cooldown;
        visuallyUsed = true; // ทำให้เป็นสีเทา
    }

    public void reduceCooldown(){
        if(cooldownRemaining > 0){
            cooldownRemaining--;

            if(cooldownRemaining == 0){
                visuallyUsed = false; // กลับมาใช้ได้
            }
        }
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public ImageIcon getIcon(){
        return icon;
    }

    @Override
    public boolean isConsumable(){
        return consumable;
    }

    // ======================
    // ARM SYSTEM
    // ======================

    @Override
    public void arm(){
        armed = true;
    }

    @Override
    public void deactivate(){
        armed = false;
        activated = false;
    }

    @Override
    public boolean isArmed(){
        return armed;
    }

    // ======================
    // ACTIVATION STATE
    // ======================

    @Override
    public boolean isActivated(){
        return activated;
    }

    public void activate(){
        activated = true;
        armed = true;

        // ⭐ NEW — แจ้ง effect ว่า item ถูกใช้แล้ว
        for(ItemEffect effect : effects){
            effect.onActivate();
        }
    }

    public void disarm(){
        armed = false;
    }

    public void expire(){
        expired = true;
    }

    @Override
    public boolean isExpired(){
        return expired;
    }

    // ======================
    // EFFECTS
    // ======================

    @Override
    public List<ItemEffect> getEffects(){
        return effects;
    }

    // ======================
    // HOOKS
    // ======================

    @Override
    public void onUse(Entity user, Entity target){}

    @Override
    public void onAcquire(Entity owner){}

    @Override
    public void onRemove(Entity owner){}
}