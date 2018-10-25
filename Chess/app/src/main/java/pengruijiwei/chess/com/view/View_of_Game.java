package pengruijiwei.chess.com.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chess.R;

import pengruijiwei.chess.com.common.Chess_Movement;
import pengruijiwei.chess.com.common.Chess_Types;
import pengruijiwei.chess.com.common.Chess_Map;
import pengruijiwei.chess.com.common.Gameview_Listener;

/**
 *  @author Pengrui liu
 * @author  Jiwei chen
 */
public class View_of_Game extends SurfaceView implements SurfaceHolder.Callback,
        Chess_Types {

    private SurfaceHolder sfh;
    private Paint paint;

    public static final int WIDTH = 625, HEIGHT = 625;

    private Thread updateThread;

    private float scaleWidth, scaleHeight;

    private boolean flag;

    private Bitmap bmBoard;

    private Bitmap wChesses[];
    private Bitmap bChesses[];

    private final float diffX = 50, diffY = 50;

    private final float unitSize = 65.5f;

    private Gameview_Listener listener;


    private boolean clickaviliable;


    private Dialog dialog;

    public View_of_Game(Context context) {
        super(context);
        init();
    }

    public View_of_Game(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public View_of_Game(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        clickaviliable = true;

        sfh = this.getHolder();
        sfh.addCallback(this);

        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setAntiAlias(true);


        Resources res = getContext().getResources();

        wChesses = new Bitmap[6];
        bChesses = new Bitmap[6];

        bmBoard = BitmapFactory.decodeResource(res, R.drawable.board);

        wChesses[0] = BitmapFactory.decodeResource(res, R.drawable.w_king);
        wChesses[1] = BitmapFactory.decodeResource(res, R.drawable.w_queen);
        wChesses[2] = BitmapFactory.decodeResource(res, R.drawable.w_rook);
        wChesses[3] = BitmapFactory.decodeResource(res, R.drawable.w_bishop);
        wChesses[4] = BitmapFactory.decodeResource(res, R.drawable.w_knight);
        wChesses[5] = BitmapFactory.decodeResource(res, R.drawable.w_pawn);

        bChesses[0] = BitmapFactory.decodeResource(res, R.drawable.b_king);
        bChesses[1] = BitmapFactory.decodeResource(res, R.drawable.b_queen);
        bChesses[2] = BitmapFactory.decodeResource(res, R.drawable.b_rook);
        bChesses[3] = BitmapFactory.decodeResource(res, R.drawable.b_bishop);
        bChesses[4] = BitmapFactory.decodeResource(res, R.drawable.b_knight);
        bChesses[5] = BitmapFactory.decodeResource(res, R.drawable.b_pawn);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        Canvas canvas = sfh.lockCanvas();
        scaleWidth = (float) canvas.getWidth() / (float) WIDTH;
        scaleHeight = (float) canvas.getHeight() / (float) HEIGHT;
        sfh.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Canvas canvas = sfh.lockCanvas();
        scaleWidth = (float) canvas.getWidth() / (float) WIDTH;
        scaleHeight = (float) canvas.getHeight() / (float) HEIGHT;
        sfh.unlockCanvasAndPost(canvas);

        flag = true;
        updateThread = new Thread(new UpdateSurface());
        updateThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }


    private void disableTouch() {
        if (clickaviliable) {
            new Thread(new DisableTouch()).start();
        }
    }

    private class DisableTouch implements Runnable {

        private long time;

        public DisableTouch() {
            this.time = 500;
        }

        public void run() {
            clickaviliable = false;
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clickaviliable = true;
        }

    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!clickaviliable) {
            return true;
        }
        disableTouch();

        if (Chess_Map.getStats() == Chess_Map.DRAW
                || Chess_Map.getStats() == Chess_Map.BW
                || Chess_Map.getStats() == Chess_Map.WW) {
            return true;
        }
        int x = (int) ((int) event.getX() / scaleWidth - diffX);
        int y = (int) ((int) event.getY() / scaleHeight - diffY);
        int tempX = (int) (x / unitSize);
        int tempY = 7 - (int) (y / unitSize);

        if (tempX < 0 || tempX >= 8 || tempY < 0 || tempY >= 8) {
            Chess_Map.cX = -1;
            return true;
        }


        if (Chess_Map.cX != -1) {
            Chess_Movement move = Chess_Map.checkCanMove(tempX, tempY);
            if (move != null) {

                if (!Chess_Map.makeAmove(move, Chess_Map.getStats())){
                    Toast.makeText(getContext(), "Illegal move! King will be in check.",
                            Toast.LENGTH_SHORT).show();
                    return true;
                }
                boolean isWhite = false;
                if (Chess_Map.getStats() == Chess_Map.BLATURN
                        || Chess_Map.getStats() == Chess_Map.BC) {
                    isWhite = false;
                } else if (Chess_Map.getStats() == Chess_Map.WHITURN
                        || Chess_Map.getStats() == Chess_Map.WC) {
                    isWhite = true;
                }
                int tempCheck = Chess_Map.isCheckMate(!isWhite);
                if (tempCheck == Chess_Map.BW) {
                    Toast.makeText(getContext(), "white king in check",
                            Toast.LENGTH_SHORT).show();
                } else if (tempCheck == Chess_Map.WW) {
                    Toast.makeText(getContext(), "black king in check",
                            Toast.LENGTH_SHORT).show();
                }

                Chess_Map.updateStatus(tempCheck);
                Chess_Map.cX = -1;
                listener.UserChange(Chess_Map.getStats());

                if (Chess_Map.getStats() == Chess_Map.DRAW
                        || Chess_Map.getStats() == Chess_Map.BW
                        || Chess_Map.getStats() == Chess_Map.WW) {
                    return true;
                }

                if (Chess_Map.checkPawnCanChange(tempX, tempY)) {
                    changePawn();
                }
                return true;
            }
        }


        if (((Chess_Map.getStats() == Chess_Map.BLATURN || Chess_Map.getStats() == Chess_Map.BC) && Chess_Map
                .getValue(tempX, tempY) > 0)
                || ((Chess_Map.getStats() == Chess_Map.WHITURN || Chess_Map
                .getStats() == Chess_Map.WC) && Chess_Map
                .getValue(tempX, tempY) < 0)) {
            return true;
        }

        Chess_Map.cX = tempX;
        Chess_Map.cY = tempY;

        Chess_Map.aMoves = Chess_Map.getLegalMove(Chess_Map.cX,
                Chess_Map.cY);
        return true;
    }


    public void AIHelp() {
        Chess_Movement move = Chess_Map.AIHelp(Chess_Map.getStats());
        listener.UserChange(Chess_Map.getStats());

        if (Chess_Map.getStats() == Chess_Map.DRAW
                || Chess_Map.getStats() == Chess_Map.BW
                || Chess_Map.getStats() == Chess_Map.WW) {
            return;
        }

        if (Chess_Map
                .checkPawnCanChange(move.ToPoint().x, move.ToPoint().y)) {
            changePawn();
        }
    }

    private void myDraw() {
        Canvas canvas = sfh.lockCanvas();
        if (canvas == null) {
            return;
        }

        canvas.drawColor(Color.BLACK);

        bmBoard = drawImage(canvas, bmBoard, 0, 0, WIDTH, HEIGHT);

        if (Chess_Map.map == null) {
            sfh.unlockCanvasAndPost(canvas);
            return;
        }

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                switch (Chess_Map.map[i][j]) {
                    case EMPTY_SPOT:
                        break;
                    case WKING:
                        wChesses[0] = drawImage(canvas, wChesses[0],
                                (int) (diffX + j * unitSize), (int) (diffY + (7 - i)
                                        * unitSize), (int) unitSize, (int) unitSize);
                        break;
                    case WQUEEN:
                        wChesses[1] = drawImage(canvas, wChesses[1],
                                (int) (diffX + j * unitSize), (int) (diffY + (7 - i)
                                        * unitSize), (int) unitSize, (int) unitSize);
                        break;
                    case WROOK:
                        wChesses[2] = drawImage(canvas, wChesses[2],
                                (int) (diffX + j * unitSize), (int) (diffY + (7 - i)
                                        * unitSize), (int) unitSize, (int) unitSize);
                        break;
                    case WBISHOP:
                        wChesses[3] = drawImage(canvas, wChesses[3],
                                (int) (diffX + j * unitSize), (int) (diffY + (7 - i)
                                        * unitSize), (int) unitSize, (int) unitSize);
                        break;
                    case WKNIGHT:
                        wChesses[4] = drawImage(canvas, wChesses[4],
                                (int) (diffX + j * unitSize), (int) (diffY + (7 - i)
                                        * unitSize), (int) unitSize, (int) unitSize);
                        break;
                    case WPAWN:
                        wChesses[5] = drawImage(canvas, wChesses[5],
                                (int) (diffX + j * unitSize), (int) (diffY + (7 - i)
                                        * unitSize), (int) unitSize, (int) unitSize);
                        break;
                    case BKING:
                        bChesses[0] = drawImage(canvas, bChesses[0],
                                (int) (diffX + j * unitSize), (int) (diffY + (7 - i)
                                        * unitSize), (int) unitSize, (int) unitSize);
                        break;
                    case BQUEEN:
                        bChesses[1] = drawImage(canvas, bChesses[1],
                                (int) (diffX + j * unitSize), (int) (diffY + (7 - i)
                                        * unitSize), (int) unitSize, (int) unitSize);
                        break;
                    case BROOK:
                        bChesses[2] = drawImage(canvas, bChesses[2],
                                (int) (diffX + j * unitSize), (int) (diffY + (7 - i)
                                        * unitSize), (int) unitSize, (int) unitSize);
                        break;
                    case BBISHOP:
                        bChesses[3] = drawImage(canvas, bChesses[3],
                                (int) (diffX + j * unitSize), (int) (diffY + (7 - i)
                                        * unitSize), (int) unitSize, (int) unitSize);
                        break;
                    case BKNIGHT:
                        bChesses[4] = drawImage(canvas, bChesses[4],
                                (int) (diffX + j * unitSize), (int) (diffY + (7 - i)
                                        * unitSize), (int) unitSize, (int) unitSize);
                        break;
                    case BPAWN:
                        bChesses[5] = drawImage(canvas, bChesses[5],
                                (int) (diffX + j * unitSize), (int) (diffY + (7 - i)
                                        * unitSize), (int) unitSize, (int) unitSize);
                        break;
                }
            }
        }

        if (Chess_Map.cX != -1) {

            drawRect(canvas, Chess_Map.cX, 7 - Chess_Map.cY, Color.RED);

            for (int i = 0; i < Chess_Map.aMoves.size(); ++i) {
                Point p = Chess_Map.aMoves.get(i).ToPoint();
                drawRect(canvas, p.x, 7 - p.y, Color.GREEN);
            }
        }

        sfh.unlockCanvasAndPost(canvas);
    }

    private void myLogic() {
    }

    private class UpdateSurface implements Runnable {

        public void run() {
            while (flag) {
                long start = System.currentTimeMillis();
                myDraw();
                myLogic();
                long end = System.currentTimeMillis();
                if (end - start < 30) {
                    try {
                        Thread.sleep(30 - (end - start));
                    } catch (InterruptedException e) {
                    }
                }
            }
        }

    }


    private Bitmap drawImage(Canvas canvas, Bitmap temp, int startX,
                             int startY, int width, int height) {
        if (temp.getWidth() == (int) (width * scaleWidth)
                && temp.getHeight() == (int) (height * scaleHeight)) {
        } else {
            temp = Bitmap.createScaledBitmap(temp, (int) (width * scaleWidth),
                    (int) (height * scaleHeight), true);
        }
        canvas.drawBitmap(temp, (int) (startX * scaleWidth),
                (int) ((startY) * scaleHeight), paint);
        return temp;
    }


    private void drawRect(Canvas canvas, int m, int n, int color) {
        float x = (int) (diffX + m * unitSize);
        float y = (int) (diffY + n * unitSize);
        int tempColor = paint.getColor();
        paint.setColor(color);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect((x * scaleWidth), (y * scaleHeight), (x * scaleWidth)
                + (unitSize * scaleWidth), (y * scaleHeight)
                + (unitSize * scaleHeight), paint);
        paint.setColor(tempColor);
    }

    public void setGameViewListener(Gameview_Listener listener) {
        this.listener = listener;
    }

    private void changePawn() {
        Button queen = new Button(getContext());
        queen.setText("Queen!!!");
        queen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Chess_Map.change(BQUEEN);
                dialog.dismiss();
            }
        });
        dialog = new AlertDialog.Builder(getContext())
                .setIcon(android.R.drawable.btn_star)
                .setTitle("Pawn Change")
                .setView(queen)
                .setPositiveButton("Knight",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Chess_Map.change(BKNIGHT);
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton("Rook",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Chess_Map.change(BROOK);
                                dialog.dismiss();
                            }
                        })
                .setNeutralButton("Bishop",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Chess_Map.change(BBISHOP);
                                dialog.dismiss();
                            }
                        }).setCancelable(false).create();
        dialog.show();
    }

}
