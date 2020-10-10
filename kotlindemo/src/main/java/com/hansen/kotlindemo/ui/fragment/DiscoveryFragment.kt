package com.hansen.kotlindemo.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.hansen.kotlindemo.R
import com.hansen.kotlindemo.base.BaseFragment
import com.hansen.kotlindemo.base.BaseFragmentAdapter
import com.hansen.kotlindemo.utils.StatusBarUtil
import com.hansen.kotlindemo.view.TabLayoutHelper
import kotlinx.android.synthetic.main.fragment_discovery.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.toolbar
import kotlinx.android.synthetic.main.fragment_home.tv_header_title

/**
 *@author HanN on 2020/10/10 9:28
 *@email: 1356548475@qq.com
 *@project customview
 *@description: 发现热门模块
 *@updateuser:
 *@updatedata: 2020/10/10 9:28
 *@updateremark:
 *@version:  2.1.67
 */
class DiscoveryFragment: BaseFragment() {
    //tables
    private val tabList = ArrayList<String>()
    //
    private val fragments = ArrayList<Fragment>()
    //discoverfragment 的标题
    private var mTitle : String? = null

    companion object {
        fun  getInstance(title: String): DiscoveryFragment {
            val discoveryFragment = DiscoveryFragment()
            val bundle = Bundle()
            discoveryFragment.arguments = bundle
            discoveryFragment.mTitle = title
            return  discoveryFragment
        }
    }

    /**
     * 初始化界面
     */
    override fun initView() {
        //状态栏透明和间距
        activity?.let { StatusBarUtil.darkMode(it) }
        activity?.let { StatusBarUtil.setPaddingSmart(it,toolbar) }

        tv_header_title.text = mTitle

        tabList.add("关注")
        tabList.add("分类")
        fragments.add(FollowFragment.getInstance("关注"))
        fragments.add(CategoryFragment.getInstance("分类"))

        mViewPager.adapter = BaseFragmentAdapter(childFragmentManager, fragments, tabList)
        mTabLayout.setupWithViewPager(mViewPager)
        TabLayoutHelper.setUpIndicatorWidth(mTabLayout)
    }

    /**
     * 懒加载
     */
    override fun lazyLoad() {
    }

    /**
     * 布局界面
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_discovery
    }
}