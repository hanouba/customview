package com.hansen.kotlindemo.base

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 *@author HanN on 2020/8/12 19:05
 *@email: 1356548475@qq.com
 *@project customview
 *@description:  "?"加在变量名后，系统在任何情况不会报它的空指针异常。
"!!"加在变量名后，如果对象为null，那么系统一定会报异常！
 *@updateuser:
 *@updatedata: 2020/8/12 19:05
 *@updateremark:
 *@version:  2.1.67
 */
class BaseFragmentAdapter : FragmentPagerAdapter {
    //    "?"加在变量名后，系统在任何情况不会报它的空指针异常。
//    "!!"加在变量名后，如果对象为null，那么系统一定会报异常！
    private var fragmentList: List<Fragment>? = ArrayList()
    private var mTitles: List<String>? = null

    constructor(fm: FragmentManager, fragmentList: List<Fragment>) : super(fm) {
        this.fragmentList = fragmentList
    }

    constructor(fm: FragmentManager, fragmentList: List<Fragment>, mTitles: List<String>) : super(fm) {
        this.mTitles = mTitles
        setFragments(fm, fragmentList, mTitles)
    }

    @SuppressLint("CommitTransaction")
    private fun setFragments(fm: FragmentManager, fragments: List<Fragment>, mTitles: List<String>) {
        this.mTitles = mTitles
        if (this.fragmentList != null) {
            val ft = fm.beginTransaction()
            fragmentList?.forEach {
                ft.remove(it)
            }
            ft?.commitAllowingStateLoss()
            fm.executePendingTransactions()
        }
        this.fragmentList = fragments
        notifyDataSetChanged()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (null != mTitles) mTitles!![position] else ""
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList!![position]
    }

    override fun getCount(): Int {
        return fragmentList!!.size
    }

}