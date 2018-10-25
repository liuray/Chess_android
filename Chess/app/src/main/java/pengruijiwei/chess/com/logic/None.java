package pengruijiwei.chess.com.logic;
/**
 *  @author Pengrui liu
 * @author  Jiwei chen
 */

public class None extends Piece {

    public None(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        this.value = EMPTY_SPOT;
    }

}