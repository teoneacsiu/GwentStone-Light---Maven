package org.poo.playingcards;

import java.util.ArrayList;

/**
 * Provides a specific minion that has a special ability.
 */
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
    /**
     * Performing the specific ability of the minion Disciple.
     */
    public void useAbility(final Cards card) {
        card.setHealth(card.getHealth() + 2);
        setUsed(true);
    }
}
