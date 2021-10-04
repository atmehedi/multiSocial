package com.mehedi.multi_social.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.webkit.WebSettingsCompat
import com.mehedi.multi_social.R

class TestActivity : AppCompatActivity() {
    lateinit var webview:WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        webview = findViewById(R.id.webview)
        webview.loadUrl("https://www.facebook.com/")
        webview.settings.javaScriptEnabled = true
        webview.webViewClient = WebViewClient()
        webview.webChromeClient = WebChromeClient()
        WebSettingsCompat.setForceDark(webview.settings, WebSettingsCompat.FORCE_DARK_ON)
    }
}