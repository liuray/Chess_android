package pengruijiwei.chess.com.chess;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chess.R;

import pengruijiwei.chess.com.common.Chess_Record;
import pengruijiwei.chess.com.common.Gameview_Listener;
import pengruijiwei.chess.com.view.View_of_Game;

import static pengruijiwei.chess.com.common.Chess_Map.*;

/**
 *  @author Pengrui liu
 * @author  Jiwei chen
 */
public class Activity_Game extends Activity {



    private View_of_Game gameview;

    private TextView textview;

    private String black, white;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_game);
        gameview = (View_of_Game) this.findViewById(R.id.sf_game);
        textview = (TextView) this.findViewById(R.id.tv_turn);
        black = getIntent().getStringExtra("black");
        white = getIntent().getStringExtra("white");
        gameview.setGameViewListener(listner);


        GameInitial();
        if (getIntent().getBooleanExtra("playBack", false)) {
            Chess_Record record = (Chess_Record) getIntent()
                    .getSerializableExtra("data");

            GameInitial(record);
        }

        Message msg = new Message();
        msg.what = getStats();
        mHandler.sendMessage(msg);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BC:
                    textview.setText(black + "'s turn! King is in check");
                    textview.setTextColor(Color.RED);
                    break;
                case WC:
                    textview.setText(white + "'s turn! King is in check");
                    textview.setTextColor(Color.RED);
                    break;
                case BLATURN:
                    textview.setText(black + "'s turn!");
                    textview.setTextColor(Color.BLACK);
                    break;
                case WHITURN:
                    textview.setText(white + "'s turn!");
                    textview.setTextColor(Color.WHITE);
                    break;
                case DRAW:
                    textview.setText("Draw! You can check the result in RECORD");
                    textview.setTextColor(Color.YELLOW);
                    break;
                case WW:
                    textview.setText(white + " wins! Game has been saved!");
                    textview.setTextColor(Color.YELLOW);
                    break;
                case BW:
                    textview.setText(black + " wins! Game has been saved!");
                    textview.setTextColor(Color.YELLOW);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }

    };

    @Override
    public void onBackPressed() {
        save();
        this.finish();
    }


    public void Click4Draw (View v) {
        String name;

        if (getStats() == DRAW
                || getStats() == BW
                || getStats() == WW) {
            Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT).show();
            return;
        }
        if (getStats() == BLATURN) {
            name = black;
        } else {
            name = white;
        }
        new AlertDialog.Builder(this)
                .setMessage(name + " is asking for a draw. Do you agree to draw?")
                .setTitle("Ask for draw")
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                                Draw();
                                draw();
                                listner.UserChange(getStats());
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }
    public void AIClick(View v) {

        if (getStats() == DRAW
                || getStats() == BW
                || getStats() == WW) {
            Toast.makeText(this, "Game Over!", Toast.LENGTH_SHORT).show();
            return;
        }
        gameview.AIHelp();
    }

    private Gameview_Listener listner = new Gameview_Listener() {

        @Override
        public void UserChange(int user) {
            Message msg = new Message();
            msg.what = user;
            mHandler.sendMessage(msg);
        }

    };

    private void Draw() {
        String player1, player2;
        if (getStats() == BLATURN) {
            player1 = black;
            player2 = white;
        } else {
            player1 = white;
            player2 = black;
        }
        new AlertDialog.Builder(this)
                .setMessage( "Game is draw." )
                .setTitle("Result").setNegativeButton("OK", null).show();
    }

    public void Click4Resign(View v) {
        String name;

        if (getStats() == DRAW
                || getStats() == BW
                || getStats() == WW) {
            Toast.makeText(this, "Game Over! You can restart a game!",
                    Toast.LENGTH_SHORT).show();
            resign();
            listner.UserChange(getStats());
            return;
        }
        if (getStats() == BLATURN) {
            name = black;
        } else {
            name = white;
        }
        new AlertDialog.Builder(this)
                .setMessage(name + " ask for new game! Do you agree to have another game?")
                .setTitle("Ask for resign")
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                                Resign();
                                resign();
                                listner.UserChange(getStats());
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }

    private void Resign() {
        String player1, player2;
        if (getStats() == BLATURN) {
            player1 = black;
            player2 = white;
        } else {
            player1 = white;
            player2 = black;
        }
        new AlertDialog.Builder(this)
                .setMessage(player1 + " resigns. " + player2 + " wins!!")
                .setTitle("Result").setNegativeButton("OK", null).show();
    }

    public void Click4Undo(View v) {
        if (undo()) {
            Toast.makeText(this, "Successfully undo!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Undo Failed", Toast.LENGTH_SHORT).show();
        }
    }

}
