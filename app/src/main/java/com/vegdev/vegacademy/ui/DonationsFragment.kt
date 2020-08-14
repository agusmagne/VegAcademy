package com.vegdev.vegacademy.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.vegdev.vegacademy.R
import kotlinx.android.synthetic.main.fragment_donations.*

class DonationsFragment : Fragment() {

    private val DONATE_1_URL =
        "https://www.mercadopago.com.ar/checkout/v1/redirect?pref_id=227730221-4fdf84a3-5b72-4ea6-a47f-4c5b39b8e994"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_donations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        donate_1.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(DONATE_1_URL))
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.action_donate).isVisible = false
    }
}