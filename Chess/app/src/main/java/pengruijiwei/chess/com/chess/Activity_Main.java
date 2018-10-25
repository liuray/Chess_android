package pengruijiwei.chess.com.chess;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.chess.R;

import pengruijiwei.chess.com.chess.Activity_Game;
import pengruijiwei.chess.com.chess.Activity_Record;
import pengruijiwei.chess.com.common.Game_Records;

/*
 *  @author Pengrui liu
 * @author  Jiwei chen
 */

public class Activity_Main extends Activity {

    private View dl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {
        Game_Records.getInstance().save();
        this.finish();
    }

    public void exit(View v) {
        Game_Records.getInstance().save();
        this.finish();
    }

    public void jumptoRecord(View v) {
        Intent intent = new Intent(this, Activity_Record.class);
        startActivity(intent);
    }

    public void jumptoPlayBack(View v) {
        Intent intent = new Intent(this, Activity_Record.class);
        intent.putExtra("playBack", true);
        startActivity(intent);
    }


    public void jumptoGame(View v) {
        LayoutInflater inflater = getLayoutInflater();
        dl = inflater.inflate(R.layout.layout_dialog,
                (ViewGroup) findViewById(R.id.dialog_name));
        new AlertDialog.Builder(this)
                .setTitle("Type the Name")
                .setView(dl)
                .setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                jumptoGame();
                            }
                        }).setNegativeButton("Cancel", null).show();

    }
    private void jumptoGame() {

        EditText et_black = (EditText) dl.findViewById(R.id.et_black);
        String black = et_black.getText().toString().trim();

        EditText et_white = (EditText) dl.findViewById(R.id.et_white);
        String white = et_white.getText().toString().trim();


        if (black.equals("")) {
            Toast.makeText(this, "black player's name is empty!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (white.equals("")) {
            Toast.makeText(this, "white player's name is empty!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (black.equals(white)) {
            Toast.makeText(this, "two player's name is same!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(Activity_Main.this, Activity_Game.class);
        intent.putExtra("black", black);
        intent.putExtra("white", white);
        startActivity(intent);
    }
}
