package model.pieces;

import model.Board;

/**
 * Bishop piece
 */
public class BishopPiece extends ChessPiece {

    public BishopPiece(boolean isWhite, Board board, int index_x, int index_y) {
        super(isWhite, index_x, index_y, board);
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
    public boolean[][] validMoves() {

        boolean[][] valid_moves = new boolean[8][8];

        if(isTurn()){

            //north west
            int incr = 1;

            while(true){
                int x = index_x + incr;
                int y = index_y + incr;
                if(withinBoundsOneVariable(x) && withinBoundsOneVariable(y)){
                    if(isOpenSpot(x, y)){
                        if(checkForEnemyPiece(x,y)){
                            valid_moves[x][y] = true;
                            hasValidMove = true;
                            break;
                        }
                        else{
                            valid_moves[x][y] = true;
                            hasValidMove = true;
                        }
                        incr++;
                    }
                    else{
                        break;
                    }
                }
                else break;
            }

            //north east
            int incr_x = -1;
            int incr_y = 1;
            while(true){
                int x = index_x + incr_x;
                int y = index_y + incr_y;
                if(withinBoundsOneVariable(x) && withinBoundsOneVariable(y)){
                    if(isOpenSpot(x, y)){
                        if(checkForEnemyPiece(x,y)){
                            valid_moves[x][y] = true;
                            hasValidMove = true;
                            break;
                        }
                        else{
                            valid_moves[x][y] = true;
                            hasValidMove = true;
                        }
                        incr_x--;
                        incr_y++;
                    }
                    else{
                        break;
                    }
                }
                else break;
            }

            //south east
            incr_x = -1;
            incr_y = -1;
            while(true){
                int x = index_x + incr_x;
                int y = index_y + incr_y;
                if(withinBoundsOneVariable(x) && withinBoundsOneVariable(y)){
                    if(isOpenSpot(x, y)){
                        if(checkForEnemyPiece(x,y)){
                            valid_moves[x][y] = true;
                            hasValidMove = true;
                            break;
                        }
                        else{
                            valid_moves[x][y] = true;
                            hasValidMove = true;
                        }
                        incr_x--;
                        incr_y--;
                    }
                    else{
                        break;
                    }
                }
                else break;
            }

            //south west
            incr_x = 1;
            incr_y = -1;
            while(true){
                int x = index_x + incr_x;
                int y = index_y + incr_y;
                if(withinBoundsOneVariable(x) && withinBoundsOneVariable(y)){
                    if(isOpenSpot(x, y)){
                        if(checkForEnemyPiece(x,y)){
                            valid_moves[x][y] = true;
                            hasValidMove = true;
                            break;
                        }
                        else{
                            valid_moves[x][y] = true;
                            hasValidMove = true;
                        }
                        incr_x++;
                        incr_y--;
                    }
                    else{
                        break;
                    }
                }
                else break;
            }
        }
        if(checkAllFalse(valid_moves)){
            hasValidMove = false;
        }
        return valid_moves;
    }
}