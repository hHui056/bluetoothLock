package com.hh.bluetoothlock.model

import com.hh.bluetoothlock.db.core.DBUtils
import com.inuker.bluetooth.library.search.SearchResult
import db.bean.Device
import java.util.*

/**
 *Create By hHui on 2018/11/19
 */
class DeviceListModel {

    fun getAllDevices(): List<Device> {
        return DBUtils.deviceService.queryAll()
    }

    fun saveDevice2Db(searchResult: SearchResult) {
        val device = Device()
        device.device_id = searchResult.address
        device.device_name = searchResult.name
        device.lastConnectTime = Date()
        DBUtils.deviceService.saveOrUpdate(device)
    }
}