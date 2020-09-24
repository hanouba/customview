package com.hansen.mzbanner.holder;

/**
 * @author HanN on 2020/9/23 11:32
 * @email: 1356548475@qq.com
 * @project customview
 * @description:
 * @updateuser:
 * @updatedata: 2020/9/23 11:32
 * @updateremark:
 * @version: 2.1.67
 */
public interface MZHolderCreator<VH extends MZViewHolder> {
    /**
     * 创建ViewHolder
     * @return
     */
    public VH createViewHolder();
}
