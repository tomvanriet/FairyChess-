package Pieces;

import Pieces.Piece.Type;

public class Knight extends Piece {
	
	
	Type type;
	
	public Knight(int x, int y ,Colour pieceColour){
		super(x, y, pieceColour);
		type = Type.KNIGHT;
	}

	@Override
	public boolean validMove(int destX, int destY, Piece[][] board) {
		int deltaX = Math.abs(destX - this.x);
		int deltaY = Math.abs(destY - this.y);
		
		if((deltaX == 1 && deltaY == 2) || (deltaX == 2 && deltaY == 1)) {
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
		if((deltaX == 1 && deltaY == 2) || (deltaX == 2 && deltaY == 1)) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		if(this.pieceColour == Colour.BLACK) {
			return Piece.Type.KNIGHT.toString().toLowerCase();
		}
		return Piece.Type.KNIGHT.toString();
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
