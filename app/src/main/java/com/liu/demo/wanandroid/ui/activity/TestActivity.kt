package com.liu.demo.wanandroid.ui.activity
import android.os.Build
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import com.liu.demo.wanandroid.R
import com.liu.demo.wanandroid.view.dialog.TestDialog

class TestActivity : AppCompatActivity() {

    var webView:WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        findViewById<AppCompatTextView>(R.id.tv).setOnClickListener {
//            object: BlurDialog(this@TestActivity, 0){
////
////                override fun onCreate(savedInstanceState: Bundle?) {
////                    super.onCreate(savedInstanceState)
////                    val tv = TextView(this@TestActivity)
////                    tv.text = "Dialog"
////                    tv.gravity = Gravity.CENTER
////                    tv.setBackgroundColor(0x66FFFFFF)
////                    setContentView(tv, ViewGroup.LayoutParams(600, 360))
////                    setContentView(R.layout.item_article)
////                    val lp = window!!.attributes
////                    lp.dimAmount = 0f
////                }
//            }.show()
            if (webView != null) {
                webView!!.loadUrl("http://www.baidu.com")
            }
        }

        findViewById<AppCompatTextView>(R.id.tv2).setOnClickListener {
            TestDialog(this, R.style.load_dialog).show()
        }
        initWebView()

    }

    fun initWebView() {
         webView = findViewById<WebView>(R.id.webView)

        val ws = webView!!.settings
        // 网页内容的宽度是否可大于WebView控件的宽度
        ws.loadWithOverviewMode = false
        // 保存表单数据
        ws.saveFormData = true
        // 是否应该支持使用其屏幕缩放控件和手势缩放
        ws.setSupportZoom(true)
        ws.builtInZoomControls = false
        ws.displayZoomControls = false

        // 设置缓存模式
        // 设置缓存模式
        ws.cacheMode = WebSettings.LOAD_NO_CACHE

        // ws.setAppCacheMaxSize(1024*1024*40);
        // setDefaultZoom  api19被弃用
        // 设置此属性，可任意比例缩放。
        ws.useWideViewPort = true
        // 不缩放
        webView!!.setInitialScale(100)
        //启用JavaScript执行。默认的是false。
        ws.javaScriptEnabled = true
        //  页面加载好以后，再放开图片
        ws.blockNetworkImage = false
        // 使用localStorage则必须打开
        ws.domStorageEnabled = true
        // 排版适应屏幕
        ws.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        // WebView是否新窗口打开(加了后可能打不开网页)
//        ws.setSupportMultipleWindows(true);
        ws.allowFileAccessFromFileURLs = false
        ws.allowUniversalAccessFromFileURLs = true

        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ws.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            //  CookieManager.getInstance().acceptThirdPartyCookies(webView);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            //WebView加载的网页播放声音是否需要用户的手势触发  默认时true
            ws.mediaPlaybackRequiresUserGesture = false
        }

        /** 设置字体默认缩放大小(改变网页字体大小,setTextSize  api14被弃用)*/
        ws.textZoom = 100

        webView!!.webViewClient = WebViewClient()
        webView!!.webChromeClient = WebChromeClient()

    }
}