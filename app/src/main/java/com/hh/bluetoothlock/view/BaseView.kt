package com.hh.bluetoothlock.view

import com.hh.bluetoothlock.widget.MyAlertDialog

/**
 *Create By hHui on 2018/11/15
 */
interface BaseView {
    fun showProgress(msg: String = "Loading...")

    fun closeProgress()

    fun alertError(title: String = "提示", message: String, callback: MyAlertDialog.SureClickBasck? = null)

    fun showToast(content: String)

    fun miss()
}