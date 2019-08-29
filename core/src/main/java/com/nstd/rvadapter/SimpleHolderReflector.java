package com.nstd.rvadapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nstd.rvadapter.Constants;
import com.nstd.rvadapter.inflater.IHolderInflater;
import com.nstd.rvadapter.inflater.InflateResult;
import com.nstd.rvadapter.inflater.NormalHolderInflaterImpl;
import com.nstd.tools.LogUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/********************************
 * @author Nstd
 * @Description:
 * @date 2019/4/24 9:58 AM
 ********************************/
public class SimpleHolderReflector {

    private static List<IHolderInflater> inflaters = new ArrayList<>();

    static {
        //Dynamic inject databinding holder inflater
        try {
            Class bindingInflater = Class.forName("com.nstd.rvadapter.inflater.BindingHolderInflaterImpl");
            IHolderInflater bindingListener = (IHolderInflater) bindingInflater.newInstance();
            inflaters.add(bindingListener);
        } catch (ClassNotFoundException fe) {
            LogUtils.i("BindingHolderInflater not found");
        } catch (Exception e) {
            LogUtils.e(e);
        }

        inflaters.add(new NormalHolderInflaterImpl());
    }

    public static SimpleRVHolder getHolder(ItemViewTypeInfo holder,
                                           ViewGroup parent,
                                           ConfigListener configListener) {
        Object targetObject = null;
        Class<?> targetClass = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        boolean handled = false;
        for(IHolderInflater holderInflater : inflaters) {
            if(holderInflater.canHandle(holder.holderClazz)) {
                InflateResult result = holderInflater.get(inflater, holder.layoutInfo.layoutResId, parent);
                targetObject = result.targetObject;
                targetClass = result.targetClass;
                handled = true;
                break;
            }
        }

        if(!handled) {
            LogUtils.w("Can't find holder inflater for: " + holder.holderClazz.getSimpleName());
        }

        try {
            Constructor c;
            SimpleRVHolder targetHolder = null;

            if(holder.holderClazz.isMemberClass()) {
                int modifiers = holder.holderClazz.getModifiers();
                if(!Modifier.isPublic(modifiers)) {
                    throw new RuntimeException("Inner class need to be public!!!");
                }
                if(!Modifier.isStatic(modifiers)) {
                    if(holder.outerClazz == null || holder.outerObject == null) {
                        throw new RuntimeException("Inner none-static class need Outer class instance to init!!!");
                    }

                    c = holder.holderClazz.getConstructor(new Class<?>[]{holder.outerClazz, targetClass});
                    targetHolder = (SimpleRVHolder) (c.newInstance(holder.outerObject, targetObject));
                } else {
                    c = holder.holderClazz.getConstructor(new Class<?>[]{targetClass});
                    targetHolder = (SimpleRVHolder) (c.newInstance(targetObject));
                }
            } else {
                c = holder.holderClazz.getConstructor(new Class<?>[]{targetClass});
                targetHolder = (SimpleRVHolder) (c.newInstance(targetObject));
            }

            if(configListener != null) {
                targetHolder.setConfig(configListener);
            }

            LogUtils.d("create holder: " + holder.holderClazz.getSimpleName());

            return targetHolder;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static LayoutInfo getLayoutResId(Class clazz) {
        return getLayoutResId(clazz, Constants.INVALIDATE_LAYOUT_ID);
    }

    public static LayoutInfo getLayoutResId(Class clazz, int layoutResId) {
        LayoutInfo info = new LayoutInfo();
        if(layoutResId != Constants.INVALIDATE_LAYOUT_ID) {
            info.layoutResId = layoutResId;
        } else {
            while(clazz.getAnnotation(SimpleHolderLayout.class) == null && clazz != SimpleRVHolder.class) {
                clazz = clazz.getSuperclass();
            }
            SimpleHolderLayout annotation = (SimpleHolderLayout) clazz.getAnnotation(SimpleHolderLayout.class);
            info.layoutResId = annotation == null ? Constants.INVALIDATE_LAYOUT_ID : annotation.value();
            info.viewType = annotation == null ? Constants.DEFAULT_VIEW_TYPE : annotation.viewType();
        }
        info.isDataBinding = !SimpleRVHolder.class.isAssignableFrom(clazz);
        return info;
    }

    public static ItemViewTypeInfo getTypeHolder(int viewType, Class holderClazz) {
        LayoutInfo info = getLayoutResId(holderClazz);
        ItemViewTypeInfo typeHolder = new ItemViewTypeInfo(info, holderClazz);
        typeHolder.viewType = viewType;
        return typeHolder;
    }

    public static class ItemViewTypeInfo {
        int viewType;           // itemView 类型
        LayoutInfo layoutInfo;
        Class holderClazz;      // itemView Holder class
        Class outerClazz;       // 如果 itemView Holder 是个内部类，这个是其外部类class
        Object outerObject;     // 如果 itemView Holder 是个内部类，这个是其外部类的实例

        public ItemViewTypeInfo(LayoutInfo layoutInfo, Class holderClazz) {
            this.layoutInfo = layoutInfo;
            this.holderClazz = holderClazz;
        }

        public ItemViewTypeInfo(int viewType, LayoutInfo layoutInfo, Class holderClazz, Class outerClazz, Object outerObject) {
            this(layoutInfo, holderClazz);
            this.viewType = viewType == Constants.DEFAULT_VIEW_TYPE ? layoutInfo.viewType : viewType;
            this.outerClazz = outerClazz;
            this.outerObject = outerObject;
        }
    }

    public static class LayoutInfo {
        int layoutResId;        // itemView 布局资源ID
        boolean isDataBinding;
        int viewType = Constants.DEFAULT_VIEW_TYPE;
    }
}
