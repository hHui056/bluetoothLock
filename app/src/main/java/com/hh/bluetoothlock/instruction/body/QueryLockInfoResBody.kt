package com.hh.bluetoothlock.instruction.body

import com.hh.bluetoothlock.instruction.enum.LockStatus
import com.hh.bluetoothlock.util.TransformUtils
import java.util.*

/**
 *Create By hHui on 2018/11/14
 *
 *  车锁---> APP
 */
class QueryLockInfoResBody : Body() {
    var lockStatus = LockStatus.CLOSE //锁状态  0--->开锁  1--->关锁
    var voltage = 0.0 //电池电压
    var isHaveUnUploadData = false //是否有未上传数据 0--->有  1--->没有
    var lockTime: Date? = null  //时间


    override fun parseContent(content: ByteArray) {
        lockStatus = if (content[5] == (0x00 as Byte)) LockStatus.OPEN else LockStatus.CLOSE
        val a = content[6].toInt().toDouble()
        voltage = a / 10
        isHaveUnUploadData = content[7] == (0x00 as Byte)

        val data = Arrays.copyOfRange(content, 8, 11)
        val time = (TransformUtils.bytesToint(data) * 1000).toLong()
        lockTime = Date(time)
    }
}