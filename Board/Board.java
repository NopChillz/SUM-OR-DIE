package Board;

import java.util.*;

public class Board {

    private int[][] grid = new int[5][5];

    public void generateBoard() {
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                grid[r][c] = (int)(Math.random() * 9) + 1;
            }
        }
        System.out.println("Board Generated!");
    }

    // ⭐ คืนเลขที่เลือกแทน sum
    public List<Integer> replaceNumbers(int row, int col, Direction dir, int len) {

        List<Integer> numbers = new ArrayList<>();

        for (int i = 0; i < len; i++) {

            int r = row;
            int c = col;

            if (dir == Direction.HORIZONTAL) c += i;
            else if (dir == Direction.VERTICAL) r += i;
            else if (dir == Direction.DIAGONAL_RIGHT) { r += i; c += i; }
            else if (dir == Direction.DIAGONAL_LEFT) { r += i; c -= i; }

            numbers.add(grid[r][c]);

            grid[r][c] = (int)(Math.random() * 9) + 1;
        }

        return numbers;
    }

    public int getValue(int r, int c) {
        return grid[r][c];
    }
}