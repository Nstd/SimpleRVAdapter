package com.github.nstd.simple.rvadapter;

import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nstd on 2017/12/21.
 */

public class SimpleRVHolderFactory<D> implements RecyclerViewHolderFactory<D> {

    protected Map<Integer, SimpleHolderReflector.ItemViewTypeInfo> mMap = new HashMap<>();
    private ViewTypeClassifier<D> typeClassifier = null;
    protected ConfigListener mConfigListener;

    public interface ViewTypeClassifier<D> {
        int getItemViewType(D data, int position);
    }

    public SimpleRVHolderFactory() {}

    public SimpleRVHolderFactory(Class clazz) {
        this(Constants.DEFAULT_VIEW_TYPE, clazz);
    }

    /**
     * 这里的 viewType 是以 {@link SimpleHolderLayout#viewType} 为准
     * @param classes
     */
    public SimpleRVHolderFactory(Class... classes) {
        if(classes != null) {
            for(Class clz : classes) {
                addViewTypeInfo(Constants.DEFAULT_VIEW_TYPE, clz);
            }
        }
    }

    /**
     * 如果layout是在library中定义的，使用 {@link SimpleHolderLayout} 注解的时候，无法使用 R.class，所以通过这种方式注入
     * 如果在主APP中，就直接使用 {@link SimpleHolderLayout} 方式
     * @param clazz
     * @param layoutResId
     */
    public SimpleRVHolderFactory(Class clazz, int layoutResId) {
        this(Constants.DEFAULT_VIEW_TYPE, clazz, layoutResId);
    }

    /**
     * 指定holder对应的viewType，这里的viewType优先级要高于 {@link SimpleHolderLayout#viewType} 中设置的值
     * @param viewType
     * @param clazz
     */
    public SimpleRVHolderFactory(int viewType, Class clazz) {
        this(viewType, clazz, null, null);
    }

    public SimpleRVHolderFactory(int viewType, Class clazz, int layoutResId) {
        this(viewType, clazz, layoutResId, null, null);
    }

    /**
     * 生成holder的时候，如果holder是一个非静态内部类，需要持有外部类的对象
     * 当 {@link SimpleHolderReflector.ItemViewTypeInfo#outerObject} 的 class
     * 和 {@link SimpleHolderReflector.ItemViewTypeInfo#outerClazz} 不一致时，需要分别指定
     * @param clazz
     * @param outerClazz
     * @param outerObject
     */
    public SimpleRVHolderFactory(Class clazz, Class outerClazz, Object outerObject) {
        this(Constants.DEFAULT_VIEW_TYPE, clazz, outerClazz, outerObject);
    }

    public SimpleRVHolderFactory(Class clazz, int layoutResId, Class outerClazz, Object outerObject) {
        this(Constants.DEFAULT_VIEW_TYPE, clazz, layoutResId, outerClazz, outerObject);
    }

    public SimpleRVHolderFactory(Class clazz, Object outerObject) {
        this(Constants.DEFAULT_VIEW_TYPE, clazz, outerObject.getClass(), outerObject);
    }

    public SimpleRVHolderFactory(Class clazz, int layoutResId, Object outerObject) {
        this(Constants.DEFAULT_VIEW_TYPE, clazz, layoutResId, outerObject.getClass(), outerObject);
    }

    /**
     * 生成holder的时候，如果holder是一个非静态内部类，需要持有外部类的对象
     * 当 {@link SimpleHolderReflector.ItemViewTypeInfo#outerObject} 的 class
     * 和 {@link SimpleHolderReflector.ItemViewTypeInfo#outerClazz} 不一致时，需要分别指定
     * @param viewType
     * @param clazz
     * @param outerClazz
     * @param outerObject
     */
    public SimpleRVHolderFactory(int viewType, Class clazz, Class outerClazz, Object outerObject) {
        addViewTypeInfo(viewType, clazz, outerClazz, outerObject);
    }

    public SimpleRVHolderFactory(int viewType, Class clazz, int layoutResId, Class outerClazz, Object outerObject) {
        addViewTypeInfo(viewType, clazz, layoutResId, outerClazz, outerObject);
    }

    public SimpleRVHolderFactory addViewTypeInfo(Class clazz) {
        return addViewTypeInfo(Constants.DEFAULT_VIEW_TYPE, clazz);
    }

    public SimpleRVHolderFactory addViewTypeInfo(int viewType, Class clazz) {
        return addViewTypeInfo(viewType, clazz, null, null);
    }

    public SimpleRVHolderFactory addViewTypeInfo(int viewType, Class clazz, int layoutResId) {
        return addViewTypeInfo(viewType, clazz, layoutResId, null, null);
    }

    public SimpleRVHolderFactory addViewTypeInfo(int viewType, Class clazz, Object outerObject) {
        return addViewTypeInfo(viewType, clazz, outerObject.getClass(), outerObject);
    }

    public SimpleRVHolderFactory addViewTypeInfo(int viewType, Class clazz, int layoutResId, Object outerObject) {
        return addViewTypeInfo(viewType, clazz, layoutResId, outerObject.getClass(), outerObject);
    }

    /**
     * 生成holder的时候，如果holder是一个非静态内部类，需要持有外部类的对象
     * 当 {@link SimpleHolderReflector.ItemViewTypeInfo#outerObject} 的 class
     * 和 {@link SimpleHolderReflector.ItemViewTypeInfo#outerClazz} 不一致时，需要分别指定
     * @param viewType
     * @param clazz
     * @param outerClazz
     * @param outerObject
     * @return
     */
    public SimpleRVHolderFactory addViewTypeInfo(int viewType, Class clazz, Class outerClazz, Object outerObject) {
        return addViewTypeInfo(viewType, clazz, Constants.INVALIDATE_LAYOUT_ID, outerClazz, outerObject);
    }

    public SimpleRVHolderFactory addViewTypeInfo(int viewType, Class clazz, int layoutResId, Class outerClazz, Object outerObject) {
        SimpleHolderReflector.LayoutInfo layoutInfo = SimpleHolderReflector.getLayoutResId(clazz, layoutResId);
        SimpleHolderReflector.ItemViewTypeInfo holder = new SimpleHolderReflector.ItemViewTypeInfo(viewType, layoutInfo, clazz, outerClazz, outerObject);
        mMap.put(holder.viewType, holder);
        return this;
    }

    /**
     * 设置viewType分类器
     * @param typeGetter
     * @return
     */
    public SimpleRVHolderFactory setViewTypeGetter(ViewTypeClassifier typeGetter) {
        this.typeClassifier = typeGetter;
        return this;
    }

    /**
     * 设置holder数据透传接口
     * @param listener
     * @return
     */
    public SimpleRVHolderFactory setConfigListener(ConfigListener listener) {
        this.mConfigListener = listener;
        return this;
    }

    @Override
    public SimpleRecyclerViewHolder create(ViewGroup parent, int viewType) {
        SimpleHolderReflector.ItemViewTypeInfo holder = mMap.get(viewType);
        if(holder == null) {
            throw new RuntimeException("can't find holder with viewType:" + viewType);
        }
        return SimpleHolderReflector.getHolder(holder, parent, mConfigListener);
    }

    @Override
    public int getItemViewType(D data, int position) {
        if(typeClassifier != null) {
            return typeClassifier.getItemViewType(data, position);
        } else if(data instanceof ViewTypeListener) {
            return ((ViewTypeListener) data).getViewType();
        }
        return Constants.DEFAULT_VIEW_TYPE;
    }
}
