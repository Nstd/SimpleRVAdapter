package com.github.nstd.simple.rvadapter;

import android.support.annotation.LayoutRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SimpleHolderLayout {

    /**
     * holder 对应的 layoutId
     * @return
     */
    @LayoutRes int value();

    /**
     * holder 对应的 viewType
     * @return
     */
    int viewType() default Constants.DEFAULT_VIEW_TYPE;
}
