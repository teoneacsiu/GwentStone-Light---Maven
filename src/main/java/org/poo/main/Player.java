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

    public void resetPlayer() {
        deck = null;
        hand = new Deck();
        hero = null;

        mana = 0;
    }

    public void shuffle(final int seed) {
        deck = originalDeck;
        deck.shuffle(seed);
    }

    public void chooseDeck(final int deckIdx) {
        originalDeck = availableDecks.get(deckIdx);
    }

    public void drawCard() {
        if (hand != null) {
            hand.addCard((deck.dealCard(0)));
        }
    }

    public boolean cardExists(final int index) {
        return hand.cardExistsDeck(index);
    }

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

    public void win() {
        wins++;
    }

    public void lose() {
        losses++;
    }

}
