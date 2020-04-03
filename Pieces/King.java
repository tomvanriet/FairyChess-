package Pieces;

import Pieces.Piece.Type;

public class King extends Piece {

	Type type;

	public King(int x, int y, Colour pieceColour) {
		super(x, y, pieceColour);
		type = Type.KING;
	}

	@Override
	public boolean validMove(int destX, int destY, Piece[][] board) {
		int deltaX = Math.abs(destX - this.x);
		int deltaY = Math.abs(destY - this.y);
		if(inCheck(destX, destY, board)) {
			return false;
		}
		if((deltaX < 2) && (deltaY < 2)) {
			return true;
		}
		
		return false;
	}

	@Override
	public boolean validCapture(int destX, int destY, Piece[][] board) {
		int deltaX = Math.abs(destX - this.x);
		int deltaY = Math.abs(destY - this.y);
		if(board[destX][destY].pieceColour == this.pieceColour ||board[destX][destY].pieceColour == Colour.EMPTY) {
			return false;
		}
		if(!validMove(destX, destY, board)) {
			return false;
		}
		
		if((deltaX < 2) && (deltaY < 2)) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		if (this.pieceColour == Colour.BLACK) {
			return Piece.Type.KING.toString().toLowerCase();
		}
		return Piece.Type.KING.toString();

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
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				if(board[i][j]!= this && board[i][j].validCapture(destX, destY, board) && board[i][j].pieceColour != this.pieceColour && board[i][j].pieceColour != Colour.EMPTY) {
					return true;
				}
			}
		}
		return false;
	}

}