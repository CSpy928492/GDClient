package com.cspy.util;

import java.util.List;

public class Solution {
    private Type type;
    private List<Poke> pokes;

    public Solution(Type type, List<Poke> pokes) {
        this.type = type;
        this.pokes = pokes;
    }

    public Type getType() {
        return type;
    }

    public List<Poke> getPokes() {
        return pokes;
    }

    public boolean isInvalid() {
        return type == Type.INVALID;
    }

    @Override
    public String toString() {
        if (type == Type.INVALID) {
            return "Invalid";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("type:" + type.getName() + "|->");
            for (Poke poke:pokes) {
                sb.append(poke);
            }
            return sb.toString();
        }
    }
}
