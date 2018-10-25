package pengruijiwei.chess.com.common;

import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;

import pengruijiwei.chess.com.logic.ChessFactory;

/**
 *
 *  @author Pengrui liu
 * @author  Jiwei chen
 */
public class Chess_Map implements Chess_Types {

    public static int[][] map;

    public static int[][] Dmap;

    public static boolean[][] isFM;

    public static int LMC = 0;

    private static int LMCB = 0;

    public static int LFX = -1;

    private static int LFXB = -1;

    public static int LFY = -1;

    private static int LFYB = -1;

    public static int LTX = -1;

    private static int LTXB = -1;

    public static int LTY = -1;

    private static int LTYB = -1;

    public static int cX = -1;

    public static int cY = -1;

    public static ArrayList<Chess_Movement> aMoves;

    private static int stats;

    private static boolean isUndoable;

    private static ArrayList<int[][]> mappings;

    private static boolean[][] LIFM = new boolean[8][8];

    public static int getStats() {
        return stats;
    }

    public static void setStats(int stats) {
        Chess_Map.stats = stats;
    }

    public static final int BLATURN = 1;
    public static final int WHITURN = 2;
    public static final int DRAW = 3;
    public static final int WW = 4;
    public static final int BW = 5;
    public static final int BC = 6;
    public static final int WC = 7;

    public static int isCheckMate(boolean isWhite) {
        if (isWhite) {
            for (int i = 0; i < 8; ++i) {
                for (int j = 0; j < 8; ++j) {
                    if (getValue(i, j) < 0 && getValue(i, j) != BKING) {
                        ArrayList<Chess_Movement> avalaibleMoves = getLegalMove(
                                i, j);
                        for (int k = 0; k < avalaibleMoves.size(); ++k) {
                            if (avalaibleMoves.get(k).isKilled()) {
                                Point tempPoint = avalaibleMoves.get(k)
                                        .getEPoint();
                                if (getValue(tempPoint.x, tempPoint.y) == WKING) {
                                    return BW;
                                }
                            }
                        }
                    }
                }
            }
        } else {

            for (int i = 0; i < 8; ++i) {
                for (int j = 0; j < 8; ++j) {
                    if (getValue(i, j) > 0 && getValue(i, j) != WKING) {
                        ArrayList<Chess_Movement> avalaibleMoves = getLegalMove(
                                i, j);
                        for (int k = 0; k < avalaibleMoves.size(); ++k) {
                            if (avalaibleMoves.get(k).isKilled()) {
                                Point tempPoint = avalaibleMoves.get(k)
                                        .getEPoint();
                                if (getValue(tempPoint.x, tempPoint.y) == BKING) {
                                    return WW;
                                }
                            }
                        }
                    }
                }
            }
        }

        return DRAW;
    }


    public static boolean isCheck(int x, int y, boolean isWhite) {
        if (isWhite) {

            for (int i = 0; i < 8; ++i) {
                for (int j = 0; j < 8; ++j) {

                    if (getValue(i, j) < 0) {
                        if (getValue(i, j) == BKING) {
                            if (Math.abs(x - i) <= 1 && Math.abs(y - j) <= 1) {
                                return true;
                            }
                        } else {
                            ArrayList<Chess_Movement> avalaibleMoves = getLegalMove(
                                    i, j);
                            for (int k = 0; k < avalaibleMoves.size(); ++k) {
                                if (avalaibleMoves.get(k).isKilled()
                                        && avalaibleMoves.get(k).getEPoint()
                                        .equals(x, y)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
            return false;
        } else {

            for (int i = 0; i < 8; ++i) {
                for (int j = 0; j < 8; ++j) {

                    if (getValue(i, j) > 0) {
                        if (getValue(i, j) == WKING) {
                            if (Math.abs(x - i) <= 1 && Math.abs(y - j) <= 1) {
                                return true;
                            }
                        } else {
                            ArrayList<Chess_Movement> avalaibleMoves = getLegalMove(
                                    i, j);
                            for (int k = 0; k < avalaibleMoves.size(); ++k) {
                                if (avalaibleMoves.get(k).isKilled()
                                        && avalaibleMoves.get(k).getEPoint()
                                        .equals(x, y)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
            return false;
        }
    }

    public static void GameInitial(Chess_Record record) {
        aMoves = new ArrayList<Chess_Movement>();
        mappings = record.getMappings();
        stats = record.getStat();
        isUndoable = record.isUndoable();
        cX = -1;
        map = record.getMap();
        isFM = record.getIsFMoved();
        Dmap = map;
    }


    public static void GameInitial() {
        aMoves = new ArrayList<Chess_Movement>();
        mappings = new ArrayList<int[][]>();
        stats = WHITURN;
        isUndoable = false;
        cX = -1;
        map = new int[][] {
                {WROOK, WKNIGHT, WBISHOP, WQUEEN, WKING, WBISHOP,
                        WKNIGHT, WROOK},
                {WPAWN, WPAWN, WPAWN, WPAWN, WPAWN, WPAWN, WPAWN,
                        WPAWN},
                {EMPTY_SPOT, EMPTY_SPOT, EMPTY_SPOT, EMPTY_SPOT, EMPTY_SPOT, EMPTY_SPOT, EMPTY_SPOT, EMPTY_SPOT},
                {EMPTY_SPOT, EMPTY_SPOT, EMPTY_SPOT, EMPTY_SPOT, EMPTY_SPOT, EMPTY_SPOT, EMPTY_SPOT, EMPTY_SPOT},
                {EMPTY_SPOT, EMPTY_SPOT, EMPTY_SPOT, EMPTY_SPOT, EMPTY_SPOT, EMPTY_SPOT, EMPTY_SPOT, EMPTY_SPOT},
                {EMPTY_SPOT, EMPTY_SPOT, EMPTY_SPOT, EMPTY_SPOT, EMPTY_SPOT, EMPTY_SPOT, EMPTY_SPOT, EMPTY_SPOT},
                {BPAWN, BPAWN, BPAWN, BPAWN, BPAWN, BPAWN, BPAWN,
                        BPAWN},
                {BROOK, BKNIGHT, BBISHOP, BQUEEN, BKING, BBISHOP,
                        BKNIGHT, BROOK} };

        isFM = new boolean[][] {
                { true, true, true, true, true, true, true, true },
                { true, true, true, true, true, true, true, true },
                { false, false, false, false, false, false, false, false },
                { false, false, false, false, false, false, false, false },
                { false, false, false, false, false, false, false, false },
                { false, false, false, false, false, false, false, false },
                { true, true, true, true, true, true, true, true },
                { true, true, true, true, true, true, true, true } };
        storeMap();
        Dmap = map;
    }


    private static int[][] tempMap(int[][] map) {
        int tempMap[][] = new int[8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                tempMap[i][j] = map[i][j];
            }
        }
        return tempMap;
    }

    private static void storeMap() {
        int tempMap[][] = new int[8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                tempMap[i][j] = map[i][j];
            }
        }
        mappings.add(tempMap);
    }


    private static int[][] getMap() {
        mappings.remove(mappings.size() - 1);
        int tempMap[][] = mappings.get(mappings.size() - 1);
        int resultMap[][] = new int[8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                resultMap[i][j] = tempMap[i][j];
            }
        }
        return resultMap;
    }


    public static boolean makeAmove(Chess_Movement move, int turn) {
        isUndoable = true;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                LIFM[i][j] = isFM[i][j];
            }
        }
        try {

            // Check for Castle
            if (move.FromPoint().x == -99) {
                isFM[7][0] = false;
                isFM[7][4] = false;
                LFX = 4;
                LFY = 7;
                LTX = 1;
                LTY = 7;
                LMC = BKING;
                map[7][0] = EMPTY_SPOT;
                map[7][4] = EMPTY_SPOT;
                map[7][2] = BKING;
                map[7][3] = BROOK;
                return true;
            }

            if (move.FromPoint().x == -999) {
                isFM[7][7] = false;
                isFM[7][4] = false;
                LFX = 4;
                LFY = 7;
                LTX = 1;
                LTY = 7;
                LMC = BKING;
                map[7][7] = EMPTY_SPOT;
                map[7][4] = EMPTY_SPOT;
                map[7][6] = BKING;
                map[7][5] = BROOK;
                return true;
            }


            if (move.FromPoint().x == 99) {
                isFM[7][0] = false;
                isFM[7][4] = false;
                LFX = 4;
                LFY = 7;
                LTX = 1;
                LTY = 7;
                LMC = WKING;
                map[0][0] = EMPTY_SPOT;
                map[0][4] = EMPTY_SPOT;
                map[0][2] = WKING;
                map[0][3] = WROOK;
                return true;
            }

            if (move.FromPoint().x == 999) {
                isFM[0][7] = false;
                isFM[0][4] = false;
                LFX = 4;
                LFY = 0;
                LTX = 1;
                LTY = 0;
                LMC = WKING;
                map[0][7] = EMPTY_SPOT;
                map[0][4] = EMPTY_SPOT;
                map[0][6] = WKING;
                map[0][5] = WROOK;
                return true;
            }

            // Move piece
            if (!isIllegal(move, turn)){
                Log.d("MyApp","Invalid");
                return false;
            }
            else
                Log.d("MyApp","valid");
            LMC = getValue(move.FromPoint().x,
                    move.FromPoint().y);
            LFX = move.FromPoint().x;
            LFY = move.FromPoint().y;
            isFM[LFY][LFX] = false;
            LTX = move.ToPoint().x;
            LTY = move.ToPoint().y;
            if (move.isKilled()) {
                map[move.getEPoint().y][move.getEPoint().x] = Chess_Types.EMPTY_SPOT;
            }
            int tempValue = map[LTY][LTX];
            map[LTY][LTX] = LMC;
            map[LFY][LFX] = tempValue;

        } finally {
            // Undo data
            LMCB = LMC;
            LFXB = LFX;
            LFYB = LFY;
            LTXB = LTX;
            LTYB = LTY;
            // Record data
            storeMap();
            Dmap = map;
        }
        return true;
    }

    public static boolean isIllegal(Chess_Movement move, int turn){

        // Copy current map and execute the move on the mapCopy
        int[][] mapCopy = tempMap(map);

        if (move.isKilled()) {
            mapCopy[move.getEPoint().y][move.getEPoint().x] = Chess_Types.EMPTY_SPOT;
        }
        int tempValue = mapCopy[move.ToPoint().y][move.ToPoint().x];
        mapCopy[move.ToPoint().y][move.ToPoint().x] = getValue(move.FromPoint().x,move.FromPoint().y);;
        mapCopy[move.FromPoint().y][move.FromPoint().x] = tempValue;

        int[][] tempCopy = tempMap(map);
        map = tempMap(mapCopy);

        // If black's turn
        if (turn==1){
            // Find black king square
            int kingX=0, kingY=0;
            for (int i=0;i<8;i++){
                for (int j=0; j<8; j++){
                    if (mapCopy[i][j]==-1){
                        kingX=j; kingY=i;
                        break;
                    }
                }
            }

            // Check other pieces for moves that land on black king square
            for (int i=0;i<8;i++){
                for (int j=0; j<8; j++){
                    if (mapCopy[i][j]>=0){
                        for (Chess_Movement z : ChessFactory.getChess(mapCopy[i][j], j, i).getAvailableMoves()){
                            int tempX = z.ToPoint().x; int tempY = z.ToPoint().y;
                            if (tempX == kingX && tempY == kingY){
                                map = tempMap(tempCopy);
                                return false;
                            }
                        }
                    }
                }
            }

        }
        else if (turn==2){
            // Find white king square
            int kingX=0, kingY=0;
            for (int i=0;i<8;i++){
                for (int j=0; j<8; j++){
                    if (mapCopy[i][j]==1){
                        kingX=j; kingY=i;
                        break;
                    }
                }
            }

            // Check other pieces for moves that land on white king square
            for (int i=0;i<8;i++){
                for (int j=0; j<8; j++){
                    if (mapCopy[i][j]<=0){
                        for (Chess_Movement z : ChessFactory.getChess(mapCopy[i][j], j, i).getAvailableMoves()){
                            int tempX = z.ToPoint().x; int tempY = z.ToPoint().y;
                            if (tempX == kingX && tempY == kingY){
                                map = tempMap(tempCopy);
                                return false;
                            }
                        }
                    }
                }
            }
        }
        map = tempMap(tempCopy);
        return true;

    }


    public static ArrayList<Chess_Movement> getLegalMove(int x, int y) {
        return ChessFactory.getChess(getValue(x, y), x, y).getAvailableMoves();
    }

    public static int[][] getCopy() {
        int copy[][] = new int[8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                copy[i][j] = map[i][j];
            }
        }
        return copy;
    }

    public static void backupMap(int[][] copy) {
        map = copy;
    }


    public static int getValue(int x, int y) {
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            return 0;
        }
        return map[y][x];
    }

    public static int getValue(int x, int y, int[][] copy) {
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            return 0;
        }
        return map[y][x];
    }


    public static void setValue(int x, int y, int value) {
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            return;
        }
        map[y][x] = value;
    }

    public static boolean getIFM(int x, int y) {
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            return false;
        }
        return isFM[y][x];
    }

    private static int tempPawnX, tempPawnY;


    public static void change(int change) {
        if (tempPawnY == 0) {
            map[tempPawnY][tempPawnX] = change;
        } else {
            map[tempPawnY][tempPawnX] = -change;
        }
    }


    public static boolean checkPawnCanChange(int x, int y) {
        tempPawnX = x;
        tempPawnY = y;
        if (y == 0 && getValue(x, y) == BPAWN) {
            return true;
        } else if (y == 7 && getValue(x, y) == WPAWN) {
            return true;
        }
        return false;
    }


    public static void updateStatus(int back) {

        boolean key = true;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (getValue(i, j) == BKING) {
                    key = false;
                }
            }
        }
        if (key) {
            stats = WW;
            return;
        }


        key = true;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (getValue(i, j) == WKING) {
                    key = false;
                }
            }
        }
        if (key) {
            stats = BW;
            return;
        }

        if (stats == BLATURN || stats == BC) {
            if (back == BW) {
                if (getAllMoves(true).size() == 0) {
                    stats = BW;
                } else {
                    stats = WC;
                }
            } else {
                stats = WHITURN;
            }
        } else {
            if (back == WW) {
                if(getAllMoves(false).size() == 0) {
                    stats = WW;
                } else {
                    stats = BC;
                }
            } else {
                stats = BLATURN;
            }
        }
    }


    public static Chess_Movement checkCanMove(int x, int y) {
        Chess_Movement result = null;
        for (int i = 0; i < Chess_Map.aMoves.size(); ++i) {
            if (Chess_Map.aMoves.get(i).ToPoint().x == x
                    && Chess_Map.aMoves.get(i).ToPoint().y == y) {
                result = Chess_Map.aMoves.get(i);
            }
        }
        return result;
    }


    public static boolean undo() {
        if (!isUndoable) {
            return false;
        }
        isUndoable = false;

        map = getMap();

        if (stats == BLATURN) {
            stats = WHITURN;
        } else {
            stats = BLATURN;
        }

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                isFM[i][j] = LIFM[i][j];
            }
        }

        LMC = LMCB;
        LFX = LFXB;
        LFY = LFYB;
        LTX = LTXB;
        LTY = LTYB;
        cX = -1;
        return true;
    }

    private static ArrayList<Chess_Movement> getAllMoves(boolean isWhite) {
        ArrayList<Chess_Movement> tempMoves = new ArrayList<Chess_Movement>();
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if ((getValue(i, j) < 0 && !isWhite)
                        || (getValue(i, j) > 0 && isWhite)) {
                    tempMoves.addAll(getLegalMove(i, j));
                }
            }
        }
        return tempMoves;
    }

    public static Chess_Movement AIHelp(int turn) {
        ArrayList<Chess_Movement> tempMoves = new ArrayList<Chess_Movement>();
        boolean isWhite = false;
        if (stats == BLATURN || stats == BC) {
            for (int i = 0; i < 8; ++i) {
                for (int j = 0; j < 8; ++j) {
                    if (getValue(i, j) < 0) {
                        tempMoves.addAll(getLegalMove(i, j));
                    }
                }
            }
            isWhite = false;
        } else if (stats == WHITURN || stats == WC) {
            for (int i = 0; i < 8; ++i) {
                for (int j = 0; j < 8; ++j) {
                    if (getValue(i, j) > 0) {
                        tempMoves.addAll(getLegalMove(i, j));
                    }
                }
            }
            isWhite = true;
        }
        Chess_Movement move = tempMoves
                .get((int) (tempMoves.size() * Math.random()));
        makeAmove(move, turn);
        int tempCheck = Chess_Map.isCheckMate(!isWhite);
        updateStatus(tempCheck);
        cX = -1;
        return move;
    }

    public static void draw() {
        stats = DRAW;
        cX = -1;
    }

    public static void resign() {
        save();
        GameInitial();
    }

    public static void save() {
        Game_Records.getInstance().Record(mappings, stats, null, map, isFM,
                isUndoable);
    }

    public static void setMap(int index) {
        map = mappings.get(index);
    }


    public static int getDataCount() {
        return mappings.size();
    }

}

