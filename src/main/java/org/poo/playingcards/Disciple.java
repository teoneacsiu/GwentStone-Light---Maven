package org.poo.playingcards;

import java.util.ArrayList;

public class Disciple extends Cards {
    public Disciple() {
        super();
    }

    public Disciple(int mana, int health, int attackDamage,
                    String description, ArrayList<String> colors, String name) {
        super(mana, health, attackDamage, description, colors, name);
    }

    @Override
    public boolean useAbility(Cards card) {
        card.setHealth(card.getHealth());
        super.setUsed(true);
        return false;
    }
}
