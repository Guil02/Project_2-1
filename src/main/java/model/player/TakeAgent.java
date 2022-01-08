package model.player;

import config.Config;
import controller.Board;
import controller.BoardUpdater;
import gui.DebugWindow.DebugWindowStage;
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
public class TakeAgent extends Player{

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
                // Stop if game is on pause
                if (DebugWindowStage.isOnPause) {
                    pauseThread();
                }

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
                    // Save the "score" of all the taken pieces in a new array with the index corresponding to takeMoves
                    int[] takeScore = new int[takeMoves.size()];
                    for (int i = 0; i < takeScore.length; i++) {
                        char currentPieceChar = Character.toUpperCase(board.getCharOffField(takeMoves.get(i)[2], takeMoves.get(i)[3]));
                        switch(currentPieceChar) {
                            case('K'): takeScore[i] = kingScore; break;
                            case('Q'): takeScore[i] = queenScore; break;
                            case('R'): takeScore[i] = rookScore; break;
                            case('B'): takeScore[i] = bishopScore; break;
                            case('N'): takeScore[i] = knightScore; break;
                            case('P'): takeScore[i] = pawnScore; break;
                            default: takeScore[i] = 1; System.out.println("ERROR IN SCORE DETERMINATION"); break;
                        }
                    }
                    // Get the index of the highest score out of the takeScore array
                    int maxScoreIndex = 0;
                    for (int i = 0; i < takeScore.length; i++) {
                        if (takeScore[i] > maxScoreIndex) {
                            maxScoreIndex = i;
                        }
                    }
                    int[] move = takeMoves.get(maxScoreIndex);
                    BoardUpdater.movePiece(board, move[0], move[1], move[2], move[3]);
                }

                if(Config.GUI_ON){
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
