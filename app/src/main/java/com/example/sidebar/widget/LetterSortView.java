package com.example.sidebar.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class LetterSortView extends View {

    private static final String[] b = {
            "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P",
            "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z","#"
    };

    private Paint mPaint = new Paint();
    private int choose = -1;
    private OnTouchChangedListener mListener;

    public interface OnTouchChangedListener{
        void onTouchUp();
        void onTouchChanged(String s);
    }

    public void setOnTouchChangedListener(OnTouchChangedListener l){
        mListener = l;
    }

    public LetterSortView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        int singleHeight = height / b.length;// 获取每个字母的高度

        for(int i=0; i<b.length; i++){
            mPaint.setColor(Color.parseColor("#9da0a4"));
            mPaint.setTypeface(Typeface.DEFAULT_BOLD);
            mPaint.setAntiAlias(true);
            mPaint.setTextSize(25);

            if(i == choose){
                mPaint.setColor(Color.parseColor("#3399ff"));
                mPaint.setFakeBoldText(true);
            }

            float xPos = width / 2 - mPaint.measureText(b[i]) / 2;
            float yPos = singleHeight * (i + 1);
            canvas.drawText(b[i], xPos, yPos, mPaint);
            mPaint.reset();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final float y = event.getY();
        int oldChoose = choose;

        final int c = (int)(y / getHeight() * b.length);

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                //setBackgroundDrawable(new ColorDrawable(0x00000000));//过时
                setBackground(new ColorDrawable(0x00000000));
                choose = -1;
                invalidate();

                if(mListener!= null){
                    mListener.onTouchUp();
                }
                break;
            default:
                //setBackgroundDrawable(new ColorDrawable(0xCCCCCC));//过时
                setBackground(new ColorDrawable(0xCCCCCC));

                if(oldChoose != c){
                    if(c >= 0 && c < b.length){
                        mListener.onTouchChanged(b[c]);
                    }

                    oldChoose = c;
                    invalidate();
                }
                break;
        }

        return true;
    }

}
