package pengruijiwei.chess.com.logic;

import pengruijiwei.chess.com.common.Chess_Types;
import pengruijiwei.chess.com.common.Chess_Map;
/**
 *  @author Pengrui liu
 * @author  Jiwei chen
 */

public class ChessFactory implements Chess_Types {

    public static Piece getChess(int value, int x, int y) {
        Piece c = null;
        switch (value) {
            case EMPTY_SPOT:
                c = new None(true, x, y);
                break;
            case WKING:
                c = new King(true, x, y);
                break;
            case WQUEEN:
                c = new Queen(true, x, y);
                break;
            case WROOK:
                c = new Rook(true, x, y);
                break;
            case WBISHOP:
                c = new Bishop(true, x, y);
                break;
            case WKNIGHT:
                c = new Knight(true, x, y);
                break;
            case WPAWN:
                c = new Pawn(true, x, y, Chess_Map.getIFM(x, y));
                break;
            case BKING:
                c = new King(false, x, y);
                break;
            case BQUEEN:
                c = new Queen(false, x, y);
                break;
            case BROOK:
                c = new Rook(false, x, y);
                break;
            case BBISHOP:
                c = new Bishop(false, x, y);
                break;
            case BKNIGHT:
                c = new Knight(false, x, y);
                break;
            case BPAWN:
                c = new Pawn(false, x, y, Chess_Map.getIFM(x, y));
                break;
        }
        return c;
    }

}
