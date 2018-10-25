package pengruijiwei.chess.com.chess;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.chess.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pengruijiwei.chess.com.common.Chess_Record;
import pengruijiwei.chess.com.common.Chess_Map;
import pengruijiwei.chess.com.common.Game_Records;

/**
 *  @author Pengrui liu
 * @author  Jiwei chen
 */
public class Activity_Record extends Activity {

    public static final int BY_DATE = 1;
    public static final int BY_TITLE = 2;


    private ListView listView;

    private TextView txtView;

    private List<Map<String, Object>> list;

    private boolean isB = false;

    private boolean isBD = true;

    private View dl;

    private int temp;

    @Override
    protected void onResume() {
        super.onResume();
        if(isBD) {
            Message msg = new Message();
            msg.what = BY_DATE;
            myHandler.sendMessage(msg);
        } else {
            Message msg = new Message();
            msg.what = BY_TITLE;
            myHandler.sendMessage(msg);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler myHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BY_DATE:
                    isBD = true;
                    list.clear();
                    ArrayList<Chess_Record> dateRecords = Game_Records.getInstance()
                            .getRecordsV1();
                    for (int i = 0; i < dateRecords.size(); ++i) {
                        addData(dateRecords.get(i), i + 1);
                    }
                    txtView.setText("Sort By Date : " + dateRecords.size()
                            + " records.");
                    break;
                case BY_TITLE:
                    isBD = false;
                    list.clear();
                    ArrayList<Chess_Record> titleRecords = Game_Records.getInstance()
                            .getRecordsV2();
                    Collections.sort(titleRecords);
                    for (int i = 0; i < titleRecords.size(); ++i) {
                        addData(titleRecords.get(i), i + 1);
                    }
                    txtView.setText("Sort By Title : " + titleRecords.size()
                            + " records.");
                    break;
                default:
                    break;
            }
            SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
                    list, R.layout.layout_list, new String[] { "num", "result",
                    "title" }, new int[] { R.id.tv_num, R.id.tv_result,
                    R.id.tv_title });
            listView.setAdapter(adapter);
            super.handleMessage(msg);
        }

    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_record);
        listView = (ListView) this.findViewById(R.id.lv_list);
        txtView = (TextView) this.findViewById(R.id.tv_head);
        isB = getIntent().getBooleanExtra("playBack", false);
        list = new ArrayList<Map<String, Object>>();
        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
                list, R.layout.layout_list, new String[] { "num", "result",
                "title" }, new int[] { R.id.tv_num, R.id.tv_result,
                R.id.tv_title });
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ClickListener());
        Message msg = new Message();
        msg.what = BY_DATE;
        myHandler.sendMessage(msg);
    }

    private void addData(Chess_Record record, int index) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("num", index + "");
        map.put("result", record.getResult());
        map.put("title", record.getTitle());
        list.add(map);
    }

    public void byDate(View v) {
        Message msg = new Message();
        msg.what = BY_DATE;
        myHandler.sendMessage(msg);
    }

    public void byTitle(View v) {
        Message msg = new Message();
        msg.what = BY_TITLE;
        myHandler.sendMessage(msg);
    }

    private class ClickListener implements AdapterView.OnItemClickListener {

        public void onItemClick(AdapterView<?> arg0, View arg1, int index,
                                long arg3) {
            temp = index;
            if (isB) {
                ArrayList<Chess_Record> tempList = isBD ? Game_Records
                        .getInstance().getRecordsV1() : Game_Records.getInstance()
                        .getRecordsV2();
                if (tempList.get(index).getStat() == Chess_Map.BLATURN
                        || tempList.get(temp).getStat() == Chess_Map.WHITURN) {
                    gotoPlayBackActivity();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "the game can not be playback!", Toast.LENGTH_SHORT)
                            .show();
                }
            } else {
                gotoConclusionActity();
            }
        }
    }

    private void gotoConclusionActity() {
        Intent intent = new Intent(this,Activity_Conclusion.class);
        ArrayList<Chess_Record> tempList = isBD ? Game_Records
                .getInstance().getRecordsV1() : Game_Records.getInstance()
                .getRecordsV2();
        intent.putExtra("data", tempList.get(temp));
        startActivity(intent);
    }

    private void gotoPlayBackActivity() {
        LayoutInflater inflater = getLayoutInflater();
        dl = inflater.inflate(R.layout.layout_dialog,
                (ViewGroup) findViewById(R.id.dialog_name));
        new AlertDialog.Builder(this)
                .setTitle("Input the Name:")
                .setView(dl)
                .setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                gotoGameActivity();
                            }
                        }).setNegativeButton("Cancel", null).show();

    }

    private void gotoGameActivity() {

        EditText et_black = (EditText) dl.findViewById(R.id.et_black);
        String black = et_black.getText().toString().trim();

        EditText et_white = (EditText) dl.findViewById(R.id.et_white);
        String white = et_white.getText().toString().trim();
        if (black.equals("")) {
            Toast.makeText(this, "black player's name can't be empty!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (white.equals("")) {
            Toast.makeText(this, "white player's name can't be empty!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (black.equals(white)) {
            Toast.makeText(this, "Opponents names can't be same",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, Activity_Game.class);
        intent.putExtra("black", black);
        intent.putExtra("white", white);
        intent.putExtra("playBack", true);
        ArrayList<Chess_Record> tempList = isBD ? Game_Records.getInstance()
                .getRecordsV1() : Game_Records.getInstance().getRecordsV2();
        intent.putExtra("data", tempList.get(temp));
        startActivity(intent);
    }

}
