package com.hansen.kotlindemo.ui.fragment

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log.d
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hansen.kotlindemo.R
import com.hansen.kotlindemo.base.BaseFragment
import com.hansen.kotlindemo.mvp.contract.HomeContract
import com.hansen.kotlindemo.mvp.model.bean.HomeBean
import com.hansen.kotlindemo.mvp.present.HomePresenter
import com.hansen.kotlindemo.showToast
import com.hansen.kotlindemo.ui.adapter.HomeAdapter
import com.hansen.kotlindemo.utils.StatusBarUtil
import com.hansen.utils.LogUtil.d
import com.hazz.kotlinmvp.net.exception.ErrorStatus
import com.scwang.smartrefresh.header.MaterialHeader
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import java.util.logging.Logger
import kotlin.math.log

/**
 *@author HanN on 2020/9/22 15:33
 *@email: 1356548475@qq.com
 *@project customview
 *@description:
 *@updateuser:
 *@updatedata: 2020/9/22 15:33
 *@updateremark:
 *@version:  2.1.67
 */
@RequiresApi(Build.VERSION_CODES.N)
class HomeFragment : BaseFragment(), HomeContract.View {
    //引入 presenter
    private val mPresenter by lazy { HomePresenter() }

    //title
    private var mTitle: String? = null

    //
    private var num: Int = 1
    private var mHomeAdapter: HomeAdapter? = null

    //加载更多
    private var loadingMore = false
    private var isRefresh = false
    private var mMateriaHeader: MaterialHeader? = null

    companion object {
        fun getInstance(title: String): HomeFragment {
            val fragment = HomeFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }
    // 初始化 linearLayoutManager
    /**
     * lateinit 和 lazy 是 Kotlin 中的两种不同的延迟初始化的实现
     * lazy 应用于单例模式(if-null-then-init-else-return)，
     * 而且当且仅当变量被第一次调用的时候，委托方法才会执行。
     */
    private val linearLayoutManager by lazy {
        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    }
    private val simpleDateFormat by lazy {
        SimpleDateFormat("- MMM. dd, 'Brunch' -", Locale.ENGLISH)
    }

    override fun setHomeData(homeBean: HomeBean) {
        //页面状态显示数据
        mLayoutStatusView?.showContent()

        //adapter赋值
        mHomeAdapter = activity?.let { HomeAdapter(it, homeBean.issueList[0].itemList) }
        mHomeAdapter?.setBannerSize(homeBean.issueList[0].count)
        //recycler
        mRecyclerView.adapter = mHomeAdapter
        mRecyclerView.layoutManager = linearLayoutManager
        mRecyclerView.itemAnimator = DefaultItemAnimator()
    }

    /**
     * 加载更多
     */
    override fun setMoreData(itemList: ArrayList<HomeBean.Issue.Item>) {
        loadingMore = false
        mHomeAdapter?.addItemData(itemList)
    }

    /**
     * 弹窗提示
     * 如果错误码是和网络有关 显示网络错误
     * 否则显示错误
     */
    override fun showError(msg: String, error: Int) {
        showToast(msg)
        if (error == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
        }
    }

    /**
     * 如果没有刷新 显示loading
     */
    override fun showLoading() {
        if (!isRefresh) {
            isRefresh = false
            mLayoutStatusView?.showLoading()
        }
    }

    override fun dismissLoading() {
        mRefreshLayout.finishRefresh()
    }

    override fun initView() {
        mPresenter.attachView(this)
        mRefreshLayout.setEnableHeaderTranslationContent(true)
        mRefreshLayout.setOnRefreshListener {
            isRefresh = true
            mPresenter.requestHomeData(num)
        }

        mMateriaHeader = mRefreshLayout.refreshHeader as MaterialHeader
        //打开下拉刷新区域背景
        mMateriaHeader?.setShowBezierWave(true)
        //设置下拉刷新主题颜色
        mRefreshLayout.setPrimaryColorsId(R.color.color_light_black, R.color.color_title_bg)

        //这段代码好好研究下
        /**
         * 滑动监听 怎么判断的加载更多
         */
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            // 滚动状态变化时回调
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                ////停止滚动
                //public static final int SCROLL_STATE_IDLE = 0;
                //
                ////正在被外部拖拽,一般为用户正在用手指滚动
                //public static final int SCROLL_STATE_DRAGGING = 1;
                //
                ////自动滚动开始
                //public static final int SCROLL_STATE_SETTLING = 2;
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val childCount = mRecyclerView.childCount
                    val itemCount = mRecyclerView.layoutManager?.itemCount

                    val firstVisibleItem = (mRecyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (firstVisibleItem + childCount == itemCount) {
                        if (!loadingMore) {
                            loadingMore = true
                            mPresenter.loadMoreData()
                        }
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val currentVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
                if (currentVisibleItemPosition == 0) {
                    //背景设置为透明
                    toolbar.setBackgroundColor(getColor(R.color.color_translucent))
                    iv_search.setImageResource(R.mipmap.ic_action_search_white)
                    tv_header_title.text = ""
                } else {
                    if (mHomeAdapter?.mData!!.size > 1) {
                        toolbar.setBackgroundColor(getColor(R.color.color_title_bg))
                        iv_search.setImageResource(R.mipmap.ic_action_search_black)
                        val itemList = mHomeAdapter!!.mData
                        val item = itemList[currentVisibleItemPosition + mHomeAdapter!!.bannerItemSize - 1]
                        if (item.type == "textHeader") {
                            tv_header_title.text = item.data?.text
                        } else {
                            tv_header_title.text = simpleDateFormat.format(item.data?.date)
                        }
                    }
                }
            }
        })

        iv_search.setOnClickListener { openSearchActivity() }

        mLayoutStatusView = multipleStatusView

        //状态栏透明和间距处理
        activity?.let { StatusBarUtil.darkMode(it) }
        activity?.let { StatusBarUtil.setPaddingSmart(it, toolbar) }

    }

    fun getColor(colorId: Int): Int {
        return resources.getColor(colorId)
    }

    /**
     * 懒加载时加载数据
     */
    override fun lazyLoad() {

        mPresenter.requestHomeData(num)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }


    private fun openSearchActivity() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val options = activity?.let { ActivityOptionsCompat.makeSceneTransitionAnimation(it, iv_search, iv_search.transitionName) }
//            startActivity(Intent(activity, SearchActivity::class.java), options?.toBundle())
//        } else {
//            startActivity(Intent(activity, SearchActivity::class.java))
//        }
    }

    /**
     * 销毁的时候解除绑定
     */
    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}

