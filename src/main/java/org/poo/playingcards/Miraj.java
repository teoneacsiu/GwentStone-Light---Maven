package org.poo.playingcards;

import java.util.ArrayList;

public class Miraj extends Cards {
    public Miraj() {
        super();
    }

    public Miraj(final int mana, final int health, final int attackDamage,
                 final String description, final ArrayList<String> colors,
                 final String name) {
        super(mana, health, attackDamage, description, colors, name);
    }

    @Override
    public boolean useAbility(final Cards card) {
        int aux = card.getHealth();
        card.setHealth(super.getHealth());
        super.setHealth(aux);
        super.setUsed(true);
        return false;
    }
}
