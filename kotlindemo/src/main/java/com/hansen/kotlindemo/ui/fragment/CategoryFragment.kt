package com.hansen.kotlindemo.ui.fragment

import android.os.Bundle
import com.hansen.kotlindemo.R
import com.hansen.kotlindemo.base.BaseFragment

/**
 *@author HanN on 2020/10/10 10:29
 *@email: 1356548475@qq.com
 *@project customview
 *@description: 分类
 *@updateuser:
 *@updatedata: 2020/10/10 10:29
 *@updateremark:
 *@version:  2.1.67
 */
class CategoryFragment : BaseFragment() {

    private var mTitle: String? = null

    companion object {
        fun getInstance(title: String): CategoryFragment {
            val fragment = CategoryFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun initView() {
    }

    override fun lazyLoad() {
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_category
    }
}