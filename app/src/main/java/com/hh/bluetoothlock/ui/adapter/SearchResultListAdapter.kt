package com.hh.bluetoothlock.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.hh.bluetoothlock.R
import com.inuker.bluetooth.library.search.SearchResult

/**
 *Create By hHui on 2018/11/15
 *
 */
class SearchResultListAdapter(private val context: Context, internal var list: List<SearchResult>) : BaseAdapter() {

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
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }
        val searchResult = list[position]
        viewHolder!!.mac!!.text = "${searchResult.name} - ${searchResult.address}"

        return convertView!!
    }

    inner class ViewHolder {
        internal var mac: TextView? = null //MAC地址
    }
}