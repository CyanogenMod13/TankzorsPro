package com.tanchiki.libgdx.util.astar;

import java.util.HashSet;
import java.util.Iterator;

public class AStarPath implements Iterator<AStarNode>, Iterable<AStarNode> {
    AStarNode begin;
    AStarNode end;

    AStarNode next;

    HashSet<AStarNode> set = new HashSet<>();

    public AStarPath(AStarNode begin, AStarNode end) {
        next = end;
        this.end = end;
        this.begin = begin;
        //set.add(end);
        set.add(begin);

    }

    public void add(AStarNode parent, AStarNode child) {
        child.parent = parent;
        set.add(child);
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public AStarNode next() {
        if (next == null) return null;
        AStarNode tmp = next;
        next = next.parent;
        return tmp;
    }

    @Override
    public void remove() {

    }

    @Override
    public Iterator<AStarNode> iterator() {

        return this;
    }
}
