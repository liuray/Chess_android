package pengruijiwei.chess.com.logic;

import pengruijiwei.chess.com.common.Chess_Movement;
import pengruijiwei.chess.com.common.Chess_Map;

/**
 *  @author Pengrui liu
 * @author  Jiwei chen
 */
public class King extends Piece {

    public King(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        if (isWhite) {
            this.value = WKING;
        } else {
            this.value = BKING;
        }
    }

    @Override
    protected void rules() {
        moves.clear();

        if (!isW) {
            blackMove();
        } else {
            whiteMove();
        }
    }

    private void whiteMove() {

        int tempX, tempY;

        // x-1,y-1
        tempX = x - 1;
        tempY = y - 1;
        baseMoveBlack(tempX, tempY);

        // x,y - 1
        tempX = x;
        tempY = y - 1;
        baseMoveBlack(tempX, tempY);

        // x+1,y - 1
        tempX = x + 1;
        tempY = y - 1;
        baseMoveBlack(tempX, tempY);

        // x+1,y
        tempX = x + 1;
        tempY = y;
        baseMoveBlack(tempX, tempY);
        // x+1,y + 1
        tempX = x + 1;
        tempY = y + 1;
        baseMoveBlack(tempX, tempY);
        // x,y + 1
        tempX = x;
        tempY = y + 1;
        baseMoveBlack(tempX, tempY);
        // x-1,y + 1
        tempX = x - 1;
        tempY = y + 1;
        baseMoveBlack(tempX, tempY);
        // x-1,y
        tempX = x - 1;
        tempY = y;
        baseMoveBlack(tempX, tempY);

        if (Chess_Map.getIFM(0, 0) && Chess_Map.getIFM(4, 0)
                && Chess_Map.getValue(1, 0) == EMPTY_SPOT
                && Chess_Map.getValue(2, 0) == EMPTY_SPOT
                && Chess_Map.getValue(3, 0) == EMPTY_SPOT
                && !Chess_Map.isCheck(2, 0, isW)
                && !Chess_Map.isCheck(3, 0, isW)
                && !Chess_Map.isCheck(4, 0, isW)) {
            moves.add(new Chess_Movement(99, y, x - 2, y));
        }


        if (Chess_Map.getIFM(7, 0) && Chess_Map.getIFM(4, 0)
                && Chess_Map.getValue(5, 0) == EMPTY_SPOT
                && Chess_Map.getValue(6, 0) == EMPTY_SPOT
                && !Chess_Map.isCheck(4, 0, isW)
                && !Chess_Map.isCheck(5, 0, isW)
                && !Chess_Map.isCheck(6, 0, isW)) {
            moves.add(new Chess_Movement(999, y, x + 2, y));
        }
    }

    private void baseMoveBlack(int tempX, int tempY) {
        if (Chess_Map.getValue(tempX, tempY) > 0) {
            return;
        }
        int copy[][] = Chess_Map.getCopy();
        Chess_Map.setValue(tempX, tempY, value);
        Chess_Map.setValue(x, y, 0);
        if (!Chess_Map.isCheck(tempX, tempY, isW)) {
            addBaseMove(tempX, tempY);
        }
        Chess_Map.backupMap(copy);
    }


    private void blackMove() {

        int tempX, tempY;

        // x-1,y-1
        tempX = x - 1;
        tempY = y - 1;
        baseMoveWhite(tempX, tempY);

        // x,y - 1
        tempX = x;
        tempY = y - 1;
        baseMoveWhite(tempX, tempY);

        // x+1,y - 1
        tempX = x + 1;
        tempY = y - 1;
        baseMoveWhite(tempX, tempY);

        // x+1,y
        tempX = x + 1;
        tempY = y;
        baseMoveWhite(tempX, tempY);
        // x+1,y + 1
        tempX = x + 1;
        tempY = y + 1;
        baseMoveWhite(tempX, tempY);
        // x,y + 1
        tempX = x;
        tempY = y + 1;
        baseMoveWhite(tempX, tempY);
        // x-1,y + 1
        tempX = x - 1;
        tempY = y + 1;
        baseMoveWhite(tempX, tempY);
        // x-1,y
        tempX = x - 1;
        tempY = y;
        baseMoveWhite(tempX, tempY);


        if (Chess_Map.getIFM(0, 7) && Chess_Map.getIFM(4, 7)
                && Chess_Map.getValue(1, 7) == EMPTY_SPOT
                && Chess_Map.getValue(2, 7) == EMPTY_SPOT
                && Chess_Map.getValue(3, 7) == EMPTY_SPOT
                && !Chess_Map.isCheck(2, 7, isW)
                && !Chess_Map.isCheck(3, 7, isW)
                && !Chess_Map.isCheck(4, 7, isW)) {
            moves.add(new Chess_Movement(-99, y, x - 2, y));
        }


        if (Chess_Map.getIFM(7, 7) && Chess_Map.getIFM(4, 7)
                && Chess_Map.getValue(5, 7) == EMPTY_SPOT
                && Chess_Map.getValue(6, 7) == EMPTY_SPOT
                && !Chess_Map.isCheck(4, 7, isW)
                && !Chess_Map.isCheck(5, 7, isW)
                && !Chess_Map.isCheck(6, 7, isW)) {
            moves.add(new Chess_Movement(-999, y, x + 2, y));
        }
    }

    private void baseMoveWhite(int tempX, int tempY) {
        if (Chess_Map.getValue(tempX, tempY) < 0) {
            return;
        }
        int copy[][] = Chess_Map.getCopy();
        Chess_Map.setValue(tempX, tempY, value);
        Chess_Map.setValue(x, y, 0);
        if (!Chess_Map.isCheck(tempX, tempY, isW)) {
            addBaseMove(tempX, tempY);
        }
        Chess_Map.backupMap(copy);
    }

}

