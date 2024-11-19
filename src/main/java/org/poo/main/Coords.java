package org.poo.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Coords {
    private final int x;
    private final int y;

    public Coords(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public ObjectNode toJson() {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode pointNode = mapper.createObjectNode();

        pointNode.put("x", x);
        pointNode.put("y", y);

        return pointNode;
    }

}
