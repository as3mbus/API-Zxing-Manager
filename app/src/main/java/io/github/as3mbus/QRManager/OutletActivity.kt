package io.github.as3mbus.QRManager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_outlet.*

class OutletActivity : AppCompatActivity() {
    var layoutMan : RecyclerView.LayoutManager? = null
    var outletAdapter : RecyclerView.Adapter<OutletAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_outlet)
//        outletGridView.setAdapter(OutletAdapter(this, resources.getStringArray(R.array.outlet_name)))
        outletRecyclerView.setHasFixedSize(true)
        layoutMan = GridLayoutManager(this,3)
        layoutMan?.isAutoMeasureEnabled = true
        outletRecyclerView.layoutManager = layoutMan
        val data = this.resources.getStringArray(R.array.outlet_name)
        outletAdapter = OutletAdapter(data.toList())
        outletRecyclerView.adapter = outletAdapter




    }
}
