package io.github.as3mbus.offline_qr_manager

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.outlet_item.view.*


/**
 * Created by as3mbus on 03/11/17.
 */
class OutletAdapter(private val context: Context, private val textViewValues: Array<String>) : BaseAdapter() {

    inner final class ViewHolder {
        var outletTextView: TextView? = null
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var holder: ViewHolder? = null
//        var gridView: View
        var _convertView: View? = null
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        if (convertView == null) {


            _convertView = convertView
            _convertView =  inflater.inflate(R.layout.outlet_item, null)
//            gridView = View(context)
            holder = ViewHolder()
            holder?.outletTextView = _convertView.outletTextView
            // get layout from mobile.xml
//            gridView = inflater.inflate(R.layout.outlet_item, null)

            // set value into textview
//            val textView = gridView.outletTextView
//            textView.text = textViewValues[position]
            _convertView.setTag(holder)
        } else {
            holder = convertView.getTag() as ViewHolder
            _convertView = convertView
//            gridView = convertView as View
        }
//        gridView = inflater.inflate(R.layout.outlet_item, null)
//        val textView = gridView.outletTextView
//        textView.text = textViewValues[position]

        holder.outletTextView?.setText(textViewValues[position])
        return _convertView as View
    }

    override fun getCount(): Int {
        return textViewValues.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

}