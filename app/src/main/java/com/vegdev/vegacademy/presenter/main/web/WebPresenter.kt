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
                webView?.let {
                    super.onPageFinished(it, url)
                    if (it.progress == 100) iMainView.hideWebProgressbar()
                }
            }
        }
    }

    fun loadUrl(link: String) {
        iMainView.showWebProgressbar()

        val client = this.buildAndSetWebViewClient()
        view.setWebClient(client)
        view.loadUrl(link)
    }

    fun exitWebFragment() {
        iMainView.hideWebProgressbar()
    }
}