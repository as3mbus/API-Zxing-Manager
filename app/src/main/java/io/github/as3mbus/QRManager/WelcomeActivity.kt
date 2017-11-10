package io.github.as3mbus.QRManager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_welcome.*
import android.content.Intent



class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        button3.setOnClickListener {
            val k = Intent(this,OutletActivity::class.java)
            startActivity(k)
        }
    }
}
