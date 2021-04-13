package com.tanchiki.libgdx.util.astar;

public class AStarNode {
    public AStarNode parent;
    public int code;

    public int x, y;

    private String toString;

    private int hashCode;

    public AStarNode(int x, int y) {
        this.x = x;
        this.y = y;
        toString = x + ":" + y;
        hashCode = toString.hashCode();
    }

    @Override
    public String toString() {

        return toString;
    }

    @Override
    public int hashCode() {

        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {

        return hashCode == obj.hashCode();
    }

    public static final int UP = 1;

    public static final int DOWN = -1;

    public static final int LEFT = 2;

    public static final int RIGHT = -2;
}
