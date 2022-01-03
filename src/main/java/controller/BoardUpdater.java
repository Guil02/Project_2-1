package controller;

import config.Config;
import javafx.application.Platform;
import model.pieces.*;
import model.player.NNAgent;
import model.player.TDLearningAgent;
import utils.GameGenerator;

public class BoardUpdater {


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
     * @param piece
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

    public static void captureEnPassantField(Board board, int x, int y){
        if(board.getPieceOffField(x,y)!=null){
            board.getBoardModel()[x][y]=null;
        }
    }

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

    public static void movePiece(Board board, int xFrom, int yFrom, int xTo, int yTo) {
//        if(board.isOriginal()) System.out.println("did a move");
        board.storeMove();
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
        if(board.getAmountOfTurns()>200){
            board.setGameOver(true);
        }
        gameOver(board);
    }

    private static void gameOver(Board board) {
        if(board.getGameOver()&& board.isOriginal()){
            board.storeMove();

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
            if(GameRunner.GENERATE_GAMES){
                System.out.println("generating new game");
                GameGenerator.writeGame(board);
                board.getGameRunner().reset();
            }
            else {
                if (board.getPlayer1() == 3 && TDLearningAgent.LEARN && board.isOriginal()) {
                    TDLearningAgent.learn(board);
                }
                if (board.getPlayer1() == 5 && NNAgent.LEARN && board.isOriginal()) {
                    double[] endEval = ((NNAgent) board.playerOne).computeEndEval(board);

                    ((NNAgent) board.playerOne).learn(board, board.getBoardStates(), endEval);
                }
            }
        }
    }

    public static void startPromotionDialog(Board board, ChessPiece targetPiece, int xTo, int yTo) {
        if(targetPiece.isOnOppositeRow(yTo) && (targetPiece.getPieceChar()=='p' || targetPiece.getPieceChar()=='P')){
            board.getGraphicsConnector().startPromotionDialog(targetPiece.isWhite(), board, xTo, yTo);
        }
    }
    public static void doPromotion(Board board, ChessPiece piece){
        removePiece(board, piece.getX(),piece.getY());
        addPiece(board, piece);
        board.getGraphicsConnector().updateImages();
    }

    public static ChessPiece createPiece(boolean isWhite, int x, int y, int pieceType){
        return switch (pieceType) {
            case 2 -> new KnightPiece(isWhite, x, y);
            case 3 -> new BishopPiece(isWhite, x, y);
            case 4 -> new RookPiece(isWhite, x, y);
            case 5 -> new QueenPiece(isWhite, x, y);
            default -> null;
        };
    }

    public static boolean containsKing(Board board, boolean white){
        if(white){
            for(int i = 0; i<Board.getBoardSize(); i++){
                for(int j = 0; j<Board.getBoardSize(); j++){
                    if(board.getPieceOffField(i,j)!=null){
                        if(board.getCharOffField(i,j)=='K'){
                            return true;
                        }
                    }
                }
            }
        }
        else{
            for(int i = 0; i<Board.getBoardSize(); i++){
                for(int j = 0; j<Board.getBoardSize(); j++){
                    if(board.getPieceOffField(i,j)!=null){
                        if(board.getCharOffField(i,j)=='k'){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void clearBoard(Board board){
        for(int i = 0; i<Board.getBoardSize(); i++){
            for(int j = 0; j<Board.getBoardSize(); j++){
                board.getBoardModel()[i][j]=null;
            }
        }
    }

    public static void printBoard(ChessPiece[][] boardModel, Board board) {
        System.out.println("--- Board State ---\n");
        for(int i = 0; i < boardModel[0].length; i++) {
            for (int j = 0; j < boardModel.length; j++) {
                System.out.print("[ " + board.getCharOffField(j,i) + " ] ");
                // System.out.print("[ " + j + " " + i + " ] ");
            }
            System.out.println();
        }
    }

}
