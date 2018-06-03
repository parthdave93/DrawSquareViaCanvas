package com.parthdave93.drawsquareviacanvas.ui.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import com.parthdave93.drawsquareviacanvas.model.FingerPath;

import java.util.ArrayList;

public class DrawSquareView extends View {
    private boolean drawNewSquareOnMove = false;
    public static final int DEFAULT_COLOR = Color.GREEN;
    public final int DEFAULT_BACKGROUND_COLOR = Color.WHITE;
    public static final int MAX_STROKE_WIDTH = 20;
    public static final int MAX_SQUARE_SIZE = 700;
    private int color = DEFAULT_COLOR;
    private int strokeWidth = 20;
    private int squareWidth = 100;
    private Paint paintSquare;
    private Paint background;
    private ArrayList<RectF> mPath;
    private final float TOUCH_TOLERANCE = 8;
    private ArrayList<FingerPath> paths = new ArrayList<>();
    float mX, mY;

    public DrawSquareView(Context context) {
        super(context);
        init();
    }

    public DrawSquareView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawSquareView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paintSquare = new Paint();
        paintSquare.setAntiAlias(true);
        paintSquare.setDither(true);
        paintSquare.setColor(DEFAULT_COLOR);
        paintSquare.setStyle(Paint.Style.STROKE);
        paintSquare.setStrokeJoin(Paint.Join.ROUND);
        paintSquare.setStrokeCap(Paint.Cap.ROUND);
        paintSquare.setXfermode(null);
        paintSquare.setAlpha(0xff);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();

        for (FingerPath fp : paths) {
            paintSquare.setColor(fp.getColor());
            paintSquare.setStrokeWidth(fp.getStrokeWidth());
            for (int index = 0; index < fp.getmPath().size(); index++) {
                canvas.drawRect(fp.getmPath().get(index), paintSquare);
            }
        }

        canvas.restore();
    }


    private void onMove(float x, float y) {
        if(drawNewSquareOnMove) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);

            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                mPath.add(new RectF(x - squareWidth, y - squareWidth, x + squareWidth, y + squareWidth));
                mX = x;
                mY = y;
            }
        }
    }

    private void onTouchDown(float x, float y) {
        mPath = new ArrayList<RectF>();
        mPath.add(new RectF(x - squareWidth, y - squareWidth, x + squareWidth, y + squareWidth));
        FingerPath fp = new FingerPath(color, strokeWidth, squareWidth, mPath);
        paths.add(fp);

        mX = x;
        mY = y;
    }

    private void onTouchUp() {

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                onTouchDown(x, y);
                invalidate();
                break;

            case MotionEvent.ACTION_MOVE:
                onMove(x, y);
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                onTouchUp();
                invalidate();
                break;
        }
        return true;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public void setSquareWidth(int squareWidth) {
        this.squareWidth = squareWidth;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setDrawNewSquareOnMove(boolean drawNewSquareOnMove) {
        this.drawNewSquareOnMove = drawNewSquareOnMove;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public int getSquareWidth() {
        return squareWidth;
    }

    public int getColor() {
        return color;
    }

    public void clearDrawing(){
        paths.clear();
        invalidate();
    }

    public ArrayList<FingerPath> getPaths() {
        return paths;
    }
}
