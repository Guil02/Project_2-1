package model.pieces;

import model.Board;

/**
 * Queen piece
 */
public class QueenPiece extends ChessPiece {

    public QueenPiece(boolean isWhite, Board board, int index_x, int index_y) {
        super(isWhite, index_x, index_y, board);
    }

    public char getPieceChar() {
        if (this.isWhite)
            return 'Q';
        else
            return 'q';
    }

    public boolean[][] validMoves() {

        boolean[][] valid_moves = new boolean[8][8];

        if(isTurn()){


            //horizontal rank
            int temp = 1;
            while(true){
                if(withinBounds(index_x,temp)){
                    if(isOpenSpot(index_x+temp,index_y)){
                        if(checkForEnemyPiece(index_x+temp,index_y)){
                            valid_moves[index_x+temp][index_y]=true;
                            setHasValidMove(true);
                            break;
                        }
                        else{
                            valid_moves[index_x+temp][index_y]=true;
                            setHasValidMove(true);
                        }
                        temp++;
                    }
                    else{
                        break;
                    }
                }
                else{
                    break;
                }
            }

            temp = -1;
            while(true){
                if(withinBounds(index_x,temp)){
                    if(isOpenSpot(index_x+temp,index_y)){
                        if(checkForEnemyPiece(index_x+temp,index_y)){
                            valid_moves[index_x+temp][index_y]=true;
                            setHasValidMove(true);
                            break;
                        }
                        else{
                            valid_moves[index_x+temp][index_y]=true;
                            setHasValidMove(true);
                        }
                        temp--;
                    }
                    else{
                        break;
                    }
                }
                else{
                    break;
                }
            }

            //vertical rank
            temp = 1;
            while(true){
                if(withinBounds(index_y,temp)){
                    if(isOpenSpot(index_x,index_y+temp)){
                        if(checkForEnemyPiece(index_x,index_y+temp)){
                            valid_moves[index_x][index_y+temp]=true;
                            setHasValidMove(true);
                            break;
                        }
                        else{
                            valid_moves[index_x][index_y+temp]=true;
                            setHasValidMove(true);
                        }
                        temp++;
                    }
                    else{
                        break;
                    }
                }
                else{
                    break;
                }
            }

            temp = -1;
            while(true){
                if(withinBounds(index_y,temp)){
                    if(isOpenSpot(index_x,index_y+temp)){
                        if(checkForEnemyPiece(index_x,index_y+temp)){
                            valid_moves[index_x][index_y+temp]=true;
                            setHasValidMove(true);
                            break;
                        }
                        else{
                            valid_moves[index_x][index_y+temp]=true;
                            setHasValidMove(true);
                        }
                        temp--;
                    }
                    else{
                        break;
                    }
                }
                else{
                    break;
                }
            }

            //north west
            int incr = 1;

            while(true){
                int x = index_x + incr;
                int y = index_y + incr;
                if(withinBoundsOneVariable(x) && withinBoundsOneVariable(y)){
                    if(isOpenSpot(x, y)){
                        if(checkForEnemyPiece(x,y)){
                            valid_moves[x][y] = true;
                            setHasValidMove(true);
                            break;
                        }
                        else{
                            valid_moves[x][y] = true;
                            setHasValidMove(true);
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
                            setHasValidMove(true);
                            break;
                        }
                        else{
                            valid_moves[x][y] = true;
                            setHasValidMove(true);
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
                            setHasValidMove(true);
                            break;
                        }
                        else{
                            valid_moves[x][y] = true;
                            setHasValidMove(true);
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
                            setHasValidMove(true);
                            break;
                        }
                        else{
                            valid_moves[x][y] = true;
                            setHasValidMove(true);
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
            setHasValidMove(false);
        }

        return valid_moves;
    }

}
