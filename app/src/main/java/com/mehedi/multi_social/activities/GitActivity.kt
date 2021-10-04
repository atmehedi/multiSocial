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
import kotlinx.android.synthetic.main.activity_git.*

class GitActivity : AppCompatActivity() {
    lateinit var gitwebview:WebView
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_git)
        gitwebview = findViewById(R.id.gitwebview)
        sharedPreferences = getSharedPreferences(getString(R.string.shared_preferences_file),
            Context.MODE_PRIVATE)
        val theme = intent.getStringExtra("theme")

        if (theme == "Dark"){
            println("dark theme applied")
            if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {

                WebSettingsCompat.setForceDark(gitwebview.settings, WebSettingsCompat.FORCE_DARK_ON)
            }
        }
        else
        {
            println("Light theme applied")
        }

        if (ConnectionManager().checkConnectivity(this)) {
            gitwebview = findViewById(R.id.gitwebview)
            val userAgent = System.getProperty("http.agent")
            gitwebview.settings.userAgentString = userAgent
            gitwebview.loadUrl("https://github.com/")
            gitwebview.settings.javaScriptEnabled = true

            gitwebview.webViewClient = WebViewClient()
            gitwebview.webChromeClient = WebChromeClient()

            gitwebview.webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    progressbargit.visibility = View.VISIBLE
                    super.onPageStarted(view, url, favicon)
                }
                override fun onLoadResource(view: WebView?, url: String?) {
                    if (url != null) {
                        if (!url.startsWith("http") || !url.startsWith("https")){
                            gitwebview.stopLoading()
                            gitwebview.goBack()
                            Toast.makeText(this@GitActivity,"sorry !!non functional area.", Toast.LENGTH_SHORT).show()
                        }
                    }
                    super.onLoadResource(view, url)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    progressbargit.visibility = View.GONE
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
        if (gitwebview.canGoBack()) {
            gitwebview.goBack()
        } else {
            super.onBackPressed()
        }
    }
}