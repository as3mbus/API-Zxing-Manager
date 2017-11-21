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
 * Created by as3mbus on 03/11/17. GGWP HEYEAYEAYEAAA
 */
class OutletAdapter(myDataset: List<String>) : RecyclerView.Adapter<OutletAdapter.ViewHolder>() {
    var values: MutableList<String>? = null

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val outletname = values?.get(position)
        holder?.layout?.setOnClickListener {
            val i = Intent(holder.viewContext, OutletConfirmActivity::class.java)

            //Create the bundle
            val bundle = Bundle()

            //Add your data to bundle
            bundle.putString("outlet", outletname)
            bundle.putInt("outletId", position + 1)

            //Add the bundle to the intent
            i.putExtras(bundle)

            //Fire that second activity

            startActivity(holder.viewContext, i, bundle)
            Toast.makeText(holder.viewContext, "Hello " + outletname + "!", Toast.LENGTH_SHORT).show()
        }
        val name = outletname
        holder?.outletTextView?.text = name
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val v = inflater.inflate(R.layout.outlet_item, parent, false)
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

    init {
        values = myDataset.toMutableList()
    }

}