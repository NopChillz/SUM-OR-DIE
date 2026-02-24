package manager;

import GameATK.Entity;
import effects.DamageContext;
import effects.StatusInterceptor;
import java.util.*;
import status.FreezeStatus;
import status.StatusEffect;

public class StatusManager {

    private final List<StatusEffect> statuses = new ArrayList<>();
    protected String iconPath;//เพิ่มใหม่

    public void add(StatusEffect effect, Entity target) {

        // ⭐ กัน status ซ้ำ
        if(has(effect.getClass())){
            if(effect instanceof FreezeStatus){
                return;
            }
        }

        // =========================
        // ITEM INTERCEPTORS
        // =========================
        for(var item : target.getItemManager().getItems()){

            if(!item.isArmed())
                continue;

            for(var ie : item.getEffects()){

                StatusInterceptor interceptor = ie.getStatusInterceptor();

                if(interceptor == null)
                    continue;

                if(interceptor.intercept(target, effect))
                    return;

                interceptor.modify(effect);
            }
        }

        effect.onApply(target);
        statuses.add(effect);
    }
    public boolean has(Class<? extends StatusEffect> type){

        for(StatusEffect s : statuses){
            if(type.isInstance(s))
                return true;
        }

        return false;
    }

    public void remove(Class<? extends StatusEffect> type){//เพิ่มใหม่ 

        Iterator<StatusEffect> it = statuses.iterator();

        while(it.hasNext()){
            StatusEffect s = it.next();

            if(type.isInstance(s)){
                it.remove();
            }
        }
    }

    public boolean canAttack(){//เพิ่มใหม่

        for(StatusEffect s : statuses)
            if(s.blocksAttack())
                return false;

        return true;
    }

    public boolean blocksItem() {
        for(StatusEffect s : statuses){
            if(s.blocksItem()){
                return true;
            }
        }

        return false;
    }

    public boolean canUseItem(){//เพิ่มใหม่

        for(StatusEffect s : statuses)
            if(s.blocksItem())
                return false;

        return true;
    }

    public void reduceAllDurations(int amount){

        Iterator<StatusEffect> it = statuses.iterator();

        while(it.hasNext()){
            StatusEffect s = it.next();

            s.reduceTurns(amount);

            if(s.isFinished())
                it.remove();
        }
    }

    public void onAttack(effects.DamageContext ctx){

        for(StatusEffect s : statuses){
            s.onAttack(ctx);
        }
    }

    public void onTurnStart(Entity e) {
        statuses.forEach(s -> s.onTurnStart(e));
    }

    public void onTurnEnd(Entity e) {
        Iterator<StatusEffect> it = statuses.iterator();

        while(it.hasNext()) {
            StatusEffect s = it.next();
            s.onTurnEnd(e);

            if(s.isFinished())
                it.remove();
        }
    }

    public void onDamageTaken(Entity defender,Entity attacker,DamageContext ctx){

        for(StatusEffect status : statuses){
            status.onDamageTaken(defender, attacker, ctx);
        }
    }

    public String getIconPath(){//เพิ่มใหม่
        return iconPath;
    }
    public List<StatusEffect> getStatuses(){ //เพิ่มใหม่
        return Collections.unmodifiableList(statuses);
    }

}