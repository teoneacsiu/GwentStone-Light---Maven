package org.poo.playingcards;

import java.util.ArrayList;

public final class TheCursedOne extends Cards {
    public TheCursedOne() {
        super();
    }

    public TheCursedOne(final int mana, final int health, final int attackDamage,
                        final String description, final ArrayList<String> colors,
                        final String name) {
        super(mana, health, attackDamage, description, colors, name);
    }

    @Override
    public void useAbility(final Cards card) {
        if (card.getAttackDamage() == 0) {
            card.setHealth(0);
            return;
        }

        int aux = card.getHealth();
        card.setHealth(card.getAttackDamage());
        card.setAttackDamage(aux);
        setUsed(true);
    }
}
