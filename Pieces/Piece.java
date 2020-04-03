package Pieces;


public abstract class Piece {
	public int x;
	public int y;
	public  Colour pieceColour;
	
	public Piece(final int x, final int y, final Colour pieceColour){
		this.x = x;
		this.y = y;
		this.pieceColour = pieceColour;
	}
	
	public abstract boolean validMove(int destX, int destY, Piece[][] board);
	
	public abstract boolean validCapture(int destX, int destY, Piece[][] board);
	
	public abstract Type getType();
	
	public abstract boolean canBePromoted(int destX, int destY, Piece[][] board);
	
	public abstract boolean inCheck(int destX, int destY, Piece[][] board);
	
	public enum Type {
		BISHOP("B"),
		KING("K"),
		KNIGHT("N"),
		PAWN("P"),
		QUEEN("Q"),
		ROOK("R"),
		PRINCESS("W"),
		SOLIDER("D"),
		DRAGON("F"),
		AMAZON("A"),
		ELEPHANT("E"),
		EMPTY(".");
		
		private String pieceType;
		
		@Override
		public String toString() {
			return this.pieceType;
		}
		
		Type(String pieceType) {
			this.pieceType = pieceType;
		}
		
	
	}

}
