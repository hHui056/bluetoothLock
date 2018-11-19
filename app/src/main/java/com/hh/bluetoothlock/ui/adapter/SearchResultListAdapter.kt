package com.hh.bluetoothlock.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.hh.bluetoothlock.R
import com.hh.bluetoothlock.util.toShowStr
import com.inuker.bluetooth.library.search.SearchResult
import db.bean.Device
import java.util.*

/**
 *Create By hHui on 2018/11/15
 *
 */
class SearchResultListAdapter<T>(private val context: Context, internal var list: List<T>) : BaseAdapter() {

    private var viewHolder: ViewHolder? = null


    override fun getItem(p0: Int): Any? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return 0L
    }

    override fun getCount(): Int {
        return list.size
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        if (convertView == null) {
            viewHolder = ViewHolder()
            val inflater = LayoutInflater.from(context)
            convertView = inflater.inflate(R.layout.search_item, parent, false)
            viewHolder!!.mac = convertView.findViewById(R.id.txt_mac) as TextView
            viewHolder!!.time = convertView.findViewById(R.id.txt_time) as TextView
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as SearchResultListAdapter<T>.ViewHolder
        }
        val searchResult = list[position]
        if (searchResult is SearchResult) {
            viewHolder!!.mac!!.text = "${searchResult.name} - ${searchResult.address}"
            viewHolder!!.time!!.text = Date().toShowStr()
        } else if (searchResult is Device) {
            viewHolder!!.mac!!.text = "${searchResult.device_name} - ${searchResult.device_id}"
            viewHolder!!.time!!.text = searchResult.lastConnectTime.toShowStr()
        }
        return convertView!!
    }

    inner class ViewHolder {
        internal var mac: TextView? = null //MAC地址
        internal var time: TextView? = null //添加时间
    }
}