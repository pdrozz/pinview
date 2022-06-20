package com.pdrozz.view

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.os.Build
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun View.setColorFilter(@ColorRes colorID: Int) {
    if(this.background == null) return

    val color = ContextCompat.getColor(context, colorID)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        this.background?.colorFilter = BlendModeColorFilter(
            color, BlendMode.SRC_ATOP
        )
    } else {
        this.background?.setColorFilter(
            color, PorterDuff.Mode.SRC_ATOP
        )
    }
}