package org.poo.playingcards;

import org.poo.main.Field;

import java.util.ArrayList;

public class GeneralKocioraw extends Cards {
    public GeneralKocioraw() {
        super();
    }

    public GeneralKocioraw(int mana,
                     String description, ArrayList<String> colors, String name) {
        super(mana, 30, 0, description, colors, name);
    }

    @Override
    public boolean useAbility(Field field, int row) {
        for (int i = 0; i < 5; i++) {
            if (field.getCard(row, i) != null) {
            field.getCard(row, i).setAttackDamage(
                    field.getCard(row, i).getAttackDamage() + 1
            );}
        }
        super.setUsed(true);
        return true;
    }
}