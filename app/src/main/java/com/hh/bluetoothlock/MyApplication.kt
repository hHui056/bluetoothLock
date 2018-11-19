package com.hh.bluetoothlock

import android.app.Application
import com.hh.bluetoothlock.db.core.DBUtils
import com.hh.bluetoothlock.manager.BluetoothClientManager

/**
 *Create By hHui on 2018/11/16
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // - 初始化数据库
        DBUtils.init(applicationContext)
        // - 初始化蓝牙连接管理类
        BluetoothClientManager.init(applicationContext)
    }
}