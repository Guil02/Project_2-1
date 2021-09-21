package model.pieces;

import model.Board;

//TODO add castling

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

        boolean[][] valid_moves = new boolean[8][8];

        if(isTurn()){

            //horizontal rank
            int temp = 1;
            while(true){
                if(withinBounds(index_x,temp)){
                    if(isOpenSpot(index_x+temp,index_y)){
                        if(checkForEnemyPiece(index_x+temp,index_y)){
                            valid_moves[index_x+temp][index_y]=true;
                            break;
                        }
                        else{
                            valid_moves[index_x+temp][index_y]=true;
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
                            break;
                        }
                        else{
                            valid_moves[index_x+temp][index_y]=true;
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
                            break;
                        }
                        else{
                            valid_moves[index_x][index_y+temp]=true;
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
                            break;
                        }
                        else{
                            valid_moves[index_x][index_y+temp]=true;
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

        }

        return valid_moves;
    }
}


