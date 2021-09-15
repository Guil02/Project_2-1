package model.pieces;

import model.Board;

/**
 * Bishop piece
 */
public class BishopPiece extends ChessPiece {

    public BishopPiece(boolean isWhite, Board board, int index_x, int index_y) {

        super(isWhite, index_x, index_y);
    }

    public char getPieceChar() {
        if (this.isWhite)
            return 'B';
        else
            return 'b';
    }

    /*
     * this method returns a 2x2 boolean matrix where each value 'true' represent a possible position for the bishop to move to
     */
    public boolean[][] validMoves(ChessPiece[][] chessPieces) {
        boolean[][] valid_moves = new boolean[8][8];

        // first diagonal
        for(int i = -7; i <= 7; i++) {
            if( !(i==0) ) { // no move
                if( (0 <= (this.index_x + i )) && ((this.index_x + i) < 7) && (0 <= (this.index_y + i )) && ((this.index_y + i) < 7)) {
                    if (this.checkForOwnPiece(index_x, index_y) == false)
                        valid_moves[this.index_x + i][this.index_y + i] = true;
                }
            }
        }

        // second diagonal
        for(int i = -7; i <= 7; i++) {
            if( !(i==0) ) { // no move
                if( (0 <= (this.index_x + i)) && ((this.index_x - i) < 7) && (0 <= (this.index_y + i )) && ((this.index_y + i) < 7)) {
                    if (this.checkForOwnPiece(index_x, index_y) == false)
                        valid_moves[this.index_x + i][this.index_y - i] = true;
                }
            }
        }
        return valid_moves;
    }
}