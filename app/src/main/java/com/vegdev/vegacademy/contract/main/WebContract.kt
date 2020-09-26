package com.vegdev.vegacademy.contract.main

import android.webkit.WebViewClient

interface WebContract {
    interface View {
        fun loadUrl(url: String)
        fun setWebClient(client: WebViewClient)
    }
    interface Actions {
        fun loadUrl(link: String)
        fun exitWebFragment()
    }
}