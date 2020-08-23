package cz.lamorak.koti.extensions

import android.view.View

fun View.setVisible(visible: Boolean) {
    val visibility = if (visible) View.VISIBLE else View.GONE
    if (this.visibility != visibility) {
        setVisibility(visibility)
    }
}