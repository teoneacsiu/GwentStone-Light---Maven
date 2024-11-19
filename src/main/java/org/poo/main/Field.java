package org.poo.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.playingcards.Cards;

import java.util.ArrayList;

public class Field {
    public static final int TABLE_ROWS = 4;
    public static final int TABLE_COLS = 5;
    private static final ArrayList<String> tanks = new ArrayList<>();
    static {
        tanks.add("Goliath");
        tanks.add("Warden");
    }

    private final Cards[][] field = new Cards[TABLE_ROWS][TABLE_COLS];

    public Field() {

        for (int i = 0; i < TABLE_ROWS; i++) {
            for (int j = 0; j < TABLE_COLS; j++) {
                field[i][j] = null;
            }
        }
    }

    public void addCard(final Cards card, final int row) {
        for (int i = 0; i < TABLE_COLS; i++) {
            if (field[row][i] == null) {
                field[row][i] = card;
                break;
            }
        }
    }

    public Cards getCard(final int row, final int col) {
        return field[row][col];
    }

    public boolean existsTank(final int row) {
        boolean exists = false;
        for (int i = 0; i < TABLE_COLS; i++) {
            if (field[row][i] != null) {
                if (tanks.contains(getCard(row, i).getName())) {
                    exists = true;
                }
            }
        }
        return exists;
    }

    public boolean attackedCardIsTank(final int row, final int col) {
        return tanks.contains(getCard(row, col).getName());
    }

    public void removeCard(final int defenseX, final int defenseY) {
        field[defenseX][defenseY] = null;
    }

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

    public void resetAttack() {
        for (int i = 0; i < TABLE_ROWS; i++) {
            for (int j = 0; j < TABLE_COLS; j++) {
                if (field[i][j] != null) {
                    field[i][j].setUsed(false);
                }
            }
        }
    }

    public void resetField() {
        for (int i = 0; i < TABLE_ROWS; i++) {
            for (int j = 0; j < TABLE_COLS; j++) {
                field[i][j] = null;
            }
        }
    }

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
