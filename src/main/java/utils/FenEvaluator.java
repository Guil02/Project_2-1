package utils;

import controller.Board;
import controller.BoardUpdater;
import model.pieces.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class FenEvaluator {


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
//            System.out.println(fen.charAt(index));
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
            default:
                enPassantActive = true;
                enPassantColumn = fen.charAt(index);
                break;
        }

        index +=2;


//        System.out.println(Arrays.toString(pieces));
//        System.out.println("white move? "+whiteMove);
//        System.out.println("short castle white: "+shortCastleWhite);
//        System.out.println("long castle white: "+longCastleWhite);
//        System.out.println("short castle black: "+shortCastleBlack);
//        System.out.println("long castle black: "+longCastleBlack);
//        System.out.println("en passant? "+enPassantActive);
//        if(enPassantActive)
//            System.out.println("en passant column: "+enPassantColumn);


        Board board = new Board();
        try{
            buildBoard(board, pieces, whiteMove, shortCastleBlack, shortCastleWhite, longCastleBlack, longCastleWhite, enPassantActive, enPassantColumn);
        }
        catch(Exception e){
            e.printStackTrace();
//            System.out.println(fen);
//            System.err.println("error");
        }
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
                   if(board.isEnPassantActive()){
                       enPassant = true;
                       epColumn = PawnPiece.getEnPassantColumn(board);

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
        if(white && model[4][7]!=null && (model[4][7].getPieceChar()!='K' || !((KingPiece)model[4][7]).getHasNotMoved())) {
            return false;
        }
        if(!white && model[4][0]!=null && (model[4][0].getPieceChar()!='k' || !((KingPiece)model[4][0]).getHasNotMoved())){
            return false;
        }
        if(white && king && model[7][7]!=null && model[7][7].getPieceChar()=='R'){
            if(model[4][7]!=null && model[4][7].getPieceChar()=='K' && ((KingPiece) model[4][7]).getHasNotMoved()){
                return ((RookPiece)(model[7][7])).getHasNotMoved();
            }
            else return false;
        }
        if(white && !king && model[0][7]!=null && model[0][7].getPieceChar()=='R'){
            if(model[4][7]!=null && model[4][7].getPieceChar()=='K' && ((KingPiece) model[4][7]).getHasNotMoved()){
                return ((RookPiece)(model[0][7])).getHasNotMoved();
            }
            else return false;
        }
        if(!white && king && model[7][0]!=null && model[7][0].getPieceChar()=='r'){
            if(model[4][0]!=null && model[4][0].getPieceChar()=='k' && ((KingPiece) model[4][0]).getHasNotMoved()){
                return ((RookPiece)(model[7][0])).getHasNotMoved();
            }
            else return false;
        }
        if(!white && !king && model[0][0]!=null && model[0][0].getPieceChar()=='r'){
            if(model[4][0]!=null && model[4][0].getPieceChar()=='k' && ((KingPiece) model[4][0]).getHasNotMoved()){
                return ((RookPiece)(model[0][0])).getHasNotMoved();
            }
            else return false;
        }
        return false;
    }
    private static void buildBoard(Board board, char[] pieces, boolean whiteMove, boolean shortCastleBlack, boolean shortCastleWhite, boolean longCastleBlack, boolean longCastleWhite, boolean enPassantActive, int enPassantColumn){
        for(int i = 0; i<pieces.length; i++){
            if(pieces[i]=='-') continue;
            int x = i%8;
            int y = (i-x)/8;
            ChessPiece piece = makePiece(pieces[i], x, y);
            if(pieces[i]=='r' || pieces[i]=='R'){
                ((RookPiece)piece).setHasNotMoved(false);
            }
            if(pieces[i]=='k' || pieces[i]=='K'){
                ((KingPiece)piece).setHasNotMoved(false);
            }
            BoardUpdater.addPiece(board, piece);
        }
        board.setWhiteMove(whiteMove);
        if(shortCastleBlack){
            ((RookPiece) board.getPieceOffField(7,0)).setHasNotMoved(true);
            ((KingPiece) board.getPieceOffField(4,0)).setHasNotMoved(true);
        }
        if(longCastleBlack){
            ((RookPiece) board.getPieceOffField(0,0)).setHasNotMoved(true);
            ((KingPiece) board.getPieceOffField(4,0)).setHasNotMoved(true);
        }
        if(shortCastleWhite){
            ((RookPiece) board.getPieceOffField(7,7)).setHasNotMoved(true);
            ((KingPiece) board.getPieceOffField(4,7)).setHasNotMoved(true);
        }
        if(longCastleWhite){
            ((RookPiece) board.getPieceOffField(0,7)).setHasNotMoved(true);
            ((KingPiece) board.getPieceOffField(4,7)).setHasNotMoved(true);
        }
        board.setEnPassantActive(enPassantActive);
        board.setEnPassantColumn(enPassantColumn);
    }

    private static ChessPiece makePiece(char c, int x, int y){
        switch (c){
            case 'k':
                return new KingPiece(false, x,y);
            case 'K':
                return new KingPiece(true, x, y);
            case 'q':
                return new QueenPiece(false, x, y);
            case 'Q':
                return new QueenPiece(true, x, y);
            case 'r':
                return new RookPiece(false, x, y);
            case 'R':
                return new RookPiece(true, x, y);
            case 'b':
                return new BishopPiece(false, x, y);
            case 'B':
                return new BishopPiece(true, x, y);
            case 'n':
                return new KnightPiece(false, x, y);
            case 'N':
                return new KnightPiece(true, x, y);
            case 'p':
                return new PawnPiece(false, x, y);
            case 'P':
                return new PawnPiece(true, x, y);
            default:
                return null;
        }
    }

    public static ArrayList<ArrayList<String>> separateWhiteAndBlack(ArrayList<String> fens){
        ArrayList<String> white = new ArrayList<>();
        ArrayList<String> black = new ArrayList<>();

        for(int i = 0; i<fens.size(); i++){
            int index = 0;
            String fen = fens.get(i);
            while(fen.charAt(index)!=' '){
                index++;
            }
            index++;
            if(fen.charAt(index)=='w'){
                white.add(fen);
            }
            else if(fen.charAt(index)=='b'){
                black.add(fen);
            }
        }

        ArrayList<ArrayList<String>> res = new ArrayList<>();
        res.add(white);
        res.add(black);
        return res;
    }
}


