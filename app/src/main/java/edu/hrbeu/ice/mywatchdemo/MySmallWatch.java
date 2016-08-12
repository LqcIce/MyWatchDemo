package edu.hrbeu.ice.mywatchdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by ice on 2016/8/1.
 */
public class MySmallWatch extends ImageView {

    private Paint paint;

    private int mWidth;
    private int mHeight;

    int val = 0;
    int total = 0;
    String name;
    String unit;
    int color;
    float radius;


    public MySmallWatch(Context context) {
        this(context, null, 0);
    }

    public MySmallWatch(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySmallWatch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         * 获得我们所定义的自定义样式属性
         */
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MySmallWatch, defStyleAttr, 0);

        val = a.getInt(R.styleable.MySmallWatch_val, 0);
        total = a.getInt(R.styleable.MySmallWatch_total, 100);
        name = a.getString(R.styleable.MySmallWatch_name);
        unit = a.getString(R.styleable.MySmallWatch_unit);
        color = a.getColor(R.styleable.MySmallWatch_circle_color, getResources().getColor(R.color.colorAccent));
        radius = a.getDimension(R.styleable.MySmallWatch_radius, 200);

        paint = new Paint();

        a.recycle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);


        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else {

            float needWidth = radius * 2 + 24;
            int desired = (int) (getPaddingLeft() + needWidth + getPaddingRight());
            mWidth = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else {
            paint.setStrokeWidth(6);

            float needWidth = radius * 2 + 24;
            int desired = (int) (getPaddingTop() + needWidth + getPaddingBottom());
            mHeight = desired;
        }


        setMeasuredDimension(mWidth, mHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        paint.setStrokeWidth(6);
        paint.setColor(Color.WHITE);
        //抗锯齿
        paint.setAntiAlias(true);


        //绘制圆圈
        canvas.drawCircle(mWidth / 2, mHeight / 2, radius, paint);


        //绘制进度
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);

        int arc = val % total * 360 / total;


        canvas.drawArc(new RectF(mWidth / 2 - radius - 6, mHeight / 2 - radius - 6, mWidth / 2 + radius + 6, mHeight / 2 + radius + 6), 180, arc, false, paint);

        //中心数字
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(48);
        canvas.drawText(val + unit, mWidth / 2, mHeight / 2, paint);
        canvas.drawText(name, mWidth / 2, mHeight / 2 + 48, paint);


    }

    public void setVal(int val) {

        this.val = val;
        invalidate();


    }
}
