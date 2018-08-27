package com.example.jordyg.foxlunches

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_fallback.*


class Fallback : Fragment() {

    public lateinit var listener: retryListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_fallback, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnRetry.setOnClickListener {
            this.listener.btnRetry()
        }
    }

    public interface retryListener {
        public fun btnRetry()
    }
}
