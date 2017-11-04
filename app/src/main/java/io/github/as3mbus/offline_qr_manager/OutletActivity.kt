package io.github.as3mbus.offline_qr_manager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_outlet.*

class OutletActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_outlet)
        outletGridView.setAdapter(OutletAdapter(this, resources.getStringArray(R.array.outlet_name)))

    }
}
