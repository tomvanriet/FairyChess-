package Pieces;

public class Amazon extends Piece {

	Type type;
	
	public Amazon(int x, int y ,Colour pieceColour){
		super(x, y, pieceColour);
		type = Type.AMAZON;
	}
	
	@Override
	public boolean validMove(int destX, int destY, Piece[][] board) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validCapture(int destX, int destY, Piece[][] board) {
		if(board[destX][destY].pieceColour == this.pieceColour ||board[destX][destY].pieceColour == Colour.EMPTY) {
			return false;
		}
		return false;
	}

	@Override
	public String toString() {
		if(this.pieceColour == Colour.BLACK) {
			return Piece.Type.AMAZON.toString().toLowerCase();
		}
		return Piece.Type.AMAZON.toString();
	}

	@Override
	public Type getType() {
		return this.type;
	}

	@Override
	public boolean canBePromoted(int destX, int destY, Piece[][] board) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean inCheck(int destX, int destY, Piece[][] board) {
		// TODO Auto-generated method stub
		return false;
	}

}