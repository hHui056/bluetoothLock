package com.hh.bluetoothlock.instruction.body

/**
 *Create By hHui on 2018/11/14
 *
 * 车锁--->APP
 */
class ClearUnUpLoadDataResBody : Body() {
    var isSuccess = false //是否清除成功

    override fun parseContent(content: ByteArray) {
        if (content[5] == (0x00 as Byte)) {
            isSuccess = true
        }
    }
}