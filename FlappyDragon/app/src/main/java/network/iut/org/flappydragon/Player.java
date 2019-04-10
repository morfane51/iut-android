package network.iut.org.flappydragon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class Player {
    /**
     * Static bitmap to reduce memory usage.
     */
    public static Bitmap globalBitmap;
    private final Bitmap bitmap;
    private final byte frameTime;
    private int frameTimeCounter;
    private final int width;
    private final int height;
    private int x;
    private int y;
    private int top;
    private float bottom;
    private float speedX;
    private float speedY;
    private float hauteurEcran;
    private float LargeurEcran;
    public boolean loose;
    private GameView view;
    private Rect rect;

    public Player(Context context, GameView view) {
        int height = context.getResources().getDisplayMetrics().heightPixels;
        int width = context.getResources().getDisplayMetrics().widthPixels;
        this.hauteurEcran=height;
        this.LargeurEcran=width;
        loose=false;
        this.top = 0;
        this.bottom =((float)height / 5) * (float)4.09;

        if (globalBitmap == null) {
            Log.e("TEST", "Height : " + height + ", width : " + width);
            globalBitmap = Util.decodeSampledBitmapFromResource(context.getResources(), R.drawable.frame1, Float.valueOf(height / 10f).intValue(), Float.valueOf(width / 10f).intValue());
        }
        this.bitmap = globalBitmap;
        this.width = this.bitmap.getWidth();
        this.height = this.bitmap.getHeight();
        this.frameTime = 3;        // the frame will change every 3 runs
        this.y = context.getResources().getDisplayMetrics().heightPixels / 2;    // Startposition in the middle of the screen

        this.view = view;
        this.x = width / 6;
        this.speedX = 0;
    }

    public void onTap() {
        this.speedY = getTabSpeed();
        this.y += getPosTabIncrease();
    }

    private float getPosTabIncrease() {
        return -view.getHeight() / 100;
    }

    private float getTabSpeed() {
        return -view.getHeight() / 16f;
    }

    public void move() {
        changeToNextFrame();

        if (speedY < 0) {
            // The character is moving up
            Log.i("Move", "Moving up");
            speedY = speedY * 2 / 3 + getSpeedTimeDecrease() / 2;
        } else {
            // the character is moving down
            Log.i("Move", "Moving down");
            this.speedY += getSpeedTimeDecrease();
        }
        if (this.speedY > getMaxSpeed()) {
            // speed limit
            this.speedY = getMaxSpeed();
        }

        // manage frames
/*        if(row != 3){
            // not dead
            if(speedY > getTabSpeed() / 3 && speedY < getMaxSpeed() * 1/3){
                row = 0;
            }else if(speedY > 0){
                row = 1;
            }else{
                row = 2;
            }
        }
*/
        this.x += speedX;
        if (speedY < 0) { // Moving up
            if ((this.y + speedY) > this.top) {
                this.y += speedY;
            } else {
                speedY = this.top - (this.y + speedY);

                if (speedY < 0) {
                    this.y += speedY;
                }
            }
        } else { // Moving down
            if ((this.y + speedY) < this.bottom) {
                this.y += speedY;

            } else {
                speedY = this.bottom - (this.y + speedY);
                loose=true;
                if (speedY > 0) {
                    this.y += speedY;
                }
            }
        }
    }

    protected void changeToNextFrame() {
        this.frameTimeCounter++;
        if (this.frameTimeCounter >= this.frameTime) {
            //TODO Change frame
            this.frameTimeCounter = 0;
        }
    }

    private float getSpeedTimeDecrease() {
        return view.getHeight() / 320;
    }

    private float getMaxSpeed() {
        return view.getHeight() / 51.2f;
    }

    public void draw(Canvas canvas) {
        rect = new Rect(x, y, x + width, y + height);
        if(!loose) {
            canvas.drawBitmap(bitmap, x, y, null);
        }
    }
    public void reset(){

        this.x= (int) this.LargeurEcran / 6;
        this.y= (int) this.hauteurEcran / 2 ;
    }

    public Rect getRect() {
        return rect;
    }
}
