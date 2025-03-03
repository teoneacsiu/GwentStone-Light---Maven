package org.poo.playingcards;

import org.poo.main.Field;

import java.util.ArrayList;

public final class KingMudface extends Cards {
    public KingMudface() {
        super();
    }

    public KingMudface(final int mana,
                       final String description, final ArrayList<String> colors,
                       final String name) {
        super(mana, Cards.HERO_HEALTH, 0, description, colors, name);
    }

    @Override
    public int useAbility(final Field field, final int row) {
        for (int i = 0; i < Field.TABLE_COLS; i++) {
            if (field.getCard(row, i) != null) {
                field.getCard(row, i).setHealth(
                        field.getCard(row, i).getHealth() + 1
                );
            }
        }
        super.setUsed(true);
        return -1;
    }
}
