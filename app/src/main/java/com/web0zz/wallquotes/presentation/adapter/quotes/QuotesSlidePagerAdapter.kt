package com.web0zz.wallquotes.presentation.adapter.quotes

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class QuotesSlidePagerAdapter(
    quoteFragment: Fragment,
    private val pageCount: Int,
    private val quotesInstance: (Int) -> Fragment
) : FragmentStateAdapter(quoteFragment) {
    override fun getItemCount(): Int = pageCount

    override fun createFragment(position: Int): Fragment = quotesInstance(position)
}