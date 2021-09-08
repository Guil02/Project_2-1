
public class Bishop extends ChessPieces {
	public boolean[][] validMoves() {
			
		boolean[][] valid_moves = new boolean[Board.BOARDSIZE][Board.BOARDSIZE];
			
			// first diagonal
			for(int i = -Board.BOARDSIZE; i<= Board.BOARDSIZE; i++) {
				if((0 <= (this.index_h + i )) && ((this.index_h + i) < Board.BOARDSIZE)){
					valid_moves[this.index_h + i][this.index_v + i] = true;
				}
			}
			// second diagonal
			for(int i = -Board.BOARDSIZE; i<= Board.BOARDSIZE; i++) {
				if((0 <= (this.index_v + i )) && ((this.index_v - i) < Board.BOARDSIZE)){
					valid_moves[this.index_h + i][this.index_v - i] = true;
				}
			}
			return valid_moves;
		}
}
