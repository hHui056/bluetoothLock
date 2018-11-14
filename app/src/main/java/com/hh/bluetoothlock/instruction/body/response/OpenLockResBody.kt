package com.hh.bluetoothlock.instruction.body.response

import com.hh.bluetoothlock.instruction.body.Body
import com.hh.bluetoothlock.util.TransformUtils
import java.util.*

/**
 *Create By hHui on 2018/11/14
 *
 * 车锁--->APP     APP收到后需要回复
 *
 * 车锁回复开锁指令
 */
class OpenLockResBody : Body() {
    var isOpenSuccess = false //是否开锁成功
    var openDate: Date? = null //开锁时间

    override fun parseContent(content: ByteArray) {
        if (content[5] == (0x00 as Byte)) {
            isOpenSuccess = true
            val data = Arrays.copyOfRange(content, 6, 9)
            val time = (TransformUtils.bytesToint(data) * 1000).toLong()
            openDate = Date(time)
        }
    }
}