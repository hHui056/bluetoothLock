package com.hh.bluetoothlock.instruction.body

/**
 *Create By hHui on 2018/11/13
 *
 * APP--->车锁
 */
class GetKeyResBody : Body() {

    var key: Byte = 0 // 通信秘钥 KEY   在断开连接之后需要重新获取key

    override fun parseContent(content: ByteArray) {
        key = content[5]
    }
}