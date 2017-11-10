package io.github.as3mbus.QRManager

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_outlet_confirm.*

class OutletConfirmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_outlet_confirm)
        val bundle = intent.extras

        outletMessageTextView.text = resources.getString(R.string.outlet_confirmation_message,bundle.getString("outlet"))
        confirmButton.setOnClickListener{
            if(checkPassword()){
                Toast.makeText(this,"Confirmation Finished",Toast.LENGTH_SHORT).show()
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
            }
            else{
                Toast.makeText(this,"invalid password",Toast.LENGTH_SHORT).show()
                passwordField.setText("")
            }
        }
    }
    fun checkPassword() : Boolean{
        return passwordField.text.toString().equals(resources.getString(R.string.password))
    }
}
