package org.poo.playingcards;

import java.util.ArrayList;

public class TheRipper extends Cards{
    public TheRipper() {
        super();
    }

    public TheRipper(int mana, int health, int attackDamage,
                     String description, ArrayList<String> colors, String name) {
        super(mana, health, attackDamage, description, colors, name);
    }

    @Override
    public boolean useAbility(Cards card) {
        if (card.getAttackDamage() < 2)
            card.setAttackDamage(0);
        else
            card.setAttackDamage(card.getAttackDamage() - 2);
        super.setUsed(true);
        return false;
    }
}
