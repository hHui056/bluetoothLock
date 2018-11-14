package com.hh.bluetoothlock.instruction.body.request

import com.hh.bluetoothlock.instruction.body.Body
import com.hh.bluetoothlock.util.TransformUtils
import java.util.*

/**
 * Create By hHui on 2018/11/14
 *
 * APP--->车锁
 *
 *@param clientId APP 端用户ID 号
 *@param date     开锁时间
 *@param openType 开锁类型 0--->围栏内开锁  1--->围栏外开锁
 */
class OpenLockReqBody(private val clientId: Int, private val date: Date, private val openType: Int) : Body() {

    override fun toByteArray(): ByteArray {
        val timestamp = date.time.toInt() / 1000
        val clientBytes = TransformUtils.toByteArray(clientId, 4)
        val timeBytes = TransformUtils.toByteArray(timestamp, 4)
        val openTypeByte = openType as Byte

        val datas = ByteArray(9)
        System.arraycopy(clientBytes, 0, datas, 0, clientBytes.size)
        System.arraycopy(timeBytes, 0, datas, 4, timeBytes.size)
        datas[8] = openTypeByte
        return datas
    }

    override fun getLength(): Int {
        return 9
    }
}