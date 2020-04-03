import Pieces.Piece;

public abstract class Tile {
	
	int coordinate;
	
	Tile(int coordinate){
		this.coordinate = coordinate;
	}
	
	public abstract boolean isOccupied();
	
	public abstract Piece getPiece();
	
	public static final class EmptyTile extends Tile{
		
		EmptyTile(int coordinate){
			super(coordinate);
		}
		
		@Override
		public boolean isOccupied() {
			return false;
		}

		@Override
		public Piece getPiece() {
			return null;
		}
		
	}
	public static final class OccupiedTile extends Tile{
		
		Piece currentPiece;
		
		OccupiedTile(int coordinate, Piece currentPiece){
			super(coordinate);
			this.currentPiece = currentPiece;
		}
		
		@Override
		public boolean isOccupied() {
			return true;
		}

		@Override
		public Piece getPiece() {
			return this.currentPiece;
		}
		
	}

}
