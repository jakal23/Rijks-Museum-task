package com.rijksmuseum.task.util.presentation

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import com.rijksmuseum.task.R

abstract class SearchMenuProvider : MenuProvider {

    private val searchListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            if (query?.trim().isNullOrEmpty()) return false
            onTextChanged(query!!)
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            val filter = newText?.trim() ?: ""
            onTextChanged(filter)
            return true
        }
    }

    abstract fun onTextChanged(query: String)

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        // Add menu items here
        menuInflater.inflate(R.menu.search_menu, menu)
        menu.findItem(R.id.search)?.apply {
            val searchView = actionView as SearchView
            searchView.setOnQueryTextListener(searchListener)
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }

}