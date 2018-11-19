package com.hh.bluetoothlock.view

import android.content.Context
import com.inuker.bluetooth.library.search.SearchResult
import db.bean.Device

/**
 *Create By hHui on 2018/11/15
 */
interface DeviceListView : BaseView {
    fun getContext(): Context
    fun refreshDeviceList(list: ArrayList<SearchResult>)
    fun refreshDeviceList(list: List<Device>)
    fun jump2Control(deviceMac: String)
    fun getModel(): Int
}