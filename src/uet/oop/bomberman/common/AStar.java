package uet.oop.bomberman.common;

import java.util.*;

public class AStar {
    private static final int[] dx = {-1, 0, 1, 0};
    private static final int[] dy = {0, 1, 0, -1};
    public static Stack<Direction> shortestPath(int[][] matrix, int i, int j, int u, int v) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        PriorityQueue<Node> queue = new PriorityQueue<>(new NodeComparator());
        boolean[][] visited = new boolean[rows][cols];
        int[][] gScore = new int[rows][cols];
        int[][] fScore = new int[rows][cols];
        int[][] prev = new int[rows][cols];
        Stack<Direction> path = new Stack<>();

        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                fScore[x][y] = Integer.MAX_VALUE;
                gScore[x][y] = Integer.MAX_VALUE;
                prev[x][y] = -1;
            }
        }

        gScore[i][j] = 0;
        fScore[i][j] = heuristic(i, j, u, v);
        queue.add(new Node(i, j, fScore[i][j]));

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            int x = current.x;
            int y = current.y;

            if (x == u && y == v) {
                while (x != i || y != j) {
                    int p = prev[x][y];
                    path.push(getDirection(p));
                    x -= dx[p];
                    y -= dy[p];
                }
                Collections.reverse(path);
                return path;
            }

            visited[x][y] = true;

            for (int k = 0; k < 4; k++) {
                int newX = x + dx[k];
                int newY = y + dy[k];
                if (newX >= 0 && newX < rows && newY >= 0 && newY < cols && matrix[newX][newY] == 0 && !visited[newX][newY]) {
                    int tentativeGScore = gScore[x][y] + 1;
                    if (tentativeGScore < gScore[newX][newY]) {
                        prev[newX][newY] = k;
                        gScore[newX][newY] = tentativeGScore;
                        fScore[newX][newY] = gScore[newX][newY] + heuristic(newX, newY, u, v);
                        if (!queue.contains(new Node(newX, newY, fScore[newX][newY]))) {
                            queue.add(new Node(newX, newY, fScore[newX][newY]));
                        }
                    }
                }
            }
        }

        return path;
    }

    private static int heuristic(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    private static Direction getDirection(int p) {
        switch (p) {
            case 0:
                return Direction.UP;
            case 1:
                return Direction.RIGHT;
            case 2:
                return Direction.DOWN;
            case 3:
                return Direction.LEFT;
            default:
                throw new IllegalArgumentException("Invalid direction");
        }
    }


    private static class Node {
        int x;
        int y;
        int fScore;

        Node(int x, int y, int fScore) {
            this.x = x;
            this.y = y;
            this.fScore = fScore;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return this.x == node.x &&
                    this.y == node.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private static class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node n1, Node n2) {
            return Integer.compare(n1.fScore, n2.fScore);
        }
    }
}