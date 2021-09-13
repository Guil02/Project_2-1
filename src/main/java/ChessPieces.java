
public abstract class ChessPieces {
	
	protected boolean color;
	protected int index_h;
	protected int index_v;
	
	public ChessPieces() {
		
	}
	public ChessPieces(boolean color, int index_h, int index_v) {
		
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
		
}