package GameATK;

import java.util.HashSet;
import java.util.Set;

public class ComboResult {

    private int damage;

    private boolean hitBoss = true;
    private boolean hitSelf = false;
    private boolean healPlayer = false;

    private Set<ComboTag> tags = new HashSet<>();

    public ComboResult(int damage){
        this.damage = damage;
    }

    public int getDamage() { return damage; }

    public boolean isHitBoss() { return hitBoss; }
    public void setHitBoss(boolean v){ hitBoss = v; }

    public boolean isHitSelf() { return hitSelf; }
    public void setHitSelf(boolean v){ hitSelf = v; }

    public boolean isHealPlayer() { return healPlayer; }
    public void setHealPlayer(boolean v){ healPlayer = v; }

    public Set<ComboTag> getTags(){ return tags; }

    public void addTag(ComboTag tag){
        tags.add(tag);
    }
}