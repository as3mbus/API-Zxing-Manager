package io.github.as3mbus.QRManager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_redeem.*

class RedeemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_redeem)

        val bundle = intent.extras

        outletTextView.text = bundle.getString("outlet")

    }
}
