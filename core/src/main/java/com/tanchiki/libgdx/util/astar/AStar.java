package com.tanchiki.libgdx.util.astar;

import java.util.ArrayList;
import com.tanchiki.libgdx.stage.*;
import com.tanchiki.libgdx.model.terrains.Object.*;
import com.tanchiki.libgdx.model.terrains.*;

public class AStar {
    AStarListener listener;
    int[][] graph;

    public AStar(int[][] graph, AStarListener listener) {
        this.listener = listener;
        this.graph = graph;
    }

    private int matrix[][] =
            {
                    {0, -2, AStarNode.DOWN},
                    {0, 2, AStarNode.UP},
                    {2, 0, AStarNode.RIGHT},
                    {-2, 0, AStarNode.LEFT}
            };

    public AStarPath search(int x0, int y0, int x, int y) {
        ArrayList<AStarNode> unchecked = new ArrayList<>();

        AStarNode begin = new AStarNode(x0, y0);
        AStarNode end = new AStarNode(x, y);

        AStarPath path = new AStarPath(begin, end);

        unchecked.add(begin);

        while (true) {
            if (unchecked.isEmpty()) return null;

            float max_price = Float.MAX_VALUE;
            AStarNode max_node = null;
            int index = 0;

            for (int i = 0; i < unchecked.size(); i++) {
                AStarNode current = unchecked.get(i);

                float price = listener.h(end, current) + listener.g(begin, current);

                if (price < max_price) {
                    max_price = price;
                    max_node = current;
                    index = i;
                }
            }

			//if (max_node != null && max_node.parent != null) max_node.parent.code = max_node.code;
            unchecked.remove(index);

            if (max_node.equals(end)) {
                end.parent = max_node.parent;
                end.code = max_node.code;
                return path;
            }

            for (int i = 0; i < matrix.length; i++) {
                int args[] = matrix[i];
                int x_new = max_node.x + args[0];
                int y_new = max_node.y + args[1];

                if (x_new >= graph.length || x_new < 0 || y_new >= graph[0].length || y_new < 0) continue;

				if (graph[x_new][y_new] == 1 && !(GameStage.getInstance().world_physic_block[x_new][y_new] instanceof DestroyableBlock) || (GameStage.getInstance().world_physic_block[x_new][y_new] instanceof Spike)) continue;
                //if (graph[x_new][y_new] != 0) continue;

                AStarNode child = new AStarNode(x_new, y_new);
                child.code = args[2];

                if (path.set.contains(child)) continue;

                path.add(max_node, child);

                unchecked.add(child);
            }
        }
    }

    public static interface AStarListener {
        public float h(AStarNode end, AStarNode current);

        public float g(AStarNode begin, AStarNode current);
    }
}
