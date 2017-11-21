package io.github.as3mbus.QRManager

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_outlet_confirm.*


class OutletConfirmActivity : AppCompatActivity() {
    val PREFS_NAME = "OutletPrefs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_outlet_confirm)
        val bundle = intent.extras

        outletMessageTextView.text = resources.getString(R.string.outlet_confirmation_message, bundle.getString("outlet"))
        confirmButton.setOnClickListener {
            //if password correct continue to main activity and save it's preference
            if (checkPassword()) {
                Toast.makeText(this, "Confirmation Finished as outlet " + bundle.getString("outlet"), Toast.LENGTH_SHORT).show()
                // We need an Editor object to make preference changes.
                // All objects are from android.context.Context
                val settings = getSharedPreferences(PREFS_NAME, 0)
                val editor = settings.edit()
                editor.putInt("outletId", bundle.getInt("outletId"))

                // Commit the edits!
                editor.apply()

                val i = Intent(this, MainActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
                finish()
            }
            //else display toast message and empty password field
            else {
                Toast.makeText(this, "invalid password", Toast.LENGTH_SHORT).show()
                passwordField.setText("")
            }
        }
    }

    //verify password
    private fun checkPassword(): Boolean {
        return passwordField.text.toString() == (resources.getString(R.string.password))
    }
}
