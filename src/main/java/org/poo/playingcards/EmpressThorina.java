package org.poo.playingcards;

import org.poo.main.Field;

import java.util.ArrayList;

public class EmpressThorina extends Cards {
    public EmpressThorina() {
        super();
    }

    public EmpressThorina(final int mana,
                          final String description, final ArrayList<String> colors,
                          final String name) {
        super(mana, Cards.HERO_HEALTH, 0, description, colors, name);
    }

    @Override
    public boolean useAbility(final Field field, final int row) {
        int max = 0;
        int removeX = 0;
        for (int i = Field.TABLE_COLS - 1; i >= 0; i--) {
            if (field.getCard(row, i) != null) {
                if (field.getCard(row, i).getHealth() > max) {
                    max = field.getCard(row, i).getHealth();
                    removeX = i;
                }
            }
        }
        if (max != 0) {
            field.removeCard(removeX, row);
            super.setUsed(true);
            return true;
        }
        super.setUsed(true);
        return false;
    }
}
