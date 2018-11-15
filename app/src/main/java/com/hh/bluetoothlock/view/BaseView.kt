package com.hh.bluetoothlock.view

/**
 *Create By hHui on 2018/11/15
 */
interface BaseView {
    fun showProgress(msg: String = "Loading...")

    fun closeProgress()

    fun alertError(title: String = "提示", message: String)

    fun showToast(content: String)

    fun miss()
}