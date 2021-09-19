package model.pieces;

public abstract class Piece {
	
	protected boolean color;
	protected int index_h;
	protected int index_v;
	private char pieceChar;
	
	public Piece() {
		
	}
	public Piece(boolean color, int index_h, int index_v) {
		
		this.color = color;
		this.index_h = index_h;
		this.index_v = index_v;
		
		//TODO show piece 
	}
	
	public void move(int index_h, int index_v){
		
		this.index_h = index_h;
		this.index_v = index_v;
		
		//TODO show new piece position
	}
	
	public abstract boolean[][] validMoves();

	/**
	 * Assigns a letter to a piece
	 * No public use
	 * @param pieceChar
	 */
	protected void setPieceChar(char pieceChar) {
		this.pieceChar = pieceChar;
	}

	/**
	 * Returns the current char of the piece
	 * Upper case = white piece
	 * Lower case = black piece
	 * (default naming)
	 * @return Letter of the current piece
	 */
	public char getPieceChar() {
		return pieceChar;
	}


}



