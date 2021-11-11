package utils;

import controller.Board;
import controller.BoardUpdater;
import model.pieces.ChessPiece;
import model.pieces.KingPiece;
import model.pieces.PawnPiece;
import model.pieces.RookPiece;

import java.util.Arrays;

public class FenEvaluator {
    public static void main(String[] args) {
        String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq -";
        read(fen);
    }

    public static Board read(String fen){
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
            case '0':
                enPassantActive = true;
                enPassantColumn = 0;
                break;
            case '1':
                enPassantActive = true;
                enPassantColumn = 1;
                break;
            case '2':
                enPassantActive = true;
                enPassantColumn = 2;
                break;
            case '3':
                enPassantActive = true;
                enPassantColumn = 3;
                break;
            case '4':
                enPassantActive = true;
                enPassantColumn = 4;
                break;
            case '5':
                enPassantActive = true;
                enPassantColumn = 5;
                break;
            case '6':
                enPassantActive = true;
                enPassantColumn = 6;
                break;
            case '7':
                enPassantActive = true;
                enPassantColumn = 7;
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

        buildBoard(board, pieces, whiteMove, shortCastleBlack, shortCastleWhite, longCastleBlack, longCastleWhite, enPassantActive, enPassantColumn);
        return board;
    }

    public static String write(Board board){
        ChessPiece[][] model = board.getBoardModel();
        StringBuilder st = new StringBuilder();
        int epColumn = 0;
        boolean enPassant = false;
        boolean k = false;
        boolean q = false;
        boolean K = false;
        boolean Q = false;

        k = CastleCheck(model, false, true);
        q = CastleCheck(model, false, false);
        K = CastleCheck(model, true, true);
        Q = CastleCheck(model, true, false);

        int consecutiveNulls = 0;
        for(int i=0; i<Board.getBoardSize(); i++){
            for(int j=0; j<Board.getBoardSize(); j++){
               if(model[j][i]==null){
                   consecutiveNulls++;
               }
               else{
                   if(consecutiveNulls>0) {
                       st.append(consecutiveNulls);
                       consecutiveNulls = 0;
                   }
                   char c = model[j][i].getPieceChar();
                   switch (c) {
                       case 'b' -> st.append("b");
                       case 'k' -> st.append("k");
                       case 'n' -> st.append("n");
                       case 'p' -> st.append("p");
                       case 'q' -> st.append("q");
                       case 'r' -> st.append("r");
                       case 'B' -> st.append("B");
                       case 'K' -> st.append("K");
                       case 'N' -> st.append("N");
                       case 'P' -> st.append("P");
                       case 'Q' -> st.append("Q");
                       case 'R' -> st.append("R");
                   }
                   if(model[i][j].isEnPassantActive()){
                       enPassant = true;
                       epColumn = ((PawnPiece)(model[i][j])).getEnPassantColumn();

                   }
               }
            }
            if(consecutiveNulls>0) {
                st.append(consecutiveNulls);
                consecutiveNulls = 0;
            }
            if(i+1!=Board.getBoardSize())
                st.append("/");
        }
        st.append(" ");
        if(board.getWhiteMove()){
            st.append("w");
        }
        else{
            st.append("b");
        }
        st.append(" ");
        if(K){
            st.append('K');
        }
        if(Q){
            st.append('Q');
        }
        if(k){
            st.append('k');
        }
        if(q){
            st.append('q');
        }
        st.append(" ");
        if(enPassant){
            st.append(epColumn);
        }
        else{
            st.append("-");
        }
        return st.toString();
    }

    public static boolean CastleCheck(ChessPiece[][] model, boolean white, boolean king){
        if(white && model[4][7].getPieceChar()!='K') {
            return false;
        }
        if(!white && model[4][0].getPieceChar()!='k'){
            return false;
        }
        if(white && king && model[7][7].getPieceChar()=='R'){
            return ((RookPiece)(model[7][7])).getHasNotMoved();
        }
        if(white && !king && model[0][7].getPieceChar()=='R'){
            return ((RookPiece)(model[0][7])).getHasNotMoved();
        }
        if(!white && king && model[7][0].getPieceChar()=='r'){
            return ((RookPiece)(model[7][0])).getHasNotMoved();
        }
        if(!white && !king && model[0][0].getPieceChar()=='r'){
            return ((RookPiece)(model[0][0])).getHasNotMoved();
        }
        return false;
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
        if(shortCastleBlack){
//            board.getBoardModel()[7][0];
        }
    }

    private static ChessPiece makePiece(char c, int x, int y){
        return new KingPiece(true,0,0);
    }
}


