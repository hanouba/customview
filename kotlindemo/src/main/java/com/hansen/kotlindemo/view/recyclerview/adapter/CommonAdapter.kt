package com.hansen.kotlindemo.view.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.hansen.kotlindemo.view.recyclerview.MultipleType
import com.hansen.kotlindemo.view.recyclerview.ViewHolder
import com.hazz.kotlinmvp.view.recyclerview.adapter.OnItemClickListener
import com.hazz.kotlinmvp.view.recyclerview.adapter.OnItemLongClickListener

/**
 *@author HanN on 2020/9/22 15:51
 *@email: 1356548475@qq.com
 *@project customview
 *@description:
 *@updateuser:
 *@updatedata: 2020/9/22 15:51
 *@updateremark:
 *@version:  2.1.67
 */
public abstract class CommonAdapter<T>(var mContext: Context,var mData: ArrayList<T>,
private var mLayoutId: Int) : RecyclerView.Adapter<ViewHolder>() {
    protected var mInflater: LayoutInflater? = null
    private var mTypeSupport: MultipleType<T>? = null

    private var mItemClickListener: OnItemClickListener? = null
    private var mItemLongClickListener: OnItemLongClickListener? = null
    init {
        mInflater = LayoutInflater.from(mContext)
    }

    constructor(context: Context, data: ArrayList<T>,typeSupport: MultipleType<T>)
            : this(context,data,-1) {
        this.mTypeSupport = typeSupport
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (mTypeSupport != null) {
            mLayoutId = viewType
        }

        val view = mInflater?.inflate(mLayoutId,parent,false)
        return ViewHolder(view!!)
    }

    override fun getItemViewType(position: Int): Int {
        return mTypeSupport?.getLayoutId(mData[position],position) ?: super.getItemViewType(position)

    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bindData(holder, mData[position], position)

        mItemClickListener?.let {
            holder.itemView.setOnClickListener { mItemClickListener!!.onItemClick(mData[position], position) }
        }

        //长按点击事件
        mItemLongClickListener?.let {
            holder.itemView.setOnLongClickListener { mItemLongClickListener!!.onItemLongClick(mData[position], position) }
        }
    }

    /**
     * 将必要参数传递出去
     *
     * @param holder
     * @param data
     * @param position
     */
    protected abstract fun bindData(holder: ViewHolder, data: T, position: Int)

}


