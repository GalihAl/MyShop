package com.galihalgiffar.myshop.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class TextViewStyleBold(context:Context, attrs: AttributeSet): AppCompatTextView(context, attrs) {
    init {
        applyFont()
    }

    private fun applyFont() {
        val typeFace: Typeface = Typeface.createFromAsset(context.assets,"Montserrat-Bold.ttf" )
        typeface = typeFace
    }
}