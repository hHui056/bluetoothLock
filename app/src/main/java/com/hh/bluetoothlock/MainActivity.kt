package com.hh.bluetoothlock

import android.content.Context
import android.os.Bundle
import com.hh.bluetoothlock.presenter.MainPresenter
import com.hh.bluetoothlock.ui.BaseActivity
import com.hh.bluetoothlock.view.MainView

/**
 * Create By hHui on 2018/11/13
 */
class MainActivity : BaseActivity(), MainView {
    private val presenter = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.start()
    }

    override fun getContext(): Context = applicationContext
}
