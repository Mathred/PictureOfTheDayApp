package com.example.pictureofthedayapp.ui.main.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.pictureofthedayapp.databinding.WikipediaSearchFragmentBinding

class WikipediaSearchFragment : TaggedFragment() {

    override fun getFragmentTag() = FRAGMENT_TAG
    override fun newInstance() = WikipediaSearchFragment()

    companion object {
        fun newInstance() = WikipediaSearchFragment()
        private const val BASE_WIKI_URL = "https://ru.wikipedia.org/w/index.php?search="
        const val FRAGMENT_TAG = "WikipediaSearchFragmentTag"
    }

    private var _binding: WikipediaSearchFragmentBinding? = null
    private val binding: WikipediaSearchFragmentBinding get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private var targetView: FrameLayout? = null
    private var contentView: FrameLayout? = null
    private var customViewCallback: WebChromeClient.CustomViewCallback? = null
    private var customView: View? = null
    private val browserChromeClient = BrowserChromeClient()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WikipediaSearchFragmentBinding.inflate(inflater, container, false).apply {
            initViews()
        }
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun WikipediaSearchFragmentBinding.initViews() {
        contentView = mainContent
        this@WikipediaSearchFragment.targetView = targetView
        browser.webChromeClient = browserChromeClient
        browser.webViewClient = WebViewClient()
        browser.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
        }

        tilSearch.setEndIconOnClickListener {
            val searchQuery = etSearch.text.toString()
            browser.loadUrl("$BASE_WIKI_URL$searchQuery")
        }

    }


    inner class BrowserChromeClient : WebChromeClient() {

        override fun onShowCustomView(view: View, callback: CustomViewCallback) {
            customViewCallback = callback
            targetView?.addView(view)
            customView = view
            contentView?.visibility = View.GONE
            targetView?.visibility = View.VISIBLE
            targetView?.bringToFront()
        }

        override fun onHideCustomView() {
            if (customView == null) return
            customView?.visibility = View.GONE
            targetView?.removeView(customView)
            customView = null
            targetView?.visibility = View.GONE
            customViewCallback?.onCustomViewHidden()
            contentView?.visibility = View.VISIBLE
        }
    }

}