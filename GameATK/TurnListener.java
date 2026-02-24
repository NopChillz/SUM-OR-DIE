package GameATK;

public interface TurnListener {

    void onPlayerTurn();

    void onBossTurn();

    void onBattleWin();

    void onBattleLose();
}