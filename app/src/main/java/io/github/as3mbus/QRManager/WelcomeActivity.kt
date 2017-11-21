package io.github.as3mbus.QRManager

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_welcome.*


class WelcomeActivity : AppCompatActivity() {
    //call intent on button pressed
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        button3.setOnClickListener {
            val k = Intent(this, OutletActivity::class.java)
            startActivity(k)
        }
    }
}
