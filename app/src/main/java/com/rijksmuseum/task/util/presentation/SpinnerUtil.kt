package com.rijksmuseum.task.util.presentation

import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner

object SpinnerUtil {

    fun Spinner.setOnItemSelectedListener(onItemSelected: (Int) -> Unit) {
        this.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                onItemSelected(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // nothing to do
            }
        }
    }

    fun Spinner.bindData(list: List<String>) {
        adapterFromList(list).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            this.adapter = adapter
        }
    }

    private fun Spinner.adapterFromList(list: List<String>): ArrayAdapter<String> {
        return ArrayAdapter(
            context,
            android.R.layout.simple_spinner_item,
            list
        )
    }
}
