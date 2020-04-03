package Pieces;

import Pieces.Piece.Type;

public class Pawn extends Piece {

	Type type;

	public Pawn(int x, int y, Colour pieceColour) {
		super(x, y, pieceColour);
		type = Type.PAWN;
	}

	@Override
	public boolean validMove(int destX, int destY, Piece[][] board) {
		int absDeltaX = Math.abs(destX - this.x);
        int absDeltaY = Math.abs(destY - this.y);
        if(this.pieceColour == Colour.WHITE) {
        	if(destY > this.y) {
            	return false;
            }
        }else {
        	if(destY < this.y) {
            	return false;
            }
        }
        
        
        if(((absDeltaX == absDeltaY) && absDeltaX == 1) && validCapture(destX, destY, board)) {
        	return true;
        }
        
        
        
		if ((this.pieceColour == Colour.BLACK && this.y == 1) || (this.pieceColour == Colour.WHITE && this.y == 8)) {
			if (this.x == destX && ((Math.abs((destY - this.y)) == 2))) {
				if((board[destX][destY].getType() == Type.EMPTY) && (board[destX][destY + 1].getType() == Type.EMPTY)) {
					return true;
				}
			}
			if (this.x == destX && ((Math.abs((destY - this.y)) == 1))) {
				if((board[destX][destY].getType() == Type.EMPTY)) {
					return true;
				}
			}
			
		}
		if ((this.pieceColour == Colour.BLACK && this.y != 1) || (this.pieceColour == Colour.WHITE && this.y != 8)) {
			if (this.x == destX && ((Math.abs((destY - this.y)) == 1) && ((board[destX][destY].getType() == Type.EMPTY)))) {
				return true;
			}
		} 

		
		return false;
	}


	@Override
	public String toString() {
		if (this.pieceColour == Colour.BLACK) {
			return Piece.Type.PAWN.toString().toLowerCase();
		}
		return Piece.Type.PAWN.toString();
	}

	@Override
	public boolean validCapture(int destX, int destY, Piece[][] board) {
		int absDeltaX = Math.abs(destX - this.x);
        int absDeltaY = Math.abs(destY - this.y);
        int deltaY = (destY - this.y);
		if(board[destX][destY].pieceColour == this.pieceColour ||board[destX][destY].pieceColour == Colour.EMPTY) {
			return false;
		}
        if(this.pieceColour == Colour.WHITE) {
        	if(destY > this.y) {
            	return false;
            }
        }else {
        	if(destY < this.y) {
            	return false;
            }
        }
        
        if((absDeltaX == absDeltaY) && absDeltaY == 1) {
        	if(this.pieceColour == Colour.WHITE && board[destX][destY].pieceColour == Colour.BLACK && deltaY < 0  ) {
        		return true;
        	}
        	if(this.pieceColour == Colour.BLACK && board[destX][destY].pieceColour == Colour.WHITE && deltaY > 0) {
        		return true;
        	}
        }
		return false;
	}

	@Override
	public Type getType() {
		return this.type;
	}
	public boolean canBePromoted(int destX, int destY, Piece[][] board) {
		if(this.pieceColour == Colour.WHITE) {
			if(destY == 0) {
				return true;
			}
		}
		if(this.pieceColour == Colour.BLACK) {
			if(destY == 9) {
				return true;
			}
		}
		return false;
	}
	@Override
	public boolean inCheck(int destX, int destY, Piece[][] board) {
		// TODO Auto-generated method stub
		return false;
	}

}