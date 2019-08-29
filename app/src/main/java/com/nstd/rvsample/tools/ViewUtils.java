package com.nstd.rvsample.tools;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.nstd.tools.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * View视图工具类
 * 1：dp、sp、px的转换；
 * 2：获取屏幕的属性参数，设置全屏；
 * 3：view的操作方法，获取view的大小等；
 * 4：生成二维码。
 */
public class ViewUtils {
    private static final String TAG = "ViewUtils";

    /*******************dp、sp、px的转换*********************/

    private static Float displayDensity;
    private static Float scaledDensity;

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param paramContext
     * @param paramFloat
     * @return
     */
    public static int dipToPx(Context paramContext, float paramFloat) {
        return roundUp(getDisplayDensity(paramContext) * paramFloat);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param paramContext
     * @param paramFloat
     * @return
     */
    public static int pxToDip(Context paramContext, float paramFloat) {
        return roundUp(paramFloat / getDisplayDensity(paramContext));
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param paramContext
     * @param paramFloat   （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context paramContext, float paramFloat) {
        return roundUp(getScaledDensity(paramContext) * paramFloat);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param paramContext
     * @param paramFloat   （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context paramContext, float paramFloat) {
        return roundUp(paramFloat / getScaledDensity(paramContext));
    }

    /**
     * 向上取整，+0.5F 是为了四舍五入
     * @param paramFloat
     * @return
     */
    public static int roundUp(float paramFloat) {
        return (int) (0.5F + paramFloat);
    }

    /*******************屏幕相关的辅助方法*********************/


    /**
     * 获取屏幕密度
     * @param paramContext
     * @return
     */
    public static float getDisplayDensity(Context paramContext) {
        if (displayDensity == null)
            displayDensity = Float.valueOf(paramContext.getResources().getDisplayMetrics().density);
        return displayDensity.floatValue();
    }

    /**
     * 获取字体缩放比例
     * @param paramContext
     * @return
     */
    private static float getScaledDensity(Context paramContext) {
        if (scaledDensity == null)
            scaledDensity = Float.valueOf(paramContext.getResources().getDisplayMetrics().scaledDensity);
        return scaledDensity.floatValue();
    }

    /**
     * 获取屏幕高度
     * @param paramContext
     * @return
     */
    public static int getDisplayHeight(Context paramContext) {
        return paramContext.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取屏幕宽度
     * @param paramContext
     * @return
     */
    public static int getDisplayWidth(Context paramContext) {
        return paramContext.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 按比例获取屏幕宽度
     * @param context
     * @param percent 0 ~ 1.0
     * @return
     */
    public static int getDisplayWidthByPercent(Context context, float percent) {
        return (int) (getDisplayWidth(context) * percent);
    }

    /**
     * 动态设置activity全屏
     *
     * @param activity
     */
    public static void setActivityFullScene(Activity activity) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(params);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * 动态设置activity非全屏状态
     *
     * @param activity
     */
    public static void setActivityNoFullScene(Activity activity) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.getWindow().setAttributes(params);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

    }

    /**
     * 动态设置activity全屏
     *
     * @param activity
     */
    public static void setActivityFullScene(Activity activity, boolean rule) {
        if (rule)
            setActivityFullScene(activity);
        else
            setActivityNoFullScene(activity);
    }

    /**
     * 设置屏幕是否可以旋转
     *
     * @param activity
     */
    public static void setActivitySceneRule(Activity activity, int rule) {
        if (activity.getRequestedOrientation() != rule)
            activity.setRequestedOrientation(rule);
    }

    /*******************View相关操作方法*********************/

    public static View getContentView(Activity paramActivity) {
        return ((ViewGroup) paramActivity.findViewById(Window.ID_ANDROID_CONTENT)).getChildAt(0);
    }

    public static void setViewMargins(View paramView, int leftMargin, int topMargin, int rightMargin, int bottomMargin) {
        ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams) paramView.getLayoutParams();
        localMarginLayoutParams.leftMargin = leftMargin;
        localMarginLayoutParams.topMargin = topMargin;
        localMarginLayoutParams.rightMargin = rightMargin;
        localMarginLayoutParams.bottomMargin = bottomMargin;
        paramView.setLayoutParams(localMarginLayoutParams);
    }

    public static void setViewMarginsInDp(View view, int marginInDp) {
        ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int value = dipToPx(view.getContext(), marginInDp);
        localMarginLayoutParams.leftMargin = value;
        localMarginLayoutParams.topMargin = value;
        localMarginLayoutParams.rightMargin = value;
        localMarginLayoutParams.bottomMargin = value;
        view.setLayoutParams(localMarginLayoutParams);
    }

    public static void setViewPaddingInDp(View view, int paddingInDp) {
        int value = dipToPx(view.getContext(), paddingInDp);
        view.setPadding(value, value, value, value);
    }


    /**
     * 计算textview的高度
     * @param activity
     * @param text
     * @param textSize
     * @param deviceWidth
     * @return
     */
    public static int measureTextViewHeight(Activity activity, String text, int textSize, int deviceWidth) {
        TextView textView = new TextView(activity);
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(deviceWidth, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        return textView.getMeasuredHeight();
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 设置带圆弧边框的背景色
     *
     * @param context           上下文
     * @param fillColorResId    除边框外的填充色（颜色resId）
     * @param strokeColorResId  边框的颜色（颜色resId）
     * @param strokeWidthInPx   边框的宽度（单位px）
     * @param radiusInPx        圆弧的半径（单位px）
     * @return
     */
    public static GradientDrawable getLabelShape(Context context,
                                                 int fillColorResId,
                                                 int strokeColorResId,
                                                 int strokeWidthInPx,
                                                 int radiusInPx) {
        return getLabelShapeByColor(context.getResources().getColor(fillColorResId),
                context.getResources().getColor(strokeColorResId),
                strokeWidthInPx,
                radiusInPx);
    }

    /**
     * 设置带圆弧边框的背景色
     *
     * @param context           上下文
     * @param fillColorResId    除边框外的填充色（颜色resId）
     * @param strokeColorResId  边框的颜色（颜色resId）
     * @param strokeWidthInDp   边框的宽度（单位dp）
     * @param radiusInDp        圆弧的半径（单位dp）
     * @return
     */
    public static GradientDrawable getLabelShapeByDp(Context context,
                                                     int fillColorResId,
                                                     int strokeColorResId,
                                                     float strokeWidthInDp,
                                                     float radiusInDp) {
        int strokeWidth = ViewUtils.dipToPx(context, strokeWidthInDp);
        int radius = ViewUtils.dipToPx(context, radiusInDp);
        return getLabelShapeByColor(context.getResources().getColor(fillColorResId),
                context.getResources().getColor(strokeColorResId),
                strokeWidth,
                radius);
    }

    /**
     * 设置带圆弧边框的背景色
     *
     * @param context           上下文
     * @param fillColor         除边框外的填充色（颜色值）
     * @param strokeColor       边框的颜色（颜色值）
     * @param strokeWidthInDp   边框的宽度（单位dp）
     * @param radiusInDp        圆弧的半径（单位dp）
     * @return
     */
    public static GradientDrawable getLabelShapeByColorAndDp(Context context,
                                                             int fillColor,
                                                             int strokeColor,
                                                             float strokeWidthInDp,
                                                             float radiusInDp) {
        int strokeWidth = ViewUtils.dipToPx(context, strokeWidthInDp);
        int radius = ViewUtils.dipToPx(context, radiusInDp);
        return getLabelShapeByColor(fillColor, strokeColor, strokeWidth, radius);
    }

    /**
     * 设置带圆弧边框的背景色
     *
     * @param fillColor         除边框外的填充色（颜色值）
     * @param strokeColor       边框的颜色（颜色值）
     * @param strokeWidthInPx   边框的宽度（单位px）
     * @param radiusInPx        圆弧的半径（单位px）
     * @return
     */
    public static GradientDrawable getLabelShapeByColor(int fillColor,
                                                        int strokeColor,
                                                        int strokeWidthInPx,
                                                        int radiusInPx) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(fillColor);
        if(strokeWidthInPx > 0) {
            gradientDrawable.setStroke(strokeWidthInPx, strokeColor);
        }
        gradientDrawable.setCornerRadius(radiusInPx);
        return gradientDrawable;
    }

    /**
     * 设置带圆弧边框的背景色
     *
     * @param context          上下文
     * @param fillColor        除边框外的填充色
     * @param strokeColor      边框的颜色
     * @param strokeWidthforPx 边框的宽度
     * @param radius           圆弧的半径
     * @return
     */
    @Deprecated
    public static GradientDrawable getLabelShape(Context context, int fillColor, int strokeColor, int strokeWidthforPx, int radius, boolean hasContext) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        if (hasContext){
            gradientDrawable.setColor(context.getResources().getColor(fillColor));
            gradientDrawable.setStroke(strokeWidthforPx, context.getResources().getColor(strokeColor));
        }else{
            gradientDrawable.setColor(fillColor);
            gradientDrawable.setStroke(strokeWidthforPx, strokeColor);
        }
        gradientDrawable.setCornerRadius(radius);
        return gradientDrawable;
    }

    public static class StateListImageDrawableBuilder {

        private List<Integer> mDrawableResIds = new ArrayList<>();
        private List<int[]> mState = new ArrayList<>();

        public StateListImageDrawableBuilder(){}

        public StateListImageDrawableBuilder addState(int imgResId, int... state) {
            mDrawableResIds.add(imgResId);
            if(state == null) {
                state = new int[]{};
            }
            mState.add(state);
            return this;
        }

        public Drawable build(Context context) {
            StateListDrawable drawable = new StateListDrawable();
            for(int i = 0; i < mState.size(); i++) {
                drawable.addState(mState.get(i), context.getResources().getDrawable(mDrawableResIds.get(i)));
            }
            return drawable;
        }
    }


    public static class StateListDrawableBuilder {
        private boolean mIsCornerSet = false;
        private float[] mCorners = {0, 0, 0, 0};
        private int mStrokeWidth = 0;
        private List<Integer> mBgColor = new ArrayList<>();
        private List<Integer> mStrokeColor = new ArrayList<>();
        private List<int[]> mState = new ArrayList<>();
        private boolean animateOnChange = true;
        private boolean isStateColorResId = true;

        public StateListDrawableBuilder(){}

        /**
         * 设置四个圆角（单位：px）
         * @param radius
         * @return
         */
        public StateListDrawableBuilder setCorner(float radius) {
            for(int i = 0; i < mCorners.length; i++) {
                mCorners[i] = radius;
            }
            mIsCornerSet = true;
            return this;
        }

        /**
         * 设置四个圆角（单位：dp）
         * @param radiusInDp
         * @return
         */
        public StateListDrawableBuilder setCornerInDp(float radiusInDp) {
            float radius = ViewUtils.dipToPx(ToolManager.mContext, radiusInDp);
            return setCorner(radius);
        }

        /**
         * 分别设置四个圆角（单位：px）
         * @param topLeft
         * @param topRight
         * @param bottomRight
         * @param bottomLeft
         * @return
         */
        public StateListDrawableBuilder setCorner(float topLeft, float topRight, float bottomRight, float bottomLeft) {
            mCorners = new float[]{topLeft, topRight, bottomRight, bottomLeft};
            mIsCornerSet = true;
            return this;
        }

        /**
         * 分别设置四个圆角（单位：dp）
         * @param topLeftInDp
         * @param topRightInDp
         * @param bottomRightInDp
         * @param bottomLeftInDp
         * @return
         */
        public StateListDrawableBuilder setCornerInDp(float topLeftInDp, float topRightInDp, float bottomRightInDp, float bottomLeftInDp) {
            float topLeft = ViewUtils.dipToPx(ToolManager.mContext, topLeftInDp);
            float topRight = ViewUtils.dipToPx(ToolManager.mContext, topRightInDp);
            float bottomRight = ViewUtils.dipToPx(ToolManager.mContext, bottomRightInDp);
            float bottomLeft = ViewUtils.dipToPx(ToolManager.mContext, bottomLeftInDp);
            return setCorner(topLeft, topRight, bottomRight, bottomLeft);
        }

        public StateListDrawableBuilder setStrokeWidth(int width) {
            this.mStrokeWidth = width;
            return this;
        }

        public StateListDrawableBuilder setStrokeWidthInDp(float widthInDp) {
            this.mStrokeWidth = ViewUtils.dipToPx(ToolManager.mContext, widthInDp);
            return this;
        }

        public StateListDrawableBuilder setAnimateOnChange(boolean isChange) {
            this.animateOnChange = isChange;
            return this;
        }

        /**
         *
         * @param bgColorResId background color resource id
         * @param strokeColorResId stroke color resource id
         * @param state eg: android.R.attr.state_pressed
         * @return
         */
        public StateListDrawableBuilder addState(int bgColorResId, int strokeColorResId, int... state) {
            if(state == null) {
                state = new int[]{};
            }
            mState.add(state);
            mBgColor.add(bgColorResId);
            mStrokeColor.add(strokeColorResId);
            return this;
        }

        public StateListDrawableBuilder addStateByColor(int bgColor, int strokeColor, int... state) {
            isStateColorResId = false;
            addState(bgColor, strokeColor, state);
            return this;
        }

        public StateListDrawable build(Context context) {
            StateListDrawable d = new StateListDrawable();
            Resources res = context.getResources();
            if(mBgColor.size() > 0) {
                for(int i = 0, len = mBgColor.size(); i < len; i++) {
                    GradientDrawable g = new GradientDrawable();
                    if(mIsCornerSet) {
                        g.setCornerRadii(new float[]{
                                mCorners[0], mCorners[0],
                                mCorners[1], mCorners[1],
                                mCorners[2], mCorners[2],
                                mCorners[3], mCorners[3],
                        });
                    }

                    if(isStateColorResId) {
                        g.setColor(res.getColor(mBgColor.get(i)));
                    } else {
                        g.setColor(mBgColor.get(i));
                    }

                    if(mStrokeWidth != 0) {
                        if(isStateColorResId) {
                            g.setStroke(mStrokeWidth, res.getColor(mStrokeColor.get(i)));
                        } else {
                            g.setStroke(mStrokeWidth, mStrokeColor.get(i));
                        }
                    }
                    d.addState(mState.get(i), g);
                }
                d.setExitFadeDuration(animateOnChange ? 400 : 0);
            } else {
                LogUtils.w(TAG, "StateListDrawableBuilder must set color!!!");
            }

            return d;
        }
    }

    public static class RoundDrawableBuilder {
        private int mBgColor;
        private int mStrokeWidth = 0;
        private int mStrokeColor;
        private float[] mCorners = {0, 0, 0, 0};
        private boolean isBgColorRes = true;
        private boolean isStrokeColorRes = true;


        public RoundDrawableBuilder(){}

        /**
         * 设置四个圆角（单位：px）
         * @param radius
         * @return
         */
        public RoundDrawableBuilder setCorner(float radius) {
            for(int i = 0; i < mCorners.length; i++) {
                mCorners[i] = radius;
            }
            return this;
        }

        /**
         * 设置四个圆角（单位：dp）
         * @param radiusInDp
         * @return
         */
        public RoundDrawableBuilder setCornerInDp(float radiusInDp) {
            float radius = ViewUtils.dipToPx(ToolManager.mContext, radiusInDp);
            return setCorner(radius);
        }

        /**
         * 分别设置四个圆角（单位：px）
         * @param topLeft
         * @param topRight
         * @param bottomRight
         * @param bottomLeft
         * @return
         */
        public RoundDrawableBuilder setCorner(float topLeft, float topRight, float bottomRight, float bottomLeft) {
            mCorners = new float[]{topLeft, topRight, bottomRight, bottomLeft};
            return this;
        }

        /**
         * 分别设置四个圆角（单位：dp）
         * @param topLeftInDp
         * @param topRightInDp
         * @param bottomRightInDp
         * @param bottomLeftInDp
         * @return
         */
        public RoundDrawableBuilder setCornerInDp(float topLeftInDp, float topRightInDp, float bottomRightInDp, float bottomLeftInDp) {
            float topLeft = ViewUtils.dipToPx(ToolManager.mContext, topLeftInDp);
            float topRight = ViewUtils.dipToPx(ToolManager.mContext, topRightInDp);
            float bottomRight = ViewUtils.dipToPx(ToolManager.mContext, bottomRightInDp);
            float bottomLeft = ViewUtils.dipToPx(ToolManager.mContext, bottomLeftInDp);
            return setCorner(topLeft, topRight, bottomRight, bottomLeft);
        }

        public RoundDrawableBuilder setStroke(int width, int colorResId) {
            mStrokeWidth = width;
            mStrokeColor = colorResId;
            return this;
        }

        public RoundDrawableBuilder setStrokeInDp(int widthInDp, int colorResId) {
            int width = ViewUtils.dipToPx(ToolManager.mContext, widthInDp);
            return setStroke(width, colorResId);
        }

        public RoundDrawableBuilder setStrokeByColor(int width, int color) {
            mStrokeWidth = width;
            mStrokeColor = color;
            isStrokeColorRes = false;
            return this;
        }

        public RoundDrawableBuilder setStrokeByDpAndColor(int widthInDp, int color) {
            int width = ViewUtils.dipToPx(ToolManager.mContext, widthInDp);
            return setStrokeByColor(width, color);
        }


        public RoundDrawableBuilder setBackgroundColor(int colorResId) {
            mBgColor = colorResId;
            isBgColorRes = true;
            return this;
        }

        public RoundDrawableBuilder setBackgroundColorByColor(int color) {
            mBgColor = color;
            isBgColorRes = false;
            return this;
        }

        public Drawable build(Context context) {
            GradientDrawable gd = new GradientDrawable();
            Resources res = context.getResources();
            gd.setColor(isBgColorRes ? res.getColor(mBgColor) : mBgColor);
            if(mStrokeWidth > 0) {
                gd.setStroke(mStrokeWidth, isStrokeColorRes ? res.getColor(mStrokeColor) : mStrokeColor);
            }
            gd.setCornerRadii(new float[]{
                    mCorners[0], mCorners[0],
                    mCorners[1], mCorners[1],
                    mCorners[2], mCorners[2],
                    mCorners[3], mCorners[3],
            });
            return gd;
        }
    }

    public static Drawable getRoundDrawable(Context context, int colorResId, int radius) {
        int color = context.getResources().getColor(colorResId);
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(color);
        gd.setCornerRadius(radius);
        return gd;
    }

    public static Drawable getRoundDrawableInDp(Context context, int colorResId, float topLeft, float topRight, float bottomRight, float bottomLeft) {
        int color = context.getResources().getColor(colorResId);
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(color);
        float topLeft_ = ViewUtils.dipToPx(context, topLeft);
        float topRight_ = ViewUtils.dipToPx(context, topRight);
        float bottomRight_ = ViewUtils.dipToPx(context, bottomRight);
        float bottomLeft_ = ViewUtils.dipToPx(context, bottomLeft);
        gd.setCornerRadii(new float[]{
                topLeft_, topLeft_,
                topRight_, topRight_,
                bottomRight_, bottomRight_,
                bottomLeft_, bottomLeft_,
        });
        return gd;
    }

    public static Drawable getRoundDrawableInDp(Context context, int colorResId, float radiusInDp) {
        int dpVal = ViewUtils.dipToPx(context, radiusInDp);
        return getRoundDrawable(context, colorResId, dpVal);
    }

    public static Drawable getGradientRoundDrawable(Context context, int startColorResId, int endColorResId, GradientDrawable.Orientation orientation, int radius) {
        if(context == null) {
            context = ToolManager.mContext;
        }
        int startColor = context.getResources().getColor(startColorResId);
        int endColor = context.getResources().getColor(endColorResId);
        GradientDrawable gd = new GradientDrawable();
        gd.setOrientation(orientation);
        gd.setCornerRadius(radius);
        gd.setColors(new int[]{startColor, endColor});
        return gd;
    }

    public static Drawable getGradientRoundDrawableInDp(Context context, int startColorResId, int endColorResId, GradientDrawable.Orientation orientation, float topLeft, float topRight, float bottomRight, float bottomLeft){
        if(context == null) {
            context = ToolManager.mContext;
        }
        int startColor = context.getResources().getColor(startColorResId);
        int endColor = context.getResources().getColor(endColorResId);
        GradientDrawable gd = new GradientDrawable();
        gd.setOrientation(orientation);
        float topLeft_ = ViewUtils.dipToPx(context, topLeft);
        float topRight_ = ViewUtils.dipToPx(context, topRight);
        float bottomRight_ = ViewUtils.dipToPx(context, bottomRight);
        float bottomLeft_ = ViewUtils.dipToPx(context, bottomLeft);
        gd.setCornerRadii(new float[]{
                topLeft_, topLeft_,
                topRight_, topRight_,
                bottomRight_, bottomRight_,
                bottomLeft_, bottomLeft_,
        });
        gd.setColors(new int[]{startColor, endColor});
        return gd;
    }

    public static Drawable getRoundDrawableByColor(Context context, int color, int radius) {
        return new RoundDrawableBuilder()
                .setBackgroundColorByColor(color)
                .setCorner(radius)
                .build(context);
    }

    public static void focusToEnd(final EditText et) {
        if(et == null) return;
        et.post(new Runnable() {
            @Override
            public void run() {
                String s = et.getText().toString();
                et.setSelection(s == null ? 0 : s.length());
                et.requestFocus();
            }
        });
    }

    public static void selectToEnd(final EditText et) {
        if(et == null) return ;
        et.post(new Runnable() {
            @Override
            public void run() {
                String s = et.getText().toString();
                et.setSelection(s == null ? 0 : s.length());
            }
        });
    }

    /**
     * 重新设置view的大小
     * 如果某个维度不想设置，直接传0
     *
     * @param view
     * @param widthInDp
     * @param heightInDp
     */
    public static void updateViewSize(View view, float widthInDp, float heightInDp) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if(widthInDp < 0) {
            lp.width = (int) widthInDp;
        } else if(widthInDp > 0) {
            lp.width = dipToPx(view.getContext(), widthInDp);
        }

        if(heightInDp < 0) {
            lp.height = (int) heightInDp;
        } else if(heightInDp > 0) {
            lp.height = dipToPx(view.getContext(), heightInDp);
        }

        view.setLayoutParams(lp);
    }
}
