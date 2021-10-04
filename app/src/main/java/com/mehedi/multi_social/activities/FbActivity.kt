package com.mehedi.multi_social.activities

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.mehedi.multi_social.R
import com.mehedi.multi_social.op_activities.NetActivity
import com.mehedi.multi_social.util.ConnectionManager
import kotlinx.android.synthetic.main.activity_fb.*


class FbActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var fbwebview: WebView
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fb)

        fbwebview = findViewById(R.id.fbwebview)
        sharedPreferences = getSharedPreferences(getString(R.string.shared_preferences_file),
            Context.MODE_PRIVATE)
        val theme = intent.getStringExtra("theme")
        fbwebview.setBackgroundColor(Color.TRANSPARENT)
        if (theme == "Dark"){
            println("dark theme applied")
            if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                WebSettingsCompat.setForceDark(fbwebview.settings, WebSettingsCompat.FORCE_DARK_ON)

            }
        }
        else
        {
            println("Light theme applied")
        }

        if (ConnectionManager().checkConnectivity(this)) {
            fbwebview = findViewById(R.id.fbwebview)
            val userAgent = System.getProperty("http.agent")
            fbwebview.settings.userAgentString = userAgent
            val url = "https://www.facebook.com/"
            fbwebview.loadUrl(url)
            fbwebview.settings.javaScriptEnabled = true
            fbwebview.webViewClient = WebViewClient()
            fbwebview.webChromeClient = WebChromeClient()


            fbwebview.webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    progressbarfb.visibility = View.VISIBLE
                    super.onPageStarted(view, url, favicon)
                }

                override fun onLoadResource(view: WebView?, url: String?) {
                    if (url != null) {
                        if (url.startsWith("fb-messenger://")){
                            fbwebview.stopLoading()

                            try {
                                val ms = Intent(Intent.ACTION_SEND)
                                ms.type = "text/plain"
                                ms.setPackage("com.facebook.orca")
                                ms.putExtra(Intent.EXTRA_TEXT,fbwebview.url + "messaged from multi-social")
                                startActivity(ms)
                            }catch (ex: ActivityNotFoundException){
                                val text = "Your device needs messenger app \n please install and try again !!!"
                                Toast.makeText(this@FbActivity,text,Toast.LENGTH_LONG).show()
                            }
                        }

                            super.onLoadResource(view, url)
                    }
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    progressbarfb.visibility = View.GONE
                    super.onPageFinished(view, url)
                }
            }
        }
        else{
            val intent = Intent(this,NetActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        if (fbwebview.canGoBack()) {
            fbwebview.goBack()
        } else {
            super.onBackPressed()
        }
    }

}