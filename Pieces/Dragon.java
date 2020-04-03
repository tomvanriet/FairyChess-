package Pieces;

import Pieces.Piece.Type;

public class Dragon extends Piece {

	Type type;
	
	public Dragon(int x, int y ,Colour pieceColour){
		super(x, y, pieceColour);
		type = Type.DRAGON;
	}
	
	@Override
	public boolean validMove(int destX, int destY, Piece[][] board) {
		int deltaX = Math.abs(destX - this.x);
		int deltaY = Math.abs(destY - this.y);
		return (deltaX == deltaY) && (deltaX < 3);
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
			return Piece.Type.DRAGON.toString().toLowerCase();
		}else {
			return Piece.Type.DRAGON.toString();
		}
		
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