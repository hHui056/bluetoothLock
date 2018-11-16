package com.hh.bluetoothlock.view

import android.content.Context
import com.inuker.bluetooth.library.search.SearchResult

/**
 *Create By hHui on 2018/11/15
 */
interface MainView : BaseView {
    fun getContext(): Context
    fun refreshDeviceList(list: ArrayList<SearchResult>)
    fun jump2Control(deviceMac: String)
}