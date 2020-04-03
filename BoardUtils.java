import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import Pieces.Colour;
import Pieces.Piece;
import Pieces.Piece.Type;
import boardValidation.BoardValidationErrors;

public class BoardUtils {

	public static int moveConvetDestX(File moveFile) {
		Scanner boardScanner = null;
		try {
			boardScanner = new Scanner(moveFile);
		} catch (FileNotFoundException exception) {
			throw new IllegalArgumentException("Board file does not exist");
		}
		char[] moves = new char[5];
		int destX = 0;
		while (boardScanner.hasNextLine()) {
			String line = boardScanner.nextLine();
			if (line.contains("%")) {
				line = boardScanner.nextLine();
			}
			moves = line.toCharArray();
			destX = (int) moves[3] - 96;
		}
		return destX;
	}

	public static int moveConvetDestY(File moveFile) {
		Scanner boardScanner = null;
		try {
			boardScanner = new Scanner(moveFile);
		} catch (FileNotFoundException exception) {
			throw new IllegalArgumentException("Board file does not exist");
		}
		char[] moves = new char[5];
		int destY = 0;
		while (boardScanner.hasNextLine()) {
			String line = boardScanner.nextLine();
			if (line.contains("%")) {
				line = boardScanner.nextLine();
			}
			moves = line.toCharArray();
			destY = Character.getNumericValue(moves[4]);
		}
		return destY;
	}

	public static int moveConvetOriginX(File moveFile) {
		Scanner boardScanner = null;
		try {
			boardScanner = new Scanner(moveFile);
		} catch (FileNotFoundException exception) {
			throw new IllegalArgumentException("Board file does not exist");
		}
		char[] moves = new char[5];
		int originX = 0;
		while (boardScanner.hasNextLine()) {
			String line = boardScanner.nextLine();
			if (line.contains("%")) {
				line = boardScanner.nextLine();
			}
			moves = line.toCharArray();
			originX = (int) moves[0] - 96;
		}
		return originX;
	}

	public static int moveConvetOriginY(File moveFile) {
		Scanner boardScanner = null;
		try {
			boardScanner = new Scanner(moveFile);
		} catch (FileNotFoundException exception) {
			throw new IllegalArgumentException("Board file does not exist");
		}
		char[] moves = new char[5];
		int originY = 0;
		while (boardScanner.hasNextLine()) {
			String line = boardScanner.nextLine();
			if (line.contains("%")) {
				line = boardScanner.nextLine();
			}
			moves = line.toCharArray();
			originY = Character.getNumericValue(moves[1]);
		}
		return originY;
	}

	public static void printAllocation(File boardFile) {
		boolean sectionEnd = false;
		// Initialize the Scanner
		Scanner boardScanner = null;
		try {
			boardScanner = new Scanner(boardFile);
		} catch (FileNotFoundException exception) {
			throw new IllegalArgumentException("Board file does not exist");
		}
		while (boardScanner.hasNextLine() && !sectionEnd) {
			String line = boardScanner.nextLine();
			System.out.println(line);
			if (line.contentEquals("-----")) {
				sectionEnd = true;
			}
		}

	}

	public static String getStatusLine(File boardFile, File moveFile, Board gameBoard) {
		// Initialize the Scanner
		String StatusLine = new String();
		Scanner boardScanner = null;
		try {
			boardScanner = new Scanner(boardFile);
		} catch (FileNotFoundException exception) {
			throw new IllegalArgumentException("Board file does not exist");
		}
		while (boardScanner.hasNextLine()) {
		
			String line = boardScanner.nextLine();
			do {
				line = boardScanner.nextLine();
			} while (line != "-----" && boardScanner.hasNextLine());
			String[] statusLine = line.split(":");
			char[] castling = statusLine[1].toCharArray();
			int destX = moveConvetDestX(moveFile) - 1;
			int destY = 10 - moveConvetDestY(moveFile);
			

			if ((gameBoard.getPiece(0, 0).pieceColour == Colour.BLACK) && (gameBoard.getPiece(0, 0).getType() == Type.ROOK) && (gameBoard.getPiece(5, 0).getType() == Type.KING && !gameBoard.getPiece(5, 0).inCheck(3, 0, gameBoard.gameBoard) && !gameBoard.getPiece(5, 0).inCheck(4, 0, gameBoard.gameBoard))) {
				//castling[0] = '+';
			} else {
				castling[0] = '-';
			}
			if ((gameBoard.getPiece(9, 0).pieceColour == Colour.BLACK) && (gameBoard.getPiece(9, 0).getType() == Type.ROOK)&& (gameBoard.getPiece(5, 0).getType() == Type.KING) && !gameBoard.getPiece(5, 0).inCheck(7, 0, gameBoard.gameBoard) && !gameBoard.getPiece(5, 0).inCheck(6, 0, gameBoard.gameBoard)) {
				//castling[1] = '+';
			} else {
				castling[1] = '-';
			}
			if ((gameBoard.getPiece(0, 9).pieceColour == Colour.WHITE) && (gameBoard.getPiece(0, 9).getType() == Type.ROOK)&& (gameBoard.getPiece(5, 9).getType() == Type.KING) && !gameBoard.getPiece(5, 9).inCheck(3, 9, gameBoard.gameBoard) && !gameBoard.getPiece(5, 9).inCheck(4, 9, gameBoard.gameBoard)) {
				//castling[2] = '+';
			} else {
				castling[2] = '-';
			}
			if ((gameBoard.getPiece(9, 9).pieceColour == Colour.WHITE) && (gameBoard.getPiece(9, 9).getType() == Type.ROOK)&& (gameBoard.getPiece(5, 9).getType() == Type.KING) && !gameBoard.getPiece(5, 9).inCheck(7, 9, gameBoard.gameBoard) && !gameBoard.getPiece(5, 9).inCheck(6, 9, gameBoard.gameBoard)) {
				//castling[3] = '+';
			} else {
				castling[3] = '-';
			}
			if (statusLine[0].equals("b")) {
				statusLine[0] = "w";
				int numMoves = Integer.parseInt(statusLine[2]);
				statusLine[3] = Integer.toString(numMoves + 1);
			} else {
				statusLine[0] = "b";
				
			}
			
			int originX = BoardUtils.moveConvetOriginX(moveFile) - 1;
			int originY = 10 - BoardUtils.moveConvetOriginY(moveFile);
			Piece current = gameBoard.getPiece(originX, originY);
			if((current.getType() != Type.PAWN) && current.validMove(destX, destY, gameBoard.gameBoard) && !captureMove(gameBoard, destX, destY, originX, originY) ) {
				int numMoves = Integer.parseInt(statusLine[2]);
				statusLine[2] = Integer.toString(numMoves + 1);
				
			}
			String castleStatus = new String(castling);
			StatusLine =(statusLine[0] + ":" + castleStatus  + ":" + statusLine[2] + ":" + statusLine[3]);
		}
		
		return StatusLine;

	}

	public static int getMoveLine(File moveFile) {
		int moveLine = 0;
		// Initialize the Scanner
		Scanner moveScanner = null;
		try {
			moveScanner = new Scanner(moveFile);
		} catch (FileNotFoundException exception) {
			throw new IllegalArgumentException("Board file does not exist");
		}

		while (moveScanner.hasNextLine()) {
			String line = moveScanner.nextLine();
			if (line.contains("%")) {
				line = moveScanner.nextLine();
				moveLine++;
			}
			moveLine++;
		}

		return moveLine;
	}

	public static String moveType(File moveFile) {
		// Initialize the Scanner
		Scanner moveScanner = null;
		try {
			moveScanner = new Scanner(moveFile);
		} catch (FileNotFoundException exception) {
			throw new IllegalArgumentException("Board file does not exist");
		}

		char[] moves = new char[9];
		while (moveScanner.hasNextLine()) {
			String line = moveScanner.nextLine();
			if (line.contains("%")) {
				line = moveScanner.nextLine();
			}
			moves = line.toCharArray();
			if (line.contains("x")) {
				return "Capture";
			}
			if (line.contains("-") && !line.contains("0-") && !line.contains("=")) {
				return "Move";
			}
			if (line.contains("=")) {
				return "Promotion";
			}
			if (line.contains("+")) {
				return "Check";
			}
			if (line.contains("0-0") && line.contains("0-0-0-0")) {
				return "Queenside";
			}
			if (line.contains("0-0-0-0")) {
				return "Kingside";
			}
			
			

		}
		return null;

	}

	public static char promotedTo(File moveFile) {
		// Initialize the Scanner
		char promotedPiece = ' ';
		Scanner moveScanner = null;
		try {
			moveScanner = new Scanner(moveFile);
		} catch (FileNotFoundException exception) {
			throw new IllegalArgumentException("Board file does not exist");
		}

		char[] moves = new char[9];
		while (moveScanner.hasNextLine()) {
			String line = moveScanner.nextLine();
			if (line.contains("%")) {
				line = moveScanner.nextLine();
			}
			moves = line.toCharArray();
			String[] moveLine = line.split("=");
			promotedPiece = moveLine[1].charAt(0);
		}
		
		
		return promotedPiece;

	}
	
	public static boolean isAllocated(File boardFile, File moveFile) {
		int k = 0;
		int r = 0;
		int q = 0;
		int n = 0;
		int b = 0;
		int p = 0;
		int d = 0;
		int f = 0;
		int e = 0;
		int a = 0;
		int w = 0;
		int lineNum = 0;
		int lineDiv = 0;
		boolean sectionEnd = false;
		// Initialize the Scanner
		Scanner boardScanner = null;
		try {
			boardScanner = new Scanner(boardFile);
		} catch (FileNotFoundException exception) {
			throw new IllegalArgumentException("Board file does not exist");
		}

		// Read the file line by line, printing out as we go along
		while (boardScanner.hasNextLine() && !sectionEnd) {
			String line = boardScanner.nextLine();
			if (line.startsWith("-") && !line.equals("-----")) {
				lineDiv = lineNum + 1;
				BoardValidationErrors.illegalPieceAllocation(lineDiv);
			}
			if (line.contains("-----")) {
				lineDiv = lineNum + 1;
				if ((p + d) == 0) {
					BoardValidationErrors.illegalPieceAllocation(lineDiv);
				} else if ((k + r + q + n + b + f + e + a + w) == 0 || (k != 1 && r < 2)) {
					BoardValidationErrors.illegalPieceAllocation(lineDiv);
				} else if (k != 1) {
					BoardValidationErrors.illegalPieceAllocation(lineDiv);
				}
				sectionEnd = true;
			}
			if ((p + d) > 10) {
				BoardValidationErrors.illegalPieceAllocation(lineNum);
			}
			if ((k + r + q + n + b + f + e + a + w) > 10) {
				BoardValidationErrors.illegalPieceAllocation(lineNum);
			}
			lineNum++;

			char piece = line.charAt(0);
			String pieceCount = line.replaceAll("[^0-9]", "");
			if (piece == '%') {

			} else {

				try {
					switch (piece) {
					case 'k':
						k = 1;
						break;
					case 'r':
						r = 1;
						break;
					case 'q':
						q = 1;
						break;
					case 'n':
						n = 1;
						break;
					case 'b':
						b = 1;
						break;
					case 'p':
						p = 1;
						break;
					case 'd':
						d = 1;
						break;
					case 'f':
						f = 1;
						break;
					case 'e':
						e = 1;
						break;
					case 'a':
						a = 1;
						break;
					case 'w':
						w = 1;
						break;
					default:
						break;
					}
				} catch (NumberFormatException exception) {

				}
			}
		}
		switch (Character.toLowerCase(promotedTo(moveFile))) {
		case 'k':
			return k > 0;

		case 'r':
			return r > 0;

		case 'q':
			return q > 0;

		case 'n':
			return n > 0;
			
		case 'b':
			return b > 0;
			
		case 'p':
			return false;
			
		case 'd':
			return false;
			
		case 'f':
			return f > 0;
			
		case 'e':
			return e > 0;
			
		case 'a':
			return a > 0;
			
		case 'w':
			return w > 0;
		default:
			
		}
		return false;
	}
	
	public static boolean captureMove(Board board, int destX, int destY, int originX, int originY) {
		if ((board.getPiece(destX, destY).pieceColour != board.getPiece(originX, originY).pieceColour)
				&& (board.getPiece(destX, destY).pieceColour != Colour.EMPTY)) {
			return true;
		} else {
			return false;
		}
	}
	

}
