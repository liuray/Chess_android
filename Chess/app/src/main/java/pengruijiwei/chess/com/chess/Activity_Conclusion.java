package pengruijiwei.chess.com.chess;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chess.R;

import pengruijiwei.chess.com.common.Chess_Record;
import pengruijiwei.chess.com.common.Chess_Map;
import pengruijiwei.chess.com.common.Game_Records;

/**
 *  @author Pengrui liu
 * @author  Jiwei chen
 */
public class Activity_Conclusion extends Activity {

    private EditText editInput;

    private Chess_Record record;
    public void back(View v) {
        this.finish();
    }

    private TextView textview;

    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_conclusion);
        record = (Chess_Record) getIntent().getSerializableExtra("data");
        textview = (TextView) this.findViewById(R.id.tv_turn);
        textview.setText(record.getResult());
        Chess_Map.GameInitial(record);
        i = 0;
        Chess_Map.setMap(i);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    public void pre(View v) {
        if (i >= 1) {
            Chess_Map.setMap(--i);
        } else {
            Toast.makeText(this, "Moving Backward", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void next(View v) {
        if (i < (Chess_Map.getDataCount() - 1)) {
            Chess_Map.setMap(++i);
        } else {
            Toast.makeText(this, "Moving Forward!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void save(View v) {
        editInput = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle("Input Title:")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(editInput)
                .setPositiveButton("confirm",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                save();
                            }
                        }).setNegativeButton("cancel", null).show();
    }

    private void save() {
        String title = editInput.getText().toString().trim();
        if (title.equals("")) {
            Toast.makeText(this, "Title shouldn't be empty!",
                    Toast.LENGTH_SHORT).show();
            return;
        } else if (title.equals("unNamed")) {
            Toast.makeText(this, "The title isn't right!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "saved successfully!", Toast.LENGTH_SHORT).show();
        Game_Records.getInstance().Record(record, title);
        this.finish();
    }


}
