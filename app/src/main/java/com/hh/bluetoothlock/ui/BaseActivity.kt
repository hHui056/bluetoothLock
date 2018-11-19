package com.hh.bluetoothlock.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hh.bluetoothlock.util.ToastUtil
import com.hh.bluetoothlock.view.BaseView
import com.hh.bluetoothlock.widget.MyAlertDialog

/**
 *Create By hHui on 2018/11/15
 */
open class BaseActivity : AppCompatActivity(), BaseView {

    lateinit var alertDialog: MyAlertDialog   //提示框

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alertDialog = MyAlertDialog(this)
    }

    override fun alertError(title: String, message: String, callback: MyAlertDialog.SureClickBasck?) {
        alertDialog.alertErrorMsg(title, message, callback)
    }

    override fun showToast(content: String) {
        ToastUtil.showToast(this, content)
    }

    override fun miss() = finish()

    override fun showProgress(msg: String) {
        alertDialog.showDialogLoading(msg)
    }

    override fun closeProgress() {
        alertDialog.closeDialogLoading()
    }

}