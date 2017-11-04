package io.github.as3mbus.offline_qr_manager

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
