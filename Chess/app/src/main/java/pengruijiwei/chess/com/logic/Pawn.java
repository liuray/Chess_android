package pengruijiwei.chess.com.logic;

import pengruijiwei.chess.com.common.Chess_Movement;
import pengruijiwei.chess.com.common.Chess_Map;

/**
 *  @author Pengrui liu
 * @author  Jiwei chen
 */
public class Pawn extends Piece {

    private boolean isFirstMove = true;

    public Pawn(boolean isWhite, int x, int y, boolean isFirstMove) {
        super(isWhite, x, y);
        this.isFirstMove = isFirstMove;
        if (isWhite) {
            this.value = WPAWN;
        } else {
            this.value = BPAWN;
        }
    }

    @Override
    protected void rules() {
        moves.clear();

        if (!isW) {

            if (Chess_Map.LMC == WPAWN
                    && Chess_Map.LTY - Chess_Map.LFY == 2
                    && Chess_Map.LTY == this.y
                    && Math.abs(this.x - Chess_Map.LTX) == 1) {
                Chess_Movement move = new Chess_Movement(x, y, Chess_Map.LTX,
                        Chess_Map.LTY - 1);
                move.setE(Chess_Map.LTX, Chess_Map.LTY);
                moves.add(move);
            }

            if (x >= 1 && y >= 1 && Chess_Map.getValue(x - 1, y - 1) > 0) {
                Chess_Movement move = new Chess_Movement(x, y, x - 1, y - 1);
                move.setE(x - 1, y - 1);
                moves.add(move);
            }

            if (x < 7 && y >= 1 && Chess_Map.getValue(x + 1, y - 1) > 0) {
                Chess_Movement move = new Chess_Movement(x, y, x + 1, y - 1);
                move.setE(x + 1, y - 1);
                moves.add(move);
            }

            if (Chess_Map.getValue(x, y - 1) == EMPTY_SPOT) {
                moves.add(new Chess_Movement(x, y, x, y - 1));
                if (isFirstMove && Chess_Map.getValue(x, y - 2) == EMPTY_SPOT) {
                    moves.add(new Chess_Movement(x, y, x, y - 2));
                }
            }
        } else {

            if (Chess_Map.LMC == BPAWN
                    && Chess_Map.LTY - Chess_Map.LFY == -2
                    && Chess_Map.LTY == this.y
                    && Math.abs(this.x - Chess_Map.LTX) == 1) {
                Chess_Movement move = new Chess_Movement(x, y, Chess_Map.LTX,
                        Chess_Map.LTY + 1);
                move.setE(Chess_Map.LTX, Chess_Map.LTY);
                moves.add(move);
            }

            if (x >= 1 && y < 7 && Chess_Map.getValue(x - 1, y + 1) < 0) {
                Chess_Movement move = new Chess_Movement(x, y, x - 1, y + 1);
                move.setE(x - 1, y + 1);
                moves.add(move);
            }

            if (x < 7 && y < 7 && Chess_Map.getValue(x + 1, y + 1) < 0) {
                Chess_Movement move = new Chess_Movement(x, y, x + 1, y + 1);
                move.setE(x + 1, y + 1);
                moves.add(move);
            }

            if (Chess_Map.getValue(x, y + 1) == EMPTY_SPOT) {
                moves.add(new Chess_Movement(x, y, x, y + 1));
                if (isFirstMove && Chess_Map.getValue(x, y + 2) == EMPTY_SPOT) {
                    moves.add(new Chess_Movement(x, y, x, y + 2));
                }
            }
        }

    }

}
