package pengruijiwei.chess.com.common;

import android.graphics.Point;

/**
 *  @author Pengrui liu
 * @author  Jiwei chen
 */
public class Chess_Movement {

    private int FX, FY, TX, TY;

    private int EX, EY;

    private boolean isKilled;

    public Chess_Movement(int FX, int FY, int TX, int TY) {
        this.FX = FX;
        this.FY = FY;
        this.TX = TX;
        this.TY = TY;
        isKilled = false;
    }

    public void setE(int x, int y) {
        this.EX = x;
        this.EY = y;
        isKilled = true;
    }

    public boolean isKilled() {
        return isKilled;
    }

    public Point getEPoint() {
        return new Point(EX, EY);
    }

    public Point FromPoint() {
        return new Point(FX, FY);
    }

    public Point ToPoint() {
        return new Point(TX, TY);
    }

}

