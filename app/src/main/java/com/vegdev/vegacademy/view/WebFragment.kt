package com.vegdev.vegacademy.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.vegdev.vegacademy.R
import kotlinx.android.synthetic.main.fragment_web.*


class WebFragment : Fragment() {

    private val args: WebFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_web, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val link = args.link
//        web_view.webViewClient = object : WebViewClient() {
//            override fun onPageFinished(view: WebView?, url: String?) {
//                super.onPageFinished(view, url)
//                if (web_view.progress == 100) {
//                    progress_bar.visibility = View.INVISIBLE
//                }
//            }
//        }
        web_view.loadUrl(link)
    }

}