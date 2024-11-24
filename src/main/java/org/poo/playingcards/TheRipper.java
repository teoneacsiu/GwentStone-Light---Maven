package org.poo.playingcards;

import java.util.ArrayList;

public final class TheRipper extends Cards {
    public TheRipper() {
        super();
    }

    public TheRipper(final int mana, final int health, final int attackDamage,
                     final String description, final ArrayList<String> colors,
                     final String name) {
        super(mana, health, attackDamage, description, colors, name);
    }

    @Override
    public void useAbility(final Cards card) {
        if (card.getAttackDamage() < 2) {
            card.setAttackDamage(0);
        } else {
            card.setAttackDamage(card.getAttackDamage() - 2);
        }
        setUsed(true);
    }
}
