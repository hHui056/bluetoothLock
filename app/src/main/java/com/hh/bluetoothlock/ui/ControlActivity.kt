package com.hh.bluetoothlock.ui

import android.os.Bundle
import com.hh.bluetoothlock.R
import com.hh.bluetoothlock.instruction.enum.LockStatus
import com.hh.bluetoothlock.presenter.ControlPresenter
import com.hh.bluetoothlock.util.Constans
import com.hh.bluetoothlock.view.ControlView
import kotlinx.android.synthetic.main.activity_control.*

/**
 * Create By hHui on 2018/11/16
 *
 * 车锁控制
 */
class ControlActivity : BaseActivity(), ControlView {
    private val presenter = ControlPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)

        initView()
    }

    private fun initView() {
        btn_open_lock.setOnClickListener { presenter.openLock() }
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.stop()
    }


    override fun getDeviceMac(): String = intent.getStringExtra(Constans.Key.DEVICE_MAC_KEY)

    override fun initDeviceName(deviceMac: String) {
        txt_device_name.text = "当前设备:$deviceMac"
    }

    override fun refreshLockStatus(status: LockStatus) {
        txt_lock_status.text = if (status == LockStatus.CLOSE) "车锁状态: 关闭" else "车锁状态: 打开"
    }

    override fun refreshVoltage(voltage: Double) {
        txt_voltage.text = "电池电压: $voltage V"
    }

    override fun refreshIsHaveUnUploadData(isHave: Boolean) {
        txt_is_have.text = if (isHave) "是否有未上传数据: 有" else "是否有未上传数据: 无"
    }

    override fun refreshDeviceTime(timeStr: String) {
        txt_device_time.text = "车锁时间: $timeStr"
    }

    override fun setOpenLockButtonCanClick(canClick: Boolean) {
        btn_open_lock.isClickable = canClick
    }
}
