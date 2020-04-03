package Pieces;

import Pieces.Piece.Type;

public class Princess extends Piece {

	Type type;
	
	public Princess(int x, int y ,Colour pieceColour){
		super(x, y, pieceColour);
		type = Type.PRINCESS;
	}
	
	@Override
	public boolean validMove(int destX, int destY, Piece[][] board) {
		int deltaX = Math.abs(destX - this.x);
		int deltaY = Math.abs(destY - this.y);
		
		if((deltaX == 1 && deltaY == 2) || (deltaX == 2 && deltaY == 1) || (deltaX == deltaY)) {
			return true;
		}else {
			return false;
		}
		
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
			return Piece.Type.PRINCESS.toString().toLowerCase();
		}
		return Piece.Type.PRINCESS.toString();
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
