package com.mehedi.multi_social.op_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mehedi.multi_social.R
import com.mehedi.multi_social.util.ConnectionManager
import kotlinx.android.synthetic.main.activity_net.*

class NetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net)
        btnta.setOnClickListener {
            if (ConnectionManager().checkConnectivity(this)){
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this,"Still not connected",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        finish()
    }

}