package com.hh.bluetoothlock.manager

import com.hh.bluetoothlock.instruction.Instruction
import com.inuker.bluetooth.library.search.SearchResult

/**
 *Create By hHui on 2018/11/15
 *
 * 所有业务层需要处理的都通过此接口回调
 */
interface BluetoothListener {
    fun onSearchStarted()
    fun onDeviceFounded(device: SearchResult)
    fun onSearchStopped()
    fun onSearchCanceled()
    fun onConnect(isSuccess: Boolean, device: SearchResult)
    fun onNotify(instruction: Instruction)
    fun onError(msg: String)
}