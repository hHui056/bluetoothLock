package com.hh.bluetoothlock

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import com.hh.bluetoothlock.presenter.DeviceListPresenter
import com.hh.bluetoothlock.ui.BaseActivity
import com.hh.bluetoothlock.ui.ControlActivity
import com.hh.bluetoothlock.ui.adapter.SearchResultListAdapter
import com.hh.bluetoothlock.util.Constans
import com.hh.bluetoothlock.view.DeviceListView
import com.inuker.bluetooth.library.search.SearchResult
import db.bean.Device
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Create By hHui on 2018/11/13
 *
 * 设备列表  扫描添加新设备/已经添加过的设备
 */
class DeviceListActivity : BaseActivity(), DeviceListView {

    private val presenter = DeviceListPresenter(this)
    private lateinit var adapter: SearchResultListAdapter<Any>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        presenter.start()
    }

    private fun initView() {
        adapter = SearchResultListAdapter(this, ArrayList<Any>())
        list_device.adapter = adapter

        list_device.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            presenter.dealSearchResultChoice(position)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.stop()
    }

    override fun refreshDeviceList(list: ArrayList<SearchResult>) {
        adapter.list = list
        adapter.notifyDataSetChanged()
    }

    override fun refreshDeviceList(list: List<Device>) {
        adapter.list = list
        adapter.notifyDataSetChanged()
    }

    override fun jump2Control(deviceMac: String) {
        val intent = Intent(this@DeviceListActivity, ControlActivity::class.java)
        intent.putExtra(Constans.Key.DEVICE_MAC_KEY, deviceMac)
        startActivity(intent)
    }

    override fun getContext(): Context = applicationContext

    override fun getModel(): Int = intent.getIntExtra(Constans.Key.TYPE_KEY, 0)   // 0-->添加新设备   1--->历史设备
}
