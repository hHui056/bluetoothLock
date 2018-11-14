package com.hh.bluetoothlock.instruction.body

import com.hh.bluetoothlock.util.TransformUtils
import java.util.*

/**
 *Create By hHui on 2018/11/14
 *
 * 车锁--->APP
 */
class GetUnUpLoadDataResBody : Body() {
    var openLockDate: Date? = null  //开锁时间
    var ridingTime = 0 //骑行时间  单位 min
    var clientId = 0 //用户ID

    override fun parseContent(content: ByteArray) {
        val data = Arrays.copyOfRange(content, 5, 8)
        val time = (TransformUtils.bytesToint(data) * 1000).toLong()
        openLockDate = Date(time)

        val data1 = Arrays.copyOfRange(content, 9, 12)
        ridingTime = TransformUtils.bytesToint(data1)

        val data2 = Arrays.copyOfRange(content, 13, 16)
        clientId = TransformUtils.bytesToint(data2)
    }
}