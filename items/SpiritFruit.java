package items;

import GameATK.Entity;
import javax.swing.ImageIcon;
import status.RegenStatus;

public class SpiritFruit extends AbstractItem {

    public SpiritFruit(){

        super("Spirit Fruit", true);

        icon = new ImageIcon(
            getClass().getResource("/Image/Items/SpiritFruit.png")
        );
    }

    @Override
    public void onUse(Entity user, Entity target){

        user.getStatusManager()
            .add(new RegenStatus(3), user);
    }
}