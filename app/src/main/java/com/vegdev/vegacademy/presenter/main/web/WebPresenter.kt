package com.vegdev.vegacademy.presenter.main.web

import android.webkit.WebView
import android.webkit.WebViewClient
import com.vegdev.vegacademy.view.main.main.MainView

class WebPresenter(
    val view: com.vegdev.vegacademy.view.main.web.WebView,
    private val iMainView: MainView
) {

    private fun buildAndSetWebViewClient(): WebViewClient {
        return object : WebViewClient() {
            override fun onPageFinished(webView: WebView?, url: String?) {
                super.onPageFinished(webView, url)
                webView?.let { if (it.progress == 100) view.hideProgressBar() }
            }
        }
    }

    fun loadUrl(link: String) {
        iMainView.hideToolbar()
        val client = this.buildAndSetWebViewClient()
        view.setWebClient(client)
        view.loadUrl(link)
    }

    fun exitWebView() {
        iMainView.showToolbar()
    }

}