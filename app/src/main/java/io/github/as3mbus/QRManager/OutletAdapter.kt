package io.github.as3mbus.QRManager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast


/**
 * Created by as3mbus on 03/11/17.
 */
class OutletAdapter : RecyclerView.Adapter<OutletAdapter.ViewHolder> {
    var values: MutableList<String>? = null
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val name = values?.get(position)
        holder?.layout?.setOnClickListener {
            val i = Intent(holder.viewContext, OutletConfirmActivity::class.java);

            //Create the bundle
            val bundle = Bundle();

            //Add your data to bundle
            bundle.putString("outlet", values?.get(position));

            //Add the bundle to the intent
            i.putExtras(bundle);

            //Fire that second activity

            startActivity(holder.viewContext,i,bundle);
            Toast.makeText(holder.viewContext, "" + position + " Index is pressed", Toast.LENGTH_SHORT).show()
        }

        holder?.outletTextView?.setText(name)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent?.getContext());
        val v = inflater.inflate(R.layout.outlet_item, parent, false);
        return ViewHolder(v)
    }


    override fun getItemCount(): Int {
        val valsize = values?.size
        var size = 0
        if (valsize != null)
            size = valsize
        return size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var layout: View? = null
        var outletTextView: TextView? = null
        var viewContext: Context? = null

        init {
            layout = itemView
            outletTextView = itemView.findViewById(R.id.outletTextView)
            viewContext = itemView.context

        }
    }

    fun add(position: Int, item: String) {
        values?.add(position, item)
        notifyItemInserted(position)
    }

    fun remove(position: Int) {
        values?.removeAt(position)
        notifyItemRemoved(position)
    }

    constructor(myDataset: List<String>) {
        values = myDataset.toMutableList()
    }
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//
//        var holder: ViewHolder? = null
////        var gridView: View
//        var _convertView: View? = null
//        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        if (convertView == null) {
//
//
//            _convertView = convertView
//            _convertView =  inflater.inflate(R.layout.outlet_item, null)
////            gridView = View(context)
//            holder = ViewHolder()
//            holder?.outletTextView = _convertView.outletTextView
//            // get layout from mobile.xml
////            gridView = inflater.inflate(R.layout.outlet_item, null)
//
//            // set value into textview
////            val textView = gridView.outletTextView
////            textView.text = textViewValues[position]
//            _convertView.setTag(holder)
//        } else {
//            holder = convertView.getTag() as ViewHolder
//            _convertView = convertView
////            gridView = convertView as View
//        }
////        gridView = inflater.inflate(R.layout.outlet_item, null)
////        val textView = gridView.outletTextView
////        textView.text = textViewValues[position]
//
//        holder.outletTextView?.setText(textViewValues[position])
//        return _convertView as View
//    }
//
//    override fun getCount(): Int {
//        return textViewValues.size
//    }
//
//    override fun getItem(position: Int): Any? {
//        return null
//    }
//
//    override fun getItemId(position: Int): Long {
//        return 0
//    }

}