package effects;

import GameATK.Entity;
import java.util.Random;
import log.GameLog;
import status.StatusEffect;

public class SteadfastHelmEffect implements ItemEffect, StatusInterceptor {

        private final Random rand = new Random();

        @Override
        public StatusInterceptor getStatusInterceptor(){
            return this;
        }

        // ⭐ 15% block
        @Override
        public boolean intercept(Entity target, StatusEffect effect){

            if(rand.nextDouble() <= 0.15){
                GameLog.add("🪖 Steadfast Helm resisted a status!");
                return true;
            }

            return false;
        }

        // ⭐ duration -1
        @Override
        public void modify(StatusEffect effect){

            if(effect.getRemainingTurns() > 1){effect.reduceTurns(1);
                GameLog.add("🪖 Helm reduced status duration!");
            }
        }

        @Override
        public void onActivate(){
            GameLog.add("🪖 Steadfast Helm activated!");
        }

    }