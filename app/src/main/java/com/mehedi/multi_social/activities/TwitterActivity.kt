package com.mehedi.multi_social.activities

import android.annotation.SuppressLint
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
import kotlinx.android.synthetic.main.activity_twitter.*

class TwitterActivity : AppCompatActivity() {
    lateinit var twitwebview: WebView
    lateinit var sharedPreferences: SharedPreferences
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twitter)
        twitwebview = findViewById(R.id.twitterwebview)
        sharedPreferences = getSharedPreferences(getString(R.string.shared_preferences_file),
            Context.MODE_PRIVATE)
        val theme = intent.getStringExtra("theme")

        if (theme == "Dark"){
            println("dark theme applied")
            if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {

                WebSettingsCompat.setForceDark(twitwebview.settings, WebSettingsCompat.FORCE_DARK_ON)
            }
        }
        else
        {
            println("Light theme applied")
        }
        if (ConnectionManager().checkConnectivity(this)) {
            twitwebview = findViewById(R.id.twitterwebview)

            val userAgent = System.getProperty("http.agent")
            twitwebview.settings.userAgentString = userAgent
            twitwebview.loadUrl("https://twitter.com/")
            twitwebview.settings.javaScriptEnabled = true

            twitwebview.webViewClient = WebViewClient()
            twitwebview.webChromeClient = WebChromeClient()

            twitwebview.webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    progressbartwitter.visibility = View.VISIBLE

                    super.onPageStarted(view, url, favicon)
                }
                override fun onLoadResource(view: WebView?, url: String?) {
                    if (url != null) {
                        if (!url.startsWith("http") || !url.startsWith("https")){
                            twitwebview.stopLoading()
                            twitwebview.goBack()
                            Toast.makeText(this@TwitterActivity,"sorry !!non functional area.", Toast.LENGTH_SHORT).show()
                        }
                    }
                    super.onLoadResource(view, url)
                }
                override fun onPageFinished(view: WebView?, url: String?) {
                    progressbartwitter.visibility = View.GONE
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
        if (twitwebview.canGoBack()) {
            twitwebview.goBack()
        } else {
            super.onBackPressed()
        }
    }
}