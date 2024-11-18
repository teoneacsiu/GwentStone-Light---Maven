package org.poo.main;

import org.poo.fileio.CardInput;
import org.poo.playingcards.*;
import org.poo.fileio.DecksInput;

import java.util.ArrayList;

public class Player {
    private int mana;
    private Deck deck;
    private ArrayList<CardInput> inputDeck;
    private ArrayList<Deck> availableDecks;
    private Cards hero;
    private Deck originalDeck;
    private Deck hand;
    private int wins;
    private int losses;

    public Player(DecksInput decksInput) {
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

    public void setHero(CardInput hero) {
        switch (hero.getName()) {
            case "Empress Thorina":
                this.hero = new EmpressThorina(hero.getMana(), hero.getDescription(),
                        hero.getColors(), hero.getName());
                break;
            case "General Kocioraw":
                this.hero = new GeneralKocioraw(hero.getMana(), hero.getDescription(),
                        hero.getColors(), hero.getName());
                break;
            case "Kind Mudface":
                this.hero = new KingMudface(hero.getMana(), hero.getDescription(),
                        hero.getColors(), hero.getName());
                break;
            case "Lord Royce":
                this.hero = new LordRoyce(hero.getMana(), hero.getDescription(),
                        hero.getColors(), hero.getName());
                break;
            default:
                this.hero = new Cards();
        }
    }

    public void resetPlayer() {
        deck = null;
        hand = new Deck();
        hero = null;

        mana = 0;
    }

    public void shuffle(int seed) {
        deck = originalDeck;
        deck.shuffle(seed);
    }

    public void chooseDeck(int deckIdx) {
        originalDeck = availableDecks.get(deckIdx);
        //System.out.println(originalDeck.printDeck());
    }

    public void drawCard() {
        if (hand != null) {
            hand.addCard((deck.dealCard(0)));
            //System.out.println(hand.printDeck());
        }
    }

    public Cards getCard(int index) {
        if (index < 0 || index >= hand.getNumOfCards()) {
            System.out.println("Invalid index for hand: " + index);
            return null;
        }

        Cards card = hand.dealCard(index);


        if (card != null) {
            //System.out.println("DAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            if (mana >= card.getMana()) {
                mana -= card.getMana();
                return card;
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

    public void setMana(int i) {
        mana = i;
    }

    public int getMana() {
        return mana;
    }

    public Cards getHero() {
        return hero;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public Deck getDeck() {
        return deck;
    }

    public Deck getHand() {
        return hand;
    }
}
