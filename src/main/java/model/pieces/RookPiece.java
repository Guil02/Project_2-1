package model.pieces;

import model.Board;

/**
 * Rook piece
 */
public class RookPiece extends ChessPiece {

    public RookPiece(boolean white, Board board, int index_x, int index_y) {
        super(white, index_x, index_y, board);
    }

    public char getPieceChar() {
        if (this.isWhite)
            return 'R';
        else
            return 'r';
    }

    public boolean[][] validMoves() {

        boolean[][] valid_moves = new boolean[Board.getBoardSize()][Board.getBoardSize()];

        if(isTurn()) {

            //horizontal rank
            int temp = 1;
            while(true) {
                if(withinBounds(index_x,temp)) {
                    if(isOpenSpot(index_x+temp,index_y)) {
                        if(checkForEnemyPiece(index_x+temp,index_y)) {
                            valid_moves[index_x+temp][index_y] = true;
                            break;
                        }
                        else {
                            valid_moves[index_x+temp][index_y] = true;
                        }
                        temp++;
                    }
                    else {
                        break;
                    }
                }
                else {
                    break;
                }
            }

            temp = -1;
            while(true) {
                if(withinBounds(index_x,temp)) {
                    if(isOpenSpot(index_x+temp,index_y)) {
                        if(checkForEnemyPiece(index_x+temp,index_y)) {
                            valid_moves[index_x+temp][index_y] = true;
                            break;
                        }
                        else {
                            valid_moves[index_x+temp][index_y] = true;
                        }
                        temp--;
                    }
                    else {
                        break;
                    }
                }
                else {
                    break;
                }
            }

            //vertical rank
            temp = 1;
            while(true) {
                if(withinBounds(index_y,temp)) {
                    if(isOpenSpot(index_x,index_y+temp)) {
                        if(checkForEnemyPiece(index_x,index_y+temp)) {
                            valid_moves[index_x][index_y+temp] = true;
                            break;
                        }
                        else {
                            valid_moves[index_x][index_y+temp] = true;
                        }
                        temp++;
                    }
                    else {
                        break;
                    }
                }
                else {
                    break;
                }
            }

            temp = -1;
            while(true) {
                if(withinBounds(index_y,temp)) {
                    if(isOpenSpot(index_x,index_y+temp)) {
                        if(checkForEnemyPiece(index_x,index_y+temp)) {
                            valid_moves[index_x][index_y+temp] = true;
                            break;
                        }
                        else {
                            valid_moves[index_x][index_y+temp] = true;
                        }
                        temp--;
                    }
                    else {
                        break;
                    }
                }
                else {
                    break;
                }
            }
        }

        //small castling
        //whites
        if(this.isWhite) {
            if(this.index_x == 7 && this.index_y == 7) { //checking if the rook is at his initial position
                if( (this.currentBoard.getBoardModel()[4][7] != null) && (this.currentBoard.getBoardModel()[4][7].getPieceChar() == 'K') ) { //checking if the king is at his initial position
                    if(isOpenSpot(this.index_x-1, this.index_y)) { //checking if no pieces on the way between the rook and the king
                        if(isOpenSpot(this.index_x-2, this.index_y)) {
                            if(isOpenSpot(this.index_x-3, this.index_y)) {
                                valid_moves[5][7] = true;
                            }
                        }
                    }
                }
            }
        }
        //blacks
        else {
            if(this.index_x == 7 && this.index_y == 0) { //checking if the rook is at his initial position
                if(this.currentBoard.getBoardModel()[4][0] != null && this.currentBoard.getBoardModel()[4][0].getPieceChar() == 'k') { //checking if the king is at his initial position
                    if(isOpenSpot(this.index_x-1, this.index_y)) { //checking if no pieces on the way between the rook and the king
                        if(isOpenSpot(this.index_x-2, this.index_y)) {
                            if(isOpenSpot(this.index_x-3, this.index_y)) {
                                valid_moves[6][0] = true;
                            }
                        }
                    }
                }
            }
        }

        //great castling
        //whites
        if(this.isWhite) {
            if(this.index_x == 0 && this.index_y == 7) { //checking if the rook is at his initial position
                if( (this.currentBoard.getBoardModel()[4][7]) != null && (this.currentBoard.getBoardModel()[4][7].getPieceChar() == 'K') ) { //checking if the king is at his initial position
                    if(isOpenSpot(this.index_x+1, this.index_y)) { //checking if no pieces on the way between the rook and the king
                        if(isOpenSpot(this.index_x+2, this.index_y)) {
                            if(isOpenSpot(this.index_x+3, this.index_y)) {
                                valid_moves[3][7] = true;
                            }
                        }
                    }
                }
            }
        }
        //blacks
        else {
            if(this.index_x == 0 && this.index_y == 0) { //checking if the rook is at his initial position
                if(this.currentBoard.getBoardModel()[4][0] != null && this.currentBoard.getBoardModel()[4][0].getPieceChar() == 'k') { //checking if the king is at his initial position
                    if(isOpenSpot(this.index_x+1, this.index_y)) { //checking if no pieces on the way between the rook and the king
                        if(isOpenSpot(this.index_x+2, this.index_y)) {
                            if(isOpenSpot(this.index_x+3, this.index_y)) {
                                valid_moves[3][0] = true;
                            }
                        }
                    }
                }
            }
        }
        return valid_moves;
    }
}


