package org.poo.playingcards;

import java.util.ArrayList;

public class Miraj extends Cards{
    public Miraj() {
        super();
    }

    public Miraj(int mana, int health, int attackDamage,
                     String description, ArrayList<String> colors, String name) {
        super(mana, health, attackDamage, description, colors, name);
    }

    @Override
    public boolean useAbility(Cards card) {
        int aux = card.getHealth();
        card.setHealth(super.getHealth());
        super.setHealth(aux);
        super.setUsed(true);
        return false;
    }
}
