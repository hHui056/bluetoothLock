package com.hh.bluetoothlock.view

import com.hh.bluetoothlock.instruction.enum.LockStatus

/**
 *Create By hHui on 2018/11/16
 */
interface ControlView : BaseView {
    fun getDeviceMac(): String
    fun initDeviceName(deviceMac: String)
    fun refreshLockStatus(status: LockStatus)
    fun refreshVoltage(voltage: Double)
    fun refreshIsHaveUnUploadData(isHave: Boolean)
    fun refreshDeviceTime(timeStr: String)
    fun setOpenLockButtonCanClick(canClick: Boolean)
}