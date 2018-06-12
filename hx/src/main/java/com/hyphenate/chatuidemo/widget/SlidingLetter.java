package com.hyphenate.chatuidemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import li.com.base.baseuntils.LogUtils;

/**
 * Created by Administrator on 2017/1/18.
 */
public class SlidingLetter extends View {

    public interface LetterClick {
        /**
         * @param index  当前点击的字母索引值
         * @param letter 当前点击的字母
         */
        public void letterClick(int index, String letter);

        /**
         * 手指抬起回调
         */
        public void up();
    }

    LetterClick lc;

    public void setLetterClickListener(LetterClick listener) {
        lc = listener;
    }


    static final String[] letters = {
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    Paint mPaint;

    public SlidingLetter(Context context) {
        super(context);
        init();
    }

    public SlidingLetter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.GREEN);
    }

    //测量控件大小


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //设置大小
        setMeasuredDimension(getMeasureWH(widthMeasureSpec, 0), getMeasureWH(heightMeasureSpec, 1));

        //设置文本大小
        mPaint.setTextSize((getMeasuredHeight() - getPaddingTop() - getPaddingBottom()) / letters.length - 5);
    }


    private int getMeasureWH(int wh, int type) {
        //取出mode
        int mode = MeasureSpec.getMode(wh);
        //取出size
        int size = MeasureSpec.getSize(wh);
        //如果模式是AT_MOST，宽度：字体的大小，高度：父控件高度
        if (mode == MeasureSpec.AT_MOST) {
            //wrap_content
            if (type == 0) {
                //宽度测量
                return (int) mPaint.measureText("W") + getPaddingLeft() + getPaddingRight();
            } else {
                // 高度测量，返回推荐大小
                return size;
            }
        }

        return size;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        LogUtils.logd("onDraw~~~~~~~~" + index);
        super.onDraw(canvas);
        for (int i = 0; i < letters.length; i++) {
            //x方向
            float dx = 0 + getPaddingLeft();
            //y方向
            float dy = getPaddingTop() - mPaint.ascent() + i * (mPaint.descent() - mPaint.ascent());
            //如果是选中的，变色
            if (index == i) {
                mPaint.setColor(Color.RED);
                //加粗
                mPaint.setFakeBoldText(true);
            } else {
                mPaint.setColor(Color.GREEN);
                //取消加粗
                mPaint.setFakeBoldText(false);
            }
            //绘制字母
            canvas.drawText(letters[i], dx, dy, mPaint);

        }

    }

    //处理响应事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //根据动作来判断
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                eventLable(event);
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                eventLable(event);
            }
            break;
            case MotionEvent.ACTION_UP: {
                //手指抬起时，重绘界面，不选中任何字母
                index = -1;
                invalidate();
                //回调接口中的方法
                if (lc != null) {
                    lc.up();
                }
            }
            break;
        }

        return true;
    }

    /**
     * 当前被点击的字母序号
     */
    int index = -1;

    /**
     * 处理点击字母
     *
     * @param event
     */
    private void eventLable(MotionEvent event) {
        //根据手指坐标，计算点击的是哪个字母的序号
        float dy = event.getY();
        //计算字母对应的索引=(手指y坐标-paddingTop)/字母高度
        int index = (int) ((dy - getPaddingTop()) / (mPaint.descent() - mPaint.ascent()));
        //越界判断
        if (index < 0) {
            index = 0;
        }
        if (index >= letters.length) {
            index = letters.length - 1;
        }
        LogUtils.logd("当前点击了：" + index + "字母：" + letters[index]);

        //判断是否需要重绘，只有上次保存的index与当前手指所在的index不一致时，才需要重绘
        if (this.index != index) {
            this.index = index;
            //重绘界面，选中字母变色
            invalidate();
            //回调接口中的方法
            if (lc != null) {
                lc.letterClick(index, letters[index]);
            }


        }
    }

}
