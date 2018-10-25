package pengruijiwei.chess.com.logic;

import pengruijiwei.chess.com.common.Chess_Map;
/**
 *  @author Pengrui liu
 * @author  Jiwei chen
 */

public class Bishop extends Piece {

    public Bishop(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        if (isWhite) {
            this.value = WBISHOP;
        } else {
            this.value = BBISHOP;
        }
    }

    @Override
    protected void rules() {
        moves.clear();


        if (!isW) {
            for (int i = 1; i < 8; ++i) {
                int tempX = x + i;
                int tempY = y + i;
                if (Chess_Map.getValue(tempX, tempY) >= 0) {
                    addBaseMove(tempX, tempY);
                }
                if (Chess_Map.getValue(tempX, tempY) != EMPTY_SPOT) {
                    break;
                }
            }
            for (int i = 1; i < 8; ++i) {
                int tempX = x + i;
                int tempY = y - i;
                if (Chess_Map.getValue(tempX, tempY) >= 0) {
                    addBaseMove(tempX, tempY);
                }
                if (Chess_Map.getValue(tempX, tempY) != EMPTY_SPOT) {
                    break;
                }
            }
            for (int i = 1; i < 8; ++i) {
                int tempX = x - i;
                int tempY = y + i;
                if (Chess_Map.getValue(tempX, tempY) >= 0) {
                    addBaseMove(tempX, tempY);
                }
                if (Chess_Map.getValue(tempX, tempY) != EMPTY_SPOT) {
                    break;
                }
            }
            for (int i = 1; i < 8; ++i) {
                int tempX = x - i;
                int tempY = y - i;
                if (Chess_Map.getValue(tempX, tempY) >= 0) {
                    addBaseMove(tempX, tempY);
                }
                if (Chess_Map.getValue(tempX, tempY) != EMPTY_SPOT) {
                    break;
                }
            }
        } else {
            for (int i = 1; i < 8; ++i) {
                int tempX = x + i;
                int tempY = y + i;
                if (Chess_Map.getValue(tempX, tempY) <= 0) {
                    addBaseMove(tempX, tempY);
                }
                if (Chess_Map.getValue(tempX, tempY) != EMPTY_SPOT) {
                    break;
                }
            }
            for (int i = 1; i < 8; ++i) {
                int tempX = x + i;
                int tempY = y - i;
                if (Chess_Map.getValue(tempX, tempY) <= 0) {
                    addBaseMove(tempX, tempY);
                }
                if (Chess_Map.getValue(tempX, tempY) != EMPTY_SPOT) {
                    break;
                }
            }
            for (int i = 1; i < 8; ++i) {
                int tempX = x - i;
                int tempY = y + i;
                if (Chess_Map.getValue(tempX, tempY) <= 0) {
                    addBaseMove(tempX, tempY);
                }
                if (Chess_Map.getValue(tempX, tempY) != EMPTY_SPOT) {
                    break;
                }
            }
            for (int i = 1; i < 8; ++i) {
                int tempX = x - i;
                int tempY = y - i;
                if (Chess_Map.getValue(tempX, tempY) <= 0) {
                    addBaseMove(tempX, tempY);
                }
                if (Chess_Map.getValue(tempX, tempY) != EMPTY_SPOT) {
                    break;
                }
            }
        }
    }

}
