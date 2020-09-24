package com.hansen.kotlindemo.view.recyclerview

/**
 * 多条目类型
 */
interface MultipleType<in T> {
    fun getLayoutId(item: T, position: Int): Int
}