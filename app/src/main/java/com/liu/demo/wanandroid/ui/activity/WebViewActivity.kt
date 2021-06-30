package com.liu.demo.wanandroid.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.webkit.*
import com.liu.demo.wanandroid.base.BaseActivity
import com.liu.demo.wanandroid.base.BaseApplication
import com.liu.demo.wanandroid.databinding.ActivityWebViewBinding

class WebViewActivity : BaseActivity<ActivityWebViewBinding>(ActivityWebViewBinding::inflate) {

    var url: String? = null
    var webView: WebView? = null

    companion object {
        fun startWebView(url: String) {
            BaseApplication.application.startActivity(
                Intent(
                    BaseApplication.application,
                    WebViewActivity::class.java
                ).putExtra("url", url)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_web_view)
        initWebView()
        init()
    }

    private fun init() {
        url = intent.getStringExtra("url")
        if (!TextUtils.isEmpty(url)) {
            binding.webView.loadUrl(url!!)
        }

        getDataFromBrowser(intent)
    }

    @SuppressLint("JavascriptInterface")
    private fun initWebView() {
        webView = binding.webView
        val setting = webView!!.settings
        setting.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
        setting.setSupportZoom(true)
        setting.useWideViewPort = true
        setting.loadWithOverviewMode = false
        setting.blockNetworkImage = false
        setting.builtInZoomControls = false
        setting.displayZoomControls = false
        setting.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        setting.allowFileAccess = true
        setting.domStorageEnabled = true
        setting.textZoom = 100
        setting.mediaPlaybackRequiresUserGesture = false
        true.also { setting.javaScriptEnabled = it }

        webView!!.setInitialScale(100)
        webView!!.webChromeClient = WebChromeClient()
        webView!!.webViewClient = object : WebViewClient() {
            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?
            ) {
                handler?.proceed()
            }
        }
        webView!!.addJavascriptInterface(this, "androidInjected")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (webView != null) {
            webView!!.clearCache(true)
            webView!!.removeAllViews()
            webView!!.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
            webView!!.stopLoading()
            webView!!.destroy()
            webView = null
        }
    }

    fun getDataFromBrowser(intent: Intent) {
        val data = intent.data
        if (data != null) {
            try {
                val scheme = data.scheme
                val host = data.host
                val path = data.path
                val url = "$scheme://$host$path"
                if (webView != null) {
                    webView!!.loadUrl(url)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}