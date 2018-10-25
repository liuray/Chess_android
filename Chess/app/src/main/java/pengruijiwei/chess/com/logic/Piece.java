package pengruijiwei.chess.com.logic;

import java.util.ArrayList;

import pengruijiwei.chess.com.common.Chess_Movement;
import pengruijiwei.chess.com.common.Chess_Types;
import pengruijiwei.chess.com.common.Chess_Map;

/**
 *  @author Pengrui liu
 * @author  Jiwei chen
 */
public abstract class Piece implements Chess_Types {

    protected int value;

    protected ArrayList<Chess_Movement> moves;

    protected final boolean isW;

    protected int x, y;

    public Piece(boolean isW, int x, int y) {
        this.isW = isW;
        moves = new ArrayList<Chess_Movement>();
        this.x = x;
        this.y = y;
    }


    public int getValue() {
        return value;
    }

    protected void rules() {
    }


    protected void addBaseMove(int toX, int toY) {
        if (toX < 0 || toX >= 8 || toY < 0 || toY >= 8) {
            return;
        }
        Chess_Movement move = new Chess_Movement(this.x, this.y, toX, toY);
        move.setE(toX, toY);
        moves.add(move);
    }



    public ArrayList<Chess_Movement> getAvailableMoves() {
        rules();
        if(value == 0) {
            return moves;
        }

        if ((isW && Chess_Map.getStats() == Chess_Map.WC)
                || (!isW && Chess_Map.getStats() == Chess_Map.BC)) {
            for (int i = 0; i < moves.size(); ++i) {
                Chess_Movement tempMove = moves.get(i);

                if (Math.abs(tempMove.FromPoint().x) > 20) {
                    continue;
                }
                int copy[][] = Chess_Map.getCopy();

                Chess_Map.setValue(tempMove.ToPoint().x,
                        tempMove.ToPoint().y, value);
                Chess_Map.setValue(x, y, 0);

                if (Chess_Map.isCheckMate(isW) != Chess_Map.DRAW) {
                    moves.remove(tempMove);
                    i--;
                }
                Chess_Map.backupMap(copy);
            }
        }
        return moves;
    }

}
