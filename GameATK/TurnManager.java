package GameATK;

import effects.DamageContext;
import javax.swing.Timer;
import log.GameLog;

public class TurnManager {

    private Player player;
    private Boss boss;
    private TurnState state = TurnState.PLAYER_TURN;
    private static final int BOSS_DELAY = 700;
    private TurnListener listener;
    private boolean extraPlayerTurn = false;

    public TurnManager(Player player, Boss boss) {
        this.player = player;
        this.boss = boss;

        // ⭐ เริ่มเกม = player turn start
        startPlayerTurn();
    }

    // =========================
    // LISTENER
    // =========================
    public void setListener(TurnListener listener) {
        this.listener = listener;
    }

    // =========================
    // PLAYER TURN START
    // =========================
    private void startPlayerTurn() {

        state = TurnState.PLAYER_TURN;

        // ⭐ safety reset
        extraPlayerTurn = false;
        
        boss.planIntent(player);

        player.getItemManager().onTurnStart();
        player.getStatusManager().onTurnStart(player);

        if (listener != null)
            listener.onPlayerTurn();
    }

    public void grantExtraTurn(){
        extraPlayerTurn = true;
    }

    // =========================
    // PLAYER ATTACK
    // =========================
    public void playerAttack(int damage) {

        if(player.getStatusManager().has(status.StunStatus.class)){
            GameLog.add("💫 Player is stunned!");
            endPlayerTurn();
            return;
        }

        if (state != TurnState.PLAYER_TURN)
            return;

        state = TurnState.BUSY;

        DamageContext ctx = new DamageContext(player, boss, damage);

        player.applyEffects(ctx);
        // item modify damage
        player.getItemManager().onAttack(ctx);

        // status modify damage
        player.getStatusManager().onAttack(ctx);

        boss.takeDamage(ctx.getDamage());

        // ===== CHECK WIN =====
        if (boss.isDead()) {
            state = TurnState.WIN;

            if (listener != null)
                listener.onBattleWin();

            return;
        }

        GameLog.add("Player attacks for " + ctx.getDamage());

        endPlayerTurn();
    }

    public void bossAttack(int damage) {

        DamageContext ctx = new DamageContext(boss, player, damage);

        // ⭐ ITEM DEFENSE EFFECT
        player.getItemManager().onDamageTaken(ctx);

        // ⭐ STATUS DEFENSE
        player.getStatusManager().onDamageTaken(player, boss, ctx);

        player.takeDamage(ctx.getDamage());
    }

    public void endPlayerTurn() {

        if (state != TurnState.PLAYER_TURN &&
                state != TurnState.BUSY)
            return;

        state = TurnState.BUSY;

        // ⭐ PLAYER TURN END EFFECTS
        player.getItemManager().onTurnEnd();

        player.clearStatusResistance();
                
        startBossTurn();
    }

    // =========================
    // BOSS TURN
    // =========================
    private void startBossTurn() {

        // ⭐⭐⭐ TIME SAND FINAL INTERCEPT ⭐⭐⭐
        if(extraPlayerTurn){

            extraPlayerTurn = false;

            GameLog.add("⏳ Time Sand rewinds time!");

            startPlayerTurn();
            return;
        }

        state = TurnState.BOSS_TURN;

        GameLog.add("---- Boss Turn ----");

        boss.getStatusManager().onTurnStart(boss); // ✅ poison now guaranteed

        Timer timer = new Timer(BOSS_DELAY, e -> {

            if (listener != null)
                listener.onBossTurn();

            int damage = boss.rollDamage(player);

            if (damage > 0) {
                bossAttack(damage);
            }
            
            if (player.isDead()) {
                state = TurnState.LOSE;

                if (listener != null)
                    listener.onBattleLose();

                return;
            }

            player.getStatusManager().onTurnEnd(player);
            boss.getStatusManager().onTurnEnd(boss);

            startPlayerTurn();
        });

        timer.setRepeats(false);
        timer.start();
    }

    public void playerTurnFinished() {

        if (state != TurnState.PLAYER_TURN)
            return;

        state = TurnState.BOSS_TURN;

        if (listener != null)
            listener.onBossTurn();

        startBossTurn(); // เรียก AI
    }

    // =========================
    // GETTERS
    // =========================
    public TurnState getState() {
        return state;
    }

    public Boss getBoss() {
        return boss;
    }

    public Player getPlayer() {
        return player;
    }
}