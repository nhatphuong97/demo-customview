package com.example.customView

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import android.widget.NumberPicker
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.kotlininmyeye.R
import kotlinx.android.synthetic.main.custompicker.view.*
import kotlinx.android.synthetic.main.fragment_first.*

/**
 * Created by Ông Hoàng Nhật Phương on 9/18/2020.
 * Copyright © 2020 VNPT IT 3. All rights reserved.
 */
class NumberCustom @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttrs) {
    init {
        init(attrs)
    }

    var minValue1: Int = 0
        set(value) {
            field = value
            updateNumber()
        }

    private fun updateNumber() {
        focus_value.text = minValue1.toString()
        focus_value.startAnimation(AnimationUtils.loadAnimation(focus_value.context, android.R.anim.fade_in));
        picker.apply {
            maxValue = maxValue1
            minValue = minValue1


        }
        picker.setOnValueChangedListener { p0, oldVal, newVal ->
            if (p0 != null)
                focus_value.text = p0.value.toString()
        }
    }
//
//    private fun customPicker() {
//        //Custom.selec
//        changeDividerColor(Custom, Color.parseColor("#00ffffff"));
//    }

    private fun changeDividerColor(picker: NumberPicker, color: Int) {
        val pickerFields =
            NumberPicker::class.java.declaredFields
        for (pf in pickerFields) {
            if (pf.name == "mSelectionDivider") {
                pf.isAccessible = true
                try {
                    val colorDrawable = ColorDrawable(color)
                    pf[picker] = colorDrawable
                } catch (e: IllegalArgumentException) {
                    e.printStackTrace()
                } catch (e: Resources.NotFoundException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }
                break
            }
        }
    }


    var maxValue1: Int = 0
        set(value) {
            field = value
            updateNumber()
        }

    fun init(attrs: AttributeSet?) {
        LayoutInflater.from(context).inflate(R.layout.custompicker, this, true)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.NumberCustom_Layout)
        try {
            minValue1 = typedArray.getInt(R.styleable.NumberCustom_Layout_minValue, 0)
            maxValue1 = typedArray.getInt(R.styleable.NumberCustom_Layout_maxValue, 0)
        } finally {
            typedArray.recycle()
        }
        changeDividerColor(picker, Color.parseColor("#00ffffff"));
    }

}