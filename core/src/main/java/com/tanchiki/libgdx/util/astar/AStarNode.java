package com.tanchiki.libgdx.util.astar;

import java.util.*;

public class AStarNode {
    public AStarNode parent;
    public int code;
    public int x, y;
    
    public AStarNode(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Node(" + x + ", " + y + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AStarNode)) return false;
        
        AStarNode node = (AStarNode) o;
        return x == node.x && y == node.y;
    }

    public static final int UP = 1;

    public static final int DOWN = -1;

    public static final int LEFT = 2;

    public static final int RIGHT = -2;
}
