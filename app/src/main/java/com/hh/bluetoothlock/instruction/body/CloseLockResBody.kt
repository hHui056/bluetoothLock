package com.hh.bluetoothlock.instruction.body

import com.hh.bluetoothlock.util.TransformUtils
import java.util.*

/**
 *Create By hHui on 2018/11/14
 *
 * 车锁 ---> APP  (协议上来看只能手动关锁通知APP,并不能通过APP发送指令控制关锁)  APP收到上锁指令后需回复
 */
class CloseLockResBody : Body() {
    var isLockSuccess = false //是否上锁成功 true--->上锁成功  false--->上锁失败
    var openLockDate: Date? = null //开锁时间
    var ridingTime = 0 //骑行时间  单位 min

    override fun parseContent(content: ByteArray) {
        if (content[5] == (0x00 as Byte)) {
            isLockSuccess = true
        }
        val data = Arrays.copyOfRange(content, 6, 9)
        val time = (TransformUtils.bytesToint(data) * 1000).toLong()
        openLockDate = Date(time)

        val data1 = Arrays.copyOfRange(content, 10, 13)
        ridingTime = TransformUtils.bytesToint(data1)
    }
}