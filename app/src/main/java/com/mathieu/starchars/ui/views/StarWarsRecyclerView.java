package com.mathieu.starchars.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Project :    Star Chars
 * Author :     Mathieu
 * Date :       21/12/2015
 */

public class StarWarsRecyclerView extends RecyclerView {

    private Matrix matrix;

    public StarWarsRecyclerView(Context context) {
        super(context);
        init();
    }

    private void init() {
        matrix = new Matrix();
    }

    public StarWarsRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StarWarsRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        final float DELTAX = w * 0.10f;
//        float[] src = {
//                0, 0, w, 0, w, h, 0, h
//        };
//        float[] dst = {
//                DELTAX, 0, w - DELTAX, 0, w + DELTAX, h, -DELTAX, h
//        };
//        matrix.setPolyToPoly(src, 0, dst, 0, 4);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
//        canvas.save();
//        canvas.concat(matrix);
        super.dispatchDraw(canvas);
//        canvas.restore();
    }
}
