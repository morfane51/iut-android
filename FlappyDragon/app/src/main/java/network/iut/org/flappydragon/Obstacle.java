package network.iut.org.flappydragon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

public class Obstacle {
    private int height;
    private int width;
    private int top;
    private int offset;
    private Rect rect;

    public Obstacle(Context context) {
        height = 150;
        width = 150;
        top = new Random().nextInt((context.getResources().getDisplayMetrics().heightPixels / 4) * 3);
        offset = context.getResources().getDisplayMetrics().widthPixels;
    }

    public boolean intersect(Player player) {
        if (rect == null) {
            return false;
        }

        if (player.getRect() == null) {
            return false;
        }

        return rect.intersect(player.getRect());
    }

    public boolean draw(Canvas canvas) {
        offset -= 30;
        if (offset + width >= 0) {
            rect = new Rect(offset, top, offset + width, top + height);
            canvas.drawRect(rect, new Paint(123));
            return true;
        } else {
            return false;
        }
    }
}
