package items;

import effects.TitaniumPlateEffect;
import javax.swing.ImageIcon;

public class TitaniumPlate extends DefenseItem {

    public TitaniumPlate(){

        super("Titanium Plate", true, true);

        icon = new ImageIcon(
            getClass().getResource("/Image/Items/TitaniumPlate.png")
        );

        effects.add(new TitaniumPlateEffect());
    }
}