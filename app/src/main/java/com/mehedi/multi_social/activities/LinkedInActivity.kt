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
import kotlinx.android.synthetic.main.activity_linked_in.*

class LinkedInActivity : AppCompatActivity() {
    lateinit var linkwebview: WebView
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linked_in)
        linkwebview = findViewById(R.id.linkwebview)
        sharedPreferences = getSharedPreferences(getString(R.string.shared_preferences_file),
            Context.MODE_PRIVATE)
        val theme = intent.getStringExtra("theme")

        if (theme == "Dark"){
            println("dark theme applied")
            if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {

                WebSettingsCompat.setForceDark(linkwebview.settings, WebSettingsCompat.FORCE_DARK_ON)
            }
        }
        else
        {
            println("Light theme applied")
        }
        if (ConnectionManager().checkConnectivity(this)) {
            linkwebview = findViewById(R.id.linkwebview)
            val userAgent = System.getProperty("http.agent")
            linkwebview.settings.userAgentString = userAgent
            linkwebview.loadUrl("https://www.linkedin.com/home/?originalSubdomain=in")
            linkwebview.settings.javaScriptEnabled = true
            linkwebview.webViewClient = WebViewClient()
            linkwebview.webChromeClient = WebChromeClient()

            linkwebview.webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    progressbarlink.visibility = View.VISIBLE
                    super.onPageStarted(view, url, favicon)
                }
                override fun onLoadResource(view: WebView?, url: String?) {
                    if (url != null) {
                        if (!url.startsWith("http") || !url.startsWith("https")){
                            linkwebview.stopLoading()
                            linkwebview.goBack()
                            Toast.makeText(this@LinkedInActivity,"sorry !!non functional area.", Toast.LENGTH_SHORT).show()
                        }
                    }
                    super.onLoadResource(view, url)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    progressbarlink.visibility = View.GONE
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
        if (linkwebview.canGoBack()) {
            linkwebview.goBack()
        } else {
            super.onBackPressed()
        }
    }
}