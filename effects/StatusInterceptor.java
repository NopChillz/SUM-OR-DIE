package effects;

import GameATK.Entity;
import status.StatusEffect;

public interface StatusInterceptor {

    /**
     * return true = BLOCK status
     * return false = allow
     */
    boolean intercept(Entity target, StatusEffect effect);

    /**
     * modify duration before apply
     */
    void modify(StatusEffect effect);
}