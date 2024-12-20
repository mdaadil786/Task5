 import javax.swing.*;
import java.awt.*;

    public class SudokuSolverGUI {
        private static final int GRID_SIZE = 9; // 9x9 Sudoku grid
        private static final int SUBGRID_SIZE = 3; // 3x3 subgrid
        private JTextField[][] grid = new JTextField[GRID_SIZE][GRID_SIZE];

        public static void main(String[] args) {
            SwingUtilities.invokeLater(SudokuSolverGUI::new);
        }

        public SudokuSolverGUI() {
            // Create the main frame
            JFrame frame = new JFrame("Sudoku Solver");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 500);
            frame.setLayout(new BorderLayout());

            // Create the grid panel
            JPanel gridPanel = new JPanel();
            gridPanel.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
            for (int row = 0; row < GRID_SIZE; row++) {
                for (int col = 0; col < GRID_SIZE; col++) {
                    grid[row][col] = new JTextField();
                    grid[row][col].setHorizontalAlignment(JTextField.CENTER);
                    grid[row][col].setFont(new Font("Arial", Font.BOLD, 18));
                    gridPanel.add(grid[row][col]);
                }
            }

            // Buttons panel
            JPanel buttonsPanel = new JPanel();
            buttonsPanel.setLayout(new FlowLayout());

            JButton solveButton = new JButton("Solve");
            JButton clearButton = new JButton("Clear");

            buttonsPanel.add(solveButton);
            buttonsPanel.add(clearButton);

            // Add components to the frame
            frame.add(gridPanel, BorderLayout.CENTER);
            frame.add(buttonsPanel, BorderLayout.SOUTH);

            // Action listener for the solve button
            solveButton.addActionListener(e -> {
                int[][] board = new int[GRID_SIZE][GRID_SIZE];
                if (readInput(board)) {
                    if (solveSudoku(board)) {
                        displaySolution(board);
                    } else {
                        JOptionPane.showMessageDialog(frame, "No solution exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Use numbers 1-9 or leave cells empty.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            // Action listener for the clear button
            clearButton.addActionListener(e -> {
                for (int row = 0; row < GRID_SIZE; row++) {
                    for (int col = 0; col < GRID_SIZE; col++) {
                        grid[row][col].setText("");
                    }
                }
            });

            // Show the frame
            frame.setVisible(true);
        }

        // Reads input from the grid into a 2D array
        private boolean readInput(int[][] board) {
            try {
                for (int row = 0; row < GRID_SIZE; row++) {
                    for (int col = 0; col < GRID_SIZE; col++) {
                        String text = grid[row][col].getText().trim();
                        if (!text.isEmpty()) {
                            int value = Integer.parseInt(text);
                            if (value < 1 || value > 9) {
                                return false;
                            }
                            board[row][col] = value;
                        } else {
                            board[row][col] = 0; // Empty cells are 0
                        }
                    }
                }
            } catch (NumberFormatException e) {
                return false;
            }
            return true;
        }

        // Displays the solved Sudoku grid in the GUI
        private void displaySolution(int[][] board) {
            for (int row = 0; row < GRID_SIZE; row++) {
                for (int col = 0; col < GRID_SIZE; col++) {
                    grid[row][col].setText(String.valueOf(board[row][col]));
                }
            }
        }

        // Solves the Sudoku puzzle using backtracking
        private boolean solveSudoku(int[][] board) {
            for (int row = 0; row < GRID_SIZE; row++) {
                for (int col = 0; col < GRID_SIZE; col++) {
                    if (board[row][col] == 0) { // Empty cell
                        for (int num = 1; num <= GRID_SIZE; num++) {
                            if (isSafe(board, row, col, num)) {
                                board[row][col] = num;
                                if (solveSudoku(board)) {
                                    return true;
                                }
                                board[row][col] = 0; // Backtrack
                            }
                        }
                        return false; // No solution found
                    }
                }
            }
            return true; // Solved
        }

        // Checks if placing a number is valid
        private boolean isSafe(int[][] board, int row, int col, int num) {
            for (int i = 0; i < GRID_SIZE; i++) {
                if (board[row][i] == num || board[i][col] == num ||
                        board[row - row % SUBGRID_SIZE + i / SUBGRID_SIZE][col - col % SUBGRID_SIZE + i % SUBGRID_SIZE] == num) {
                    return false;
                }
            }
            return true;
        }
    }


