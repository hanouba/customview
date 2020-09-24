package com.hansen.mzbanner.holder;

import android.content.Context;
import android.view.View;

/**
 * @author HanN on 2020/9/23 11:31
 * @email: 1356548475@qq.com
 * @project customview
 * @description:
 * @updateuser:
 * @updatedata: 2020/9/23 11:31
 * @updateremark:
 * @version: 2.1.67
 */
public interface MZViewHolder<T> {
    /**
     *  创建View
     * @param context
     * @return
     */
    View createView(Context context);

    /**
     * 绑定数据
     * @param context
     * @param position
     * @param data
     */
    void onBind(Context context, int position, T data);
}
