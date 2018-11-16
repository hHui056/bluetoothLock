package com.hh.bluetoothlock.presenter

import com.hh.bluetoothlock.instruction.Instruction
import com.hh.bluetoothlock.instruction.InstructionFactory
import com.hh.bluetoothlock.instruction.body.response.GetKeyResBody
import com.hh.bluetoothlock.manager.BluetoothClientManager
import com.hh.bluetoothlock.manager.BluetoothListener
import com.hh.bluetoothlock.util.RxBus
import com.hh.bluetoothlock.view.MainView
import com.inuker.bluetooth.library.search.SearchResult

/**
 *Create By hHui on 2018/11/15
 */
class MainPresenter(val view: MainView) {
    private val deviceList = ArrayList<SearchResult>()

    fun start() {
        BluetoothClientManager.init(view.getContext())
        BluetoothClientManager.instance.callback = object : BluetoothListener {
            override fun onSearchStarted() {
                view.showProgress("设备搜索中")
            }

            override fun onDeviceFounded(device: SearchResult) {
                if (!deviceList.contains(device)) {
                    deviceList.add(device)
                    view.refreshDeviceList(deviceList)
                }
            }

            override fun onSearchStopped() {
                view.closeProgress()
                if (deviceList.size == 0) {
                    view.alertError(message = "未搜索到蓝牙设备")
                }
            }

            override fun onSearchCanceled() {
                view.closeProgress()
                if (deviceList.size == 0) {
                    view.alertError(message = "未搜索到蓝牙设备")
                }
            }

            override fun onConnect(isSuccess: Boolean, device: SearchResult) {
                if (isSuccess) {//连接成功--->发送获取通信秘钥的指令
                    view.closeProgress()
                    view.showToast("连接成功")
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
        BluetoothClientManager.instance.start()
    }

    fun dealSearchResultChoice(position: Int) {
        val device = deviceList[position]
        BluetoothClientManager.instance.connectDevices(device)
        view.showProgress("设备连接中")
    }
}