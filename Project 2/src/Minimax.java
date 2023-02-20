import java.util.Map;

public class Minimax {
    public static int prunings = 0;
    public static int maxDepth = 0;

    public static MinimaxInfo minimaxsearch(Board board, Map<Board, MinimaxInfo> table) {
        if (table.containsKey(board)) {
            return table.get(board);
        } else if (board.getGameState() != GameState.IN_PROGRESS) {
            int utility = Utility(board);
            MinimaxInfo info = new MinimaxInfo(utility, null);
            table.put(board, info);
            return info;
        } else if (board.getPlayerToMoveNext() == Player.MAX) {
            double v = Double.NEGATIVE_INFINITY;
            Integer bestMove = null;
            for (int i = 0; i < board.getCols(); i++) {
                if (board.isColumnFull(i)) {
                    continue;
                }
                Board childState = board.makeMove(i);
                MinimaxInfo childInfo = minimaxsearch(childState, table);
                double v2 = childInfo.MinimaxValue();
                if (v2 > v) {
                    v = v2;
                    bestMove = i;
                }
            }
            MinimaxInfo info = new MinimaxInfo(v, bestMove);
            table.put(board, info);
            return info;
        } else {
            double v = Double.POSITIVE_INFINITY;
            Integer bestMove = null;
            for (int i = 0; i < board.getCols(); i++) {
                if (board.isColumnFull(i)) {
                    continue;
                }
                Board childState = board.makeMove(i);
                MinimaxInfo childInfo = minimaxsearch(childState, table);
                double v2 = childInfo.MinimaxValue();
                if (v2 < v) {
                    v = v2;
                    bestMove = i;
                }
            }
            MinimaxInfo info = new MinimaxInfo(v, bestMove);
            table.put(board, info);
            return info;
        }
    }

    public static MinimaxInfo alphaBetaSearch(Board board, double alpha, double beta, Map<Board, MinimaxInfo> table) {
        if (table.containsKey(board)) {
            return table.get(board);
        } else if (board.getGameState() != GameState.IN_PROGRESS) {
            int utility = Utility(board);
            MinimaxInfo info = new MinimaxInfo(utility, null);
            table.put(board, info);
            return info;
        } else if (board.getPlayerToMoveNext() == Player.MAX) {
            double v = Double.NEGATIVE_INFINITY;
            Integer bestMove = null;
            for (int i = 0; i < board.getCols(); i++) {
                if (board.isColumnFull(i)) {
                    continue;
                }
                Board childState = board.makeMove(i);
                MinimaxInfo childInfo = alphaBetaSearch(childState, alpha, beta, table);
                double v2 = childInfo.MinimaxValue();
                if (v2 > v) {
                    v = v2;
                    bestMove = i;
                    alpha = Math.max(alpha, v);
                }
                if (v >= beta) { // prune tree, don't store state in TT
                    prunings++;
                    return new MinimaxInfo(v, bestMove);
                }
            }
            MinimaxInfo info = new MinimaxInfo(v, bestMove);
            table.put(board, info);
            return info;
        } else {
            double v = Double.POSITIVE_INFINITY;
            Integer bestMove = null;
            for (int i = 0; i < board.getCols(); i++) {
                if (board.isColumnFull(i)) {
                    continue;
                }
                Board childState = board.makeMove(i);
                MinimaxInfo childInfo = alphaBetaSearch(childState, alpha, beta, table);
                double v2 = childInfo.MinimaxValue();
                if (v2 < v) {
                    v = v2;
                    bestMove = i;
                    beta = Math.min(beta, v);
                }
                if (v <= alpha) {
                    prunings++;
                    return new MinimaxInfo(v, bestMove);
                }
            }
            MinimaxInfo info = new MinimaxInfo(v, bestMove);
            table.put(board, info);
            return info;
        }
    }

    public static MinimaxInfo alphaBetaHeuristicSearch(Board board, double alpha, double beta, int depth, Map<Board, MinimaxInfo> table) {
        if (table.containsKey(board)) {
            return table.get(board);
        } else if (board.getGameState() != GameState.IN_PROGRESS) {
            int utility = Utility(board);
            MinimaxInfo info = new MinimaxInfo(utility, null);
            table.put(board, info);
            return info;

        } else if (depth == maxDepth) {
            int heuristic = eval(board);
            MinimaxInfo info = new MinimaxInfo(heuristic, null);
            table.put(board, info);
            return info;

        } else if (board.getPlayerToMoveNext() == Player.MAX) {
            double v = Double.NEGATIVE_INFINITY;
            Integer bestMove = null;
            for (int i = 0; i < board.getCols(); i++) {
                if (board.isColumnFull(i)) {
                    continue;
                }
                Board childState = board.makeMove(i);
                MinimaxInfo childInfo = alphaBetaHeuristicSearch(childState, alpha, beta, depth + 1, table);
                double v2 = childInfo.MinimaxValue();
                if (v2 > v) {
                    v = v2;
                    bestMove = i;
                    alpha = Math.max(alpha, v);
                }
                if (v >= beta) { // prune tree, don't store state in TT
                    prunings++;
                    return new MinimaxInfo(v, bestMove);
                }
            }
            MinimaxInfo info = new MinimaxInfo(v, bestMove);
            table.put(board, info);
            return info;

        } else {
            double v = Double.POSITIVE_INFINITY;
            Integer bestMove = null;
            for (int i = 0; i < board.getCols(); i++) {
                if (board.isColumnFull(i)) {
                    continue;
                }
                Board childState = board.makeMove(i);
                MinimaxInfo childInfo = alphaBetaHeuristicSearch(childState, alpha, beta, depth + 1, table);
                double v2 = childInfo.MinimaxValue();
                if (v2 < v) {
                    v = v2;
                    bestMove = i;
                    beta = Math.min(beta, v);
                }
                if (v <= alpha) {
                    prunings++;
                    return new MinimaxInfo(v, bestMove);
                }
            }
            MinimaxInfo info = new MinimaxInfo(v, bestMove);
            table.put(board, info);
            return info;

        }
    }

    public static int Utility(Board board) {
        if (board.getGameState() == GameState.TIE) {
            return 0;
        } else {
            int utility = (int) (10000.0 * board.getRows() * board.getCols() / board.getNumberOfMoves());
            if (board.getWinner() == Player.MAX) {
                return utility;
            } else {
                return -utility;
            }

        }
    }

    public static int eval(Board board) {
        return board.eval();
    }
}