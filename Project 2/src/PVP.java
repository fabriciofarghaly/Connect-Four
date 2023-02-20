import java.util.Scanner;

public class PVP
{
    public static void main (String[] args)
    {
        //Ask user how to set up board (dimensions)
        Scanner scan = new Scanner(System.in);
        System.out.println("How many rows do you want on the board?: ");
        int row = Integer.parseInt(scan.nextLine());
        System.out.println("How many columns do you want on the board?: ");
        int col = Integer.parseInt(scan.nextLine());
        System.out.println("How many consecutive tokens to win?: ");
        int con = Integer.parseInt(scan.nextLine());
        Board board = new Board(row, col, con);
        System.out.println("Here is the board:");
        System.out.println(board.to2DString());

        //Player 1 = MAX
        //Player 2 = MIN
        //Below is the gameplay stuff

        System.out.println("Where to move - PLAYER 1 (enter a column number)");
        board = board.makeMove(Integer.parseInt(scan.nextLine()));
        System.out.println("Here is the updated board:");
        System.out.println(board.to2DString());
        System.out.println("State of the game: " + board.getGameState());
        while (board.getGameState() == GameState.IN_PROGRESS)
        {
            if (board.getPlayerToMoveNext() == Player.MAX)
            {
                System.out.println("Where to move - PLAYER 1 (enter a column number)");
                board = board.makeMove(Integer.parseInt(scan.nextLine()));
                System.out.println("Here is the updated board:");
                System.out.println(board.to2DString());
                System.out.println("State of the game: " + board.getGameState());
            }

            else
            {
                System.out.println("Where to move - PLAYER 2 (enter a column number)");
                board = board.makeMove(Integer.parseInt(scan.nextLine()));
                System.out.println("Here is the updated board:");
                System.out.println(board.to2DString());
                System.out.println("State of the game: " + board.getGameState());
            }
        }

    }
}
