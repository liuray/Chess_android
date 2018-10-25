package pengruijiwei.chess.com.logic;

import pengruijiwei.chess.com.common.Chess_Map;
/**
 *  @author Pengrui liu
 * @author  Jiwei chen
 */

public class Rook extends Piece {

    public Rook(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        if (isWhite) {
            this.value = WROOK;
        } else {
            this.value = BROOK;
        }
    }

    @Override
    protected void rules() {
        moves.clear();

        if (!isW) {
            for (int i = 1; i < 8; ++i) {
                int tempX = x + i;
                if (Chess_Map.getValue(tempX, y) >= 0) {
                    addBaseMove(tempX, y);
                }
                if (Chess_Map.getValue(tempX, y) != EMPTY_SPOT) {
                    break;
                }
            }
            for (int i = 1; i < 8; ++i) {
                int tempX = x - i;
                if (Chess_Map.getValue(tempX, y) >= 0) {
                    addBaseMove(tempX, y);
                }
                if (Chess_Map.getValue(tempX, y) != EMPTY_SPOT) {
                    break;
                }
            }
            for (int i = 1; i < 8; ++i) {
                int tempY = y + i;
                if (Chess_Map.getValue(x, tempY) >= 0) {
                    addBaseMove(x, tempY);
                }
                if (Chess_Map.getValue(x, tempY) != EMPTY_SPOT) {
                    break;
                }
            }
            for (int i = 1; i < 8; ++i) {
                int tempY = y - i;
                if (Chess_Map.getValue(x, tempY) >= 0) {
                    addBaseMove(x, tempY);
                }
                if (Chess_Map.getValue(x, tempY) != EMPTY_SPOT) {
                    break;
                }
            }
        } else {

            for (int i = 1; i < 8; ++i) {
                int tempX = x + i;
                if (Chess_Map.getValue(tempX, y) <= 0) {
                    addBaseMove(tempX, y);
                }
                if (Chess_Map.getValue(tempX, y) != EMPTY_SPOT) {
                    break;
                }
            }
            for (int i = 1; i < 8; ++i) {
                int tempX = x - i;
                if (Chess_Map.getValue(tempX, y) <= 0) {
                    addBaseMove(tempX, y);
                }
                if (Chess_Map.getValue(tempX, y) != EMPTY_SPOT) {
                    break;
                }
            }
            for (int i = 1; i < 8; ++i) {
                int tempY = y + i;
                if (Chess_Map.getValue(x, tempY) <= 0) {
                    addBaseMove(x, tempY);
                }
                if (Chess_Map.getValue(x, tempY) != EMPTY_SPOT) {
                    break;
                }
            }
            for (int i = 1; i < 8; ++i) {
                int tempY = y - i;
                if (Chess_Map.getValue(x, tempY) <= 0) {
                    addBaseMove(x, tempY);
                }
                if (Chess_Map.getValue(x, tempY) != EMPTY_SPOT) {
                    break;
                }
            }

        }
    }

}
