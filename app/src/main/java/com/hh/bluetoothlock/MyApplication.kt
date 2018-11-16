package com.hh.bluetoothlock

import android.app.Application
import com.hh.bluetoothlock.db.core.DBUtils

/**
 *Create By hHui on 2018/11/16
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // - 初始化数据库
        DBUtils.init(applicationContext)
    }
}