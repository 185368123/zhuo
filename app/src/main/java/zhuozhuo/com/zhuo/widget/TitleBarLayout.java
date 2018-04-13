package zhuozhuo.com.zhuo.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import zhuozhuo.com.zhuo.R;


/**
 * author：kang
 * time:  2017/3/6
 *
 * 自定义 TitleBarLayout
 */
public class TitleBarLayout extends LinearLayout implements View.OnClickListener{

    /** 布局容器 */
    private LinearLayout linearRoot;

    /** 左边的 布局 */
    private LinearLayout linearLeft;
    private TextView tvLeft;
    private ImageView ivLeft;

    /** 标题 */
    private TextView tvTitle;

    /** 右边的 布局 */
    private LinearLayout linearRight;
    private TextView tvRight;
    private ImageView ivRight;

    /** 标题栏 点击事件监听 */
    private OnTitleBarClickListener onTitleBarClickListener;

    /** 默认字体大小 sp */
    private float defaultTextSize;
    /**  默认 字体颜色 */
    int defaultTextColor;

    /** 两边的 LinearLayout 是否显示 */
    private static final int LINEAR_VISIBLE = 0; // 显示
    private static final int LINEAR_INVISIBLE = 1; // 隐藏
    private static final int LINEAR_GONE = 2;  // 隐藏

    public TitleBarLayout(Context context) {
        this(context, null);
    }

    public TitleBarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 初始化 TitleBar
        this.initTitleBar(context, attrs);
    }

    /** 初始化 TitleBar */
    private void initTitleBar(Context context,AttributeSet attrs){
        // 默认字体大小 sp
        defaultTextSize = this.sp2px(16);
        // 默认 字体颜色
        defaultTextColor = this.getResources().getColor(R.color.main_color2);

        // 布局容器
        View mView = LayoutInflater.from(context).inflate(R.layout.title_bar,this);

        // 布局容器
        linearRoot = (LinearLayout)mView.findViewById(R.id.title_bar_linear3);

        // 左边的 布局
        linearLeft = (LinearLayout)mView.findViewById(R.id.title_bar_linear1);
        linearLeft.setOnClickListener(this);
        // 左 文本
        tvLeft = (TextView)mView.findViewById(R.id.title_bar_text1);
        tvLeft.setOnClickListener(this);
        // 左 图片
        ivLeft = (ImageView)mView.findViewById(R.id.title_bar_image1);


        // 标题
        tvTitle = (TextView)mView.findViewById(R.id.title_bar_text2);


        // 右边的 布局
        linearRight = (LinearLayout)mView.findViewById(R.id.title_bar_linear2);
        linearRight.setOnClickListener(this);
        // 右 文本
        tvRight = (TextView)mView.findViewById(R.id.title_bar_text3);
        tvRight.setOnClickListener(this);
        // 右 图片
        ivRight = (ImageView)mView.findViewById(R.id.title_bar_image2);

        // 获取 TitleBar 样式
        this.setTitleBarStyle(context,attrs);
    }

    /** 获取 TitleBar 样式 */
    private void setTitleBarStyle(Context context,AttributeSet attrs){
        if (attrs != null){
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBarLayout);

            // ============================== 标题内容 ==============================
            // 左边文本
            String leftTitle = typedArray.getString(R.styleable.TitleBarLayout_title_bar_left_text);
            tvLeft.setText(leftTitle);
            // 标题
            String title = typedArray.getString(R.styleable.TitleBarLayout_title_bar_title);
            tvTitle.setText(title);
            // 右边文本
            String rightTitle = typedArray.getString(R.styleable.TitleBarLayout_title_bar_right_text);
            tvRight.setText(rightTitle);

            // ============================== 字体大小 ==============================
            // 左边 文本字体大小
            float leftTextSize = typedArray.getDimension(R.styleable.TitleBarLayout_title_bar_left_text_size, defaultTextSize);
            tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTextSize);
            // 标题 大小
            float textSize = typedArray.getDimension(R.styleable.TitleBarLayout_title_bar_text_size, defaultTextSize);
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            // 右边 文本字体大小
            float rightTextSize = typedArray.getDimension(R.styleable.TitleBarLayout_title_bar_right_text_size, defaultTextSize);
            tvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize);

            // ============================== 字体颜色 ==============================
            // 左边 字体颜色
            int leftTextColor = typedArray.getColor(R.styleable.TitleBarLayout_title_bar_left_text_color, defaultTextColor);
            tvLeft.setTextColor(leftTextColor);
            // 标题 颜色
            int textColor = typedArray.getColor(R.styleable.TitleBarLayout_title_bar_text_color, defaultTextColor);
            tvTitle.setTextColor(textColor);
            // 右边 字体颜色
            int rightTextColor = typedArray.getColor(R.styleable.TitleBarLayout_title_bar_right_text_color, defaultTextColor);
            tvRight.setTextColor(rightTextColor);


            // ============================== TitleBar 的背景 ==============================
            // 整个 TitleBarLayout 的背景
            Drawable rootDrawable = typedArray.getDrawable(R.styleable.TitleBarLayout_title_bar_background);
            if (rootDrawable != null){
                linearRoot.setBackgroundDrawable(rootDrawable);
            }

            // ============================== 左右边的背景图片(ImageView) ==============================
            // 左边的图片
            Drawable leftDrawable = typedArray.getDrawable(R.styleable.TitleBarLayout_title_bar_left_image_src);
            if (leftDrawable != null){
                this.setLeftLayoutIsVisible(VISIBLE);
                ivLeft.setImageDrawable(leftDrawable);
            }
            // 右边的图片
            Drawable rightDrawable = typedArray.getDrawable(R.styleable.TitleBarLayout_title_bar_right_image_src);
            if (rightDrawable != null){
                this.setRightLayoutIsVisible(VISIBLE);
                ivRight.setImageDrawable(rightDrawable);
            }else {
                this.setRightLayoutIsVisible(INVISIBLE);
            }

            // ============================== 左右边两个 TextView 的背景 ==============================
            Drawable leftTextDrawable = typedArray.getDrawable(R.styleable.TitleBarLayout_title_bar_left_text_background);
            if (leftTextDrawable != null){
                tvLeft.setBackgroundDrawable(leftTextDrawable);
            }
            Drawable rightTextDrawable = typedArray.getDrawable(R.styleable.TitleBarLayout_title_bar_right_text_background);
            if (rightTextDrawable != null){
                tvRight.setBackgroundDrawable(rightTextDrawable);
            }

            // ============================== 左右边的布局背景(LinearLayout) ==============================
            // 左边的布局背景
            Drawable leftLinearDrawable = typedArray.getDrawable(R.styleable.TitleBarLayout_title_bar_left_linear_background);
            if (leftLinearDrawable != null){
                this.setLeftLayoutIsVisible(VISIBLE);
                linearLeft.setBackgroundDrawable(leftLinearDrawable);
            }
            // 右边的布局背景
            Drawable rightLinearDrawable = typedArray.getDrawable(R.styleable.TitleBarLayout_title_bar_right_linear_background);
            if (rightLinearDrawable != null){
                this.setRightLayoutIsVisible(VISIBLE);
                linearRight.setBackgroundDrawable(rightLinearDrawable);
            }

            // ============================== 两边的 LinearLayout 是否显示 ==============================
            // 左边的 LinearLayout
            int leftVisibility = typedArray.getInt(R.styleable.TitleBarLayout_title_bar_left_linear_visibility, LINEAR_VISIBLE);
            if (leftVisibility == LINEAR_INVISIBLE){
                // 隐藏 - 不能点击
                this.setLeftLayoutIsVisible(INVISIBLE);
            }else if (leftVisibility == LINEAR_GONE){
                // 隐藏 - 不能点击
                this.setLeftLayoutIsVisible(GONE);
            }else{
                // 显示
                this.setLeftLayoutIsVisible(VISIBLE);
            }
            // 右边的 LinearLayout
            int rightVisibility = typedArray.getInt(R.styleable.TitleBarLayout_title_bar_right_linear_visibility, LINEAR_INVISIBLE);
            if (rightVisibility == LINEAR_INVISIBLE){
                // 隐藏 - 不能点击
                this.setRightLayoutIsVisible(INVISIBLE);
            }else if (rightVisibility == LINEAR_GONE){
                // 隐藏 - 不能点击
                this.setRightLayoutIsVisible(GONE);
            } else {
                // 显示
                this.setRightLayoutIsVisible(VISIBLE);
            }

            // ============================== 两边的 TextView 是否显示 ==============================
            // 左边 TextView
            int leftTextVisibility = typedArray.getInt(R.styleable.TitleBarLayout_title_bar_left_text_visibility,LINEAR_GONE);
            if (leftTextVisibility == LINEAR_INVISIBLE){
                this.setLeftTextIsVisible(INVISIBLE);
            }else if (leftTextVisibility == LINEAR_VISIBLE){
                this.setLeftTextIsVisible(VISIBLE);
            }else {
                if (!TextUtils.isEmpty(leftTitle)) {
                    this.setLeftTextIsVisible(VISIBLE);
                }else {
                    this.setLeftTextIsVisible(GONE);
                }
            }
            // 右边 TextView
            int rightTextVisibility = typedArray.getInt(R.styleable.TitleBarLayout_title_bar_right_text_visibility,LINEAR_GONE);
            if (rightTextVisibility == LINEAR_INVISIBLE){
                this.setRightTextIsVisible(INVISIBLE);
            }else if (rightTextVisibility == LINEAR_VISIBLE){
                this.setRightTextIsVisible(VISIBLE);
            }else {
                if (!TextUtils.isEmpty(rightTitle)) {
                    this.setRightTextIsVisible(VISIBLE);
                }else {
                    this.setRightTextIsVisible(GONE);
                }
            }

            typedArray.recycle();
        }
    }

    // ============================== 标题内容 ==============================
    /** 左边文本 */
    public void setLeftTitle(CharSequence leftText){
        tvLeft.setText(leftText);
        tvLeft.setVisibility(VISIBLE);
    }

    /** 设置标题 */
    public void setTitle(CharSequence text){
        tvTitle.setText(text);
    }

    /** 右边文本 */
    public void setRightTitle(CharSequence rightText){
        tvRight.setText(rightText);
        tvRight.setVisibility(VISIBLE);
    }


    // ============================== 字体大小 ==============================
    /** 左边文本 字体大小 sp */
    public void setLeftTextSize(int spLeftTextSize){
        tvLeft.setTextSize(this.sp2px(spLeftTextSize));
    }

    /** 标题文本 字体大小 sp */
    public void setTitleTextSize(int spTitleTextSize){
        tvTitle.setTextSize(this.sp2px(spTitleTextSize));
    }

    /** 右边文本 字体大小 sp */
    public void setRightTextSize(int spRightTextSize){
        tvRight.setTextSize(this.sp2px(spRightTextSize));
    }


    // ============================== 字体颜色 ==============================
    /** 左边文本 字体颜色 */
    public void setLeftTextColor(@ColorInt int color){
        tvLeft.setTextColor(color);
    }

    /** 标题文本 字体颜色 */
    public void setTitleTextColor(@ColorInt int color){
        tvTitle.setTextColor(color);
    }

    /** 右边文本 字体颜色 */
    public void setRightTextColor(@ColorInt int color){
        tvRight.setTextColor(color);
    }


    // ============================== TitleBar 的背景 ==============================
    /** 设置 TitleBar 的背景颜色 */
    public void setRootBackgroundColor(@ColorInt int color){
        linearRoot.setBackgroundColor(color);
    }
    /** 设置 TitleBar 的背景 */
    public void setRootBackgroundResource(@DrawableRes int resId){
        linearRoot.setBackgroundResource(resId);
    }


    // ============================== 左右边的背景图片(ImageView) ==============================
    /** 设置 左边 的图片 */
    public void setLeftImageResource(@DrawableRes int resId){
        ivLeft.setImageResource(resId);
        this.setViewIsVisible(linearLeft, VISIBLE);
    }

    /** 设置 右边 的图片 */
    public void setRightImageResource(@DrawableRes int resId){
        ivRight.setImageResource(resId);
        this.setViewIsVisible(linearRight, VISIBLE);
    }


    // ============================== 左右边两个 TextView 的背景 ==============================
    /** 设置左边的 TextView 背景 */
    public void setLeftTextBackground(@DrawableRes int resId){
        tvLeft.setBackgroundResource(resId);
    }

    /** 设置右边的 TextView 背景 */
    public void setRightTextBackground(@DrawableRes int resId){
        tvRight.setBackgroundResource(resId);
    }

    /** 设置右边的 TextView 背景颜色 */
    public void setLeftTextBackgroundColor(@ColorInt int color){
        tvLeft.setBackgroundColor(color);
    }

    /** 设置右边的 TextView 背景颜色 */
    public void setRightTextBackgroundColor(@ColorInt int color){
        tvRight.setBackgroundColor(color);
    }

    // ============================== 左右边的布局背景颜色(LinearLayout) ==============================
    /** 设置 左边 布局的背景颜色 */
    public void setLeftLinearBackgroundColor(@ColorInt int color) {
        linearLeft.setBackgroundColor(color);
    }

    /** 设置 右边 布局的背景颜色 */
    public void setRightLinearBackgroundColor(@ColorInt int color) {
        linearRight.setBackgroundColor(color);
    }


    // ============================== 左右边的布局背景图片(LinearLayout) ==============================
    /** 设置 左边 布局的背景 */
    public void setLeftLinearBackgroundResource(@DrawableRes int resId) {
        linearLeft.setBackgroundResource(resId);
    }

    /** 设置 右边 布局的背景 */
    public void setRightLinearBackgroundResource(@DrawableRes int resId){
        linearRight.setBackgroundResource(resId);
    }

    /** 设置 左边的布局是否有焦点 */
    public void setLeftLinearFocusable(boolean isFocusable){
        linearLeft.setClickable(isFocusable);
        linearLeft.setFocusable(isFocusable);
    }

    /** 设置 右边的布局是否有焦点 */
    public void setRightLinearFocusable(boolean isFocusable){
        linearRight.setClickable(isFocusable);
        linearRight.setFocusable(isFocusable);
    }

    /** 设置 左边的 TextView 是否有焦点 */
    public void setLeftTextFocusable(boolean isFocusable){
        tvLeft.setClickable(isFocusable);
        tvLeft.setFocusable(isFocusable);
    }

    /** 设置 右边的 TextView 是否有焦点 */
    public void setRightTextFocusable(boolean isFocusable){
        tvRight.setClickable(isFocusable);
        tvRight.setFocusable(isFocusable);
    }

    /** 设置布局隐藏 或 显示 */
    private void setViewIsVisible(View view,int isVisibility){
        if (view == null)return;
        view.setVisibility(isVisibility);
    }

    /**
     * 设置 左边 的布局是否显示
     * @param isVisibility View.GONE，INVISIBLE，VISIBLE
     */
    public void setLeftLayoutIsVisible(int isVisibility){
        if (isVisibility == GONE || isVisibility == INVISIBLE){
            // 不显示 - 不能点击
            this.setLeftLinearFocusable(false);
            this.setViewIsVisible(linearLeft, isVisibility);
        }else {
            this.setLeftLinearFocusable(true);
            this.setViewIsVisible(linearLeft, isVisibility);
        }
    }

    /** 设置 右边 的布局是否显示
     * @param isVisibility View.GONE，INVISIBLE，VISIBLE
     */
    public void setRightLayoutIsVisible(int isVisibility){
        if (isVisibility == GONE || isVisibility == INVISIBLE){
            // 不显示 - 不能点击
            this.setRightLinearFocusable(false);
            this.setViewIsVisible(linearRight, isVisibility);
        }else {
            this.setRightLinearFocusable(true);
            this.setViewIsVisible(linearRight, isVisibility);
        }
    }

    /** 设置 左边 的 TextView 是否显示
     * @param isVisibility View.GONE，INVISIBLE，VISIBLE
     */
    public void setLeftTextIsVisible(int isVisibility){
        if (isVisibility == GONE || isVisibility == INVISIBLE){
            // 不显示 - 不能点击
            this.setLeftTextFocusable(false);
            this.setViewIsVisible(tvLeft, isVisibility);
        }else {
            this.setLeftTextFocusable(true);
            this.setViewIsVisible(tvLeft, isVisibility);
        }
    }

    /** 设置 右边 的 TextView 是否显示
     * @param isVisibility View.GONE，INVISIBLE，VISIBLE
     */
    public void setRightTextIsVisible(int isVisibility){
        if (isVisibility == GONE || isVisibility == INVISIBLE){
            // 不显示 - 不能点击
            this.setRightTextFocusable(false);
            this.setViewIsVisible(tvRight, isVisibility);
        }else {
            this.setRightTextFocusable(true);
            this.setViewIsVisible(tvRight, isVisibility);
        }
    }

    /** 设置背景颜色或图片 */
    @TargetApi(16)
    private void setBackgroundAndDrawable(LinearLayout linearLayout,Drawable drawable){
        if (linearLayout != null && drawable != null){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                linearLayout.setBackground(drawable);
            }else {
                linearLayout.setBackgroundDrawable(drawable);
            }
        }
    }

    /** 获取 左边的标题 */
    public String getLeftTitle(){
        return tvLeft.getText().toString();
    }

    /** 获取 标题 */
    public String getTitle(){
        return tvTitle.getText().toString();
    }

    /** 获取 右边的标题 */
    public String getRightTitle(){
        return tvRight.getText().toString();
    }

    /** 获取 TextView */
    public TextView getLeftTextView() {
        return tvLeft;
    }

    public TextView getTitleTextView() {
        return tvTitle;
    }

    public TextView getRightTextView() {
        return tvRight;
    }

    public ImageView getLeftImageView() {
        return ivLeft;
    }

    public ImageView getRightImageView() {
        return ivRight;
    }

    public OnTitleBarClickListener getOnTitleBarClickListener() {
        return onTitleBarClickListener;
    }

    /** 为标题栏添加监听器 */
    public void setOnTitleBarClickListener(OnTitleBarClickListener onTitleBarClickListener) {
        this.onTitleBarClickListener = onTitleBarClickListener;
    }

    /** sp2px */
    public float sp2px(float sp){
        final float scale = getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_bar_linear1: // 点击左边的 布局按钮
                Log.e("leftView", "判断左布局");
                if (onTitleBarClickListener != null){
                    onTitleBarClickListener.onLeftLinearClick(linearLeft, ivLeft);
                }
                break;

            case R.id.title_bar_linear2: // 点击右边的 布局按钮
                if (onTitleBarClickListener != null){
                    onTitleBarClickListener.onRightLinearClick(linearRight, ivRight);
                }
                break;

            case R.id.title_bar_text1: // 点击左边的 TextView
                Log.e("leftView", "判断左文件");
                if (onTitleBarClickListener != null){
                    onTitleBarClickListener.onLeftTextClick(tvLeft);
                }
                break;

            case R.id.title_bar_text3:// 点击右边的 TextView
                if (onTitleBarClickListener != null){
                    onTitleBarClickListener.onRightTextClick(tvRight);
                }
                break;
        }
    }

    /** 标题栏 点击事件监听 */
    public interface OnTitleBarClickListener{
        /**
         * 点击 左 边的 布局 按钮
         * @param linearLeft 被点击的 LinearLayout
         * @param imageLeft LinearLayout 中显示的图片
         */
        void onLeftLinearClick(LinearLayout linearLeft, ImageView imageLeft);

        /**
         * 点击 右 边的 布局 按钮
         * @param linearRight 被点击的 LinearLayout
         * @param imageRight LinearLayout 中显示的图片
         */
        void onRightLinearClick(LinearLayout linearRight, ImageView imageRight);

        /**
         * 点击 左 边的 TextView
         * @param tvLeft 被点击的 TextView
         */
        void onLeftTextClick(TextView tvLeft);

        /**
         * 点击 右 边的 TextView
         * @param tvRight 被点击的 TextView
         */
        void onRightTextClick(TextView tvRight);
    }
}

