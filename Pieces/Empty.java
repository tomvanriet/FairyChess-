package Pieces;

import Pieces.Piece.Type;

public class Empty extends Piece {

	Type type;
	
	public Empty(int x, int y ,Colour pieceColour){
		super(x, y, pieceColour);
		type = Type.EMPTY;
	}
	
	@Override
	public boolean validMove(int destX, int destY, Piece[][] board) {
		return false;
	}

	@Override
	public boolean validCapture(int destX, int destY, Piece[][] board) {
		return false;
	}

	@Override
	public String toString() {
		return Piece.Type.EMPTY.toString();
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