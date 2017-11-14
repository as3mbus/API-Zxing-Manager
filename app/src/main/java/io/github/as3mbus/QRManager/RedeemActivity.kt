package io.github.as3mbus.QRManager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_redeem.*

class RedeemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_redeem)

        val bundle = intent.extras
        val redeemActivate= bundle.getBoolean("redeemActivate")
        if(redeemActivate){
            actionButton.text = resources.getText(R.string.redeem_button)

        }else
            actionButton.text = resources.getText(R.string.activate_button)

        outletTextView.text = bundle.getString("outlet")

    }
}
