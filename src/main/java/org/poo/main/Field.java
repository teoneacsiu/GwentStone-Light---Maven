package org.poo.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.playingcards.Cards;

import java.util.ArrayList;

public final class Field {
    public static final int TABLE_ROWS = 4;
    public static final int TABLE_COLS = 5;
    private static final ArrayList<String> TANKS = new ArrayList<>();
    static {
        TANKS.add("Goliath");
        TANKS.add("Warden");
    }

    private final Cards[][] field = new Cards[TABLE_ROWS][TABLE_COLS];

    public Field() {

        for (int i = 0; i < TABLE_ROWS; i++) {
            for (int j = 0; j < TABLE_COLS; j++) {
                field[i][j] = null;
            }
        }
    }

    /**
     * Adding a card to the field.
     * @param card the card to be added.
     * @param row the row of the card.
     */
    public void addCard(final Cards card, final int row) {
        if (isRowFull(row)) {
            return;
        }

        for (int i = 0; i < TABLE_COLS; i++) {
            if (field[row][i] == null) {
                field[row][i] = card;
                break;
            }
        }
    }

    /**
     * Getting the card information from the field.
     * @param row coordinate of the wanted card.
     * @param col coordinate of the wanted card.
     * @return the information of the card.
     */
    public Cards getCard(final int row, final int col) {
        return field[row][col];
    }

    /**
     * Verify if a tank is present on the field.
     * @param row the specific row for the search.
     * @return if a tank exists or not.
     */
    public boolean existsTank(final int row) {
        boolean exists = false;
        for (int i = 0; i < TABLE_COLS; i++) {
            if (field[row][i] != null) {
                if (TANKS.contains(getCard(row, i).getName())) {
                    exists = true;
                }
            }
        }
        return exists;
    }

    /**
     * Verify is a row is full.
     * @param row specific row for search.
     * @return if row is full or not.
     */
    public boolean isRowFull(final int row) {
        for (int i = 0; i < TABLE_COLS; i++) {
            if (field[row][i] == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verify is the attacked card is the type tank.
     * @param row the row to be searched.
     * @param col the col to be searched.
     * @return if the attacked card is tank or not.
     */
    public boolean attackedCardIsTank(final int row, final int col) {
        return TANKS.contains(getCard(row, col).getName());
    }

    /**
     * Remove a card from the field and shifts to left the remaining cards.
     * @param defenseX the row of the specific card.
     * @param defenseY the col of the specific card.
     */
    public void removeCard(final int defenseX, final int defenseY) {
        field[defenseX][defenseY] = null;
        int y = defenseY;

        while (y + 1 < TABLE_COLS) {
            if (field[defenseX][y + 1] != null) {
                field[defenseX][y] = field[defenseX][y + 1];
            } else {
                field[defenseX][y] = null;
            }
            y++;
        }
    }

    /**
     * Unfreeze all cards of one player
     * @param backRow the specific row of the player.
     * @param frontRow the specific row of the player.
     */
    public void unfreeze(final int backRow, final int frontRow) {
        for (int i = 0; i < TABLE_COLS; i++) {
            if (field[backRow][i] != null) {
                field[backRow][i].setFrozen(false);
            }

            if (field[frontRow][i] != null) {
                field[frontRow][i].setFrozen(false);
            }
        }
    }

    /**
     * Reset the attacked of every card on field.
     */
    public void resetAttack() {
        for (int i = 0; i < TABLE_ROWS; i++) {
            for (int j = 0; j < TABLE_COLS; j++) {
                if (field[i][j] != null) {
                    field[i][j].setUsed(false);
                }
            }
        }
    }

    /**
     * Empties the field before a match.
     */
    public void resetField() {
        for (int i = 0; i < TABLE_ROWS; i++) {
            for (int j = 0; j < TABLE_COLS; j++) {
                field[i][j] = null;
            }
        }
    }

    /**
     * Putting in required form the output.
     * @return the output form.
     */
    public ArrayNode printAll() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode output = mapper.createArrayNode();

        // Iterate through the entire field
        for (int i = 0; i < TABLE_ROWS; i++) {
            ArrayNode row = mapper.createArrayNode();
            for (int j = 0; j < TABLE_COLS; j++) {
                if (field[i][j] != null) {
                    row.add(field[i][j].printCard());
                }
            }
            output.add(row);
        }

        return output;
    }

    /**
     * Print the frozen cards present on field at that moment.
     * @return the output form.
     */
    public ArrayNode printAllFrozen() {
        // Creates the output
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode output = mapper.createArrayNode();

        // Iterate through the entire field
        for (int i = 0; i < TABLE_ROWS; i++) {
            for (int j = 0; j < TABLE_COLS; j++) {
                if (field[i][j] != null && field[i][j].isFrozen()) {
                    output.add(field[i][j].printCard());
                }
            }
        }

        return output;
    }
}
