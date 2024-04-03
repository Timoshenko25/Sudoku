import javax.swing.*;
import java.awt.Point;
import java.util.HashMap;

public class SudokuGenerator {
    final JTextField[][] grid = new JTextField[9][9];
    HashMap<JTextField, Point> mapFieldToCoordinates = new HashMap<>();
    public boolean checkSudoku(JTextField[][] grid){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!grid[i][j].getText().isEmpty() && grid[i][j].getText().matches("\\d+")) {
                    int a = Integer.parseInt(grid[i][j].getText());
                    if (CheckIfSafe(grid, i, j, a)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public JTextField[][] generateRandomSudoku(JTextField[][] grid,int k) {
        int N = 9;
        Double SRNd = Math.sqrt(9);
        int SRN = SRNd.intValue();
        fillDiagonal(grid);
        fillRemaining(grid, 0, SRN);
        removeKDigits(grid,k);
        return grid;
    }

    void fillDiagonal(JTextField[][] grid) {
        for (int i = 0; i < 9; i = i + 3)
            fillBox(grid, i, i);
    }

    boolean unUsedInBox(JTextField[][] grid, int rowStart, int colStart, int num) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (grid[rowStart + i][colStart + j].getText().equals(String.valueOf(num)))
                    return false;
        return true;
    }

    void fillBox(JTextField[][] grid, int row, int col) {
        int num;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                do {
                    num = randomGenerator(9);
                } while (!unUsedInBox(grid, row, col, num));

                grid[row + i][col + j].setText(String.valueOf(num));
            }
        }
    }

    int randomGenerator(int num) {
        return (int) Math.floor((Math.random() * num + 1));
    }

    boolean CheckIfSafe(JTextField[][] grid, int i, int j, int num) {
        return (unUsedInRow(grid, i, num) &&
                unUsedInCol(grid, j, num) &&
                unUsedInBox(grid, i - i % 3, j - j % 3, num));
    }

    boolean unUsedInRow(JTextField[][] grid, int i, int num) {
        for (int j = 0; j < 9; j++)
            if (j < grid[i].length && grid[i][j].getText().equals(String.valueOf(num)))
                return false;
        return true;
    }

    boolean unUsedInCol(JTextField[][] grid, int j, int num) {
        for (int i = 0; i < 9; i++)
            if (i < grid.length && grid[i][j].getText().equals(String.valueOf(num)))
                return false;
        return true;
    }

    boolean fillRemaining(JTextField[][] grid, int i, int j) {
        if (i < 8 && j >= 9) {
            i = i + 1;
            j = 0;
        }
        if (i >= 9 && j >= 9)
            return true;

        if (i < 3) {
            if (j < 3)
                j = 3;
        } else if (i < 6) {
            if (j == (int) (i / 3) * 3)
                j = j + 3;
        } else {
            if (j == 6) {
                i = i + 1;
                j = 0;
                if (i >= 9)
                    return true;
            }
        }

        for (int num = 1; num <= 9; num++) {
            if (CheckIfSafe(grid, i, j, num)) {
                grid[i][j].setText(String.valueOf(num));
                if (fillRemaining(grid, i, j + 1))
                    return true;
                grid[i][j].setText(" ");
            }
        }
        return false;
    }

    public void removeKDigits(JTextField[][] grid,int k) {
        int count = k;
        while (count != 0) {
            int cellId = randomGenerator(9 * 9) - 1;
            int i = (cellId / 9);
            int j = cellId % 9;
            if (j != 0)
                j = j - 1;
            if (!grid[i][j].getText().equals(" ")) {
                count--;
                grid[i][j].setText(" ");
            }
        }
    }
}
