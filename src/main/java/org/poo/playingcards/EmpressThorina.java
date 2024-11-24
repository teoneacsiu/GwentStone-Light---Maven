package org.poo.playingcards;

import org.poo.main.Field;

import java.util.ArrayList;

public final class EmpressThorina extends Cards {
    public EmpressThorina() {
        super();
    }

    public EmpressThorina(final int mana,
                          final String description, final ArrayList<String> colors,
                          final String name) {
        super(mana, Cards.HERO_HEALTH, 0, description, colors, name);
    }

    @Override
    public int useAbility(final Field field, final int row) {
        int max = -1;
        int removeY = -1;
        for (int i = Field.TABLE_COLS - 1; i >= 0; i--) {
            if (field.getCard(row, i) != null) {
                if (field.getCard(row, i).getHealth() > max) {
                    max = field.getCard(row, i).getHealth();
                    removeY = i;
                }
            }
        }
        if (max != -1) {
            super.setUsed(true);
            return removeY;
        }
        super.setUsed(true);
        return -1;
    }
}
