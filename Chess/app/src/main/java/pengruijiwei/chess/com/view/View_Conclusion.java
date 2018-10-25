package pengruijiwei.chess.com.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.chess.R;

import pengruijiwei.chess.com.common.Chess_Types;
import pengruijiwei.chess.com.common.Chess_Map;

/**
 *  @author Pengrui liu
 * @author  Jiwei chen
 */
public class View_Conclusion extends SurfaceView implements SurfaceHolder.Callback,
        Chess_Types {

    private SurfaceHolder sfh;
    private Paint p;

    public static final int W = 625, H = 625;

    private Thread updateThread;

    private float scaleWidth, scaleHeight;

    private boolean flag;

    private Bitmap bmBoard;

    private Bitmap wChesses[];
    private Bitmap bChesses[];

    private final float diffX = 50, diffY = 50;

    private final float unitSize = 65.5f;

    public View_Conclusion(Context context) {
        super(context);
        init();
    }

    public View_Conclusion(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public View_Conclusion(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        sfh = this.getHolder();
        sfh.addCallback(this);

        p = new Paint();
        p.setColor(Color.GREEN);
        p.setAntiAlias(true);


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
        scaleWidth = (float) canvas.getWidth() / (float) W;
        scaleHeight = (float) canvas.getHeight() / (float) H;
        sfh.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Canvas canvas = sfh.lockCanvas();
        scaleWidth = (float) canvas.getWidth() / (float) W;
        scaleHeight = (float) canvas.getHeight() / (float) H;
        sfh.unlockCanvasAndPost(canvas);

        flag = true;
        updateThread = new Thread(new UpdateSurface());
        updateThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }


    private void myDraw() {
        Canvas canvas = sfh.lockCanvas();
        if (canvas == null) {
            return;
        }

        canvas.drawColor(Color.BLACK);

        bmBoard = drawImage(canvas, bmBoard, 0, 0, W, H);

        if(Chess_Map.map == null) {
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
            canvas.drawBitmap(temp, (int) (startX * scaleWidth),
                    (int) (startY * scaleHeight), p);
        } else {
            temp = Bitmap.createScaledBitmap(temp, (int) (width * scaleWidth),
                    (int) (height * scaleHeight), true);
        }

        return temp;
    }


    private void drawRect(Canvas canvas, int m, int n, int color) {
        float x = (int) (diffX + m * unitSize);
        float y = (int) (diffY + n * unitSize);
        int tempColor = p.getColor();
        p.setColor(color);
        p.setStrokeWidth(10);
        p.setStyle(Paint.Style.STROKE);
        canvas.drawRect((x * scaleWidth), (y * scaleHeight), (x * scaleWidth)
                + (unitSize * scaleWidth), (y * scaleHeight)
                + (unitSize * scaleHeight), p);
        p.setColor(tempColor);
    }

}
