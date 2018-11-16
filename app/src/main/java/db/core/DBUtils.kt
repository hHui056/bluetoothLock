package com.hh.bluetoothlock.db.core

import android.annotation.SuppressLint
import android.content.Context
import db.DaoSession
import db.service.*

@SuppressLint("StaticFieldLeak")
/**
 *Create By hHui on 2018/11/16
 */
object DBUtils {
    private lateinit var context: Context
    private lateinit var dbCore: DbCore
    private lateinit var session: DaoSession

    lateinit var deviceService: DeviceService

    fun init(context: Context) {
        this.context = context
        dbCore = DbCore(context)
        session = dbCore.getDaoSession()

        deviceService = DeviceService(session.deviceDao)
    }

}