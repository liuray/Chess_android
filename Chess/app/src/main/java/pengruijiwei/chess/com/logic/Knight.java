package pengruijiwei.chess.com.logic;

import pengruijiwei.chess.com.common.Chess_Map;
/**
 *  @author Pengrui liu
 * @author  Jiwei chen
 */

public class Knight extends Piece {

    public Knight(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        if (isWhite) {
            this.value = WKNIGHT;
        } else {
            this.value = BKNIGHT;
        }
    }

    @Override
    protected void rules() {
        moves.clear();


        if (!isW) {


            // x+1 y+2
            if (Chess_Map.getValue(x + 1, y + 2) >= 0)
                addBaseMove(x + 1, y + 2);
            // x+1 y-2
            if (Chess_Map.getValue(x + 1, y - 2) >= 0)
                addBaseMove(x + 1, y - 2);
            // x-1 y+2
            if (Chess_Map.getValue(x - 1, y + 2) >= 0)
                addBaseMove(x - 1, y + 2);
            // x-1 y-2
            if (Chess_Map.getValue(x - 1, y - 2) >= 0)
                addBaseMove(x - 1, y - 2);
            // x+2 y+1
            if (Chess_Map.getValue(x + 2, y + 1) >= 0)
                addBaseMove(x + 2, y + 1);
            // x+2 y-1
            if (Chess_Map.getValue(x + 2, y - 1) >= 0)
                addBaseMove(x + 2, y - 1);
            // x-2 y+1
            if (Chess_Map.getValue(x - 2, y + 1) >= 0)
                addBaseMove(x - 2, y + 1);
            // x-2 y-1
            if (Chess_Map.getValue(x - 2, y - 1) >= 0)
                addBaseMove(x - 2, y - 1);
        } else {
            // x+1 y+2
            if (Chess_Map.getValue(x + 1, y + 2) <= 0)
                addBaseMove(x + 1, y + 2);
            // x+1 y-2
            if (Chess_Map.getValue(x + 1, y - 2) <= 0)
                addBaseMove(x + 1, y - 2);
            // x-1 y+2
            if (Chess_Map.getValue(x - 1, y + 2) <= 0)
                addBaseMove(x - 1, y + 2);
            // x-1 y-2
            if (Chess_Map.getValue(x - 1, y - 2) <= 0)
                addBaseMove(x - 1, y - 2);
            // x+2 y+1
            if (Chess_Map.getValue(x + 2, y + 1) <= 0)
                addBaseMove(x + 2, y + 1);
            // x+2 y-1
            if (Chess_Map.getValue(x + 2, y - 1) <= 0)
                addBaseMove(x + 2, y - 1);
            // x-2 y+1
            if (Chess_Map.getValue(x - 2, y + 1) <= 0)
                addBaseMove(x - 2, y + 1);
            // x-2 y-1
            if (Chess_Map.getValue(x - 2, y - 1) <= 0)
                addBaseMove(x - 2, y - 1);
        }
    }

}

