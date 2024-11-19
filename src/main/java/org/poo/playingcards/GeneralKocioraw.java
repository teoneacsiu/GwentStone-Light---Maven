package org.poo.playingcards;

import org.poo.main.Field;

import java.util.ArrayList;

public class GeneralKocioraw extends Cards {
    public GeneralKocioraw() {
        super();
    }

    public GeneralKocioraw(final int mana,
                           final String description, final ArrayList<String> colors,
                           final String name) {
        super(mana, Cards.HERO_HEALTH, 0, description, colors, name);
    }

    @Override
    public boolean useAbility(final Field field, final int row) {
        for (int i = 0; i < Field.TABLE_COLS; i++) {
            if (field.getCard(row, i) != null) {
                field.getCard(row, i).setAttackDamage(
                    field.getCard(row, i).getAttackDamage() + 1
                );
            }
        }
        super.setUsed(true);
        return true;
    }
}
