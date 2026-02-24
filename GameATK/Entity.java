package GameATK;

import effects.DamageContext;
import effects.ItemEffect;
import items.Item;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import log.GameLog;
import manager.ItemManager;
import manager.StatusManager;


public abstract class Entity {


    private ItemManager itemManager = new ItemManager(this);
    private StatusManager statusManager = new StatusManager();
    private double statusResistance = 0;
    private Runnable statusListener; //เพิ่มใหม่

    public ItemManager getItemManager() {
        return itemManager;
    }

    public StatusManager getStatusManager() {
        return statusManager;
    }
    /* =========================
       IDENTITY
       ========================= */
    protected String name;

    /* =========================
       HEALTH SYSTEM
       ========================= */
    protected int maxHp;
    protected int currentHp;

    /* =========================
       FUTURE EXTENSIONS
       (prepared but lightweight)
       ========================= */

    // temporary shield HP (future mechanic)
    protected int shieldHp = 0;

    // inventory system
    protected List<Item> inventory = new ArrayList<>();
    private List<ItemEffect> activeEffects = new ArrayList<>();

    // status effects placeholder (future system)
    protected List<String> statusEffects = new ArrayList<>();


    /* =========================
       CONSTRUCTOR
       ========================= */
    public Entity(String name, int maxHp) {
        this.name = name;
        this.maxHp = Math.max(1, maxHp);
        this.currentHp = this.maxHp;
    }


    /* =========================
       DAMAGE SYSTEM
       ========================= */
    public void takeDamage(int damage) {

        if (damage <= 0 || isDead()) return;

        int remainingDamage = damage;

        // shield absorbs damage first
        if (shieldHp > 0) {
            int absorbed = Math.min(shieldHp, remainingDamage);
            shieldHp -= absorbed;
            remainingDamage -= absorbed;
        }

        currentHp -= remainingDamage;

        if (currentHp < 0)
            currentHp = 0;

        GameLog.add(getName() + " takes " + damage + " damage!");
    }


    /* =========================
       HEAL SYSTEM
       ========================= */
    public void heal(int amount) {

        if (amount <= 0 || isDead()) return;

        currentHp += amount;

        if (currentHp > maxHp)
            currentHp = maxHp;
    }


    /* =========================
       SHIELD SYSTEM (future)
       ========================= */
    public void addShield(int amount) {
        if (amount > 0)
            shieldHp += amount;
    }

    public void addStatusResistance(double v){
        statusResistance += v;
    }

    public void setStatusListener(Runnable r){ //เพิ่มใหม่
        this.statusListener = r;
    }

    public void notifyStatusChanged(){ //เพิ่มใหม่
        if(statusListener != null)
            statusListener.run();
    }

    public double getStatusResistance(){
        return statusResistance;
    }

    public void clearStatusResistance(){
        statusResistance = 0;
    }


    /* =========================
       LIFE STATE
       ========================= */
    public boolean isDead() {
        return currentHp <= 0;
    }

    public boolean isAlive() {
        return !isDead();
    }


    /* =========================
       INVENTORY SYSTEM
       ========================= */
    public void addItem(Item item) {
        if (item != null)
            inventory.add(item);
    }

    public void removeItem(Item item) {
        inventory.remove(item);
    }

    // return read-only list (protect encapsulation)
    public List<Item> getInventory() {
        return Collections.unmodifiableList(inventory);
    }


    /* =========================
       STATUS EFFECT SYSTEM (placeholder)
       ========================= */
    public void addStatus(String status) {
        statusEffects.add(status);
    }

    public boolean hasStatus(String status) {
        return statusEffects.contains(status);
    }

    public void removeStatus(String status) {
        statusEffects.remove(status);
    }

    public void addEffect(ItemEffect effect){
    activeEffects.add(effect);
}

    public void applyEffects(DamageContext ctx){

        Iterator<ItemEffect> it = activeEffects.iterator();

        while(it.hasNext()){
            ItemEffect effect = it.next();

            effect.onAttack(ctx.getAttacker(), ctx.getTarget(), ctx);

            // auto remove buff ที่หมดอายุ
            if(effect instanceof effects.DamageBuffEffect buff){
                if(buff.isExpired()){
                    it.remove();
                }
            }
        }
    }

    /* =========================
       GETTERS
       ========================= */
    public String getName() {
        return name;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getHpPercent(){

        if(maxHp == 0) return 0;

        return (int)((currentHp * 100.0) / maxHp);
    }

    public int getShieldHp() {
        return shieldHp;
    }
}