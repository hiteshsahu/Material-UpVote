package com.hiteshsahu.materialupvotewidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

public class TriangleShapeView extends View {

    private int colorCode = Color.DKGRAY;

    public int getColorCode() {
        return colorCode;
    }

    public void setColorCode(int colorCode) {
        this.colorCode = colorCode;
    }

    public TriangleShapeView(Context context) {
        super(context);
        if (isInEditMode())
            return;
    }

    public TriangleShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode())
            return;
    }

    public TriangleShapeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if (isInEditMode())
            return;
    }


    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth() / 2;
        int h = getHeight() / 2;
        Path path = getUpTriangle(w, h);
        path.close();
        Paint p = new Paint();
        p.setColor(colorCode);
        p.setAntiAlias(true);

        canvas.drawPath(path, p);
    }

    @NonNull
    /**
     * Return Path for down facing triangle
     */
    private Path getInvertedTriangle(int w, int h) {
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(w, 2 * h);
        path.lineTo(2 * w, 0);
        path.lineTo(0, 0);
        return path;
    }

    @NonNull
    /**
     * Return Path for Up facing triangle
     */
    private Path getUpTriangle(int w, int h) {
        Path path = new Path();
        path.moveTo(0, 2 * h);
        path.lineTo(w, 0);
        path.lineTo(2 * w, 2 * h);
        path.lineTo(0, 2 * h);
        return path;
    }

    @NonNull
    /**
     * Return Path for Right pointing triangle
     */
    private Path getRightTriangle(int w, int h) {
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(2 * w, h);
        path.lineTo(0, 2 * h);
        path.lineTo(0, 0);
        return path;
    }

    @NonNull
    /**
     * Return Path for Left pointing triangle
     */
    private Path getLeftTriangle(int w, int h) {
        Path path = new Path();
        path.moveTo(2 * w, 0);
        path.lineTo(0, h);
        path.lineTo(2 * w, 2 * h);
        path.lineTo(2 * w, 0);
        return path;
    }
}