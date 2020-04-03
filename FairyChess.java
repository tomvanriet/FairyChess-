import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import Pieces.Colour;
import Pieces.King;
import Pieces.Piece;
import boardValidation.*;

/**
 * The driver class for the Fairy Chess project.
 */
public class FairyChess {
    public static void main(String[] args) {

        // Get the board file's name, and initialize a File object to represent it
		if (args.length < 1) {
			throw new IllegalArgumentException("Provide a file name as first argument");
		}
		
        String boardFilename = args[0];
        String moveFilename = args[1];
        File boardFile = new File(boardFilename);
        File moveFile = new File(moveFilename);
    	int originX = BoardUtils.moveConvetOriginX(moveFile) - 1;
		int originY = 10 - BoardUtils.moveConvetOriginY(moveFile);
		int destX = BoardUtils.moveConvetDestX(moveFile) - 1;
		int destY = 10 - BoardUtils.moveConvetDestY(moveFile);
        validateBoard.validateBoard(boardFile);
        Board gameBoard = new Board(validateBoard.createBoard(boardFile));
        gameBoard.initPieces(validateBoard.createBoard(boardFile));
        gameBoard.Move(moveFile, boardFile);
        
        
     
    }
    
    
}
