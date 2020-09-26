package com.vegdev.vegacademy.view.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.contract.main.MainContract
import com.vegdev.vegacademy.contract.main.WebContract
import com.vegdev.vegacademy.presenter.main.WebPresenter
import kotlinx.android.synthetic.main.web.*


class WebFragment : Fragment(), WebContract.View {

    private val args: WebFragmentArgs by navArgs()
    private var presenter: WebPresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.web, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter?.loadUrl(args.link)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainContract.View) presenter = WebPresenter(this, context)
    }

    override fun onDetach() {
        super.onDetach()
        presenter?.exitWebFragment()
        presenter = null
    }

    override fun loadUrl(url: String) {
        web_view.loadUrl(url)
    }

    override fun setWebClient(client: WebViewClient) {
        web_view.webViewClient = client
    }
}