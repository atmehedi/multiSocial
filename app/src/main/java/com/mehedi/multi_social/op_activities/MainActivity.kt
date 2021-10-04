package com.mehedi.multi_social.op_activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.mehedi.multi_social.R
import com.mehedi.multi_social.activities.*
import com.mehedi.multi_social.util.ConnectionManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var previousMenuItem: MenuItem? = null
    lateinit var sharedPreferences: SharedPreferences
    private var checkedItem: Int = -1
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences(getString(R.string.shared_preferences_file), Context.MODE_PRIVATE)

        setUptoolBar()
        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this, drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        if(ConnectionManager().checkConnectivity(this)) {
        navigation.setNavigationItemSelectedListener {
            if (previousMenuItem != null) {
                previousMenuItem?.isChecked = false
            }
            it.isCheckable = true
            it.isChecked = true
            previousMenuItem = it
            when(it.itemId){
                R.id.mntheme ->{
                    val theme = AlertDialog.Builder(this)
                    theme.setTitle("Select theme")
                    theme.setCancelable(true)
                    theme.setSingleChoiceItems(R.array.themes,checkedItem){_, isChecked->
                        checkedItem = isChecked
                    }
                    theme.setPositiveButton("Apply"){_,_->
                        when(checkedItem){
                            0->{
                                //do something

                                sharedPreferences.edit().putString("theme","Dark").apply()


                            }
                            1->{
                                //do something
                                llodin.setBackgroundColor(R.color.design_default_color_primary_dark)
                                sharedPreferences.edit().putString("theme","Light").apply()
                            }
                        }
                        finish()
                        startActivity(intent)
                    }
                    theme.create()
                    theme.show()
                    drawerLayout.closeDrawers()

                }
                R.id.exit ->{
                    val exit = AlertDialog.Builder(this)
                    exit.setTitle("Exit")
                    exit.setMessage("Are you sure ?")
                    exit.setPositiveButton("yes"){_,_->
                        finish()
                    }
                    exit.setNegativeButton("No"){_,_->
                        //do nothing
                    }
                    exit.setCancelable(false)
                    exit.create()
                    exit.show()

                drawerLayout.closeDrawers()
                }

                else -> {
                    Toast.makeText(this,"something went wrong in menu !!!",Toast.LENGTH_SHORT).show()
                }
            }
            return@setNavigationItemSelectedListener true
        }

val theme=   sharedPreferences.getString("theme","Light")
            println("$theme")

            imgfb.setOnClickListener {
                val intent = Intent(this, FbActivity::class.java)
                intent.putExtra("theme",theme)
                startActivity(intent)
                Toast.makeText(this, "opening Facebook", Toast.LENGTH_SHORT).show()
            }
            imginsta.setOnClickListener {
                val intent = Intent(this, InstaActivity::class.java)
                intent.putExtra("theme",theme)
                startActivity(intent)
                Toast.makeText(this, "opening Instagrame", Toast.LENGTH_SHORT).show()
            }
            imglinkd.setOnClickListener {
                val intent = Intent(this, LinkedInActivity::class.java)
                intent.putExtra("theme",theme)
                startActivity(intent)
                Toast.makeText(this, "Opening LinkIn", Toast.LENGTH_SHORT).show()
            }
            imggit.setOnClickListener {
                val intent = Intent(this, GitActivity::class.java)
                intent.putExtra("theme",theme)
                startActivity(intent)
                Toast.makeText(this, "opening Github", Toast.LENGTH_SHORT).show()
            }
            imgredit.setOnClickListener {
                val intent = Intent(this, RedditActivity::class.java)
                intent.putExtra("theme",theme)
                startActivity(intent)
                Toast.makeText(this, "opening Reddit", Toast.LENGTH_SHORT).show()

            }
            imgpint.setOnClickListener {
                val intent = Intent(this, PintActivity::class.java)
                intent.putExtra("theme",theme)
                startActivity(intent)
                Toast.makeText(this, "opening Pintrest", Toast.LENGTH_SHORT).show()

            }
            imgtumb.setOnClickListener {
                val intent = Intent(this, TumbActivity::class.java)
                intent.putExtra("theme",theme)
                startActivity(intent)
                Toast.makeText(this, "opening Tumblr", Toast.LENGTH_SHORT).show()

            }
            imgtwitter.setOnClickListener {
                val intent = Intent(this, TwitterActivity::class.java)
                intent.putExtra("theme",theme)
                startActivity(intent)
                Toast.makeText(this, "opening Twitter", Toast.LENGTH_SHORT).show()

            }
            imgfod.setOnClickListener {
                val intent = Intent(this, FodActivity::class.java)
                intent.putExtra("theme",theme)
                startActivity(intent)
                Toast.makeText(this, "opening Funny or Die", Toast.LENGTH_SHORT).show()

            }

        }
        else{
        val intent = Intent(this,NetActivity::class.java)
            startActivity(intent)
        }
        val styles = sharedPreferences.getString("theme","Light")
       if (styles == "Dark"){
           llodin.setBackgroundColor(Color.parseColor("#000000"))
           txtheadtext.setTextColor(Color.parseColor("#ff0000"))
           navigation.setBackgroundColor(Color.parseColor("#000000"))
           navigation.itemTextColor = ColorStateList.valueOf(Color.parseColor("#ffffff"))
           toolbar.setBackgroundColor(Color.parseColor("#000000"))
           toolbar.setTitleTextColor(Color.parseColor("#ff0000"))



       }
        else{
           llodin.setBackgroundColor(Color.parseColor("#ffffff"))
           txtheadtext.setTextColor(Color.parseColor("#000000"))
           navigation.setBackgroundColor(Color.parseColor("#ffffff"))
           navigation.itemTextColor = ColorStateList.valueOf(Color.parseColor("#000000"))
           toolbar.setBackgroundColor(Color.parseColor("#ff0000"))
           toolbar.setTitleTextColor(Color.parseColor("#000000"))


       }



    }
    fun setUptoolBar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Social Medias"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }

}