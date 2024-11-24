package org.poo.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CardInput;
import lombok.Getter;
import org.poo.playingcards.Cards;
import org.poo.playingcards.Disciple;
import org.poo.playingcards.TheRipper;
import org.poo.playingcards.Miraj;
import org.poo.playingcards.TheCursedOne;


public final class Deck {
    @Getter
    private int numOfCards;
    private final ArrayList<Cards> cards;

    public Deck() {
        numOfCards = 0;
        cards = new ArrayList<>();
    }

    /**
     * Adding a card to the deck.
     * @param card the card to be added.
     */
    public void addCard(final Cards card) {
        cards.add(card);
        numOfCards++;
    }

    /**
     * adding a card from the input.
     * @param cardInput the card to be added.
     */
    public void addCard(final CardInput cardInput) {
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

    /**
     * Shuffle the deck.
     * @param seed the shuffle param from the input.
     */
    public void shuffle(final int seed) {
        Random rand = new Random(seed);
        Collections.shuffle(cards, rand);
    }

    /**
     * Checks if a card exists in deck.
     * @param index the card index.
     * @return true or false depending on finding the card or not.
     */
    public boolean cardExistsDeck(final int index) {
        if (cards.size() <= index) {
            return false;
        }

        return cards.get(index) != null;
    }

    /**
     * Getting a specific card from the deck.
     * @param index the index of the searched card
     * @return the card information.
     */
    public Cards getCard(final int index) {
        return cards.get(index);
    }

    /**
     * Getting a card from the deck and removing it.
     * @param index the index of the searched card.
     * @return the card information.
     */
    public Cards dealCard(final int index) {
        if (index >= cards.size()) {
            return null;
        }

        numOfCards--;
        return cards.remove(index);
    }

    /**
     * Print the deck information in required form.
     * @return the output form of the deck.
     */
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

}
