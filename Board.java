import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import Pieces.*;
import Pieces.Piece.Type;

public class Board {

	public Piece[][] gameBoard;

	public Board(String[][] Board) {
		gameBoard = new Piece[10][10];

	}

	public Piece getPiece(int i, int j) {
		return gameBoard[i][j];
	}

	public void initPieces(String[][] stringBoard) {
		Piece[][] board = this.gameBoard;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				switch (stringBoard[i][j]) {
				case "k":
					Piece blackKing = new King(j, i, Colour.BLACK);
					board[j][i] = blackKing;
					break;
				case "K":
					Piece whiteKing = new King(j, i, Colour.WHITE);
					board[j][i] = whiteKing;
					break;
				case "r":
					Piece blackRook = new Rook(j, i, Colour.BLACK);
					board[j][i] = blackRook;
					break;
				case "R":
					Piece whiteRook = new Rook(j, i, Colour.WHITE);
					board[j][i] = whiteRook;
					break;
				case "q":
					Piece blackQueen = new Queen(j, i, Colour.BLACK);
					board[j][i] = blackQueen;
					break;
				case "Q":
					Piece whiteQueen = new Queen(j, i, Colour.WHITE);
					board[j][i] = whiteQueen;
					break;
				case "n":
					Piece blackKnight = new Knight(j, i, Colour.BLACK);
					board[j][i] = blackKnight;
					break;
				case "N":
					Piece whiteKnight = new Knight(j, i, Colour.WHITE);
					board[j][i] = whiteKnight;
					break;
				case "b":
					Piece blackBishop = new Bishop(j, i, Colour.BLACK);
					board[j][i] = blackBishop;
					break;
				case "B":
					Piece whiteBishop = new Bishop(j, i, Colour.WHITE);
					board[j][i] = whiteBishop;
					break;
				case "p":
					Piece blackPawn = new Pawn(j, i, Colour.BLACK);
					board[j][i] = blackPawn;
					break;
				case "P":
					Piece whitePawn = new Pawn(j, i, Colour.WHITE);
					board[j][i] = whitePawn;
					break;
				case "d":
					Piece blackSolider = new Solider(j, i, Colour.BLACK);
					board[j][i] = blackSolider;
					break;
				case "D":
					Piece whiteSolider = new Solider(j, i, Colour.WHITE);
					board[j][i] = whiteSolider;
					break;
				case "f":
					Piece blackDragon = new Dragon(j, i, Colour.BLACK);
					board[j][i] = blackDragon;
					break;
				case "F":
					Piece whiteDragon = new Dragon(j, i, Colour.WHITE);
					board[j][i] = whiteDragon;
					break;
				case "e":
					Piece blackElephant = new Elephant(j, i, Colour.BLACK);
					board[j][i] = blackElephant;
					break;
				case "E":
					Piece whiteElephant = new Elephant(j, i, Colour.WHITE);
					board[j][i] = whiteElephant;
					break;
				case "a":
					Piece blackAmazon = new Amazon(j, i, Colour.BLACK);
					board[j][i] = blackAmazon;
					break;
				case "A":
					Piece whiteAmazon = new Amazon(j, i, Colour.BLACK);
					board[j][i] = whiteAmazon;
					break;
				case "w":
					Piece blackPrincess = new Princess(j, i, Colour.BLACK);
					board[j][i] = blackPrincess;
					break;
				case "W":
					Piece whitePrincess = new Princess(j, i, Colour.BLACK);
					board[j][i] = whitePrincess;
					break;
				case ".":
					Piece emptyPiece = new Empty(j, i, Colour.EMPTY);
					board[j][i] = emptyPiece;
					break;
				default:
					break;
				}
			}
		}

	}

	public void Move(File moveFile, File boardFile) {
		int halfMove = getHalfMove(boardFile);
		int moveCount = getMoveCount(boardFile);
		Scanner boardScanner = null;
		try {
			boardScanner = new Scanner(moveFile);
		} catch (FileNotFoundException exception) {
			throw new IllegalArgumentException("Move file does not exist");
		}
		char[] moves = new char[5];
		String StatusLine = " ";
		while (boardScanner.hasNextLine()) {
			String line = boardScanner.nextLine();
			if (line.contains("%")) {
				line = boardScanner.nextLine();
			}
			if (line.equals("0-0-0")) {
				String castleStatus = " ";
				queensideCastle(boardFile, moveFile, castleStatus);
				
				BoardUtils.printAllocation(boardFile);
				printBoard();
				System.out.println("-----");
				System.out.println(queensideCastleStatus(boardFile, moveFile, castleStatus));
				System.exit(0);
			}
			if (line.equals("0-0-0-0")) {
				String castleStatus = " ";
				kingsideCastle(boardFile, moveFile, castleStatus);
				
				BoardUtils.printAllocation(boardFile);
				printBoard();
				System.out.println("-----");
				System.out.println(kingsideCastleStatus(boardFile, moveFile, castleStatus));
				System.exit(0);
			}
			
			moves = line.toCharArray();
			int oX = (int) moves[0] - 96;
			int originX = oX - 1;
			int oY = Character.getNumericValue(moves[1]);
			int originY = 10 - oY;
			int dX = (int) moves[3] - 96;
			int destX = dX - 1;
			int dY = 0;
			if (moves.length > 5) {
				if (line.charAt(5) == '0') {
					String s = "" + moves[4] + line.charAt(5);
					dY = Integer.parseInt(s);
				} else {
					dY = Character.getNumericValue(moves[4]);
				}
			} else {
				dY = Character.getNumericValue(moves[4]);
			}

			int destY = 10 - dY;
			Piece piece = getPiece(originX, originY);
			if (piece.canBePromoted(destX, destY, this.gameBoard) && BoardUtils.moveType(moveFile).equals("Promotion")
					&& BoardUtils.isAllocated(boardFile, moveFile)) {
				StatusLine = getStatusLine(piece, boardFile, moveFile, this);
				Promotion(piece, destX, destY, moveFile);
				if (piece.pieceColour == Colour.BLACK) {
					moveCount++;
				}
				continue;
			}
			if (!piece.validCapture(destX, destY, this.gameBoard) && BoardUtils.moveType(moveFile).equals("Capture")) {
				MoveValidationErrors.illegalCapture(BoardUtils.getMoveLine(moveFile));
			}
			if (piece.canBePromoted(destX, destY, this.gameBoard) && BoardUtils.moveType(moveFile).equals("Promotion")
					&& !BoardUtils.isAllocated(boardFile, moveFile)) {
				MoveValidationErrors.illegalPromotion(BoardUtils.getMoveLine(moveFile));
			}
			if (!piece.validCapture(destX, destY, this.gameBoard) && !piece.validMove(destX, destY, this.gameBoard)) {
				MoveValidationErrors.illegalMove(BoardUtils.getMoveLine(moveFile));
			}

			if (captureMove(piece, destX, destY) && !BoardUtils.moveType(moveFile).equals("Capture")) {
				MoveValidationErrors.illegalMove(BoardUtils.getMoveLine(moveFile));
			}
			if (BoardUtils.moveType(moveFile).equals("Capture") && !piece.validCapture(destX, destY, this.gameBoard)) {
				MoveValidationErrors.illegalCapture(BoardUtils.getMoveLine(moveFile));
			}
			if (!piece.validMove(destX, destY, this.gameBoard) && !piece.canBePromoted(destX, destY, this.gameBoard)) {
				MoveValidationErrors.illegalMove(BoardUtils.getMoveLine(moveFile));
			}
			if (captureMove(piece, destX, destY) && !piece.validCapture(destX, destY, this.gameBoard)) {
				MoveValidationErrors.illegalCapture(BoardUtils.getMoveLine(moveFile));
			}
			if (piece.validMove(destX, destY, this.gameBoard) && !captureMove(piece, destX, destY)
					&& !piece.inCheck(destX, destY, this.gameBoard) && BoardUtils.moveType(moveFile).equals("Move")
					&& !piece.canBePromoted(destX, destY, this.gameBoard)) {
				StatusLine = getStatusLine(piece, boardFile, moveFile, this);
				int originX2 = piece.x;
				int originY2 = piece.y;

				piece.x = destX;
				piece.y = destY;

				this.gameBoard[destX][destY] = piece;
				this.gameBoard[originX2][originY2] = new Empty(originX, originY, Colour.EMPTY);

				if (piece.getType() == Type.KING && piece.inCheck(destX, destY, this.gameBoard)) {
					MoveValidationErrors.illegalMove(BoardUtils.getMoveLine(moveFile));
				} else {
					if (piece.pieceColour == Colour.BLACK) {
						moveCount++;
					}

					if (piece.getType() != Type.PAWN && piece.getType() != Type.SOLIDER) {
						halfMove++;
					}
				}

			}
			if (piece.validCapture(destX, destY, this.gameBoard) && captureMove(piece, destX, destY)
					&& BoardUtils.moveType(moveFile).equals("Capture")
					&& !piece.canBePromoted(destX, destY, this.gameBoard) && !piece.inCheck(destX, destY, this.gameBoard)) {
				StatusLine = getStatusLine(piece, boardFile, moveFile, this);
				int originX2 = piece.x;
				int originY2 = piece.y;

				this.gameBoard[destX][destY] = new Empty(originX, originY, Colour.EMPTY);
				piece.x = destX;
				piece.y = destY;

				this.gameBoard[destX][destY] = piece;
				this.gameBoard[originX2][originY2] = new Empty(originX2, originY2, Colour.EMPTY);
			
					if (piece.pieceColour == Colour.BLACK) {
						moveCount++;
					}
				
			}

		}
		
		
		if (!boardHasChanged(moveFile)) {
			getEmptyStatus(boardFile);
		}else {
			BoardUtils.printAllocation(boardFile);
			printBoard();
			System.out.println("-----");
			System.out.println(StatusLine + ":" + halfMove + ":" + moveCount);
		}
		
		

	}

	private boolean boardHasChanged(File moveFile) {
		Scanner boardScanner = null;
		try {
			boardScanner = new Scanner(moveFile);
		} catch (FileNotFoundException exception) {
			throw new IllegalArgumentException("Move file does not exist");
		}
		int lineNum = 0;

		if(!boardScanner.hasNextLine()) {
			return false;
		}

		return true;

	}

	public boolean captureMove(Piece piece, int destX, int destY) {
		if ((this.getPiece(destX, destY).pieceColour != piece.pieceColour)
				&& (this.getPiece(destX, destY).pieceColour != Colour.EMPTY)) {
			return true;
		} else {
			return false;
		}
	}

	public void printBoard() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				System.out.print(gameBoard[j][i]);
				if (j != 9) {
					System.out.print(" ");
				}
				if ((j + 1) % 10 == 0) {
					System.out.println();
				}

			}
		}
	}

	public void Promotion(Piece piece, int destX, int destY, File moveFile) {
		char promotedPiece = BoardUtils.promotedTo(moveFile);
		if (piece.pieceColour == Colour.BLACK && Character.isUpperCase(promotedPiece)) {
			MoveValidationErrors.illegalPromotion(BoardUtils.getMoveLine(moveFile));
		}
		if (piece.pieceColour == Colour.BLACK && !Character.isUpperCase(promotedPiece)) {
			int originX = piece.x;
			int originY = piece.y;

			piece.x = destX;
			piece.y = destY;
			switch (promotedPiece) {
			case 'k':
				this.gameBoard[destX][destY] = new King(destX, destY, Colour.BLACK);
				break;
			case 'r':
				this.gameBoard[destX][destY] = new Rook(destX, destY, Colour.BLACK);
				break;
			case 'q':
				this.gameBoard[destX][destY] = new Queen(destX, destY, Colour.BLACK);
				break;
			case 'n':
				this.gameBoard[destX][destY] = new Knight(destX, destY, Colour.BLACK);
				break;
			case 'b':
				this.gameBoard[destX][destY] = new Bishop(destX, destY, Colour.BLACK);
				break;
			case 'p':
				MoveValidationErrors.illegalPromotion(BoardUtils.getMoveLine(moveFile));
				break;
			case 'd':
				MoveValidationErrors.illegalPromotion(BoardUtils.getMoveLine(moveFile));
				break;
			case 'f':
				this.gameBoard[destX][destY] = new Dragon(destX, destY, Colour.BLACK);
				break;
			case 'e':
				this.gameBoard[destX][destY] = new Elephant(destX, destY, Colour.BLACK);
				break;
			case 'a':
				this.gameBoard[destX][destY] = new Amazon(destX, destY, Colour.BLACK);
				break;
			case 'w':
				this.gameBoard[destX][destY] = new Princess(destX, destY, Colour.BLACK);
				break;
			default:
				break;
			}
			this.gameBoard[originX][originY] = new Empty(originX, originY, Colour.EMPTY);
		}
		if (piece.pieceColour == Colour.WHITE && !Character.isUpperCase(promotedPiece)) {
			MoveValidationErrors.illegalPromotion(BoardUtils.getMoveLine(moveFile));
		}
		if (piece.pieceColour == Colour.WHITE && Character.isUpperCase(promotedPiece)) {
			int originX = piece.x;
			int originY = piece.y;
			int newY = destY;
			switch (Character.toLowerCase(promotedPiece)) {
			case 'k':
				this.gameBoard[destX][newY] = new King(destX, destY, Colour.WHITE);
				break;
			case 'r':
				this.gameBoard[destX][newY] = new Rook(destX, destY, Colour.WHITE);
				break;
			case 'q':
				this.gameBoard[destX][newY] = new Queen(destX, destY, Colour.WHITE);
				break;
			case 'n':
				this.gameBoard[destX][newY] = new Knight(destX, destY, Colour.WHITE);
				break;
			case 'b':
				this.gameBoard[destX][newY] = new Bishop(destX, destY, Colour.WHITE);
				break;
			case 'p':
				MoveValidationErrors.illegalPromotion(BoardUtils.getMoveLine(moveFile));
				break;
			case 'd':
				MoveValidationErrors.illegalPromotion(BoardUtils.getMoveLine(moveFile));
				break;
			case 'f':
				this.gameBoard[destX][newY] = new Dragon(destX, destY, Colour.WHITE);
				break;
			case 'e':
				this.gameBoard[destX][newY] = new Elephant(destX, destY, Colour.WHITE);
				break;
			case 'a':
				this.gameBoard[destX][newY] = new Amazon(destX, destY, Colour.WHITE);
				break;
			case 'w':
				this.gameBoard[destX][newY] = new Princess(destX, destY, Colour.WHITE);
				break;
			default:
				break;
			}
			this.gameBoard[originX][originY] = new Empty(originX, originY, Colour.EMPTY);
		}
	}

	public String getStatusLine(Piece piece, File boardFile, File moveFile, Board gameBoard) {
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
			int destX = BoardUtils.moveConvetDestX(moveFile) - 1;
			int destY = 10 - BoardUtils.moveConvetDestY(moveFile);

			if ((gameBoard.getPiece(0, 0).pieceColour == Colour.BLACK)
					&& (gameBoard.getPiece(0, 0).getType() == Type.ROOK)
					&& (gameBoard.getPiece(5, 0).getType() == Type.KING
					/*
					 * && !gameBoard.getPiece(5, 0).inCheck(3, 0, gameBoard.gameBoard) &&
					 * !gameBoard.getPiece(5, 0).inCheck(4, 0, gameBoard.gameBoard)
					 */)) {
				// castling[0] = '+';
			} else {
				castling[0] = '-';
			}
			if ((gameBoard.getPiece(9, 0).pieceColour == Colour.BLACK)
					&& (gameBoard.getPiece(9, 0).getType() == Type.ROOK) && (gameBoard.getPiece(5, 0)
							.getType() == Type.KING)/*
													 * && !gameBoard.getPiece(5, 0).inCheck(7, 0, gameBoard.gameBoard)
													 * && !gameBoard.getPiece(5, 0).inCheck(6, 0, gameBoard.gameBoard)
													 */) {
				// castling[1] = '+';
			} else {
				castling[1] = '-';
			}
			if ((gameBoard.getPiece(0, 9).pieceColour == Colour.WHITE)
					&& (gameBoard.getPiece(0, 9).getType() == Type.ROOK) && (gameBoard.getPiece(5, 9)
							.getType() == Type.KING)/*
													 * && !gameBoard.getPiece(5, 9).inCheck(3, 9, gameBoard.gameBoard)
													 * && !gameBoard.getPiece(5, 9).inCheck(4, 9, gameBoard.gameBoard)
													 */) {
				// castling[2] = '+';
			} else {
				castling[2] = '-';
			}
			if ((gameBoard.getPiece(9, 9).pieceColour == Colour.WHITE)
					&& (gameBoard.getPiece(9, 9).getType() == Type.ROOK) && (gameBoard.getPiece(5, 9)
							.getType() == Type.KING)/*
													 * && !gameBoard.getPiece(5, 9).inCheck(7, 9, gameBoard.gameBoard)
													 * && !gameBoard.getPiece(5, 9).inCheck(6, 9, gameBoard.gameBoard)
													 */) {
				// castling[3] = '+';
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
			if ((piece.getType() != Type.PAWN) && (piece.getType() != Type.SOLIDER)
					&& piece.validMove(destX, destY, gameBoard.gameBoard) && !captureMove(piece, destX, destY)) {
				int numMoves = Integer.parseInt(statusLine[2]);
				statusLine[2] = Integer.toString(numMoves + 1);

			}
			String castleStatus = new String(castling);
			StatusLine = (statusLine[0] + ":" + castleStatus);
		}

		return StatusLine;

	}

	public int getHalfMove(File boardFile) {
		int halfMove = 0;
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

			halfMove = Integer.parseInt(statusLine[2]);

		}
		return halfMove;
	}

	public int getMoveCount(File boardFile) {
		int moveCount = 0;
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
			moveCount = Integer.parseInt(statusLine[3]);

		}
		return moveCount;
	}

	private void getEmptyStatus(File boardFile) {
		Scanner boardScanner = null;
		try {
			boardScanner = new Scanner(boardFile);
		} catch (FileNotFoundException exception) {
			throw new IllegalArgumentException("Board file does not exist");
		}
		while (boardScanner.hasNextLine()) {
			String line = boardScanner.nextLine();
			if(line.startsWith("%")) {
				line = boardScanner.nextLine();
			}
			System.out.println(line);
		}
	}
	
	public void queensideCastle(File boardFile, File moveFile, String castleStatus) {
		Scanner boardScanner = null;
		String[] statusLine = new String[10];
		String turn = " ";
		int halfMoveCount = 0;
		int moveCount = 0;
		char[] castling = {'+','+','+','+'};
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
			statusLine = line.split(":");
		}

		if(statusLine[0].contentEquals("b")) {
			if(this.gameBoard[5][0].pieceColour == Colour.BLACK && this.gameBoard[0][0].pieceColour == Colour.BLACK && this.gameBoard[5][0].getType() == Type.KING && this.gameBoard[0][0].getType() == Type.ROOK) {
				if(this.gameBoard[4][0].getType() == Type.EMPTY && this.gameBoard[3][0].getType() == Type.EMPTY && this.gameBoard[2][0].getType() == Type.EMPTY && this.gameBoard[1][0].getType() == Type.EMPTY) {
					this.gameBoard[5][0] = new Empty(5,0, Colour.EMPTY);
					this.gameBoard[0][0] = new Empty(0,0, Colour.EMPTY);
					this.gameBoard[1][0] = new King(1,0, Colour.BLACK);
					this.gameBoard[2][0] = new Rook(2,0, Colour.BLACK);
					castling[0] = '-';
					castling[1] = '-';
					turn = "w";
					halfMoveCount++;
					moveCount++;
				}else {
					MoveValidationErrors.illegalCastlingMove(BoardUtils.getMoveLine(moveFile));
				}
			}
		}
		if(statusLine[0].contentEquals("w")) {
			if(this.gameBoard[5][9].pieceColour == Colour.WHITE&& this.gameBoard[0][9].pieceColour == Colour.WHITE && this.gameBoard[5][9].getType() == Type.KING && this.gameBoard[0][9].getType() == Type.ROOK) {
				if(this.gameBoard[4][9].getType() == Type.EMPTY && this.gameBoard[3][9].getType() == Type.EMPTY && this.gameBoard[2][9].getType() == Type.EMPTY && this.gameBoard[1][9].getType() == Type.EMPTY) {
					this.gameBoard[5][9] = new Empty(5,9, Colour.EMPTY);
					this.gameBoard[0][9] = new Empty(0,9, Colour.EMPTY);
					this.gameBoard[1][9] = new King(1,9, Colour.WHITE);
					this.gameBoard[2][9] = new Rook(2,9, Colour.WHITE);
					castling[0] = '-';
					castling[1] = '-';
					turn = "b";
					halfMoveCount++;
				}else {
					MoveValidationErrors.illegalCastlingMove(BoardUtils.getMoveLine(moveFile));
				}
			}
		}
		castleStatus = turn + ":" + String.valueOf(castling)+":" + halfMoveCount + ":" + moveCount;
		
	}
	public void kingsideCastle(File boardFile, File moveFile, String castleStatus) {
		Scanner boardScanner = null;
		String[] statusLine = new String[10];
		String turn = " ";
		int halfMoveCount = 0;
		int moveCount = 0;
		char[] castling = {'+','+','+','+'};
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
			statusLine = line.split(":");
		}

		if(statusLine[0].contentEquals("b")) {
			if(this.gameBoard[5][0].pieceColour == Colour.BLACK && this.gameBoard[9][0].pieceColour == Colour.BLACK && this.gameBoard[5][0].getType() == Type.KING && this.gameBoard[9][0].getType() == Type.ROOK) {
				if(this.gameBoard[6][0].getType() == Type.EMPTY && this.gameBoard[7][0].getType() == Type.EMPTY && this.gameBoard[8][0].getType() == Type.EMPTY) {
					this.gameBoard[5][0] = new Empty(5,0, Colour.EMPTY);
					this.gameBoard[9][0] = new Empty(9,0, Colour.EMPTY);
					this.gameBoard[8][0] = new King(8,0, Colour.BLACK);
					this.gameBoard[7][0] = new Rook(7,0, Colour.BLACK);
				}else {
					MoveValidationErrors.illegalCastlingMove(BoardUtils.getMoveLine(moveFile));
				}
			}
		}
		if(statusLine[0].contentEquals("w")) {
			if(this.gameBoard[5][9].pieceColour == Colour.BLACK && this.gameBoard[9][9].pieceColour == Colour.BLACK && this.gameBoard[5][9].getType() == Type.KING && this.gameBoard[9][9].getType() == Type.ROOK) {
				if(this.gameBoard[6][9].getType() == Type.EMPTY && this.gameBoard[7][9].getType() == Type.EMPTY && this.gameBoard[8][9].getType() == Type.EMPTY) {
					this.gameBoard[5][9] = new Empty(5,9, Colour.EMPTY);
					this.gameBoard[9][9] = new Empty(9,9, Colour.EMPTY);
					this.gameBoard[8][9] = new King(8,9, Colour.BLACK);
					this.gameBoard[7][9] = new Rook(7,9, Colour.BLACK);
					castling[0] = '-';
					castling[1] = '-';
					turn = "w";
					halfMoveCount++;
					moveCount++;
				}else {
					MoveValidationErrors.illegalCastlingMove(BoardUtils.getMoveLine(moveFile));
				}
			}
		}
		castleStatus = turn + ":" + String.valueOf(castling)+":" + halfMoveCount + ":" + moveCount;
		
	}
	
	public String kingsideCastleStatus(File boardFile, File moveFile, String castleStatus) {
		Scanner boardScanner = null;
		String[] statusLine = new String[10];
		String turn = " ";
		int halfMoveCount = 0;
		int moveCount = 0;
		char[] castling = {'+','+','+','+'};
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
			statusLine = line.split(":");
		}

		if(statusLine[0].contentEquals("b")) {
			turn = "w";
			halfMoveCount++;
			moveCount++;
			if(statusLine[2].charAt(1) == '-') {
				MoveValidationErrors.illegalCastlingMove(BoardUtils.getMoveLine(moveFile));
			}
			castling[0] = '-';
			castling[1] = '-';
			if(this.gameBoard[5][0].pieceColour == Colour.BLACK && this.gameBoard[9][0].pieceColour == Colour.BLACK && this.gameBoard[5][0].getType() == Type.KING && this.gameBoard[9][0].getType() == Type.ROOK) {
				if(this.gameBoard[6][0].getType() == Type.EMPTY && this.gameBoard[7][0].getType() == Type.EMPTY && this.gameBoard[8][0].getType() == Type.EMPTY) {
					this.gameBoard[5][0] = new Empty(5,0, Colour.EMPTY);
					this.gameBoard[9][0] = new Empty(9,0, Colour.EMPTY);
					this.gameBoard[8][0] = new King(8,0, Colour.BLACK);
					this.gameBoard[7][0] = new Rook(7,0, Colour.BLACK);
				}else {
					MoveValidationErrors.illegalCastlingMove(BoardUtils.getMoveLine(moveFile));
				}
			}
		}
		if(statusLine[0].contentEquals("w")) {
			turn = "b";
			halfMoveCount++;
			castling[2] = '-';
			castling[3] = '-';
			if(statusLine[2].charAt(3) == '-') {
				MoveValidationErrors.illegalCastlingMove(BoardUtils.getMoveLine(moveFile));
			}
			if(this.gameBoard[5][9].pieceColour == Colour.BLACK && this.gameBoard[9][9].pieceColour == Colour.BLACK && this.gameBoard[5][9].getType() == Type.KING && this.gameBoard[9][9].getType() == Type.ROOK) {
				if(this.gameBoard[6][9].getType() == Type.EMPTY && this.gameBoard[7][9].getType() == Type.EMPTY && this.gameBoard[8][9].getType() == Type.EMPTY) {
					this.gameBoard[5][9] = new Empty(5,9, Colour.EMPTY);
					this.gameBoard[9][9] = new Empty(9,9, Colour.EMPTY);
					this.gameBoard[8][9] = new King(8,9, Colour.BLACK);
					this.gameBoard[7][9] = new Rook(7,9, Colour.BLACK);
					castling[0] = '-';
					castling[1] = '-';
					turn = "w";
					halfMoveCount++;
					moveCount++;
				}else {
					MoveValidationErrors.illegalCastlingMove(BoardUtils.getMoveLine(moveFile));
				}
			}
		}
		return turn + ":" + String.valueOf(castling)+":" + halfMoveCount + ":" + moveCount;
		
	}
	
	public String queensideCastleStatus(File boardFile, File moveFile, String castleStatus) {
		Scanner boardScanner = null;
		String[] statusLine = new String[10];
		String turn = " ";
		int halfMoveCount = 0;
		int moveCount = 0;
		char[] castling = {'+','+','+','+'};
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
			statusLine = line.split(":");
		}

		if(statusLine[0].contentEquals("b")) {
			turn = "w";
			halfMoveCount++;
			moveCount++;
			if(statusLine[2].startsWith("-")) {
				MoveValidationErrors.illegalCastlingMove(BoardUtils.getMoveLine(moveFile));
			}
			castling[0] = '-';
			castling[1] = '-';
			if(this.gameBoard[5][0].pieceColour == Colour.BLACK && this.gameBoard[0][0].pieceColour == Colour.BLACK && this.gameBoard[5][0].getType() == Type.KING && this.gameBoard[0][0].getType() == Type.ROOK) {
				
				if(this.gameBoard[4][0].getType() == Type.EMPTY && this.gameBoard[3][0].getType() == Type.EMPTY && this.gameBoard[2][0].getType() == Type.EMPTY && this.gameBoard[1][0].getType() == Type.EMPTY) {
					this.gameBoard[5][0] = new Empty(5,0, Colour.EMPTY);
					this.gameBoard[0][0] = new Empty(0,0, Colour.EMPTY);
					this.gameBoard[1][0] = new King(1,0, Colour.BLACK);
					this.gameBoard[2][0] = new Rook(2,0, Colour.BLACK);
					castling[0] = '-';
					castling[1] = '-';
					
					
				}else {
					MoveValidationErrors.illegalCastlingMove(BoardUtils.getMoveLine(moveFile));
				}
			}
		}
		if(statusLine[0].contentEquals("w")) {
			turn = "b";
			halfMoveCount++;
			castling[2] = '-';
			castling[3] = '-';
			if(statusLine[2].charAt(2) == '-') {
				MoveValidationErrors.illegalCastlingMove(BoardUtils.getMoveLine(moveFile));
			}
			if(this.gameBoard[5][9].pieceColour == Colour.WHITE&& this.gameBoard[0][9].pieceColour == Colour.WHITE && this.gameBoard[5][9].getType() == Type.KING && this.gameBoard[0][9].getType() == Type.ROOK) {
			
				if(this.gameBoard[4][9].getType() == Type.EMPTY && this.gameBoard[3][9].getType() == Type.EMPTY && this.gameBoard[2][9].getType() == Type.EMPTY && this.gameBoard[1][9].getType() == Type.EMPTY) {
					this.gameBoard[5][9] = new Empty(5,9, Colour.EMPTY);
					this.gameBoard[0][9] = new Empty(0,9, Colour.EMPTY);
					this.gameBoard[1][9] = new King(1,9, Colour.WHITE);
					this.gameBoard[2][9] = new Rook(2,9, Colour.WHITE);
					
					
					
				}else {
					MoveValidationErrors.illegalCastlingMove(BoardUtils.getMoveLine(moveFile));
				}
			}
		}
		return turn + ":" + String.valueOf(castling)+":" + halfMoveCount + ":" + moveCount;
		
	}
	
	
}
