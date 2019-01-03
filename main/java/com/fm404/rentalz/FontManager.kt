package com.fm404.rentalz

import android.content.Context
import android.graphics.Typeface

/**
 * Created by macaronlover22 on 02/12/2018
 */
object FontManager {

    private val ROOT = "fonts/"
    internal val FONTAWESOME = ROOT + "fontawesome-webfont.ttf"

    internal fun getTypeface(context: Context, font: String): Typeface {
        return Typeface.createFromAsset(context.assets, font)
    }
}