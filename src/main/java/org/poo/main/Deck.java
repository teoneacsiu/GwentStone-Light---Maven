package org.poo.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CardInput;
import lombok.Getter;
import org.poo.playingcards.*;

public class Deck {
    private int numOfCards;
    private final ArrayList<Cards> cards;

    public Deck() {
        numOfCards = 0;
        cards = new ArrayList<>();
    }

    public void addCard(Cards card) {
        cards.add(card);
        numOfCards++;
    }

    public void addCard(CardInput cardInput) {
        Cards currentCard = switch (cardInput.getName()) {
            case "Disciple" -> new Disciple(cardInput.getMana(), cardInput.getHealth(),
                    cardInput.getAttackDamage(), cardInput.getDescription(),
                    cardInput.getColors(), cardInput.getName());
            case "Miraj" -> new Miraj(cardInput.getMana(), cardInput.getHealth(),
                    cardInput.getAttackDamage(), cardInput.getDescription(),
                    cardInput.getColors(), cardInput.getName());
            case "The Cursed One" -> new TheCursedOne(cardInput.getMana(), cardInput.getHealth(),
                    cardInput.getAttackDamage(), cardInput.getDescription(),
                    cardInput.getColors(), cardInput.getName());
            case "The Ripper" -> new TheRipper(cardInput.getMana(), cardInput.getHealth(),
                    cardInput.getAttackDamage(), cardInput.getDescription(),
                    cardInput.getColors(), cardInput.getName());
            default -> new Cards(cardInput.getMana(), cardInput.getHealth(),
                    cardInput.getAttackDamage(), cardInput.getDescription(),
                    cardInput.getColors(), cardInput.getName());
        };
        numOfCards++;
        cards.add(currentCard);
    }

    public void shuffle(int seed) {
        Random rand = new Random(seed);
        Collections.shuffle(cards, rand);
    }

    public Cards dealCard(int index) {
        //System.out.println(index + " + " + cards.size());
        if (index >= cards.size())
            return null;

        numOfCards--;
        return cards.remove(index);
    }

    public ArrayNode printDeck() {
        // Creates the output
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode output = mapper.createArrayNode();

        // Iterate over the deck
        for (int i = 0; i < numOfCards; i++) {
            Cards card = cards.get(i);
            if (card == null) {
                continue;
            }
            // Create the card node and format the output
            ObjectNode cardNode = card.printCard();
            output.add(cardNode);
        }

        return output;
    }

    public int getNumOfCards() {
        return numOfCards;
    }
}
