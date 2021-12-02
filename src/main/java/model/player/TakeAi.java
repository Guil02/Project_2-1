package model.player;

import controller.Board;
import model.pieces.ChessPiece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * This agent is an improvement of the baseline agent. It looks at the current board and if there
 * is any piece that it can take, it will do so.
 * If there are multiple pieces to take, it will take the most "valuable".
 * If there is a draw between two equally maximum valuable pieces, it will decide randomly (e.g. two knights).
 */
public class TakeAi extends Player{

    /**
     * Launches a single move of the Take-AI.
     * @param board
     */
    public void launch(Board board) {
        System.gc();
        new Thread(() -> {
            try{
                Thread.sleep(50);
                // Actual logic for the move starts here
                ArrayList<int[]> allMoves = new ArrayList<int[]>();
                ArrayList<ChessPiece> pieceList = new ArrayList<ChessPiece>();
                char movablePieceChar = board.getMovablePiece(); // All movable pieces
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (movablePieceChar == board.getCharOffField(i, j)) {
                            pieceList.add(board.getPieceOffField(i, j));
                        }
                    }
                }
                // Get all the moves for each piece
                for (ChessPiece piece : pieceList) {
                    boolean[][] validMoves = piece.validMoves(board);
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (validMoves[i][j]) {
                                allMoves.add(new int[]{piece.getX(), piece.getY(), i, j});
                            }
                        }
                    }
                }
                // For debug: Print all moves:
                System.out.println("All Moves:");
                for (int[] entry : allMoves) {
                    System.out.println("---");
                    System.out.println(Arrays.toString(entry));
                }
            }
            catch(Exception e) {
                System.err.println("Piece might already have been moved due to glitch in the threading");
                e.printStackTrace();
            }
        }).start();
    }
}
