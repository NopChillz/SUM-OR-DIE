package manager;

import GameATK.Entity;
import effects.DamageContext;
import effects.ItemEffect;
import items.AbstractItem;
import items.Item;
import items.SteadfastHelm;
import java.util.*;
import log.GameLog;

public class ItemManager {

    private final List<Item> items = new ArrayList<>();
    private List<Item> pendingRemove = new ArrayList<>();
    private final Entity owner;

    public ItemManager(Entity owner) {
        this.owner = owner;
    }

    public void addItem(Item item) {
        item.onAcquire(owner);
        items.add(item);
    }

    public void onAttack(DamageContext ctx){

        Entity attacker = ctx.getAttacker();
        Entity target   = ctx.getTarget();

        for(Item item : items){

            if(!item.isArmed())
                continue;

            for(ItemEffect effect : item.getEffects()){
                effect.onAttack(attacker, target, ctx);
            }
        }

        // ⭐ REMOVE AFTER LOOP (SAFE)
        if(!pendingRemove.isEmpty()){

            for(Item item : pendingRemove){
                item.onRemove(owner);
                items.remove(item);

                GameLog.add(item.getName() + " has vanished.");
            }

            pendingRemove.clear();
        }
    }

    public void onDamageTaken(DamageContext ctx){

        Entity defender = ctx.getTarget();
        Entity attacker = ctx.getAttacker();

        for(Item item : items){

            if(!item.isArmed())
                continue;

            for(ItemEffect effect : item.getEffects()){
                effect.onDamageTaken(defender, attacker, ctx);
            }
        }

        // ⭐⭐⭐ ADD THIS BLOCK ⭐⭐⭐
        if(!pendingRemove.isEmpty()){

            for(Item item : pendingRemove){
                item.onRemove(owner);
                items.remove(item);

                GameLog.add(item.getName() + " has vanished.");
            }

            pendingRemove.clear();
        }
    }

    public void onTurnStart() {

        // ⭐ ลด cooldown ก่อน
        for(Item item : items){
            if(item instanceof AbstractItem ai){
                ai.reduceCooldown();
            }
        }

        // ⭐ แล้วค่อย trigger effects
        items.forEach(i ->
            i.getEffects().forEach(e -> e.onTurnStart(owner)));
    }

    public void onTurnEnd() {
        items.forEach(i ->
            i.getEffects().forEach(e -> e.onTurnEnd(owner)));

        removeExpired();
    }

    public void useItem(Item item, Entity target){

        if(item == null) return;

        StatusManager sm = owner.getStatusManager();

        // ❄ STATUS BLOCK
        if(sm.blocksItem()){

            // 🪖 Steadfast Helm = exception
            if(!(item instanceof SteadfastHelm)){
                GameLog.add("❄ Cannot use items while frozen!");
                return;
            }
        }

        // ⭐ cooldown check
        if(item instanceof AbstractItem ai && ai.isOnCooldown()){
            GameLog.add(item.getName() + " is on cooldown!");
            return;
        }

        // ⭐ USE ITEM
        item.activate();
        item.onUse(owner, target);

        // ⭐ consume
        if(item.isConsumable()
            && item instanceof AbstractItem ai
            && !ai.isPersistent()){

            ai.expire();
        }
    }
        
    public void expireItemByEffect(ItemEffect effect){

        for(Item item : items){

            if(item.getEffects().contains(effect)){
                pendingRemove.add(item);
                return;
            }
        }
    }

    public void removeItem(Item item){
        items.remove(item);
    }
    
    private void removeExpired() {
        Iterator<Item> it = items.iterator();

        while(it.hasNext()) {
            Item item = it.next();

            if(item.isExpired()) {
                item.onRemove(owner); // ⭐ สำคัญ
                it.remove();
            }
        }
    }

    public java.util.List<Item> getItems(){
    return items;
    }
}