package org.poo.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public final class Coords {
    private final int x;
    private final int y;

    public Coords(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * This is the method to put in the output form the coordinates.
     * @return the output form.
     */
    public ObjectNode toJson() {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode pointNode = mapper.createObjectNode();

        pointNode.put("x", x);
        pointNode.put("y", y);

        return pointNode;
    }

}
