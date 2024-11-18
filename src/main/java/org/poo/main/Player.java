package org.poo.main;

import lombok.Getter;
import lombok.Setter;
import org.poo.fileio.CardInput;
import org.poo.playingcards.*;
import org.poo.fileio.DecksInput;

import java.util.ArrayList;

@Getter @Setter
public class Player {
    @Setter
    @Getter
    private int mana;
    @Getter
    private Deck deck;
    private ArrayList<CardInput> inputDeck;
    private final ArrayList<Deck> availableDecks;
    @Getter
    private Cards hero;
    private Deck originalDeck;
    @Getter
    private Deck hand;
    @Getter
    private int wins;
    @Getter
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
    }

    public void drawCard() {
        if (hand != null) {
            hand.addCard((deck.dealCard(0)));
        }
    }

    public Cards getCard(int index) {
        if (index < 0 || index >= hand.getNumOfCards()) {
            System.out.println("Invalid index for hand: " + index);
            return null;
        }

        Cards card = hand.dealCard(index);


        if (card != null) {
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

}
