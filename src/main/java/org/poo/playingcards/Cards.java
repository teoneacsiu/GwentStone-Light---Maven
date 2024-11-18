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
public class Cards {
    private static final ArrayList<String> frontRowMinions = new ArrayList<>();
    static {
        frontRowMinions.add("Goliath");
        frontRowMinions.add("Warden");
        frontRowMinions.add("The Ripper");
        frontRowMinions.add("Miraj");
    }

    private static final ArrayList<String> backRowMinions = new ArrayList<>();
    static {
        backRowMinions.add("Sentinel");
        backRowMinions.add("Berserker");
        backRowMinions.add("The Cursed One");
        backRowMinions.add("Disciple");
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

    public Cards(int mana, int health, int attackDamage,
                 String description, ArrayList<String> colors, String name) {
        this.mana = mana;
        this.health = health;
        this.attackDamage = attackDamage;
        this.description = description;
        this.name = name;
        this.colors = colors;
        used = false;
        frozen = false;
    }

    public int getCardRow() {
        int row = 0;
        if (frontRowMinions.contains(name))
            row = 1;
        if (backRowMinions.contains(name))
            row = 2;
        return row;
    }

    public boolean isDead() {
        return health == 0;
    }

    public void attack(Cards defender) {
        used = true;
        if(defender.getHealth() < attackDamage)
            defender.setHealth(0);
        else defender.setHealth(defender.getHealth() - attackDamage);
    }

    public boolean useAbility(Cards card) {
        return false;
    }

    public boolean useAbility(Field field, int row) {
        return false;
    }

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
