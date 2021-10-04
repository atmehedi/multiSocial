package com.mehedi.multi_social.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import com.mehedi.multi_social.R
import com.mehedi.multi_social.op_activities.NetActivity
import com.mehedi.multi_social.util.ConnectionManager
import kotlinx.android.synthetic.main.activity_tumb.*

class TumbActivity : AppCompatActivity() {
    lateinit var tumbwebview: WebView
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tumb)
        tumbwebview = findViewById(R.id.tumbwebview)
        sharedPreferences = getSharedPreferences(getString(R.string.shared_preferences_file),
            Context.MODE_PRIVATE)
        val theme = intent.getStringExtra("theme")

        if (theme == "Dark"){
            println("dark theme applied")
            if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {

                WebSettingsCompat.setForceDark(tumbwebview.settings, WebSettingsCompat.FORCE_DARK_ON)
            }
        }
        else
        {
            println("Light theme applied")
        }

        if (ConnectionManager().checkConnectivity(this)) {
            tumbwebview = findViewById(R.id.tumbwebview)
            val userAgent = System.getProperty("http.agent")
            tumbwebview.settings.userAgentString = userAgent
            tumbwebview.loadUrl("https://www.tumblr.com/")

            tumbwebview.settings.javaScriptEnabled = true

            tumbwebview.webViewClient = WebViewClient()
            tumbwebview.webChromeClient = WebChromeClient()

            tumbwebview.webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    progressbartumb.visibility = View.VISIBLE
                    super.onPageStarted(view, url, favicon)
                }
                override fun onLoadResource(view: WebView?, url: String?) {
                    if (url != null) {
                        if (!url.startsWith("http") || !url.startsWith("https")){
                            tumbwebview.stopLoading()
                            tumbwebview.goBack()
                            Toast.makeText(this@TumbActivity,"sorry !!non functional area.", Toast.LENGTH_SHORT).show()
                        }
                    }
                    super.onLoadResource(view, url)
                }
                override fun onPageFinished(view: WebView?, url: String?) {
                    progressbartumb.visibility = View.GONE
                    super.onPageFinished(view, url)
                }
            }
        }
        else{
            val intent = Intent(this, NetActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        if (tumbwebview.canGoBack()) {
            tumbwebview.goBack()
        } else {
            super.onBackPressed()
        }
    }
}