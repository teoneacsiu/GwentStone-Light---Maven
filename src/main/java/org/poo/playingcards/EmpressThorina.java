package org.poo.playingcards;

import org.poo.main.Field;

import java.util.ArrayList;

public class EmpressThorina extends Cards {
    public EmpressThorina() {
        super();
    }

    public EmpressThorina(int mana,
                     String description, ArrayList<String> colors, String name) {
        super(mana, 30, 0, description, colors, name);
    }

    @Override
    public boolean useAbility(Field field, int row) {
        int max = 0;
        int removeX = 0;
        for (int i = 4; i >= 0; i--) {
            if (field.getCard(row, i) != null)
                if (field.getCard(row, i).getHealth() > max) {
                    max = field.getCard(row, i).getHealth();
                    removeX = i;
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
