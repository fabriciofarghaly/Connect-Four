import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PVE {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Run part A, B, or C?: ");
        String select = (scan.nextLine().toUpperCase());
        System.out.print("Include debugging info? (y/n): ");
        //String debugstr = (scan.nextLine());
        System.out.print("How many rows do you want on the board?: ");
        int row = Integer.parseInt(scan.nextLine());
        System.out.print("How many columns do you want on the board?: ");
        int col = Integer.parseInt(scan.nextLine());
        System.out.print("Enter number in a row to win: ");
        int con = Integer.parseInt(scan.nextLine());
        int depth = 0;
        if (select.equals("C")) {
            System.out.print("Number of moves to look ahead (depth): ");
            depth = Integer.parseInt(scan.nextLine());
        }

        while (true) {
            Board board = new Board(row, col, con);
            Map<Board, MinimaxInfo> table = new HashMap<>();
            MinimaxInfo minimaxInfo;
            if (select.equals("A")) {
                minimaxInfo = Minimax.minimaxsearch(board, table);
                System.out.println("Transposition table has " + table.size() + " states.");
            } else if (select.equals("B")) {
                table = new HashMap<>();
                Minimax.prunings = 0;
                minimaxInfo = Minimax.alphaBetaSearch(board, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, table);
                System.out.println("Transposition table has " + table.size() + " states.");
                System.out.println("The tree was pruned " + Minimax.prunings + " times.");
            } else {
                table = new HashMap<>();
                Minimax.prunings = 0;
                Minimax.maxDepth = depth;
                minimaxInfo = Minimax.alphaBetaHeuristicSearch(board, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0, table);
                System.out.println("Transposition table has " + table.size() + " states.");
                System.out.println("The tree was pruned " + Minimax.prunings + " times.");
            }

            if (!select.equals("C")) {
                if (minimaxInfo.MinimaxValue() > 0)
                    System.out.println("First player has a guaranteed win with perfect play.");
                else if (minimaxInfo.MinimaxValue() < 0)
                    System.out.println("Second player has a guaranteed win with perfect play.");
                else
                    System.out.println("Neither player has a guaranteed win; game will end in tie with perfect play on both sides.");
            }

            System.out.print("Who plays first? 1=human, 2=computer: ");
            int turnorder = Integer.parseInt(scan.nextLine());

            System.out.println("Here is the board:");
            System.out.println(board.to2DString());

            while (board.getGameState() == GameState.IN_PROGRESS) {
                int val = (int) minimaxInfo.MinimaxValue();
                System.out.println("Minimax value for this state: " + val + ", optimal move: " + minimaxInfo.bestMove());
                System.out.println("It is " + board.getPlayerToMoveNext() + "'s turn!");

                if ((turnorder == 1 && board.getPlayerToMoveNext() == Player.MAX) || (turnorder == 2 && board.getPlayerToMoveNext() == Player.MIN)) {
                    System.out.print("Enter Move: ");
                    int play = Integer.parseInt(scan.nextLine());
                    board = board.makeMove(play);
                } else {
                    System.out.println("Computer chooses move: " + minimaxInfo.bestMove());
                    board = board.makeMove(minimaxInfo.bestMove());
                }

                System.out.println("Here is the updated board:");
                System.out.println(board.to2DString());

                if (select.equals("C")) {
                    table = new HashMap<>();
                    minimaxInfo = Minimax.alphaBetaHeuristicSearch(board, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0, table);
                    System.out.println("Transposition table has " + table.size() + " states.");
                } else if (table.containsKey(board)) {
                    minimaxInfo = Minimax.minimaxsearch(board, table);
                } else {
                    table = new HashMap<>();
                    minimaxInfo = Minimax.alphaBetaSearch(board, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, table);
                }
            }

            if (board.getGameState() == GameState.TIE) {
                System.out.println("The game is a tie!");
            } else {
                String output = board.getWinner() + " is the winner! ";
                if (turnorder == 1) {
                    if (board.getWinner() == Player.MAX) {
                        output += "(human)";
                    } else {
                        output += "(computer)";
                    }
                } else {
                    if (board.getWinner() == Player.MAX) {
                        output += "(computer)";
                    } else {
                        output += "(human)";
                    }
                }
                System.out.println(output);
            }

            System.out.print("Play again? (y/n): ");
            if (scan.nextLine().equals("n")) break;
        }
    }
}
