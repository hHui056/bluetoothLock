package com.hh.bluetoothlock.ui

import android.content.Intent
import android.os.Bundle
import com.hh.bluetoothlock.DeviceListActivity
import com.hh.bluetoothlock.R
import com.hh.bluetoothlock.util.Constans
import kotlinx.android.synthetic.main.activity_start.*

/**
 * Create By hHui on 2018/11/19
 *
 * 选择扫描添加新的设备/历史设备
 */
class StartActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        btn_add.setOnClickListener { jump2DeviceList(0) }
        btn_history.setOnClickListener { jump2DeviceList(1) }
    }

    private fun jump2DeviceList(model: Int) {
        val intent = Intent(this, DeviceListActivity::class.java)
        intent.putExtra(Constans.Key.TYPE_KEY, model)
        startActivity(intent)
    }
}
