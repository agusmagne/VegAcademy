package com.vegdev.vegacademy.view.main.web

import android.webkit.WebViewClient

interface WebView {

    fun loadUrl(url: String)
    fun setWebClient(client: WebViewClient)
    fun hideProgressBar()

}