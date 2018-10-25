package pengruijiwei.chess.com.common;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 *  @author Pengrui liu
 * @author  Jiwei chen
 */
public class Chess_Record implements Serializable, Comparable<Chess_Record> {

    private static final long serialVersionUID = 1L;

    private static SimpleDateFormat sdf = new SimpleDateFormat(
            "yyyy:MM:dd HH:mm:ss", Locale.US);
    private boolean isUndoable;

    private ArrayList<int[][]> mappings;

    private int stat;

    private long reTime;

    private String title;

    private int[][] map;

    private boolean[][] isFMoved;

    public boolean[][] getIsFMoved() {
        return isFMoved;
    }

    public int[][] getMap() {
        return map;
    }

    public boolean isUndoable() {
        return isUndoable;
    }

    public ArrayList<int[][]> getMappings() {
        return mappings;
    }

    public int getStat() {
        return stat;
    }

    public long getReTime() {
        return reTime;
    }

    public Chess_Record(ArrayList<int[][]> mappings, int stat, long reTime,
                        String title, int[][] map, boolean[][] isFMoved, boolean isUndoable) {
        super();
        this.mappings = mappings;
        this.stat = stat;
        this.reTime = reTime;
        this.title = title;
        this.map = new int[8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                this.map[i][j] = map[i][j];
            }
        }
        this.isFMoved = isFMoved;
        this.isFMoved = new boolean[8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                this.isFMoved[i][j] = isFMoved[i][j];
            }
        }
        this.isUndoable = isUndoable;
    }


    public String getResult() {
        String result = "";
        switch (stat) {
            case Chess_Map.BLATURN:
                result = "black turn!";
                break;
            case Chess_Map.BW:
                result = "black win!";
                break;
            case Chess_Map.DRAW:
                result = "draw!";
                break;
            case Chess_Map.WHITURN:
                result = "white turn!";
                break;
            case Chess_Map.WW:
                result = "white win!";
                break;
        }
        result = result + "[" + sdf.format(new Date(reTime)) + "]";
        return result;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        if (title == null) {
            return "unNamed!";
        }
        return title;
    }

    @Override
    public int compareTo(Chess_Record another) {
        if (title != null && another.title != null) {
            return title.compareTo(another.title);
        }
        return (int) (reTime - another.reTime);
    }

}
