package com.hh.bluetoothlock.presenter

import com.hh.bluetoothlock.instruction.Instruction
import com.hh.bluetoothlock.manager.BluetoothClientManager
import com.hh.bluetoothlock.manager.BluetoothListener
import com.hh.bluetoothlock.view.MainView
import com.inuker.bluetooth.library.search.SearchResult

/**
 *Create By hHui on 2018/11/15
 */
class MainPresenter(val view: MainView) {
    private var device: SearchResult? = null

    fun start() {
        BluetoothClientManager.init(view.getContext())
        BluetoothClientManager.instance.callback = object : BluetoothListener {
            override fun onSearchStarted() {
                view.showProgress("设备搜索中")
            }

            override fun onDeviceFounded(device: SearchResult) {
                if (device.address == "MAC") {
                    BluetoothClientManager.instance.connectDevices(device)
                }
            }

            override fun onSearchStopped() {
                if (device == null) {
                    view.showToast("未搜索到可用设备")
                    view.closeProgress()
                }
            }

            override fun onSearchCanceled() {
                if (device == null) {
                    view.showToast("未搜索到可用设备")
                    view.closeProgress()
                }
            }

            override fun onConnect(isSuccess: Boolean, device: SearchResult) {
                if (isSuccess) {//连接成功
                    BluetoothClientManager.instance.openBluetoothNotification(device)
                }
            }

            override fun onNotify(instruction: Instruction) {//TODO 处理车锁回复的指令
            }

            override fun onError(msg: String) {//出错  详细信息见 [msg]

            }
        }

        BluetoothClientManager.instance.start()
    }
}