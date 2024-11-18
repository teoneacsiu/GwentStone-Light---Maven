package org.poo.playingcards;

import org.poo.main.Field;

import java.util.ArrayList;

public class KingMudface extends Cards {
    public KingMudface() {
        super();
    }

    public KingMudface(int mana,
                     String description, ArrayList<String> colors, String name) {
        super(mana, 30, 0, description, colors, name);
    }

    @Override
    public boolean useAbility(Field field, int row) {
        for (int i = 0; i < 5; i++) {
            field.getCard(row, i).setHealth(
                    field.getCard(row, i).getHealth() + 1
            );
        }
        super.setUsed(true);
        return true;
    }
}
