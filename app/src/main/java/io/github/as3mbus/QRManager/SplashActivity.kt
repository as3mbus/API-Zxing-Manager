package io.github.as3mbus.QRManager

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity


class SplashActivity : AppCompatActivity() {
    val PREFS_NAME = "OutletPrefs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        // Restore preferences
        val settings = getSharedPreferences(PREFS_NAME, 0)
        val outletId = settings.getInt("outletId", -1)
        if(outletId>-1){
            val k = Intent(this,MainActivity::class.java)
            startActivity(k)
            finish()
        }
        else{
            val k = Intent(this,WelcomeActivity::class.java)
            startActivity(k)
            finish()
        }

    }
}
