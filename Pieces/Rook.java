package Pieces;

import Pieces.Piece.Type;

public class Rook extends Piece {

	Type type;
	
	public Rook(int x, int y ,Colour pieceColour){
		super(x, y, pieceColour);
		type = Type.ROOK;
	}
	
	@Override
	public boolean validMove(int destX, int destY, Piece[][] board) {
		if((destX == this.x) ||(destY == this.y)) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean validCapture(int destX, int destY, Piece[][] board) {
		if((destX == this.x) ||(destY == this.y) && (board[destX][destY].pieceColour != this.pieceColour && board[destX][destY].pieceColour != Colour.EMPTY)) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public String toString() {
		if(this.pieceColour == Colour.BLACK) {
			return Piece.Type.ROOK.toString().toLowerCase();
		}
		return Piece.Type.ROOK.toString();
	}
	@Override
	public Type getType() {
		return this.type;
	}
	@Override
	public boolean canBePromoted(int destX, int destY, Piece[][] board) {
		return false;
	}
	@Override
	public boolean inCheck(int destX, int destY, Piece[][] board) {
		return false;
	}

}