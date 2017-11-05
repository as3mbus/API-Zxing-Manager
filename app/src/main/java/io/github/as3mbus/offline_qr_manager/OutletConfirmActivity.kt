package io.github.as3mbus.offline_qr_manager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_outlet_confirm.*

class OutletConfirmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_outlet_confirm)
        val bundle = intent.extras

        outletMessageTextView.text = resources.getString(R.string.outlet_confirmation_message,bundle.getString("outlet"))
    }
}
