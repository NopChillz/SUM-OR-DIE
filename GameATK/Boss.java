package GameATK;

import log.GameLog;
import status.FreezeStatus;

public abstract class Boss extends Entity {

    protected BossAI ai;
    protected BossIntent currentIntent;
    protected BossBrain brain;
    private boolean intentLocked = false;//เพิ่มใหม่
    protected int phase = 1;//เพิ่มใหม่
    private boolean phase2Triggered = false;//เพิ่มใหม่

    public Boss(String name, int hp, BossAI ai) {
        super(name, hp);
        this.ai = ai;
        this.brain = new BossBrain(this);
    }
    
    public BossAI getAI(){
        return ai;
    }

    public void planIntent(Player player){ //เพิ่มใหม่

        // ⭐ ห้ามคิดใหม่ถ้ายัง lock
        if(intentLocked)
            return;

        if(brain == null || !isAlive())
            return;

        currentIntent = brain.decide(player);
        intentLocked = true;

        GameLog.add(getName() + " intends to " + currentIntent.getType());
    }

    // =========================
    // EXECUTION PHASE
    // =========================
    public int rollDamage(Player player){
        if(!isAlive())
            return 0;
            
        checkPhaseTransition();
        

        // safety fallback (should rarely happen)
        if(currentIntent == null && brain != null){
            planIntent(player);
        }

        return resolveIntent(player);
    }

    // =========================
    // INTENT → ACTION
    // =========================
    private int resolveIntent(Player player){ //เพิ่มใหม่

        int result = 0;

        switch(currentIntent.getType()){
            case ATTACK:
                result = currentIntent.getValue();
                break;

            case FREEZE_STRIKE:
            player.getStatusManager().add(new FreezeStatus(3), player);
            GameLog.add("❄ Player is frozen!");
            result = currentIntent.getValue();
            break;

            case GRAVITY_SMASH:
            player.addShield(-5);
            GameLog.add("🌑 Gravity crushes the player!");
            result = currentIntent.getValue();
            break;

            case HEAL:
                this.heal(currentIntent.getValue());
                break;

            case DEFEND:
                this.addShield(currentIntent.getValue());
                break;
        }

        intentLocked = false; // ⭐ unlock after execution
        return result;
    }

    protected void checkPhaseTransition(){//เพิ่มใหม่

        // Phase 2 trigger
        if(!phase2Triggered && getHpPercent() <= 50){

            phase = 2;
            phase2Triggered = true;

            onPhaseTwoStart();
        }
    }

    protected void onPhaseTwoStart(){//เพิ่มใหม่
        // default: do nothing
    }

    // =========================
    // GETTERS
    // =========================
    public BossIntent getIntent(){ //เพิ่มใหม่
        return currentIntent;
    }

    public int getPhase(){
        return phase;
    }

    public void setAI(BossAI ai) {
        this.ai = ai;
    }
}