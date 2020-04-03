package Pieces;

import Pieces.Piece.Type;

public class Queen extends Piece {

	Type type;
	
	public Queen(int x, int y ,Colour pieceColour){
		super(x, y, pieceColour);
		type = Type.QUEEN;
	}
	
	@Override
	public boolean validMove(int destX, int destY, Piece[][] board) {
		int deltaX = Math.abs(destX - this.x);
		int deltaY = Math.abs(destY - this.y);
		if((destX == this.x) || (destY == this.y) || (deltaX == deltaY)) {
			return true;
		}else {
			return false;
		}
	}
		

	@Override
	public boolean validCapture(int destX, int destY, Piece[][] board) {
		int deltaX = Math.abs(destX - this.x);
		int deltaY = Math.abs(destY - this.y);
		if(board[destX][destY].pieceColour == this.pieceColour ||board[destX][destY].pieceColour == Colour.EMPTY) {
			return false;
		}
		if((destX == this.x) || (destY == this.y) || (deltaX == deltaY)) {
			return true;
		}else {
			return false;
		}
	
	}

	@Override
	public String toString() {
		if(this.pieceColour == Colour.BLACK) {
			return Piece.Type.QUEEN.toString().toLowerCase();
		}
		return Piece.Type.QUEEN.toString();
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
		// TODO Auto-generated method stub
		return false;
	}
	

}