package org.poo.playingcards;

import java.util.ArrayList;

public class TheCursedOne extends Cards{
    public TheCursedOne(){
        super();
    }

    public TheCursedOne(int mana, int health, int attackDamage,
                     String description, ArrayList<String> colors, String name) {
        super(mana, health, attackDamage, description, colors, name);
    }

    @Override
    public boolean useAbility(Cards card) {
        if (card.getAttackDamage() == 0) {
            setHealth(0);
            return true;
        }

        int aux = card.getHealth();
        card.setHealth(super.getHealth());
        super.setHealth(aux);
        card.setAttackDamage(0);
        super.setUsed(true);
        return false;
    }
}
