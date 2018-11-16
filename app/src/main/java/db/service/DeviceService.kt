package db.service

import db.DeviceDao
import db.bean.Device

/**
 *Create By hHui on 2018/11/16
 */
class DeviceService(deviceDao: DeviceDao) : DaoService<Device, String>(deviceDao)