package org.poo.playingcards;

import java.util.ArrayList;

public final class Disciple extends Cards {
    public Disciple() {
        super();
    }

    public Disciple(final int mana, final int health, final int attackDamage,
                    final String description, final ArrayList<String> colors,
                    final String name) {
        super(mana, health, attackDamage, description, colors, name);
    }

    @Override
    public boolean useAbility(final Cards card) {
        card.setHealth(card.getHealth());
        super.setUsed(true);
        return false;
    }
}
