package utils;

import controller.Board;
import controller.BoardUpdater;
import model.pieces.ChessPiece;
import model.pieces.KingPiece;

import java.util.Arrays;

public class FenEvaluator {
    public static void main(String[] args) {
        String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - P";
        read(fen);
    }

    public static void read(String fen){
        char[] pieces = new char[64];
        boolean whiteMove = true;
        boolean longCastleBlack = false;
        boolean shortCastleBlack = false;
        boolean longCastleWhite = false;
        boolean shortCastleWhite = false;
        boolean enPassantActive = false;
        int enPassantColumn = 0;

        int index = 0;
        int pos = 0;
        Arrays.fill(pieces, '-');
        while(fen.charAt(index)!=' '){
            switch(fen.charAt(index)){
                case '1','2','3','4','5','6','7','8':
                    pos = pos + Character.getNumericValue(fen.charAt(index));
                    break;
                case '/':
                    break;
                default:
                    pieces[pos++] = fen.charAt(index);
            }
            index++;
        }

        index++;

        if(fen.charAt(index)=='w'){
            whiteMove = true;
        }
        else if(fen.charAt(index) == 'b'){
            whiteMove = false;
        }

        index += 2;

        while(fen.charAt(index)!= ' '){
            System.out.println(fen.charAt(index));
            switch (fen.charAt(index)) {
                case 'K' -> shortCastleWhite = true;
                case 'Q' -> longCastleWhite = true;
                case 'k' -> shortCastleBlack = true;
                case 'q' -> longCastleBlack = true;
            }
            index++;
        }

        index++;

        switch (fen.charAt(index)){
            case '-':
                enPassantActive = false;
                break;
            case 'a':
                enPassantActive = true;
                enPassantColumn = 1;
                break;
            case 'b':
                enPassantActive = true;
                enPassantColumn = 2;
                break;
            case 'c':
                enPassantActive = true;
                enPassantColumn = 3;
                break;
            case 'd':
                enPassantActive = true;
                enPassantColumn = 4;
                break;
            case 'e':
                enPassantActive = true;
                enPassantColumn = 5;
                break;
            case 'f':
                enPassantActive = true;
                enPassantColumn = 6;
                break;
            case 'g':
                enPassantActive = true;
                enPassantColumn = 7;
                break;
            case 'h':
                enPassantActive = true;
                enPassantColumn = 8;
                break;
        }

        index +=2;


        System.out.println(Arrays.toString(pieces));
        System.out.println("white move? "+whiteMove);
        System.out.println("short castle white: "+shortCastleWhite);
        System.out.println("long castle white: "+longCastleWhite);
        System.out.println("short castle black: "+shortCastleBlack);
        System.out.println("long castle black: "+longCastleBlack);
        System.out.println("en passant? "+enPassantActive);
        if(enPassantActive)
            System.out.println("en passant column: "+enPassantColumn);


        Board board = new Board();
    }

    private static void buildBoard(Board board, char[] pieces, boolean whiteMove, boolean shortCastleBlack, boolean shortCastleWhite, boolean longCastleBlack, boolean longCastleWhite, boolean enPassantActive, int enPassantColumn){
        for(int i = 0; i<pieces.length; i++){
            int x = i%8;
            int y = (i-x)/8;
            ChessPiece piece = makePiece(pieces[i], x, y);
            BoardUpdater.addPiece(board, piece);
        }
        board.setWhiteMove(whiteMove);
//        board.setEnPassant(false);

    }

    private static ChessPiece makePiece(char c, int x, int y){
        return new KingPiece(true,0,0);
    }
}
