package model.pieces;

import model.Board;

/**
 * class that determines every valid moves for a rook
 */
public class RookPiece extends ChessPiece {
    private boolean hasNotMoved = true;

    /**
     * constructor that creates a rook chess piece
     */
    public RookPiece(boolean white, Board board, int index_x, int index_y) {
        super(white, index_x, index_y, board);
    }

    public char getPieceChar() {
        if (this.isWhite)
            return 'R';
        else
            return 'r';
    }

    @Override
    public void move(int new_index_x, int new_index_y) {
        this.hasNotMoved=false;
        super.move(new_index_x, new_index_y);
    }

    /**
     * method that determines if the piece in question has already moved or not
     */
    public boolean isHasNotMoved() {
        return hasNotMoved;
    }

    /*
     * method that returns all possible positions for a rook to move to
     */
    public boolean[][] validMoves() {

        boolean[][] valid_moves = new boolean[Board.getBoardSize()][Board.getBoardSize()];

        if(isTurn()) {

            // horizontal rank
            int temp = 1;
            while(true) {
                if(withinBounds(index_x,temp)) {
                    if(isOpenSpot(index_x+temp,index_y)) {
                        if(checkForEnemyPiece(index_x+temp,index_y)) {
                            valid_moves[index_x+temp][index_y] = true;
                            setHasValidMove(true);
                            break;
                        }
                        else {
                            valid_moves[index_x+temp][index_y] = true;
                            setHasValidMove(true);
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
                            setHasValidMove(true);
                            break;
                        }
                        else {
                            valid_moves[index_x+temp][index_y] = true;
                            setHasValidMove(true);
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

            // vertical rank
            temp = 1;
            while(true) {
                if(withinBounds(index_y,temp)) {
                    if(isOpenSpot(index_x,index_y+temp)) {
                        if(checkForEnemyPiece(index_x,index_y+temp)) {
                            valid_moves[index_x][index_y+temp] = true;
                            setHasValidMove(true);
                            break;
                        }
                        else {
                            valid_moves[index_x][index_y+temp] = true;
                            setHasValidMove(true);
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
                            setHasValidMove(true);
                            break;
                        }
                        else {
                            valid_moves[index_x][index_y+temp] = true;
                            setHasValidMove(true);
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
        if(checkAllFalse(valid_moves)){
            setHasValidMove(false);
        }
        return valid_moves;
    }
}


