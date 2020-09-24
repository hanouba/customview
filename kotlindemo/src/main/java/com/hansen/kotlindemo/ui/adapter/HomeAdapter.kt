package com.hansen.kotlindemo.ui.adapter

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hansen.kotlindemo.R
import com.hansen.kotlindemo.durationFormat
import com.hansen.kotlindemo.mvp.model.bean.HomeBean
import com.hansen.kotlindemo.view.recyclerview.ViewHolder
import com.hansen.kotlindemo.view.recyclerview.adapter.CommonAdapter
import io.reactivex.Observable


/**
 * @author HanN on 2020/9/23 10:28
 * @email: 1356548475@qq.com
 * @project customview
 * @description:
 * @updateuser:
 * @updatedata: 2020/9/23 10:28
 * @updateremark:
 * @version: 2.1.67
 */
class HomeAdapter(context: Context, data: ArrayList<HomeBean.Issue.Item>) : CommonAdapter<HomeBean.Issue.Item>(context, data, -1) {

    // banner 作为 RecyclerView 的第一项
    var bannerItemSize = 0

    companion object {
        private const val ITEM_TYPE_BANNER = 1 ///Banner 类型
        private const val ITEM_TYPE_TEXT_HEADER = 2 //textHeader
        private val ITEM_TYPE_CONTENT = 3   //item
    }

    /**
     * 设置 Banner 大小
     */
    fun setBannerSize(count: Int) {
        bannerItemSize = count
    }

    /**
     * 添加更多数据
     */
    fun addItemData(itemList: ArrayList<HomeBean.Issue.Item>) {
        this.mData.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 ->
                ITEM_TYPE_BANNER
            //第一个是banner
            mData[position + bannerItemSize - 1].type == "textHeader" ->
                ITEM_TYPE_TEXT_HEADER
            //不好理解
            else ->
                ITEM_TYPE_CONTENT
        }
    }

    override fun getItemCount(): Int {
        return when {

            //为什么
            mData.size > bannerItemSize -> mData.size - bannerItemSize + 1
            mData.isEmpty() -> 0
            else -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ITEM_TYPE_BANNER ->
                ViewHolder(inflaterView(R.layout.item_home_banner, parent))
            ITEM_TYPE_TEXT_HEADER ->
                ViewHolder(inflaterView(R.layout.item_home_header, parent))
            else ->
                ViewHolder(inflaterView(R.layout.item_home_content, parent))
        }
    }



    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {
        when (getItemViewType(position)) {
            ITEM_TYPE_BANNER -> {
                val bannerItemData: ArrayList<HomeBean.Issue.Item> = mData.take(bannerItemSize).toCollection(ArrayList())
                val bannerFeedList = ArrayList<String>()
                val bannerTitleList = ArrayList<String>()
                //取出banner 显示的 img 和 Title
                Observable.fromIterable(bannerItemData)
                        .subscribe{ list ->
                            bannerFeedList.add(list.data?.cover?.feed?:"")
                            bannerTitleList.add(list.data?.title?: "")

                        }

                //设置banner
//                with(holder) {
//                   getView<MZBannerView<Objects>>(R.id.banner).run {
//                        setDelayedTime(3000)
//                        setdata
//                   }
//                }
            }
            ITEM_TYPE_TEXT_HEADER -> {
                holder.setText(R.id.tvHeader,mData[position + bannerItemSize - 1].data?.text?:"")

            }
            ITEM_TYPE_CONTENT -> {
                setVideoItem(holder,mData[position + bannerItemSize - 1])
            }
        }
    }

    private fun inflaterView(mLayoutId: Int, parent: ViewGroup): View {
        val view = mInflater?.inflate(mLayoutId, parent, false)
        return view ?: View(parent.context)
    }

    private fun setVideoItem(holder: ViewHolder,item: HomeBean.Issue.Item) {
        val itemData = item.data
        //默认背景图
        val defAvatar = R.mipmap.default_avatar
        //地址
        var cover = itemData?.cover?.feed
        //图标
        var avatar = itemData?.author?.icon
        //
        var  tagText: String?="#"

        if (avatar.isNullOrEmpty()) {
            avatar = itemData?.provider?.icon

        }
        //加载图片
        if (avatar.isNullOrEmpty()) {

        Glide.with(mContext)
                .load(cover)
                .placeholder(R.mipmap.placeholder_banner)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.getView(R.id.iv_cover_feed))

        }else{
            //加载默认图片
            Glide.with(mContext)
                    .load(cover)
                    .placeholder(R.mipmap.default_avatar).circleCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(holder.getView(R.id.iv_cover_feed))
        }

        holder.setText(R.id.tv_title,itemData?.title?: "")
        //遍历标签
        //包含前四个数据遍历
        itemData?.tags?.take(4)?.forEach{
            tagText += (it.name + "/")
        }
        //格式化时间
        val timeFormatter = durationFormat(itemData?.duration)
        tagText += timeFormatter

        holder.setText(R.id.tv_tag,tagText!!)

        holder.setText(R.id.tv_category,"#" + itemData?.category)
        holder.setOnItemClickListener(listener = View.OnClickListener {
            gotoVideoPlayer(mContext as Activity,holder.getView(R.id.iv_cover_feed),item)
        })

    }

    private fun gotoVideoPlayer(actvity: Activity, view: View, itemData: HomeBean.Issue.Item) {
        Toast.makeText(actvity,"跳转到播放界面",Toast.LENGTH_SHORT).show()
    }


}