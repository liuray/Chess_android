package pengruijiwei.chess.com.common;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Pengrui liu
 * @author  Jiwei chen
 */
public class Game_Records implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private ArrayList<Chess_Record> records1;
    private ArrayList<Chess_Record> records2;

    private static final String fileName = Environment
            .getExternalStorageDirectory() + "/myData.file";

    private static Game_Records TObject;

    public ArrayList<Chess_Record> getRecordsV1() {

        return records1;

    }

    public ArrayList<Chess_Record> getRecordsV2() {

        return records2;

    }

    private Game_Records() {
        records1 = new ArrayList<Chess_Record>();
        records2 = new ArrayList<Chess_Record>();
    }

    public void Record(ArrayList<int[][]> md, int stats, String t,
                       int[][] map, boolean[][] isFM, boolean Uable) {
        Chess_Record tempData = new Chess_Record(md, stats,
                System.currentTimeMillis(), t, map, isFM, Uable);
        records1.add(tempData);
    }

    public void save() {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            ObjectOutputStream output = new ObjectOutputStream(
                    new FileOutputStream(file));
            output.writeObject(TObject);
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Record(Chess_Record r, String t) {
        for (int i = 0; i < records1.size(); ++i) {
            if (records1.get(i).getReTime() == r.getReTime()) {
                records1.get(i).setTitle(t);
                break;
            }
        }
        r.setTitle(t);
        records2.add(r);
    }

    public static Game_Records getInstance() {
        if (TObject == null) {
            File file = new File(fileName);
            if (!file.exists()) {
                TObject = new Game_Records();
            } else {
                try {
                    ObjectInputStream input = new ObjectInputStream(
                            new FileInputStream(file));
                    TObject = (Game_Records) input.readObject();
                    input.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    file.delete();
                    TObject = new Game_Records();
                }
            }
        }
        return TObject;
    }

}
