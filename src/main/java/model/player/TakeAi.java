package model.player;

import controller.Board;
import controller.BoardUpdater;
import javafx.application.Platform;
import model.pieces.ChessPiece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

/**
 * This agent is an improvement of the baseline agent. It looks at the current board and if there
 * is any piece that it can take, it will do so.
 * If there are multiple pieces to take, it will take the most "valuable".
 * If there is a draw between two equally maximum valuable pieces, it will decide randomly (e.g. two knights).
 */
public class TakeAi extends Player{

    private int kingScore = 1000;
    private int queenScore = 100;
    private int rookScore = 50;
    private int bishopScore = 40;
    private int knightScore = 30;
    private int pawnScore = 10;

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
                printMoves(allMoves);

                // Scan all moves for a set of moves that take pieces
                ArrayList<int[]> takeMoves = new ArrayList<int[]>();
                for (int[] entry : allMoves) {
                    if (board.getCharOffField(entry[2], entry[3]) != '-') {
                        takeMoves.add(entry);
                    }
                }

                // Execute move

                if (takeMoves.size() == 0) { // If AI can't take a piece right now, do a random move
                    Random rand = new Random();
                    int[] move = allMoves.get(rand.nextInt(allMoves.size()));
                    BoardUpdater.movePiece(board, move[0], move[1], move[2], move[3]);
                }
                else {
                    Random rand = new Random();
                    int[] move = takeMoves.get(rand.nextInt(takeMoves.size()));
                    BoardUpdater.movePiece(board, move[0], move[1], move[2], move[3]);
                }

                if(Board.GUI_ON){
                    Platform.runLater(
                            new Thread(board::launchGuiUpdate)
                    );
                }
            }
            catch(Exception e) {
                System.err.println("Piece might already have been moved due to glitch in the threading");
                e.printStackTrace();
            }
        }).start();
    }

    void printMoves(ArrayList<int[]> allMoves) {
        System.out.println("--- All Moves: ---");
        for (int[] entry : allMoves) {
            System.out.println(Arrays.toString(entry));
        }
    }
}
