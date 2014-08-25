package com.gravityrd.jofogas.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;


/**
 * Created by zsolt on 2014.08.11..
 */
class DynamicHImageView extends com.etsy.android.grid.util.DynamicHeightImageView {
    public static float radius = 5.0f;
    Path clipPath = new Path();
    RectF rect = new RectF(0, 0, this.getWidth(), this.getHeight());

    public DynamicHImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicHImageView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        rect.left = 0;
        rect.top = 0;
        rect.right = this.getWidth();
        rect.bottom = this.getHeight();
        clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(clipPath);
        super.onDraw(canvas);

    }
}
