package model.player;

import controller.Board;
import model.pieces.ChessPiece;
import model.pieces.PawnPiece;

import java.util.ArrayList;

public class Factor {
    public static double piece_value(Board board, boolean whiteIsMax, char c){
        c = convertChar(c, whiteIsMax);
        char adverse = convertChar(c, !whiteIsMax);
        return board.getAmountOfPieces(c)-board.getAmountOfPieces(adverse);
    }

    public static double pawn_doubled_penalty_value(Board board, boolean whiteIsMax){
        char c = convertChar('P', whiteIsMax);

        ArrayList<ChessPiece> list = board.getPieces(c);
        double res = 0;
        for(int x = 0; x<Board.getBoardSize(); x++){
            int count = 0;
            for (ChessPiece chessPiece : list) {
                if (chessPiece.getX() == x) {
                    count++;
                }
            }
            if(count>1){
                res += count-1;
            }
        }
        return res;
    }

    public static double pawn_isolated_penalty_value(Board board, boolean whiteIsMax){
        char c = convertChar('P', whiteIsMax);

        ArrayList<ChessPiece> list = board.getPieces(c);
        double result = 0;
        for(int i=0; i<list.size(); i++){

            boolean isIsolated = true;
            int coordinate = list.get(i).getX();
            for(int j=0; j<list.size(); j++){
                if(list.get(j).getX() == coordinate+1 || list.get(j).getX() == coordinate-1){
                    isIsolated = false;
                }
            }
            if(isIsolated){
                result++;
            }
        }
        return result;
    }

    public static double pawn_central(Board board, boolean whiteIsMax){
        char c = convertChar('P', whiteIsMax);
        ArrayList<ChessPiece> list = board.getPieces(c);

        double res = 0;
        for(ChessPiece chessPiece: list){
            if(isCentral(chessPiece.getX(), chessPiece.getY())){
                res++;
            }
        }

        return res;
    }

    public static double piece_mobility(Board board, boolean whiteIsMax, char c){
        c = convertChar('P', whiteIsMax);
        ArrayList<ChessPiece> list = board.getPieces(c);
        double res = 0;
        for(ChessPiece chessPiece: list){
            boolean[][] valid_moves = chessPiece.validMoves(board);
            int temp = 0;
            for(boolean[] valid: valid_moves){
                for(boolean value: valid){
                    if(value){
                        temp++;
                    }
                }
            }
            res += temp;
        }
        return res;
    }

    public static double passedPawn(Board board, boolean whiteisMax){
        char c = convertChar('P', whiteisMax);
        double amountPassed = 0;
        ArrayList<ChessPiece> pawns = board.getPieces(c);
        ArrayList<ChessPiece> opponentPawns = board.getPieces(Character.toLowerCase(c));

        if(!whiteisMax){
            pawns = board.getPieces(c);
            opponentPawns = board.getPieces(Character.toUpperCase(c));
        }
        for(ChessPiece pawn : pawns){
                boolean passed = true;
                for(ChessPiece opponentPawn : pawns){
                    if(whiteisMax) {
                        if (pawn.getY() > opponentPawn.getY() && (pawn.getX() - 1 == opponentPawn.getX() || pawn.getX() + 1 == opponentPawn.getX())) {
                            passed = false;
                        }
                    }
                    else{
                        if(pawn.getY()<opponentPawn.getY() && (pawn.getX()-1== opponentPawn.getX() || pawn.getX()+1== opponentPawn.getX())){
                            passed = false;
                        }

                    }
                }
                if(passed)
                    amountPassed++;
            }
            return amountPassed;
        }


    public static double rooks_on_seventh_rank(Board board, boolean whiteIsMax){
        char c = convertChar('R', whiteIsMax);
        ArrayList<ChessPiece> rooks = board.getPieces(c);
        double result = 0;
        for(ChessPiece rook : rooks){
            if(whiteIsMax){
                if(rook.getY()==1)
                    result++;
            }
            else{
                if(rook.getY()==6)
                    result++;
            }
        }
        return result;
    }

    public static double knight_periphery_0(Board board, boolean whiteIsMax){
        char c = convertChar('N', whiteIsMax);
        ArrayList<ChessPiece> list = board.getPieces(c);
        double res = 0;
        for(ChessPiece piece: list){
            if(piece.getX() == 0 || piece.getX() == 7 || piece.getY() == 0 || piece.getY() == 7){
                res++;
            }
        }
        return res;
    }

    public static double knight_periphery_1(Board board, boolean whiteIsMax){
        char c = convertChar('N', whiteIsMax);
        ArrayList<ChessPiece> list = board.getPieces(c);

        double res = 0;
        for(ChessPiece piece: list){
            if((piece.getX() == 1 || piece.getX()==6)&&(piece.getY()>1 && piece.getY()<6)){
                res++;
            }
            else if((piece.getY() == 1 || piece.getY() == 6)&&(piece.getX()>1&&piece.getX()<6)){
                res++;
            }
        }
        return res;
    }

    public static double knight_periphery_2(Board board, boolean whiteIsMax){
        char c = convertChar('N', whiteIsMax);
        ArrayList<ChessPiece> list = board.getPieces(c);

        double res = 0;
        for(ChessPiece piece: list){
            if((piece.getX() == 2 || piece.getX()==5)&&(piece.getY()>2 && piece.getY()<5)){
                res++;
            }
            else if((piece.getY() == 2 || piece.getY() == 5)&&(piece.getX()>2&&piece.getX()<5)){
                res++;
            }
        }
        return res;
    }

    public static double knight_periphery_3(Board board, boolean whiteIsMax){
        char c = convertChar('N', whiteIsMax);
        ArrayList<ChessPiece> list = board.getPieces(c);

        double res = 0;
        for(ChessPiece piece: list){
            if((piece.getX() == 3 || piece.getX()==4)&&(piece.getY()>3 && piece.getY()<4)){
                res++;
            }
            else if((piece.getY() == 3 || piece.getY() == 4)&&(piece.getX()>3&&piece.getX()<4)){
                res++;
            }
        }
        return res;
    }

    public static double attacking_king(Board board, boolean whiteIsMax){

        ArrayList<ChessPiece> k = board.getPieces(convertChar('k', whiteIsMax));
        if(k.size()==0){
            return 0;
        }
        ChessPiece opponentKing = k.get(0);
        ChessPiece[][] model = board.getBoardModel();
        double result = 0;

        for(int i=0; i<Board.getBoardSize(); i++){
            for(int j=0; j<Board.getBoardSize(); j++){
                if(model[i][j] != null && model[i][j].isWhite()==whiteIsMax){
                    boolean[][] validMoves = model[i][j].validMoves(board);
                    if(validMoves[opponentKing.getX()][opponentKing.getY()])
                        result++;
                }
            }
        }
        return result;
    }

    public static boolean isCentral(int x, int y){
        if(x>1&&x<6&&y>3&&y<6){
            return true;
        }
        else{
            return false;
        }
    }

    public static char convertChar(char c, boolean whiteIsMax){
        if(whiteIsMax)
            return Character.toUpperCase(c);
        return Character.toLowerCase(c);
    }
}
