package utils;

import controller.Board;
import model.pieces.*;

public class PieceFactory {
    public static ChessPiece createChessPiece(char c, int x, int y){
        return switch (c) {
            case 'K' -> new KingPiece(true, x, y);
            case 'Q' -> new QueenPiece(true, x, y);
            case 'R' -> new RookPiece(true, x, y);
            case 'B' -> new BishopPiece(true, x, y);
            case 'N' -> new KnightPiece(true, x, y);
            case 'P' -> new PawnPiece(true, x, y);
            case 'k' -> new KingPiece(false, x, y);
            case 'q' -> new QueenPiece(false, x, y);
            case 'r' -> new RookPiece(false, x, y);
            case 'b' -> new BishopPiece(false, x, y);
            case 'n' -> new KnightPiece(false, x, y);
            case 'p' -> new PawnPiece(false, x, y);
            default -> null;
        };
    }
}
