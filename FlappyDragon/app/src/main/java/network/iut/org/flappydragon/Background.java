package network.iut.org.flappydragon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Background {
    private int height;
    private int width;
    private int offset1;
    private int offset2;
    private int offset1Sky;
    private int offset2Sky;
    private int offset1Clouds;
    private int offset2Clouds;
    private int offset1Mountains;
    private int offset2Mountains;
    private int offset1Trees;
    private int offset2Trees;
    private Bitmap background1;
    private Bitmap background2;
    private Bitmap background3;
    private Bitmap background4;
    private Bitmap background5;
    private Bitmap background6;
    private Bitmap background7;
    private Bitmap background8;
    private Bitmap background9;
    private Bitmap background10;

    public Background(Context context) {
        height = context.getResources().getDisplayMetrics().heightPixels;
        width = context.getResources().getDisplayMetrics().widthPixels;
        offset1 = 0;
        offset2 = 0;
        offset1Sky = 0;
        offset2Sky = 0;
        offset1Clouds = 0;
        offset2Clouds = 0;
        offset1Mountains = 0;
        offset2Mountains = 0;
        offset1Trees = 0;
        offset2Trees = 0;
        background1 = Util.decodeSampledBitmapFromResource(context.getResources(), R.drawable.layer1, width, height);
        background2 = Util.decodeSampledBitmapFromResource(context.getResources(), R.drawable.layer2, width, height);
        background3 = Util.decodeSampledBitmapFromResource(context.getResources(), R.drawable.layer3, width, height);
        background4 = Util.decodeSampledBitmapFromResource(context.getResources(), R.drawable.layer4, width, height);
        background5 = Util.decodeSampledBitmapFromResource(context.getResources(), R.drawable.layer5, width, height);
        background6 = Util.decodeSampledBitmapFromResource(context.getResources(), R.drawable.layer1, width, height);
        background7 = Util.decodeSampledBitmapFromResource(context.getResources(), R.drawable.layer2, width, height);
        background8 = Util.decodeSampledBitmapFromResource(context.getResources(), R.drawable.layer3, width, height);
        background9 = Util.decodeSampledBitmapFromResource(context.getResources(), R.drawable.layer4, width, height);
        background10 = Util.decodeSampledBitmapFromResource(context.getResources(), R.drawable.layer5, width, height);
    }

    public void draw(Canvas canvas) {
        offset1Sky += 5;
        offset2Sky = offset1Sky - background1.getWidth();
        if (offset2Sky >= 0 && offset2Sky < 5) {
            int i = offset2Sky;
            offset2Sky = offset1Sky;
            offset1Sky = i;
        }

        offset1Clouds += 12;
        offset2Clouds = offset1Clouds - background2.getWidth();
        if (offset2Clouds >= 0 && offset2Clouds < 12) {
            int j = offset2Clouds;
            offset2Clouds = offset1Clouds;
            offset1Clouds = j;
        }

        offset1Mountains += 14;
        offset2Mountains = offset1Mountains - background3.getWidth();
        if (offset2Mountains >= 0 && offset2Mountains < 14) {
            int k = offset2Mountains;
            offset2Mountains = offset1Mountains;
            offset1Mountains = k;
        }

        offset1Trees += 20;
        offset2Trees = offset1Trees - background4.getWidth();
        if (offset2Trees >= 0 && offset2Trees < 20) {
            int l = offset2Trees;
            offset2Trees = offset1Trees;
            offset1Trees = l;
        }

        offset1 += 20;
        offset2 = offset1 - background5.getWidth();
        if (offset2 >= 0 && offset2 < 20) {
            int m = offset2;
            offset2 = offset1;
            offset1 = m;
        }

        canvas.drawBitmap(background1, new Rect(offset1Sky, 0, offset1Sky + width, background1.getHeight()), new Rect(0, 0, width, height), null);
        canvas.drawBitmap(background6, new Rect(offset2Sky, 0, offset2Sky + width, background1.getHeight()), new Rect(0, 0, width, height), null);
        canvas.drawBitmap(background2, new Rect(offset1Clouds, 0, offset1Clouds + width, background1.getHeight()), new Rect(0, 0, width, height), null);
        canvas.drawBitmap(background7, new Rect(offset2Clouds, 0, offset2Clouds + width, background1.getHeight()), new Rect(0, 0, width, height), null);
        canvas.drawBitmap(background3, new Rect(offset1Mountains, 0, offset1Mountains + width, background1.getHeight()), new Rect(0, 0, width, height), null);
        canvas.drawBitmap(background8, new Rect(offset2Mountains, 0, offset2Mountains + width, background1.getHeight()), new Rect(0, 0, width, height), null);
        canvas.drawBitmap(background4, new Rect(offset1Trees, 0, offset1Trees + width, background1.getHeight()), new Rect(0, 0, width, height), null);
        canvas.drawBitmap(background9, new Rect(offset2Trees, 0, offset2Trees + width, background1.getHeight()), new Rect(0, 0, width, height), null);
        canvas.drawBitmap(background5, new Rect(offset1, 0, offset1 + width, background1.getHeight()), new Rect(0, 0, width, height), null);
       canvas.drawBitmap(background10, new Rect(offset2, 0, offset2 + width, background1.getHeight()), new Rect(0, 0, width, height), null);
    }
}
