package org.poo.main;

import lombok.Getter;
import lombok.Setter;
import org.poo.fileio.CardInput;
import org.poo.playingcards.Cards;

import org.poo.fileio.DecksInput;
import org.poo.playingcards.EmpressThorina;
import org.poo.playingcards.GeneralKocioraw;
import org.poo.playingcards.KingMudface;
import org.poo.playingcards.LordRoyce;

import java.util.ArrayList;

@Getter @Setter
public final class Player {
    private int mana;
    private Deck deck;
    private ArrayList<CardInput> inputDeck;
    private final ArrayList<Deck> availableDecks;
    private Cards hero;
    private Deck originalDeck;
    private Deck hand;
    private int wins;
    private int losses;

    public Player(final DecksInput decksInput) {
        this.hand = new Deck();
        originalDeck = new Deck();
        mana = 0;
        wins = 0;
        losses = 0;

        availableDecks = new ArrayList<>();
        Deck newDeck;
        for (int i = 0; i < decksInput.getNrDecks(); i++) {
            newDeck = new Deck();

            inputDeck = decksInput.getDecks().get(i);
            for (CardInput cardInput : inputDeck) {
                newDeck.addCard(cardInput);
            }

            availableDecks.add(newDeck);
        }
    }

    /**
     * Setting up the hero of the player.
     * @param hero the input information of the hero.
     */
    public void setHero(final CardInput hero) {
        switch (hero.getName()) {
            case "Empress Thorina":
                this.hero = new EmpressThorina(hero.getMana(), hero.getDescription(),
                        hero.getColors(), hero.getName());
                break;
            case "General Kocioraw":
                this.hero = new GeneralKocioraw(hero.getMana(), hero.getDescription(),
                        hero.getColors(), hero.getName());
                break;
            case "King Mudface":
                this.hero = new KingMudface(hero.getMana(), hero.getDescription(),
                        hero.getColors(), hero.getName());
                break;
            default:
                this.hero = new LordRoyce(hero.getMana(), hero.getDescription(),
                        hero.getColors(), hero.getName());
        }
    }

    /**
     * Resetting the player before a new match.
     */
    public void resetPlayer() {
        hand = new Deck();
        hero = null;

        mana = 0;
    }

    /**
     * Copying the deck from the original one to protect the information.
     * @param seed the shuffle seed for the randomization.
     */
    public void shuffle(final int seed) {
        deck = originalDeck;
        deck.shuffle(seed);
    }

    /**
     * Choosing the deck to play with.
     * @param deckIdx the index of the specified deck.
     */
    public void chooseDeck(final int deckIdx) {
        originalDeck = availableDecks.get(deckIdx);
    }

    /**
     * Draw a card from the deck before every round.
     */
    public void drawCard() {
        if (hand != null) {
            hand.addCard((deck.dealCard(0)));
        }
    }

    /**
     * Checks if a card exists in hand.
     * @param index the index of the card.
     * @return if the card exists or not.
     */
    public boolean cardExists(final int index) {
        return hand.cardExistsDeck(index);
    }

    /**
     * Getting a card from the hand and decrementing the player mana,
     * used to place the card on the field.
     * @param index the index of the card.
     * @return the card information.
     */
    public Cards getCard(final int index) {
        if (index < 0 || index >= hand.getNumOfCards()) {
            System.out.println("Invalid index for hand: " + index);
            return null;
        }

        Cards card = hand.getCard(index);


        if (card != null) {
            if (mana >= card.getMana()) {
                mana -= card.getMana();
                return hand.dealCard(index);
            }
        }
        return null;
    }

    /**
     * Increments the wins of the player.
     */
    public void win() {
        wins++;
    }

    /**
     * Increments the losses of the player.
     */
    public void lose() {
        losses++;
    }

}
