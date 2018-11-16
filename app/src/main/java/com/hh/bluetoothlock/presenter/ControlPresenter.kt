package com.hh.bluetoothlock.presenter

import android.annotation.SuppressLint
import com.hh.bluetoothlock.instruction.InstructionFactory
import com.hh.bluetoothlock.instruction.body.response.*
import com.hh.bluetoothlock.instruction.enum.LockStatus
import com.hh.bluetoothlock.manager.BluetoothClientManager
import com.hh.bluetoothlock.util.RxBus
import com.hh.bluetoothlock.util.toShowStr
import com.hh.bluetoothlock.view.ControlView
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 *Create By hHui on 2018/11/16
 */
class ControlPresenter(private val view: ControlView) {

    @SuppressLint("CheckResult")
    fun start() {
        view.initDeviceName("当前设备:${view.getDeviceMac()}")

        RxBus.default.register(this, RxBus.EventType::class.java).observeOn(AndroidSchedulers.mainThread()).subscribe {
            if (it is InstructionEvent) {
                when (it.instruction.body) {
                    is QueryLockInfoResBody -> {// - 查询车锁状态反馈
                        val qliBody = it.instruction.body as QueryLockInfoResBody
                        view.refreshVoltage(qliBody.voltage)
                        view.refreshDeviceTime(qliBody.lockTime!!.toShowStr())
                        view.refreshIsHaveUnUploadData(qliBody.isHaveUnUploadData)
                        view.refreshLockStatus(qliBody.lockStatus)
                        if (qliBody.lockStatus == LockStatus.CLOSE) {
                            view.setOpenLockButtonCanClick(true)
                        } else {
                            view.setOpenLockButtonCanClick(false)
                        }
                        if (qliBody.isHaveUnUploadData) {//开始获取未上传的数据
                            BluetoothClientManager.instance.sendInstructionByCharacteristic(view.getDeviceMac(), InstructionFactory.getUnUploadDataInstruction)
                        }
                    }
                    is OpenLockResBody -> { // - 开锁反馈
                        val olBody = it.instruction.body as OpenLockResBody
                        if (olBody.isOpenSuccess) {
                            view.refreshLockStatus(LockStatus.OPEN)
                            view.showToast("开锁成功")
                        } else {
                            view.refreshLockStatus(LockStatus.CLOSE)
                            view.showToast("开锁失败")
                        }
                        BluetoothClientManager.instance.sendInstructionByCharacteristic(view.getDeviceMac(), InstructionFactory.openLockReplyInstruction)
                    }
                    is CloseLockResBody -> {// - 关锁反馈（车锁主动发出）
                        //TODO 收到关锁回复后待处理

                        BluetoothClientManager.instance.sendInstructionByCharacteristic(view.getDeviceMac(), InstructionFactory.closeLockReplyInstruction)
                    }
                    is GetUnUpLoadDataResBody -> {//获取到的未上传的数据反馈
                        //  TODO 此处待APP处理

                        // - 清除未上传的数据
                        BluetoothClientManager.instance.sendInstructionByCharacteristic(view.getDeviceMac(), InstructionFactory.clearUnUpLoadDataInstruction)
                    }
                    is ClearUnUpLoadDataResBody -> {
                        val cuulBody = it.instruction.body as ClearUnUpLoadDataResBody
                        if (cuulBody.isSuccess) {
                            view.showToast("未上传数据清除成功")
                        } else {
                            view.showToast("未上传数据清除失败")
                        }
                    }
                }
            }
        }
        //查询车锁状态 TODO 是否需要轮询查车锁状态
        BluetoothClientManager.instance.sendInstructionByCharacteristic(view.getDeviceMac(), InstructionFactory.queryLockInfoInstruction)
    }

    fun openLock() {
        BluetoothClientManager.instance.sendInstructionByCharacteristic(view.getDeviceMac(), InstructionFactory.openLockInstruction)
    }

    fun stop() {
        RxBus.default.unregister(this, RxBus.EventType::class.java)
    }
}