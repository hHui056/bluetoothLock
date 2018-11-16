package com.hh.bluetoothlock

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import com.hh.bluetoothlock.presenter.MainPresenter
import com.hh.bluetoothlock.ui.BaseActivity
import com.hh.bluetoothlock.ui.ControlActivity
import com.hh.bluetoothlock.ui.adapter.SearchResultListAdapter
import com.hh.bluetoothlock.util.Constans
import com.hh.bluetoothlock.view.MainView
import com.inuker.bluetooth.library.search.SearchResult
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Create By hHui on 2018/11/13
 */
class MainActivity : BaseActivity(), MainView {

    private val presenter = MainPresenter(this)

    private lateinit var adapter: SearchResultListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        presenter.start()
    }

    private fun initView() {
        adapter = SearchResultListAdapter(this, ArrayList())
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

    override fun jump2Control(deviceMac: String) {
        val intent = Intent(this@MainActivity, ControlActivity::class.java)
        intent.putExtra(Constans.Key.DEVICE_MAC_KEY, deviceMac)
        startActivity(intent)
    }

    override fun getContext(): Context = applicationContext
}
