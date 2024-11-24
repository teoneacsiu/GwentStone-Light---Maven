package org.poo.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.ActionsInput;
import org.poo.fileio.Input;
import org.poo.fileio.StartGameInput;
import org.poo.playingcards.Cards;

import java.util.ArrayList;

import static java.lang.Math.min;

public final class Match {
    private static final int PLAYER2_FRONT_ROW = 1;
    private static final int PLAYER2_BACK_ROW = 0;
    private static final int PLAYER1_FRONT_ROW = 2;
    private static final int PLAYER1_BACK_ROW = 3;
    private static final int MAX_MANA = 10;
    private final Player player1;
    private final Player player2;
    private final Field field;
    private int playerTurn;
    private int turnCounter;
    private boolean gameEnded;

    public Match(final Input input) {
        field = new Field();
        player1 = new Player(input.getPlayerOneDecks());
        player2 = new Player(input.getPlayerTwoDecks());
        gameEnded = false;
    }

    /**
     * Perform the setup for the match.
     * @param gameInput provides the required informations.
     */
    public void startGame(final StartGameInput gameInput) {
        playerTurn = gameInput.getStartingPlayer();
        turnCounter = 1;

        player1.chooseDeck(gameInput.getPlayerOneDeckIdx());
        player2.chooseDeck(gameInput.getPlayerTwoDeckIdx());

        player1.setHero(gameInput.getPlayerOneHero());
        player2.setHero(gameInput.getPlayerTwoHero());

        player1.shuffle(gameInput.getShuffleSeed());
        player2.shuffle(gameInput.getShuffleSeed());

        player1.drawCard();
        player2.drawCard();

        player1.setMana(1);
        player2.setMana(1);
    }

    /**
     * After every player turn, this method updates the specified
     * player params.
     */
    public void startRound() {
        int manaGain = min(turnCounter / 2 + 1, MAX_MANA);

        if (turnCounter % 2 == 1) {
            player1.setMana(player1.getMana() + manaGain);
            player2.setMana(player2.getMana() + manaGain);
        }

        player1.drawCard();
        player2.drawCard();
        field.resetAttack();

        player1.getHero().setUsed(false);
        player2.getHero().setUsed(false);
    }

    /**
     * After every game, the game elements need to be reset
     */
    public void resetMatch() {
        field.resetField();
        player1.resetPlayer();
        player2.resetPlayer();
        turnCounter = 1;
        gameEnded = false;
    }

    /**
     * Analyze and perform the task for every action provided.
     * @param input provides the commands list
     * @return the output in the asked form
     */
    public ArrayNode playing(final ArrayList<ActionsInput> input) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode output = mapper.createArrayNode();

        for (ActionsInput action : input) {
            ObjectNode actionNode = mapper.createObjectNode();
            String res;
            switch (action.getCommand()) {
                case "placeCard":
                    res = placeCard(action);
                    if (res == null) {
                        break;
                    }
                    actionNode.put("command", action.getCommand());
                    actionNode.put("handIdx", action.getHandIdx());
                    actionNode.put("error", res);
                    break;
                case "cardUsesAttack":
                    Coords attackerCoords = new Coords(action.getCardAttacker().getX(),
                            action.getCardAttacker().getY());
                    Coords defenderCoords = new Coords(action.getCardAttacked().getX(),
                            action.getCardAttacked().getY());
                    res = attackCard(attackerCoords, defenderCoords);
                    if (res == null) {
                        break;
                    }
                    actionNode.put("command", action.getCommand());
                    actionNode.set("cardAttacker", attackerCoords.toJson());
                    actionNode.set("cardAttacked", defenderCoords.toJson());
                    actionNode.put("error", res);
                    break;
                case "cardUsesAbility":
                    Coords attackerCooords = new Coords(action.getCardAttacker().getX(),
                            action.getCardAttacker().getY());
                    Coords defenderCooords = new Coords(action.getCardAttacked().getX(),
                            action.getCardAttacked().getY());
                    res = cardUseAbility(attackerCooords, defenderCooords);
                    if (res == null) {
                        break;
                    }
                    actionNode.put("command", action.getCommand());
                    actionNode.set("cardAttacker", attackerCooords.toJson());
                    actionNode.set("cardAttacked", defenderCooords.toJson());
                    actionNode.put("error", res);
                    break;
                case "useAttackHero":
                    Coords attackerCoooords = new Coords(action.getCardAttacker().getX(),
                            action.getCardAttacker().getY());
                    res = attackHero(attackerCoooords);
                    if (res == null) {
                        break;
                    }
                    if (res.equals("Player one killed the enemy hero.")
                           || res.equals("Player two killed the enemy hero.")) {
                        actionNode.put("gameEnded", res);
                        gameEnded = true;
                            break;
                    }
                    actionNode.put("command", action.getCommand());
                    actionNode.set("cardAttacker", attackerCoooords.toJson());
                    actionNode.put("error", res);
                    break;
                case "useHeroAbility":
                    res = heroAbility(action.getAffectedRow());
                    if (res == null) {
                        break;
                    }
                    actionNode.put("command", action.getCommand());
                    actionNode.put("affectedRow", action.getAffectedRow());
                    actionNode.put("error", res);
                    break;
                case "endPlayerTurn":
                    if (playerTurn == 1) {
                        field.unfreeze(PLAYER1_BACK_ROW, PLAYER1_FRONT_ROW);
                        playerTurn = 2;
                    } else {
                        field.unfreeze(PLAYER2_BACK_ROW, PLAYER2_FRONT_ROW);
                        playerTurn = 1;
                    }
                    turnCounter++;
                    if (turnCounter % 2 == 1) {
                        startRound();
                    }
                    break;
                case "getPlayerOneWins":
                    actionNode.put("command", action.getCommand());
                    actionNode.put("output", player1.getWins());
                    break;
                case "getPlayerTwoWins":
                    actionNode.put("command", action.getCommand());
                    actionNode.put("output", player2.getWins());
                    break;
                case "getTotalGamesPlayed":
                    actionNode.put("command", action.getCommand());
                    actionNode.put("output", player1.getWins() + player2.getWins());
                    break;
                case "getPlayerDeck":
                    actionNode.put("command", action.getCommand());
                    actionNode.put("playerIdx", action.getPlayerIdx());
                    if (action.getPlayerIdx() == 1) {
                        actionNode.set("output", player1.getDeck().printDeck());
                    } else {
                        actionNode.set("output", player2.getDeck().printDeck());
                    }
                    break;
                case "getPlayerHero":
                    actionNode.put("command", action.getCommand());
                    actionNode.put("playerIdx", action.getPlayerIdx());
                    if (action.getPlayerIdx() == 1) {
                        actionNode.set("output", player1.getHero().printHero());
                    } else {
                        actionNode.set("output", player2.getHero().printHero());
                    }
                    break;
                case "getCardsInHand":
                    actionNode.put("command", action.getCommand());
                    actionNode.put("playerIdx", action.getPlayerIdx());
                    if (action.getPlayerIdx() == 1) {
                        actionNode.set("output", player1.getHand().printDeck());
                    } else {
                        actionNode.set("output", player2.getHand().printDeck());
                    }
                    break;
                case "getPlayerMana":
                    actionNode.put("command", action.getCommand());
                    actionNode.put("playerIdx", action.getPlayerIdx());
                    if (action.getPlayerIdx() == 1) {
                        actionNode.put("output", player1.getMana());
                    } else {
                        actionNode.put("output", player2.getMana());
                    }
                    break;
                case "getPlayerTurn":
                    actionNode.put("command", action.getCommand());
                    actionNode.put("output", playerTurn);
                    break;
                case "getCardAtPosition":
                    actionNode.put("command", action.getCommand());
                    actionNode.put("x", action.getX());
                    actionNode.put("y", action.getY());
                    Cards card = field.getCard(action.getX(), action.getY());
                    if (card == null) {
                        actionNode.put("output", "No card available at that position.");
                        break;
                    }
                    actionNode.set("output", card.printCard());
                    break;
                case "getCardsOnTable":
                    actionNode.put("command", action.getCommand());
                    actionNode.set("output", field.printAll());
                    break;
                case "getFrozenCardsOnTable":
                    actionNode.put("command", action.getCommand());
                    actionNode.set("output", field.printAllFrozen());
                    break;
                default:
                    System.out.println("Error: Command not recognised.");
                    System.out.println("Command: " + action.getCommand());
            }
            if (!actionNode.isEmpty()) {
                output.add(actionNode);
            }
        }
        return output;
    }

    /**
     * This method handles the case of "placeCard" command
     * with every edge cases.
     * @param action provides the index of the card in player`s hand.
     * @return the error if exists.
     */
    public String placeCard(final ActionsInput action) {
        Cards card = new Cards();
        String notEnoughMana =
                "Not enough mana to place card on table.";
        String rowFull =
                "Cannot place card on table since row is full.";

        if (playerTurn == 1) {
            if (player1.cardExists(action.getHandIdx())) {
                card = player1.getCard(action.getHandIdx());
            } else {
                return null;
            }
        } else if (playerTurn == 2) {
            if (player2.cardExists(action.getHandIdx())) {
                card = player2.getCard(action.getHandIdx());
            } else {
                return null;
            }
        }

        if (card == null) {
            return notEnoughMana;
        }

        int row = card.getCardRow();
        switch (row) {
            case 1:
                if (playerTurn == 1 && !field.isRowFull(PLAYER1_FRONT_ROW)) {
                    field.addCard(card, PLAYER1_FRONT_ROW);
                } else if (playerTurn == 2 && !field.isRowFull(PLAYER2_FRONT_ROW)) {
                    field.addCard(card, PLAYER2_FRONT_ROW);
                }
                break;
            case 2:
                if (playerTurn == 1 && !field.isRowFull(PLAYER1_BACK_ROW)) {
                    field.addCard(card, PLAYER1_BACK_ROW);
                } else if (!field.isRowFull(PLAYER2_BACK_ROW)) {
                    field.addCard(card, PLAYER2_BACK_ROW);
                }
                break;
            default:
                return rowFull;
        }
        return null;
    }

    /**
     * This method handles the case of "cardUsesAttack" command
     * with every edge cases.
     * @param attackerCoords provides the coordinates of the attacker card.
     * @param defenderCoords provides the coordinates of the attacked card.
     * @return the error if exists.
     */
    public String attackCard(final Coords attackerCoords, final Coords defenderCoords) {
        Cards attacker = field.getCard(attackerCoords.getX(), attackerCoords.getY());
        Cards defender = field.getCard(defenderCoords.getX(), defenderCoords.getY());

        if (attacker == null || defender == null) {
            return "Card not found.";
        }

        if (attacker.isUsed()) {
            return "Attacker card has already attacked this turn.";
        }
        if (attacker.isFrozen()) {
            return "Attacker card is frozen.";
        }

        if (playerTurn == 1) {
            if (defenderCoords.getX() == PLAYER1_BACK_ROW
                    || defenderCoords.getX() == PLAYER1_FRONT_ROW) {
                return "Attacked card does not belong to the enemy.";
            }
        } else if (playerTurn == 2) {
            if (defenderCoords.getX() == PLAYER2_BACK_ROW
                    || defenderCoords.getX() == PLAYER2_FRONT_ROW) {
                return "Attacked card does not belong to the enemy.";
            }
        }

        int opponentFrontRow = (playerTurn == 1) ? PLAYER2_FRONT_ROW : PLAYER1_FRONT_ROW;
        if (field.existsTank(opponentFrontRow)) {
            if (!field.attackedCardIsTank(defenderCoords.getX(), defenderCoords.getY())) {
                return "Attacked card is not of type 'Tank'.";
            }
        }

        attacker.attack(defender);
        if (defender.isDead()) {
            field.removeCard(defenderCoords.getX(), defenderCoords.getY());
        }

        return null;
    }

    /**
     * This method handles the case of "cardUsesAbility" command
     * with every edge cases.
     * @param attackerCoords provides the coordinates of the attacker card.
     * @param defenderCoords provides the coordinates of the attacked card.
     * @return the error if exists.
     */
    public String cardUseAbility(final Coords attackerCoords, final Coords defenderCoords) {
        Cards attacker = field.getCard(attackerCoords.getX(), attackerCoords.getY());
        Cards defender = field.getCard(defenderCoords.getX(), defenderCoords.getY());

        if (attacker == null || defender == null) {
            return "Card not found.";
        }

        if (attacker.isFrozen()) {
            return "Attacker card is frozen.";
        }

        if (attacker.isUsed()) {
            return "Attacker card has already attacked this turn.";
        }

        if (attacker.getName().equals("Disciple")) {
            if (playerTurn == 1) {
                if (defenderCoords.getX() == PLAYER2_BACK_ROW
                        || defenderCoords.getX() == PLAYER2_FRONT_ROW) {
                    return "Attacked card does not belong to the current player.";
                }
            } else if (playerTurn == 2) {
                if (defenderCoords.getX() == PLAYER1_BACK_ROW
                        || defenderCoords.getX() == PLAYER1_FRONT_ROW) {
                    return "Attacked card does not belong to the current player.";
                }
            }
        }

        if (attacker.getName().equals("The Ripper")
            || attacker.getName().equals("Miraj")
            || attacker.getName().equals("The Cursed One")) {
            if (playerTurn == 1) {
                if (defenderCoords.getX() == PLAYER1_BACK_ROW
                    || defenderCoords.getX() == PLAYER1_FRONT_ROW) {
                    return "Attacked card does not belong to the enemy.";
                }

                if (field.existsTank(PLAYER2_FRONT_ROW)
                   && !field.attackedCardIsTank(defenderCoords.getX(), defenderCoords.getY())) {
                    return "Attacked card is not of type 'Tank'.";
                }
            } else if (playerTurn == 2) {
                if (defenderCoords.getX() == PLAYER2_BACK_ROW
                        || defenderCoords.getX() == PLAYER2_FRONT_ROW) {
                    return "Attacked card does not belong to the enemy.";
                }

                if (field.existsTank(PLAYER1_FRONT_ROW)
                   && !field.attackedCardIsTank(defenderCoords.getX(), defenderCoords.getY())) {
                    return "Attacked card is not of type 'Tank'.";
                }
            }
        }

        attacker.useAbility(defender);
        if (defender.getHealth() == 0) {
            field.removeCard(defenderCoords.getX(), defenderCoords.getY());
        }
        return null;
    }

    /**
     * This method handles the case of "useAttackHero" command
     * with every edge cases.
     *@param attackerCoords provides the coordinates of the attacker card.
     * @return the error if exists
     */
    public String attackHero(final Coords attackerCoords) {
        if (gameEnded) {
            return null;
        }

        Cards attacker = field.getCard(attackerCoords.getX(), attackerCoords.getY());
        if (attacker == null) {
            return "Card not found.";
        }

        if (attacker.isFrozen()) {
            return "Attacker card is frozen.";
        }

        if (attacker.isUsed()) {
            return "Attacker card has already attacked this turn.";
        }

        if (playerTurn == 1) {
            if (field.existsTank(PLAYER2_FRONT_ROW)) {
                return "Attacked card is not of type 'Tank'.";
            }
        } else if (field.existsTank(PLAYER1_FRONT_ROW)) {
            return "Attacked card is not of type 'Tank'.";
        }

        if (playerTurn == 1) {
            player2.getHero().setHealth(
                    player2.getHero().getHealth() - attacker.getAttackDamage()
            );
            attacker.setUsed(true);
            if (player2.getHero().getHealth() <= 0) {
                player1.win();
                player2.lose();
                return "Player one killed the enemy hero.";
            }
        } else if (playerTurn == 2) {
            player1.getHero().setHealth(
                    player1.getHero().getHealth() - attacker.getAttackDamage()
            );
            attacker.setUsed(true);
            if (player1.getHero().getHealth() <= 0) {
                player2.win();
                player1.lose();
                return "Player two killed the enemy hero.";
            }
        }
        return null;
    }

    /**
     * This method handles the case of "useHeroAbility" command
     * with every edge cases.
     * @param row the given affected row in the field.
     * @return the error if exists.
     */
    public String heroAbility(final int row) {
        Cards currHero;
        Player currPlayer;
        if (playerTurn == 1) {
            currPlayer = player1;
        } else {
            currPlayer = player2;
        }

        currHero = currPlayer.getHero();

        if (currPlayer.getMana() < currHero.getMana()) {
            return "Not enough mana to use hero's ability.";
        }

        if (currHero.isUsed()) {
            return "Hero has already attacked this turn.";
        }

        if (currHero.getName().equals("Lord Royce")
               || currHero.getName().equals("Empress Thorina")) {
            if (playerTurn == 1) {
                if (row == PLAYER1_BACK_ROW || row == PLAYER1_FRONT_ROW) {
                    return "Selected row does not belong to the enemy.";
                }
            } else if (playerTurn == 2) {
                if (row == PLAYER2_BACK_ROW || row == PLAYER2_FRONT_ROW) {
                    return "Selected row does not belong to the enemy.";
                }
            }
        }

        if (currHero.getName().equals("General Kocioraw")
               || currHero.getName().equals("King Mudface")) {
            if (playerTurn == 1) {
                if (row == PLAYER2_BACK_ROW || row == PLAYER2_FRONT_ROW) {
                    return "Selected row does not belong to the current player.";
                }
            } else if (playerTurn == 2) {
                if (row == PLAYER1_BACK_ROW || row == PLAYER1_FRONT_ROW) {
                    return "Selected row does not belong to the current player.";
                }
            }
        }

        currPlayer.setMana(currPlayer.getMana() - currHero.getMana());
        int y = currHero.useAbility(field, row);
        if (y != -1) {
            field.removeCard(row, y);
        }
        return null;
    }
}
