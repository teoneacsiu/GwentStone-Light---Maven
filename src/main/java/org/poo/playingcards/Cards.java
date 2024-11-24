package org.poo.playingcards;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.poo.main.Field;

import java.util.ArrayList;

@Setter
@Getter
/**
 * Class Cards provides functionality for a generic card.
 */
public class Cards {
    public static final int HERO_HEALTH = 30;
    private static final ArrayList<String> FRONT_ROW_MINIONS = new ArrayList<>();
    static {
        FRONT_ROW_MINIONS.add("Goliath");
        FRONT_ROW_MINIONS.add("Warden");
        FRONT_ROW_MINIONS.add("The Ripper");
        FRONT_ROW_MINIONS.add("Miraj");
    }

    private static final ArrayList<String> BACK_ROW_MINIONS = new ArrayList<>();
    static {
        BACK_ROW_MINIONS.add("Sentinel");
        BACK_ROW_MINIONS.add("Berserker");
        BACK_ROW_MINIONS.add("The Cursed One");
        BACK_ROW_MINIONS.add("Disciple");
    }

    private int mana;
    private int health;
    private int attackDamage;
    private String description;
    private ArrayList<String> colors;
    private String name;
    private boolean used;
    private boolean frozen;

    public Cards() {
        mana = 0;
        health = 0;
        attackDamage = 0;
        description = "";
        name = "";
        colors = new ArrayList<>();
        used = false;
        frozen = false;
    }

    public Cards(final int mana, final int health, final int attackDamage,
                 final String description, final ArrayList<String> colors, final String name) {
        this.mana = mana;
        this.health = health;
        this.attackDamage = attackDamage;
        this.description = description;
        this.name = name;
        this.colors = colors;
        used = false;
        frozen = false;
    }

     /**
      *  Method getCardRow.
      * @return returns the row that the card needs to be placed.
      */
    public int getCardRow() {
        int row = 0;
        if (FRONT_ROW_MINIONS.contains(name)) {
            row = 1;
        }
        if (BACK_ROW_MINIONS.contains(name)) {
            row = 2;
        }
        return row;
    }

    /**
     *
     * @return if the minion is dead
     */
    public boolean isDead() {
        return health == 0;
    }

    /**
     * Modify the health of the attacked card after is attacked.
     * @param defender the card that is attacked.
     */
    public void attack(final Cards defender) {
        used = true;
        if (defender.getHealth() < attackDamage) {
            defender.setHealth(0);
        } else {
            defender.setHealth(defender.getHealth() - attackDamage);
        }
    }

    /**
     * Performs the ability of the minion.
     * @param card provides the card that is affected.
     */
    public void useAbility(final Cards card) {

    }

    /**
     * Performs the ability of the card.
     * @param field the whole field of the game.
     * @param row the row that is affected by the ability.
     * @return the col of the card that needs to be removed.
     */
    public int useAbility(final Field field, final int row) {
        return -1;
    }

    /**
     * Add to output the card
     * @return the card information in output form.
     */
    public ObjectNode printCard() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode cardNode = mapper.createObjectNode();
        cardNode.put("mana", mana);
        cardNode.put("attackDamage", attackDamage);
        cardNode.put("health", health);
        cardNode.put("description", description);

        // Create a new mapper for colors
        ObjectMapper colorMapper = new ObjectMapper();
        ArrayNode colorsArray = colorMapper.createArrayNode();
        for (String color : colors) {
            colorsArray.add(color);
        }

        cardNode.set("colors", colorsArray);

        cardNode.put("name", name);

        return cardNode;
    }

    /**
     * Add to output the hero specific to the player.
     * @return the hero information in output form.
     */
    public ObjectNode printHero() {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode cardNode = mapper.createObjectNode();
        cardNode.put("mana", getMana());
        cardNode.put("description", getDescription());

        // Create a new mapper for colors
        ObjectMapper colorMapper = new ObjectMapper();
        ArrayNode colorsArray = colorMapper.createArrayNode();
        for (int j = 0; j < getColors().size(); j++) {
            colorsArray.add(getColors().get(j));
        }

        cardNode.set("colors", colorsArray);

        cardNode.put("name", getName());
        cardNode.put("health", getHealth());

        return cardNode;
    }

}
