package com.hh.bluetoothlock.instruction.body.request

import com.hh.bluetoothlock.instruction.body.Body

/**
 * Create By hHui on 2018/11/13
 *
 * 车锁--->APP
 *
 * @param key 设备识别Key 默认为 "yOTmK50z"
 */
class GetKeyReqBody(private val key: String) : Body() {

    override fun toByteArray(): ByteArray {
        return key.toByteArray()
    }

    override fun getLength(): Int {
        return key.length
    }
}
