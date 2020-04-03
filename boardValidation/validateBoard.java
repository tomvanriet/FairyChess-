package boardValidation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class validateBoard {

	public static void validateBoard(File boardFile) {
		String[][] gameBoard = createBoard(boardFile);
		validatePieceAllocations(boardFile, gameBoard);
		checkBoard(gameBoard);
		checkBoardStatus(boardFile, gameBoard);
	}

	public static void validatePieceAllocations(File boardFile, String[][] gameBoard) {
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
		int kCount = 0;
		int rCount = 0;
		int qCount = 0;
		int nCount = 0;
		int bCount = 0;
		int pCount = 0;
		int dCount = 0;
		int fCount = 0;
		int eCount = 0;
		int aCount = 0;
		int wCount = 0;
		int KCount = 0;
		int RCount = 0;
		int QCount = 0;
		int NCount = 0;
		int BCount = 0;
		int PCount = 0;
		int DCount = 0;
		int FCount = 0;
		int ECount = 0;
		int ACount = 0;
		int WCount = 0;
		int pieceAllocation = k + r + q + n + b + p + d + f + e + a + w;
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
						k = Integer.parseInt(pieceCount);
						break;
					case 'r':
						r = Integer.parseInt(pieceCount);
						break;
					case 'q':
						q = Integer.parseInt(pieceCount);
						break;
					case 'n':
						n = Integer.parseInt(pieceCount);
						break;
					case 'b':
						b = Integer.parseInt(pieceCount);
						break;
					case 'p':
						p = Integer.parseInt(pieceCount);
						break;
					case 'd':
						d = Integer.parseInt(pieceCount);
						break;
					case 'f':
						f = Integer.parseInt(pieceCount);
						break;
					case 'e':
						e = Integer.parseInt(pieceCount);
						break;
					case 'a':
						a = Integer.parseInt(pieceCount);
						break;
					case 'w':
						w = Integer.parseInt(pieceCount);
						break;
					default:
						break;
					}
				} catch (NumberFormatException exception) {

				}
			}
		}
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				int oCount = k + r + q + n + b + f + e + a + w;
				int currentOCount = kCount + rCount + qCount + nCount + bCount + fCount + eCount + aCount + wCount;
				int CurrentOCount = KCount + RCount + QCount + NCount + BCount + FCount + ECount + ACount + WCount;
				int MaxO = oCount + 10 - (PCount + DCount);
				int maxO = oCount + 10 - (pCount + dCount);
				int PawnCount = PCount + DCount;
				int pawnCount = pCount + dCount;
				switch (gameBoard[i][j]) {
				case "k":
					kCount++;
					if (kCount > 0 && k == 0) {
						BoardValidationErrors.illegalPiece((char) (97 + j), 10 - i);
					} else if ((pawnCount == (p + d) && kCount > k) || (kCount > k && (currentOCount >= maxO))) {
						BoardValidationErrors.officerAllocationExceeded((char) (97 + j), 10 - i);
					}
					break;
				case "K":
					KCount++;
					if (KCount > 0 && k == 0) {
						BoardValidationErrors.illegalPiece((char) (97 + j), 10 - i);
					} else if ((PawnCount == (p + d) && KCount > k) || (KCount > k && (CurrentOCount >= MaxO))) {
						BoardValidationErrors.officerAllocationExceeded((char) (97 + j), 10 - i);
					}
					break;
				case "r":
					rCount++;
					if (rCount > 0 && r == 0) {
						BoardValidationErrors.illegalPiece((char) (97 + j), 10 - i);
					} else if ((pawnCount == (p + d) && rCount > r) || (rCount > r && (currentOCount >= maxO))) {
						BoardValidationErrors.officerAllocationExceeded((char) (97 + j), 10 - i);
					}
					break;
				case "R":
					RCount++;
					if (RCount > 0 && r == 0) {
						BoardValidationErrors.illegalPiece((char) (97 + j), 10 - i);
					} else if ((PawnCount == (p + d) && RCount > r) || (RCount > r && (CurrentOCount >= MaxO))) {
						BoardValidationErrors.officerAllocationExceeded((char) (97 + j), 10 - i);
					}
					break;
				case "q":
					qCount++;
					if (qCount > 0 && q == 0) {
						BoardValidationErrors.illegalPiece((char) (97 + j), 10 - i);
					} else if ((pawnCount == (p + d) && qCount > q) || (qCount > q && (currentOCount >= maxO))) {
						BoardValidationErrors.officerAllocationExceeded((char) (97 + j), 10 - i);
					}
					break;
				case "Q":
					QCount++;
					if (QCount > 0 && q == 0) {
						BoardValidationErrors.illegalPiece((char) (97 + j), 10 - i);
					} else if ((PawnCount == (p + d) && QCount > q) || (QCount > q && (CurrentOCount >= MaxO))) {
						BoardValidationErrors.officerAllocationExceeded((char) (97 + j), 10 - i);
					}

					break;
				case "n":
					nCount++;
					if (nCount > 0 && n == 0) {
						BoardValidationErrors.illegalPiece((char) (97 + j), 10 - i);
					} else if ((pawnCount == (p + d) && nCount > n) || (nCount > n && (currentOCount >= maxO))) {
						BoardValidationErrors.officerAllocationExceeded((char) (97 + j), 10 - i);
					}
					break;
				case "N":
					NCount++;
					if (NCount > 0 && n == 0) {
						BoardValidationErrors.illegalPiece((char) (97 + j), 10 - i);
					} else if ((PawnCount == (p + d) && NCount > n) || (NCount > n && (CurrentOCount >= MaxO))) {
						BoardValidationErrors.officerAllocationExceeded((char) (97 + j), 10 - i);
					}
					break;
				case "b":
					bCount++;
					if (bCount > 0 && b == 0) {
						BoardValidationErrors.illegalPiece((char) (97 + j), 10 - i);
					} else if ((pawnCount == (p + d) && bCount > b) || (bCount > b && (currentOCount >= maxO))) {
						BoardValidationErrors.officerAllocationExceeded((char) (97 + j), 10 - i);
					}
					break;
				case "B":
					BCount++;
					if (BCount > 0 && b == 0) {
						BoardValidationErrors.illegalPiece((char) (97 + j), 10 - i);
					} else if ((PawnCount == (p + d) && BCount > b) || (BCount > b && (CurrentOCount >= MaxO))) {
						BoardValidationErrors.officerAllocationExceeded((char) (97 + j), 10 - i);
					}
					break;
				case "p":
					pCount++;
					if (pCount > 0 && p == 0) {
						BoardValidationErrors.illegalPiece((char) (97 + j), 10 - i);
					} else if (pCount > p) {
						BoardValidationErrors.pawnAllocationExceeded((char) (97 + j), 10 - i);
					}
					break;
				case "P":
					PCount++;
					if (PCount > 0 && p == 0) {
						BoardValidationErrors.illegalPiece((char) (97 + j), 10 - i);
					} else if (PCount > p) {
						BoardValidationErrors.pawnAllocationExceeded((char) (97 + j), 10 - i);
					}
					break;
				case "d":
					dCount++;
					if (dCount > 0 && d == 0) {
						BoardValidationErrors.illegalPiece((char) (97 + j), 10 - i);
					} else if (dCount > d) {
						BoardValidationErrors.pawnAllocationExceeded((char) (97 + j), 10 - i);
					}
					break;
				case "D":
					DCount++;
					if (DCount > 0 && d == 0) {
						BoardValidationErrors.illegalPiece((char) (97 + j), 10 - i);
					} else if (DCount > d) {
						BoardValidationErrors.pawnAllocationExceeded((char) (97 + j), 10 - i);
					}
					break;
				case "f":
					fCount++;
					if (fCount > 0 && f == 0) {
						BoardValidationErrors.illegalPiece((char) (97 + j), 10 - i);
					} else if ((pawnCount == (p + d) && fCount > f) || (fCount > f && (currentOCount >= maxO))) {
						BoardValidationErrors.officerAllocationExceeded((char) (97 + j), 10 - i);
					}
					break;
				case "F":
					FCount++;
					if (FCount > 0 && f == 0) {
						BoardValidationErrors.illegalPiece((char) (97 + j), 10 - i);
					} else if ((PawnCount == (p + d) && FCount > f) || (FCount > f && (CurrentOCount >= MaxO))) {
						BoardValidationErrors.officerAllocationExceeded((char) (97 + j), 10 - i);
					}
					break;
				case "e":
					eCount++;
					if (eCount > 0 && e == 0) {
						BoardValidationErrors.illegalPiece((char) (97 + j), 10 - i);
					} else if ((pawnCount == (p + d) && eCount > e) || (eCount > e && (currentOCount >= maxO))) {
						BoardValidationErrors.officerAllocationExceeded((char) (97 + j), 10 - i);
					}
					break;
				case "E":
					ECount++;
					if (ECount > 0 && e == 0) {
						BoardValidationErrors.illegalPiece((char) (97 + j), 10 - i);
					} else if ((PawnCount == (p + d) && ECount > e) || (ECount > e && (CurrentOCount >= MaxO))) {
						BoardValidationErrors.officerAllocationExceeded((char) (97 + j), 10 - i);
					}
					break;
				case "a":
					aCount++;
					if (aCount > 0 && a == 0) {
						BoardValidationErrors.illegalPiece((char) (97 + j), 10 - i);
					} else if ((pawnCount == (p + d) && aCount > a) || (aCount > a && (currentOCount >= maxO))) {
						BoardValidationErrors.officerAllocationExceeded((char) (97 + j), 10 - i);
					}
					break;
				case "A":
					ACount++;
					if (ACount > 0 && a == 0) {
						BoardValidationErrors.illegalPiece((char) (97 + j), 10 - i);
					} else if ((PawnCount == (p + d) && ACount > a) || (ACount > a && (CurrentOCount >= MaxO))) {
						BoardValidationErrors.officerAllocationExceeded((char) (97 + j), 10 - i);
					}
					break;
				case "w":
					wCount++;
					if (wCount > 0 && w == 0) {
						BoardValidationErrors.illegalPiece((char) (97 + j), 10 - i);
					} else if ((pawnCount == (p + d) && wCount > w) || (wCount > w && (currentOCount >= maxO))) {
						BoardValidationErrors.officerAllocationExceeded((char) (97 + j), 10 - i);
					}
					break;
				case "W":
					WCount++;
					if (WCount > 0 && w == 0) {
						BoardValidationErrors.illegalPiece((char) (97 + j), 10 - i);
					} else if ((PawnCount == (p + d) && WCount > w) || (WCount > w && (CurrentOCount >= MaxO))) {
						BoardValidationErrors.officerAllocationExceeded((char) (97 + j), 10 - i);
					}
					break;
				default:
					break;
				}
			}
		}

	}

	public static String[][] createBoard(File boardFile) {
		String[][] board = new String[10][10];
		int lineN = 0;
		// Initialize the Scanner
		Scanner boardScanner = null;
		try {
			boardScanner = new Scanner(boardFile);
		} catch (FileNotFoundException exception) {
			throw new IllegalArgumentException("Board file does not exist");
		}

		while (boardScanner.hasNextLine()) {
			String line = boardScanner.nextLine();
			String[] boardTest = line.split(" ");
			if (boardTest.length > 10) {
				BoardValidationErrors.illegalBoardDimension();
			}
			if (line.contains(":") || line.contains("%") || line.contains("-")) {

			} else {
				for (int i = 0; i < 10; i++) {
					board[lineN][i] = boardTest[i];

				}
				lineN++;
			}
		}

		return board;
	}

	public static void checkBoard(String[][] board) {
		String[] validPieces = { "k", "K", "r", "R", "n", "N", "b", "B", "p", "P", "d", "D", "f", "F", "e", "E", "a",
				"A", "w", "W", ".", "q", "Q" };
		boolean isValid = false;
		for (int i = 0; i < 10; i++) {
			isValid = false;
			for (int j = 0; j < 10; j++) {
				isValid = false;
				for (int k = 0; k < validPieces.length; k++) {
					if (board[i][j].equals(validPieces[k])) {
						isValid = true;
					}
				}
				if (!isValid) {
					BoardValidationErrors.illegalPiece((char) (97 + j), 10 - i);
				}
			}
		}
	}

	public static void checkBoardStatus(File boardFile, String[][] board) {
		int lineNum = 0;
		int dividerCount = 0;
		// Initialize the Scanner
		Scanner boardScanner = null;
		try {
			boardScanner = new Scanner(boardFile);
		} catch (FileNotFoundException exception) {
			throw new IllegalArgumentException("Board file does not exist");
		}
		Scanner boardScanner2 = null;
		try {
			boardScanner2 = new Scanner(boardFile);
		} catch (FileNotFoundException exception) {
			throw new IllegalArgumentException("Board file does not exist");
		}
		while (boardScanner2.hasNextLine()) {
			String dividerCheck = boardScanner2.nextLine();
			if (dividerCheck.equals("-----")) {
				dividerCount++;
			}
		}
		if (dividerCount != 2) {
			BoardValidationErrors.illegalBoardDimension();
		}
		while (boardScanner.hasNextLine()) {
			String line = boardScanner.nextLine();
			do {
				line = boardScanner.nextLine();
				lineNum++;
			} while (line != "-----" && boardScanner.hasNextLine());
			String[] statusLine = line.split(":");
			char[] castling = statusLine[1].toCharArray();
			if (!statusLine[0].equals("b") && !statusLine[0].equals("w")) {
				BoardValidationErrors.illegalNextPlayerMarker(lineNum + 1);
			}
			if (Integer.parseInt(statusLine[2]) < 0 || Integer.parseInt(statusLine[2]) > 50) {
				BoardValidationErrors.illegalHalfmoveClock(lineNum + 1);
			}
			if (Integer.parseInt(statusLine[3]) < 0) {
				BoardValidationErrors.illegalMoveCounter(lineNum + 1);
			}
			if (!board[0][0].equals("r") && castling[0] != '-') {
				BoardValidationErrors.illegalCastlingOpportunity(lineNum + 1, 0);
			}
			if (!board[0][5].equals("k") && castling[0] != '-') {
				BoardValidationErrors.illegalCastlingOpportunity(lineNum + 1, 0);
			}
			if (!board[0][9].equals("r") && castling[1] != '-') {
				BoardValidationErrors.illegalCastlingOpportunity(lineNum + 1, 1);
			}
			if (!board[0][5].equals("k") && castling[1] != '-') {
				BoardValidationErrors.illegalCastlingOpportunity(lineNum + 1, 1);
			}

			if (!board[9][0].equals("R") && castling[2] != '-') {
				BoardValidationErrors.illegalCastlingOpportunity(lineNum + 1, 2);
			}
			if (!board[9][5].equals("K") && castling[2] != '-') {
				BoardValidationErrors.illegalCastlingOpportunity(lineNum + 1, 2);
			}

			if (!board[9][9].equals("R") && castling[3] != '-') {
				BoardValidationErrors.illegalCastlingOpportunity(lineNum + 1, 3);
			}
			if (!board[9][5].equals("K") && castling[3] != '-') {
				BoardValidationErrors.illegalCastlingOpportunity(lineNum + 1, 3);
			}

			if (line.startsWith("-") && !line.equals("-----")) {
				BoardValidationErrors.illegalBoardDimension();
			}

		}

	}

	public static String[] creategameBoard(String[][] gameBoard) {
		String[] board = new String[100];
		int count = 0;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				board[count] = gameBoard[i][j];
				count++;
			}
		}

		return board;

	}

}
