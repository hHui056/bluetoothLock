package com.hh.bluetoothlock.manager

import android.content.Context
import com.hh.bluetoothlock.instruction.Instruction
import com.hh.bluetoothlock.util.DescriptorUUID
import com.hh.bluetoothlock.util.characterNotifyUUID
import com.hh.bluetoothlock.util.characterWriteUUID
import com.hh.bluetoothlock.util.serviceUUID
import com.inuker.bluetooth.library.BluetoothClient
import com.inuker.bluetooth.library.Code.REQUEST_SUCCESS
import com.inuker.bluetooth.library.search.response.SearchResponse
import com.inuker.bluetooth.library.search.SearchRequest
import com.inuker.bluetooth.library.search.SearchResult
import com.inuker.bluetooth.library.connect.options.BleConnectOptions
import com.inuker.bluetooth.library.connect.response.BleNotifyResponse
import java.util.*

/**
 *Create By hHui on 2018/11/14
 */
class BluetoothClientManager {
    private val ServiceUUID = UUID.fromString(serviceUUID)
    private val characterUUID_Notify = UUID.fromString(characterNotifyUUID)
    private val characterUUID_Write = UUID.fromString(characterWriteUUID)
    private val descriptorUUID = UUID.fromString(DescriptorUUID)

    companion object {
        lateinit var bluetoothClient: BluetoothClient
        // must load in Application
        fun init(context: Context) {
            bluetoothClient = BluetoothClient(context)
        }

        val instance: BluetoothClientManager by lazy {
            BluetoothClientManager()
        }
    }

    /**
     * 开始搜索周围的蓝牙设备
     */
    fun startSearchBluetooth() {
        val request = SearchRequest.Builder()
                .searchBluetoothLeDevice(3000, 3)   // 先扫BLE设备3次，每次3s
                .searchBluetoothClassicDevice(5000) // 再扫经典蓝牙5s
                .searchBluetoothLeDevice(2000)      // 再扫BLE设备2s
                .build()
        bluetoothClient.search(request, object : SearchResponse {
            override fun onSearchStarted() {  //搜索开始

            }

            override fun onDeviceFounded(device: SearchResult) { //搜索成功,找到设备(会多次回调)

            }

            override fun onSearchStopped() { //搜索停止

            }

            override fun onSearchCanceled() {  //搜索被取消

            }
        })
    }

    /**
     * 连接设备
     */
    fun connectDevices(device: SearchResult) {
        val options = BleConnectOptions.Builder()
                .setConnectRetry(3)   // 连接如果失败重试3次
                .setConnectTimeout(30000)   // 连接超时30s
                .setServiceDiscoverRetry(3)  // 发现服务如果失败重试3次
                .setServiceDiscoverTimeout(20000)  // 发现服务超时20s
                .build()
        bluetoothClient.connect(device.address) { code, profile ->
            if (code == REQUEST_SUCCESS) {
                //当连接成功时,我们需要进行一些数据通信
            }
        }
    }

    /**
     * 注册蓝牙通知
     */
    fun notifyBluetoothNotification(device: SearchResult) {
        bluetoothClient.notify(device.address, ServiceUUID, characterUUID_Notify, object : BleNotifyResponse {
            override fun onNotify(service: UUID, character: UUID, value: ByteArray) {   //这里是接收蓝牙指令的回调

            }

            override fun onResponse(code: Int) {
                if (code == REQUEST_SUCCESS) {  //注册成功

                }
            }
        })
    }

    /**
     * 发送命令  写Characteristic
     */
    fun sendInstruction(device: SearchResult, instruction: Instruction) {
        bluetoothClient.write(device.address, ServiceUUID, characterUUID_Write, instruction.toEnciphermentByteArray()) { code ->
            if (code == REQUEST_SUCCESS) { //命令发送成功

            }
        }
    }


}