package controller;

import config.Config;
import gui.DebugWindow.DebugWindowStage;
import javafx.application.Platform;
import model.GeneticAlgorithm.GA;
import model.pieces.*;
import model.player.NNAgent;
import model.player.TDLearningAgent;
import utils.GameGenerator;

/**
 * Class with a set of methods to mutate the board.
 */
public class BoardUpdater {

    /**
     * Fills the board with the default start setup.
     * @param board
     */
    public static void fillGameStart(Board board) {
        // Black side
        addPiece(board, new RookPiece(false, 0, 0));
        addPiece(board, new KnightPiece(false, 1, 0));
        addPiece(board, new BishopPiece(false, 2, 0));
        addPiece(board, new QueenPiece(false, 3, 0));
        addPiece(board, new KingPiece(false, 4, 0));
        addPiece(board, new BishopPiece(false, 5, 0));
        addPiece(board, new KnightPiece(false, 6, 0));
        addPiece(board, new RookPiece(false, 7, 0));
        for (int i = 0; i < 8; i++)
            addPiece(board, new PawnPiece(false, i, 1));
        // White side
        addPiece(board, new RookPiece(true, 0, 7));
        addPiece(board, new KnightPiece(true, 1, 7));
        addPiece(board, new BishopPiece(true, 2, 7));
        addPiece(board, new QueenPiece(true, 3, 7));
        addPiece(board, new KingPiece(true, 4, 7));
        addPiece(board, new BishopPiece(true, 5, 7));
        addPiece(board, new KnightPiece(true, 6, 7));
        addPiece(board, new RookPiece(true, 7, 7));
        for (int i = 0; i < 8; i++)
            addPiece(board, new PawnPiece(true, i, 6));
    }

    /**
     * Adds a piece to a board.
     * @param board target board
     * @param piece new piece
     */
    public static void addPiece(Board board, ChessPiece piece) {
        board.getBoardModel()[piece.getX()][piece.getY()]=piece;
    }

    /**
     * Removes a piece from the board.
     */
    public static void removePiece(Board board, int x, int y) {
        board.getBoardModel()[x][y]=null;
    }

    /**
     * Captures an opponents piece.
     * @param board target board
     * @param x target x-pos
     * @param y target y-pos
     */
    public static void capturePiece(Board board, int x, int y){
        if(board.getPieceOffField(x,y)!=null) {
            if (board.getPieceOffField(x,y).getPieceChar() == 'K') {
                board.getBoardModel()[x][y] = null;
                if(Config.GUI_ON && board.isOriginal()){
                    Platform.runLater(
                        new Thread(()->{
                            board.getGraphicsConnector().setWin(false);
                        })
                    );
                }
                board.setGameOver(true);
            } else if (board.getPieceOffField(x,y).getPieceChar() == 'k') {
                board.getBoardModel()[x][y] = null;
                if(Config.GUI_ON && board.isOriginal()){
                    Platform.runLater(
                            new Thread(()->{
                                board.getGraphicsConnector().setWin(true);
                            })
                    );
                }
                board.setGameOver(true);
            }
        }
    }

    /**
     * Special case of an en passant capture
     * @param board target board
     * @param x target x-pos
     * @param y target y-pos
     */
    public static void captureEnPassantField(Board board, int x, int y){
        if(board.getPieceOffField(x,y)!=null){
            board.getBoardModel()[x][y]=null;
        }
    }

    /**
     * Executes promotion (when opponents pawn reaches other side)
     * @param board current board
     * @param target target board
     * @param xFrom source x-pos
     * @param yFrom source y-pos
     * @param xTo target x-pos
     * @param yTo target y-pos
     */
    public static void runPromotion(Board board, Board target, int xFrom, int yFrom, int xTo, int yTo){
        boolean isWhite = board.getPieceOffField(xFrom, yFrom).isWhite();
        int pieceType = getPieceType(target.getCharOffField(xTo, yTo));
        ChessPiece promoted = BoardUpdater.createPiece(isWhite, xTo,yTo, pieceType);
        capturePiece(board, xTo, yTo);
        removePiece(board, xFrom, yFrom);
        addPiece(board, promoted);
        board.changeTurn();
        if(board.getAmountOfTurns()>200){
            board.setGameOver(true);
        }
        gameOver(board);
    }

    /**
     * Gets the numerical value of a piece type based on the letter (char).
     * @param pieceType input char
     * @return numeric value of the piece type
     */
    public static int getPieceType(char pieceType){
        switch(pieceType){
            case 'n','N':
                return 2;
            case 'b','B':
                return 3;
            case 'r','R':
                return 4;
            case 'q','Q':
                return 5;
        }
        return 0;
    }

    /**
     * Moves a piece from a source to a target location.
     * @param board board to perform the move on
     * @param xFrom source x-pos
     * @param yFrom source y-pos
     * @param xTo target x-pos
     * @param yTo target y-pos
     */
    public static void movePiece(Board board, int xFrom, int yFrom, int xTo, int yTo) {
        if(board.isOriginal()){
            board.storeMove();
        }
        try {
            if (Config.GUI_ON && !(board.getPlayer1() == 0) && !(board.getPlayer2() == 0))
                Thread.sleep(DebugWindowStage.delayMS);
        } catch (Exception e){};
        ChessPiece pieceToMove = board.getPieceOffField(xFrom, yFrom);
        if(pieceToMove!= null){
            pieceToMove.move(board, xTo,yTo);
            capturePiece(board, xTo, yTo);
            removePiece(board, xFrom, yFrom);
            addPiece(board, pieceToMove);
        }
        if(!board.getGameOver()&& board.isOriginal() && board.isHumanPlayer()) {
            startPromotionDialog(board, pieceToMove, xTo, yTo);
        }
        board.changeTurn();
        if(board.getAmountOfTurns()>500){
            board.setGameOver(true);
        }
        gameOver(board);
    }

    /**
     * Sets a board to a state of "game over".
     * @param board target board
     */
    private static void gameOver(Board board) {
        if(board.getGameOver()&& board.isOriginal()){
            board.storeMove();
            if(Config.GA){
                GA.training = false;
            }
            if(GameRunner.EXPERIMENT1){
                if(!board.containsKing(false)){
                    board.getGameRunner().incrementWhiteWin();
                }
                else if(!board.containsKing(true)){
                    board.getGameRunner().incrementBlackWin();
                }
                System.out.println("white wins: "+ board.getGameRunner().getWhiteWin());
                System.out.println("black wins: "+ board.getGameRunner().getBlackWin());
                if(board.getGameRunner().continuePlaying()){
                    board.getGameRunner().reset();
                }
            }
            if(Config.GENERATE_GAMES){
                System.out.println("generating new game");
                GameGenerator.writeGame(board);
                board.getGameRunner().reset();
            }
            else {
                if (board.getPlayer1() == 3 && TDLearningAgent.LEARN && board.isOriginal()) {
                    System.out.println("Game is over");
                    TDLearningAgent.learn(board);
                }
                if (board.getPlayer1() == 5 && NNAgent.LEARN && board.isOriginal()) {
                    ((NNAgent) board.playerOne).learn(board);
                }
            }
        }
    }

    /**
     * Starts a dialog when promotion is happening.
     * @param board target board
     * @param targetPiece target piece
     * @param xTo target x-pos
     * @param yTo target y-pos
     */
    public static void startPromotionDialog(Board board, ChessPiece targetPiece, int xTo, int yTo) {
        if(targetPiece.isOnOppositeRow(yTo) && (targetPiece.getPieceChar()=='p' || targetPiece.getPieceChar()=='P')){
            board.getGraphicsConnector().startPromotionDialog(targetPiece.isWhite(), board, xTo, yTo);
        }
    }

    /**
     * Does promotion on the board.
     * @param board target board
     * @param piece target piece
     */
    public static void doPromotion(Board board, ChessPiece piece){
        removePiece(board, piece.getX(),piece.getY());
        addPiece(board, piece);
        board.getGraphicsConnector().updateImages();
    }

    /**
     * Creates a new piece.
     * @param isWhite true if it is white, false if it is black
     * @param x x-pos
     * @param y y-pos
     * @param pieceType type of the piece in numerical form
     * @return new ChessPiece
     */
    public static ChessPiece createPiece(boolean isWhite, int x, int y, int pieceType){
        return switch (pieceType) {
            case 2 -> new KnightPiece(isWhite, x, y);
            case 3 -> new BishopPiece(isWhite, x, y);
            case 4 -> new RookPiece(isWhite, x, y);
            case 5 -> new QueenPiece(isWhite, x, y);
            default -> null;
        };
    }

    /**
     * Removes all pieces from the board.
     * @param board target board
     */
    public static void clearBoard(Board board){
        for(int i = 0; i<Board.getBoardSize(); i++){
            for(int j = 0; j<Board.getBoardSize(); j++){
                board.getBoardModel()[i][j]=null;
            }
        }
    }
}
