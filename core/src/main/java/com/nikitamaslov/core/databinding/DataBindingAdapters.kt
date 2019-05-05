package com.nikitamaslov.core.databinding

import android.content.res.Resources
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter

object DataBindingAdapters {

    @JvmStatic
    @BindingAdapter("stringResId")
    fun TextView.setStringResId(@StringRes stringResId: Int?) {
        val string = if (stringResId == null) null
        else try {
            context.getString(stringResId)
        } catch (e: Resources.NotFoundException) {
            null
        }
        this.text = string ?: ""
    }
}
