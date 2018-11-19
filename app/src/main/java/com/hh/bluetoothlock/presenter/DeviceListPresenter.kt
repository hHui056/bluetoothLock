package com.hh.bluetoothlock.presenter

import com.hh.bluetoothlock.instruction.Instruction
import com.hh.bluetoothlock.instruction.InstructionFactory
import com.hh.bluetoothlock.instruction.body.response.GetKeyResBody
import com.hh.bluetoothlock.manager.BluetoothClientManager
import com.hh.bluetoothlock.manager.BluetoothListener
import com.hh.bluetoothlock.model.DeviceListModel
import com.hh.bluetoothlock.util.RxBus
import com.hh.bluetoothlock.view.DeviceListView
import com.hh.bluetoothlock.widget.MyAlertDialog
import com.inuker.bluetooth.library.search.SearchResult

/**
 *Create By hHui on 2018/11/15
 */
class DeviceListPresenter(val view: DeviceListView) {
    private val deviceList = ArrayList<SearchResult>()
    private val model = DeviceListModel()

    fun start() {
        if (view.getModel() == 1) {
            if (model.getAllDevices().isEmpty()) {
                view.alertError(message = "没有连接过的车锁,请返回主页添加车锁", callback = MyAlertDialog.SureClickBasck {
                    view.miss()
                })
            }
            view.refreshDeviceList(model.getAllDevices())
        }
        BluetoothClientManager.instance.callback = object : BluetoothListener {
            override fun onConnect(isSuccess: Boolean, deviceMac: String) {
                view.closeProgress()
                view.showToast("连接成功")
                view.jump2Control(deviceMac)
                BluetoothClientManager.instance.openBluetoothNotification(deviceMac)
                BluetoothClientManager.instance.sendInstructionByCharacteristic(deviceMac, InstructionFactory.getKeyInstruction)
            }

            override fun onSearchStarted() {
                if (view.getModel() == 0) view.showProgress("设备搜索中")
            }

            override fun onDeviceFounded(device: SearchResult) {
                if (view.getModel() != 0) return
                if (!deviceList.contains(device)) {
                    deviceList.add(device)
                    view.refreshDeviceList(deviceList)
                }
            }

            override fun onSearchStopped() {
                if (view.getModel() != 0) return
                view.closeProgress()
                if (deviceList.size == 0) {
                    view.alertError(message = "未搜索到蓝牙设备")
                }
            }

            override fun onSearchCanceled() {
                if (view.getModel() != 0) return
                view.closeProgress()
                if (deviceList.size == 0) {
                    view.alertError(message = "未搜索到蓝牙设备")
                }
            }

            override fun onConnect(isSuccess: Boolean, device: SearchResult) {
                if (isSuccess) {//连接成功--->发送获取通信秘钥的指令
                    model.saveDevice2Db(device)
                    view.closeProgress()
                    view.showToast("连接成功")
                    view.jump2Control(device.address)
                    BluetoothClientManager.instance.openBluetoothNotification(device)
                    BluetoothClientManager.instance.sendInstructionByCharacteristic(device, InstructionFactory.getKeyInstruction)
                }
            }

            override fun onNotify(instruction: Instruction) {
                when (instruction.body) {
                    is GetKeyResBody -> {//获取秘钥成功--->更新通信秘钥
                        val getKeyResBody = instruction.body as GetKeyResBody
                        InstructionFactory.communicationKey = getKeyResBody.key
                    }
                    else -> {
                        RxBus.default.post(InstructionEvent(instruction))
                    }
                }
            }

            override fun onError(msg: String) {//出错  详细信息见 [msg]
                view.closeProgress()
                view.showToast(msg)
            }
        }
        BluetoothClientManager.instance.start(view.getModel() == 0)
    }

    fun dealSearchResultChoice(position: Int) {
        if (view.getModel() == 0) {
            val device = deviceList[position]
            BluetoothClientManager.instance.connectDevices(device)
        } else {
            val deviceMac = model.getAllDevices()[position].device_id
            BluetoothClientManager.instance.connectDevices(deviceMac)
        }
        view.showProgress("设备连接中")
    }

    fun stop() {
        BluetoothClientManager.instance.closeBlueTooth()
    }
}