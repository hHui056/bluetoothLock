package com.hh.bluetoothlock.db.core

import android.content.Context
import db.DaoMaster
import db.DaoSession

/**
 *Create By hHui on 2018/11/16
 */
class DbCore(context: Context) {

    private val daoSession: DaoSession
    
    private val DATABASE_NAME = "bluetoothlock-db"

    init {
        val helper = DaoMaster.DevOpenHelper(context, DATABASE_NAME, null)
        val db = helper.writableDb
        daoSession= DaoMaster(db).newSession()
    }

    fun getDaoSession(): DaoSession {
        return daoSession
    }
}