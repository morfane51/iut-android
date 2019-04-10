package network.iut.org.flappydragon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameView extends SurfaceView implements Runnable {
    public static final long UPDATE_INTERVAL = 50; // = 20 FPS
    private SurfaceHolder holder;
    private boolean paused = true;
    private Timer timer = new Timer();
    private TimerTask timerTask;
    private Player player;
    private Background background;
    private List<Obstacle> obstacles;
    private Random r;
    private Context context;
    private int score;
    private int topScore;

    public GameView(Context context) {
        super(context);
        this.context = context;
        player = new Player(context, this);
        background = new Background(context);
        obstacles = new ArrayList<>();
        holder = getHolder();
        score = 0;
        topScore = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                GameView.this.run();
            }
        }).start();
        r = new Random();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (paused || player.loose) {
                player.reset();
                if (score > topScore) {
                    topScore = score;
                }
                score = 0;
                obstacles.removeAll(obstacles);
                resume();
            } else {
                Log.i("PLAYER", "PLAYER TAPPED");
                this.player.onTap();
            }
        }
        return true;
    }

    private void resume() {
        paused = false;
        player.loose = false;
        startTimer();
    }

    private void startTimer() {
        Log.i("TIMER", "START TIMER");
        setUpTimerTask();
        timer = new Timer();
        timer.schedule(timerTask, UPDATE_INTERVAL, UPDATE_INTERVAL);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
        if (timerTask != null) {
            timerTask.cancel();
        }
    }

    private void setUpTimerTask() {
        stopTimer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                GameView.this.run();
            }
        };
    }

    @Override
    public void run() {
        player.move();
        draw();
    }

    private void draw() {
        while (!holder.getSurface().isValid()) {
            /*wait*/
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Canvas canvas = holder.lockCanvas();
        if (canvas != null) {
            drawCanvas(canvas);
        }
        try {
            holder.unlockCanvasAndPost(canvas);
        } catch (IllegalStateException e) {
            // Already unlocked
        }
    }

    private void drawCanvas(final Canvas canvas) {
        background.draw(canvas);
        player.draw(canvas);

        if (paused) {
            canvas.drawText("PAUSED", canvas.getWidth() / 2, canvas.getHeight() / 2, new Paint());
        }
        if (player.loose) {
            //BEFORE Paint dans player

            Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setTypeface(Typeface.create("Arial", Typeface.ITALIC));
            mPaint.setTextSize(120);
            String pageTitle = "YOU DIED";
            float right = mPaint.measureText(pageTitle, 0, pageTitle.length());
            mPaint.setColor(Color.RED);
            canvas.drawText(pageTitle, canvas.getWidth() / 2 - (right / 2), canvas.getHeight() / 2, mPaint);

        } else {
            for (Obstacle obstacle : obstacles) {
                if (!obstacle.draw(canvas)) {
                    obstacle = null;

                } else {
                    if (obstacle.intersect(player)) {
                        player.loose = true;
                    }
                }
            }

            int ratio = score / 50;

            if (ratio < 3) {
                ratio = 3;
            } else if (ratio > 50) {
                ratio = 50;
            }

            if (r.nextInt(100) >= 100 - ratio) {
                obstacles.add(new Obstacle(context));
            }
        }

        Paint mPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint1.setTypeface(Typeface.create("Arial", Typeface.ITALIC));
        mPaint1.setTextSize(80);
        String pageScore = score + "m";
        float right2 = mPaint1.measureText(pageScore, 0, pageScore.length());
        mPaint1.setColor(Color.WHITE);
        canvas.drawText(pageScore, canvas.getWidth() / 2 - (right2 / 2), canvas.getHeight() / 10, mPaint1);

        mPaint1.setTextSize(40);
        String pageTopScore = topScore + "m";
        mPaint1.setColor(Color.WHITE);
        canvas.drawText(pageTopScore, canvas.getWidth() - mPaint1.measureText(pageTopScore, 0, pageTopScore.length()), 40, mPaint1);

        if (!player.loose) {
            score++;
        }
    }

}
