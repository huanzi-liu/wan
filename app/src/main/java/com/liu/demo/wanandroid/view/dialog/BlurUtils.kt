package com.liu.demo.wanandroid.view.dialog

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.Log
import kotlin.math.roundToInt

object BlurUtils {

    var renderScript: RenderScript? = null
    var scriptIntrinsicBlur: ScriptIntrinsicBlur? = null

    @JvmStatic
    fun blur(context: Context, source: Bitmap, radius: Float, scale: Float): Bitmap {
        val width = (source.width * scale).roundToInt()
        val height = (source.height * scale).roundToInt()
        Log.i("BLUR","$width ---- $height ==== ${source.width}-----${source.height}")
        val inputBitmap = Bitmap.createScaledBitmap(source, width, height, true)

        if(renderScript == null) {
           renderScript = RenderScript.create(context.applicationContext)
        }

        val input = Allocation.createFromBitmap(renderScript, inputBitmap)
        val output = Allocation.createTyped(renderScript, input.type)

        if (scriptIntrinsicBlur == null) {
            scriptIntrinsicBlur =
                ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
        }
        scriptIntrinsicBlur!!.setInput(input)
        scriptIntrinsicBlur!!.setRadius(radius)
        scriptIntrinsicBlur!!.forEach(output)
        output.copyTo(inputBitmap)
        renderScript!!.destroy()

        return inputBitmap

    }
}

object BlurUtils2 {
    private var renderScript: RenderScript? = null
    private var scriptIntrinsicBlur: ScriptIntrinsicBlur? = null
    fun blur(context: Context, source: Bitmap, radius: Int, scale: Float): Bitmap {
        val width = Math.round(source.width * scale)
        val height = Math.round(source.height * scale)
        val inputBmp = Bitmap.createScaledBitmap(source, width, height, true)
        if (renderScript == null) {
            renderScript = RenderScript.create(context.applicationContext)
        }
        // Allocate memory for Renderscript to work with
        val input = Allocation.createFromBitmap(renderScript, inputBmp)
        val output = Allocation.createTyped(renderScript, input.type)
        // Load up an instance of the specific script that we want to use.
        if (scriptIntrinsicBlur == null) {
            scriptIntrinsicBlur =
                ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
        }
        scriptIntrinsicBlur!!.setInput(input)
        // Set the blur radius
        scriptIntrinsicBlur!!.setRadius(radius.toFloat())
        // Start the ScriptIntrinisicBlur
        scriptIntrinsicBlur!!.forEach(output)
        // Copy the output to the blurred bitmap
        output.copyTo(inputBmp)
        renderScript!!.destroy()
        return inputBmp
    }
}