package model.player;

import config.Config;
import controller.Board;
import controller.BoardUpdater;
import gui.DebugWindow.DebugWindowStage;
import javafx.application.Platform;
import model.pieces.ChessPiece;
import java.util.LinkedList;
import java.util.Random;

/**
 * This agent has no strategy. It just picks random moves based on the roll of the dice.
 */
public class BaselineAgent extends Player{

    Object pauseLock = DebugWindowStage.pauseLock;

    /**
     * Launches a single move of the baseline agent.
     * @param board
     */
    public void launch(Board board) {
        System.gc();
            new Thread(() -> {
                try {
                    Thread t = Thread.currentThread();
                    t.setName("AI");

                    if (DebugWindowStage.isOnPause) {
                        pauseThread();
                    }

                    Thread.sleep(50);
                    char movablePieceChar = board.getMovablePiece();
                    // Select random piece if multiple pieces are movable
                    LinkedList<ChessPiece> pieceList = new LinkedList<ChessPiece>();
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (movablePieceChar == board.getCharOffField(i, j)) {
                                pieceList.add(board.getPieceOffField(i, j));
                            }
                        }
                    }
                    ChessPiece randomPiece;
                    boolean repeat = true;
                    LinkedList<int[]> targetPositions = new LinkedList<>();
                    int pieceX = 0;
                    int pieceY = 0;
                    while (repeat) { // In case the target piece has no valid moves, it looks for a different piece.
                        Random rand = new Random();
                        randomPiece = pieceList.get(rand.nextInt(pieceList.size()));
                        pieceX = randomPiece.getX();
                        pieceY = randomPiece.getY();

                        // Select random target position of the selected piece
                        boolean[][] validMoves = randomPiece.validMoves(board);
                        if (targetPositions.size() != 0)
                            targetPositions = new LinkedList<>();
                        for (int i = 0; i < 8; i++) {
                            for (int j = 0; j < 8; j++) {
                                if (validMoves[i][j]) {
                                    targetPositions.add(new int[]{i, j});
                                }
                            }
                        }
                        if (targetPositions.size() != 0)
                            repeat = false;
                        else
                            pieceList.remove(randomPiece);
                    }
                    Random rand = new Random();
                    int[] destination = targetPositions.get(rand.nextInt(targetPositions.size()));
                    int destinationX = destination[0];
                    int destinationY = destination[1];

                    // Execute move
                    BoardUpdater.movePiece(board, pieceX, pieceY, destinationX, destinationY);
                    if (Config.GUI_ON) {
                        Platform.runLater(
                                new Thread(board::launchGuiUpdate)
                        );
                    }
                } catch (Exception e) {
                    System.err.println("Piece might already have been moved due to glitch in the threading");
                    e.printStackTrace();
                }
            }).start();
    }

    private void pauseThread() throws InterruptedException {
        synchronized (pauseLock) {
            pauseLock.wait();
        }
    }
}
