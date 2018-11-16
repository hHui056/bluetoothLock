package com.hh.bluetoothlock.util

import java.text.SimpleDateFormat
import java.util.*

/**
 *Create By hHui on 2018/11/16
 *
 * 对已知类的扩展
 */
fun Date.toShowStr(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return sdf.format(this)
}