// TODO check if king doesn't put himself in danger

public class King extends ChessPieces {
	
	public King(boolean color, int index_h, int index_v) {
		super(color, index_h, index_v);
	}
	
	public boolean[][] validMoves() {
		
		boolean[][] valid_moves = new boolean[Board.BOARDSIZE][Board.BOARDSIZE];
		
		for(int i = -1; i<= 1; i++) {
			for(int j = -1; j<=1; j++) {
				
				if(!(i==0 && j==0)) {
					if((0 <= (this.index_h+i)) && ((this.index_h+i) < Board.BOARDSIZE)) {
						if((0 <= (this.index_v+i)) && ((this.index_v+i) < Board.BOARDSIZE)) {
							valid_moves[this.index_h + i][this.index_v + j] = true;
						}
					}
				}
			}
		}
		return valid_moves;
	}
}
