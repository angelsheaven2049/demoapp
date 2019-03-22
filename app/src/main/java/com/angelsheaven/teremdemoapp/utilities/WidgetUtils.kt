package com.angelsheaven.teremdemoapp.utilities

import android.view.View
import android.widget.TextView
import com.androidadvance.topsnackbar.TSnackbar
import com.google.android.material.snackbar.Snackbar

fun View.mySnackBar(message: Int, backgroundColor: Int): TSnackbar {
    return TSnackbar.make(this, message, Snackbar.LENGTH_LONG).also {
        val snackBarView = it.view

        //Set textview aligns center
        val snackBarTextView = snackBarView
                .findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        snackBarTextView.textAlignment = View.TEXT_ALIGNMENT_CENTER

        //Change background color of snackbar
        val color =
            context.myGetColor(backgroundColor)

        it.view.setBackgroundColor(color)

    }
}

fun View.mySnackBar(message: String, backgroundColor: Int): TSnackbar {
    return TSnackbar.make(this, message, Snackbar.LENGTH_LONG).also {
        val snackBarView = it.view

        //Set textview aligns center
        val snackBarTextView = snackBarView
            .findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        snackBarTextView.textAlignment = View.TEXT_ALIGNMENT_CENTER

        //Change background color of snackbar
        val color =
            context.myGetColor(backgroundColor)

        it.view.setBackgroundColor(color)

    }
}
