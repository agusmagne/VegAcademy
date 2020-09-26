package com.vegdev.vegacademy.presenter.main

import android.webkit.WebView
import android.webkit.WebViewClient
import com.vegdev.vegacademy.contract.main.WebContract

class WebPresenter(
    private val iView: WebContract.View,
    private val iMainView: MainView
) : WebContract.Actions {

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

    override fun loadUrl(link: String) {
        iMainView.showWebProgressbar()

        val client = this.buildAndSetWebViewClient()
        iView.setWebClient(client)
        iView.loadUrl(link)
    }

    override fun exitWebFragment() {
        iMainView.hideWebProgressbar()
    }
}