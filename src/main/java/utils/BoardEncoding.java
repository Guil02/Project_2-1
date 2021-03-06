package utils;

import controller.Board;
import controller.BoardUpdater;
import model.pieces.ChessPiece;

import java.util.Arrays;

public class BoardEncoding {
    public BoardEncoding() {
    }

    private static final char[] pieceTypes = {'K', 'k', 'Q', 'q', 'R', 'r', 'B', 'b', 'N', 'n', 'P', 'p'};
    public double[] boardToArray(Board board) {
        int index = 0;
        String fen = FenEvaluator.write(board);
        double[] list = new double[315];

        if (board.getWhiteMove()) {
            list[index] = 1;
        } else {
            list[index] = 0;
        }
        index++;

        setCastling(list, fen, index);
        index += 4;

        amountOfPieces(list, board, index);
        index += 12;

        evaluatePieces(list, board, index);
        index += 170;

        attackDefendMap(list, board, index);
        index += 128;


//        System.out.println(Arrays.toString(list));
//        System.out.println(index-1);

        return list;

    }

    public double[] boardToArray2(Board board){
        int index = 0;
        String fen = FenEvaluator.write(board);
        double[] list = new double[13];

        if (board.getWhiteMove()) {
            list[index] = 1;
        } else {
            list[index] = 0;
        }
        index++;

        amountOfPieces(list, board, index);
        index += 12;

        return list;
    }

    public double[] boardToArray3(Board board) {
        int index = 0;
        String fen = FenEvaluator.write(board);
        double[] list = new double[46];

        if (board.getWhiteMove()) {
            list[index] = 1;
        } else {
            list[index] = 0;
        }
        index++;

//        setCastling(list, fen, index);
//        index += 4;

        amountOfPieces(list, board, index);
        index += 12;

//        evaluatePieces(list, board, index);
        evaluatePieceMobility(list, board, index);
        index += 32;


//        attackDefendMap(list, board, index);
//        index += 128;


//        System.out.println(Arrays.toString(list));
//        System.out.println(index-1);

        return list;
    }

    private void setCastling(double[] features, String fen, int startIndex) {
        int index = 0;
        while (fen.charAt(index) != ' ') {
            index++;
        }
        index += 3;

        for (int i = startIndex; i < startIndex + 4; i++) {
            features[i] = 0;
        }

        while (fen.charAt(index) != ' ') {
            switch (fen.charAt(index)) {
                case 'K' -> features[startIndex] = 1;
                case 'Q' -> features[startIndex + 1] = 1;
                case 'k' -> features[startIndex + 2] = 1;
                case 'q' -> features[startIndex + 3] = 1;
            }
            index++;
        }
    }

    private void amountOfPieces(double[] features, Board board, int startIndex) {
        for (char c : pieceTypes) {
            int amount = 0;
            for (int i = 0; i < Board.getBoardSize(); i++) {
                for (int j = 0; j < Board.getBoardSize(); j++) {
                    if (board.getCharOffField(i, j) == c) {
                        amount++;
                    }
                }
            }
            features[startIndex] = amount;
            startIndex++;
        }
    }

    private void slidingPieceMobility(double[] features, Board board, int startIndex, ChessPiece piece) {
        if (piece != null && isSlidingPiece(piece.getPieceChar())) {
            boolean[][] validMoves = piece.validMoves(board);
            int value = getAmountOfMoves(validMoves);
            features[startIndex] = value;
        }
    }

    private int getAmountOfMoves(boolean[][] validMoves) {
        int val = 0;
        for (boolean[] validMove : validMoves) {
            for (boolean b : validMove) {
                if (b) {
                    val++;
                }
            }
        }
        return val;
    }

    private void attackDefendMap(double[] features, Board board, int startIndex){
        for(int i = 0; i < Board.getBoardSize(); i++){
            for(int j = 0; j<Board.getBoardSize(); j++){
//                System.out.println("lowest attacker: "+getLowestValueAttacker(board, i,j, board.getWhiteMove()));
//                System.out.println("lowest defender: "+getLowestValueDefender(board, i,j, board.getWhiteMove()));
//                System.out.println("x: "+j+", y: "+i);
                features[startIndex++] = getLowestValueAttacker(board, j,i, board.getWhiteMove());
                features[startIndex++] = getLowestValueDefender(board, j, i, board.getWhiteMove());
            }
        }
//        BoardUpdater.printBoard(board.getBoardModel(), board);
    }

    private void evaluatePieceMobility(double[] features, Board board, int startIndex){
        int amountToFind;
        for (char c : pieceTypes) {
            switch (c) {
                case 'K', 'k', 'Q', 'q' -> {
                    for (int i = startIndex; i < startIndex + 1; i++) {
                        features[i] = 0;
                    }
                    amountToFind = 1;
                    startIndex = getSlidingMobilityOfField(features, board, startIndex, c, amountToFind);
                }
                case 'R', 'r', 'B', 'b', 'N', 'n' -> {
                    for (int i = startIndex; i < startIndex + 2; i++) {
                        features[i] = 0;
                    }
                    amountToFind = 2;
                    startIndex = getSlidingMobilityOfField(features, board, startIndex, c, amountToFind);
                }
                case 'P', 'p' -> {
                    for (int i = startIndex; i < startIndex + 8; i++) {
                        features[i] = 0;
                    }
                    amountToFind = 8;
                    startIndex = getSlidingMobilityOfField(features, board, startIndex, c, amountToFind);
                }
            }
        }
    }

    private void evaluatePieces(double[] features, Board board, int startIndex) {
        int amountToFind;
        for (char c : pieceTypes) {
            switch (c) {
                case 'K', 'k', 'Q', 'q' -> {
                    for (int i = startIndex; i < startIndex + 2; i++) {
                        features[i] = 0;
                    }
                    amountToFind = 1;
                    startIndex = getOfField(features, board, startIndex, c, amountToFind);
                }
                case 'R', 'r', 'B', 'b', 'N', 'n' -> {
                    for (int i = startIndex; i < startIndex + 4; i++) {
                        features[i] = 0;
                    }
                    amountToFind = 2;
                    startIndex = getOfField(features, board, startIndex, c, amountToFind);
                }
                case 'P', 'p' -> {
                    for (int i = startIndex; i < startIndex + 16; i++) {
                        features[i] = 0;
                    }
                    amountToFind = 8;
                    startIndex = getOfField(features, board, startIndex, c, amountToFind);
                }
            }
        }
    }

    private int getOfField(double[] features, Board board, int startIndex, char c, int amountToFind) {
        int amountFound = 0;
        for (int i = 0; i < Board.getBoardSize(); i++) {
            for (int j = 0; j < Board.getBoardSize(); j++) {
                if (board.getCharOffField(i, j) == c) {
                    if(amountFound<amountToFind){

                        features[startIndex++] = 1;
                        features[startIndex++] = ((double) i) / Board.getBoardSize();
                        features[startIndex++] = ((double) j) / Board.getBoardSize();
                        features[startIndex++] = getLowestValueAttacker(board, i, j, isWhite(c));
                        features[startIndex++] = getLowestValueDefender(board, i, j, isWhite(c));
                        slidingPieceMobility(features, board, startIndex++, board.getPieceOffField(i,j));
                        amountFound++;
                    }
                }
            }
        }
        while (amountFound < amountToFind) {
            startIndex += 4;
            if(isSlidingPiece(c)){
                startIndex++;
            }
            amountFound++;
        }
        return startIndex;
    }

    private int getSlidingMobilityOfField(double[] features, Board board, int startIndex, char c, int amountToFind) {
        int amountFound = 0;
        for (int i = 0; i < Board.getBoardSize(); i++) {
            for (int j = 0; j < Board.getBoardSize(); j++) {
                if (board.getCharOffField(i, j) == c) {
                    if(amountFound<amountToFind){
                        slidingPieceMobility(features, board, startIndex++, board.getPieceOffField(i,j));
                        amountFound++;
                    }
                }
            }
        }
        while (amountFound < amountToFind) {
            startIndex += 1;
//            if(isSlidingPiece(c)){
//                startIndex++;
//            }
            amountFound++;
        }
        return startIndex;
    }

    private boolean isWhite(char c) {
        switch (c) {
            case 'K', 'Q', 'R', 'N', 'B', 'P':
                return true;
            default:
                return false;
        }
    }

    private int getLowestValueAttacker(Board board, int x, int y, boolean white) {
        int lowestValue = Integer.MAX_VALUE;

        ChessPiece pieceOffField = board.getPieceOffField(x, y);
        if(pieceOffField !=null){
            if(pieceOffField.isWhite()){
                for(int i = 0; i<Board.getBoardSize(); i++){
                    for(int j = 0; j<Board.getBoardSize(); j++){
                        ChessPiece piece = board.getPieceOffField(i,j);
                        if(piece!=null&&!piece.isWhite()){
                            int value = getValue(piece.getPieceChar());
                            Board clone = board.clone();
                            if(!piece.isWhite()){
                                clone.setWhiteMove(false);
                            }
                            else{
                                clone.setWhiteMove(true);
                            }
                            boolean[][] validMoves = piece.validMoves(clone);
                            if (validMoves[x][y] && value < lowestValue) {
                                lowestValue = value;
                            }
                        }
                    }
                }
            }
            else if(!pieceOffField.isWhite()){
                for(int i = 0; i<Board.getBoardSize(); i++){
                    for(int j = 0; j<Board.getBoardSize(); j++){
                        ChessPiece piece = board.getPieceOffField(i,j);
                        if(piece!=null&&piece.isWhite()){
                            int value = getValue(piece.getPieceChar());
                            Board clone = board.clone();
                            if(!piece.isWhite()){
                                clone.setWhiteMove(false);
                            }
                            else{
                                clone.setWhiteMove(true);
                            }
                            boolean[][] validMoves = piece.validMoves(clone);
                            if (validMoves[x][y] && value < lowestValue) {
                                lowestValue = value;
                            }
                        }
                    }
                }
            }
        }
        else{
            for(int i = 0; i<Board.getBoardSize(); i++){
                for(int j = 0; j<Board.getBoardSize(); j++){
                    ChessPiece piece = board.getPieceOffField(i,j);
                    if(piece!=null&&piece.isWhite()==white){
                        int value = getValue(piece.getPieceChar());
                        Board clone = board.clone();
                        if(!piece.isWhite()){
                            clone.setWhiteMove(false);
                        }
                        else{
                            clone.setWhiteMove(true);
                        }
                        boolean[][] validMoves = piece.validMoves(clone);
                        if (validMoves[x][y] && value < lowestValue) {
                            lowestValue = value;
                        }
                    }
                }
            }
        }
        if (lowestValue == Integer.MAX_VALUE) {
            return 0;
        } else return lowestValue;
    }

    private int getLowestValueDefender(Board board, int x, int y, boolean white) {
        int lowestValue = Integer.MAX_VALUE;

        ChessPiece pieceOffField = board.getPieceOffField(x, y);
        if(pieceOffField !=null){
            for(int i = 0; i<Board.getBoardSize(); i++){
                for(int j = 0; j<Board.getBoardSize(); j++){
                    ChessPiece piece = board.getPieceOffField(i,j);
                    if(piece!=null&&piece.isWhite()==pieceOffField.isWhite()){
                        Board clone = board.clone();
                        BoardUpdater.removePiece(clone, x,y);
                        int value = getValue(piece.getPieceChar());
                        if(!piece.isWhite()){
                            clone.setWhiteMove(false);
                        }
                        else{
                            clone.setWhiteMove(true);
                        }
                        boolean[][] validMoves = piece.validMoves(clone);
                        if (validMoves[x][y] && value < lowestValue) {
                            lowestValue = value;
                        }
                    }
                }
            }
        }
        else{
            for(int i = 0; i<Board.getBoardSize(); i++){
                for(int j = 0; j<Board.getBoardSize(); j++){
                    ChessPiece piece = board.getPieceOffField(i,j);
                    if(piece!=null&&piece.isWhite()!=white){
                        int value = getValue(piece.getPieceChar());
                        Board clone = board.clone();
                        if(!piece.isWhite()){
                            clone.setWhiteMove(false);
                        }
                        else{
                            clone.setWhiteMove(true);
                        }
                        boolean[][] validMoves = piece.validMoves(clone);
                        if (validMoves[x][y] && value < lowestValue) {
                            lowestValue = value;
                        }
                    }
                }
            }
        }

        if (lowestValue == Integer.MAX_VALUE) {
            return 0;
        } else return lowestValue;
    }

    private int getValue(char c) {
        return switch (c) {
            case 'Q', 'q' -> 9;
            case 'R', 'r' -> 5;
            case 'B', 'b', 'N', 'n' -> 3;
            case 'P', 'p' -> 1;
            case 'K', 'k' -> 10;
            default -> 0;
        };
    }

    private boolean isSlidingPiece(char c) {
        return switch (c) {
            case 'B', 'b', 'R', 'r', 'Q', 'q' -> true;
            default -> false;
        };
    }
}
