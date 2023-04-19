package uet.oop.bomberman.common;

import javafx.util.Pair;

import java.util.*;

public class BFS {
    private static final int[] dx = {-1, 0, 1, 0};
    private static final int[] dy = {0, 1, 0, -1};
    public static Stack<Direction> shortestPath(int[][] matrix, int i, int j, int u, int v) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        boolean[][] visited = new boolean[rows][cols];
        int[][] prev = new int[rows][cols];

        Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
        queue.add(new Pair<>(i, j));

        while (!queue.isEmpty()) {
            Pair<Integer, Integer> node = queue.remove();
            int x = node.getKey();
            int y = node.getValue();

            if (x == u && y == v) {
                Stack<Direction> path = new Stack<>();
                while (x != i || y != j) {
                    int k = prev[x][y];
                    Direction dir;
                    switch (k) {
                        case 0:
                            dir = Direction.UP;
                            break;
                        case 1:
                            dir = Direction.RIGHT;
                            break;
                        case 2:
                            dir = Direction.DOWN;
                            break;
                        default:
                            dir = Direction.LEFT;
                            break;
                    }
                    path.push(dir);
                    int newX = x - dx[k];
                    int newY = y - dy[k];
                    x = newX;
                    y = newY;
                }
                return path;
            }

            visited[x][y] = true;

            for (int k = 0; k < 4; k++) {
                int newX = x + dx[k];
                int newY = y + dy[k];

                if (newX >= 0 && newX < rows && newY >= 0 && newY < cols && matrix[newX][newY] == 0 && !visited[newX][newY]) {
                    prev[newX][newY] = k;
                    queue.add(new Pair<>(newX, newY));
                }
            }
        }

        return new Stack<>();
    }
}
