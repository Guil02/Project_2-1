package gui;

import gui.Pieces.*;

public class FENReader {
    public FENReader() {
    }
    public ChessBoard readInFEN(String FEN){
        ChessBoard board = new ChessBoard();
        int toBeSkipped = 0;
        int pos = 0;
        int index = 0;
        loop:
        for(; index<FEN.length(); index++){
            switch (FEN.charAt(index)){
                case '/':
                    toBeSkipped++;
                    break;
                case 'k':
                    board.getBoard()[pos++].setPiece(new King("black"));
                    break;
                case 'q':
                    board.getBoard()[pos++].setPiece(new Queen("black"));
                    break;
                case 'r':
                    board.getBoard()[pos++].setPiece(new Rook("black"));
                    break;
                case 'b':
                    board.getBoard()[pos++].setPiece(new Bishop("black"));
                    break;
                case 'n':
                    board.getBoard()[pos++].setPiece(new Knight("black"));
                    break;
                case 'p':
                    board.getBoard()[pos++].setPiece(new Pawn("black"));
                    break;
                case 'K':
                    board.getBoard()[pos++].setPiece(new King("white"));
                    break;
                case 'Q':
                    board.getBoard()[pos++].setPiece(new Queen("white"));
                    break;
                case 'R':
                    board.getBoard()[pos++].setPiece(new Rook("white"));
                    break;
                case 'B':
                    board.getBoard()[pos++].setPiece(new Bishop("white"));
                    break;
                case 'N':
                    board.getBoard()[pos++].setPiece(new Knight("white"));
                    break;
                case 'P':
                    board.getBoard()[pos++].setPiece(new Pawn("white"));
                    break;
                case ' ':
                    index++;
                    break loop;
                default:
                    int step = Character.getNumericValue(FEN.charAt(index));
                    pos = pos + step;
                    break;
            }
        }

        board.setWhiteMove(FEN.charAt(index++) == 'w');
        index++;
        loop:
        for(; index<FEN.length();index++){
            switch (FEN.charAt(index)){
                case 'K':
                    board.setWhiteKingCastling(true);
                    break;
                case 'Q':
                    board.setWhiteQueenCastling(true);
                    break;
                case 'k':
                    board.setBlackKingCastling(true);
                    break;
                case 'q':
                    board.setBlackQueenCastling(true);
                    break;
                case ' ':
                    index++;
                    break loop;
            }
        }

        return board;
    }
}
