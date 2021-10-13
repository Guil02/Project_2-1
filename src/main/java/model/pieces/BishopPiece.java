package model.pieces;

import model.Board;

/**
 * class that determines every valid moves for a bishop
 */
public class BishopPiece extends ChessPiece {

    /**
     * constructor that creates a bishop chess piece
     */
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
     * method that returns all possible positions for a bishop to move to
     */
    public boolean[][] validMoves() {

        boolean[][] valid_moves = new boolean[Board.getBoardSize()][Board.getBoardSize()];

        if(isTurn()){

            //north-west diagonal
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

            //north-east diagonal
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

            //south-east diagonal
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

            //south-west diagonal
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
        if(checkAllFalse(valid_moves)){ // if there are no valid moves for the current state of the board
            hasValidMove = false;
        }
        return valid_moves;
    }
}