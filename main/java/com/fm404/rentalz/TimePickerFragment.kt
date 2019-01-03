package com.fm404.rentalz

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import java.util.*

/**
 * Created by macaronlover22 on 02/12/2018
 */
class TimePickerFragment : android.support.v4.app.DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        return TimePickerDialog(activity, activity as TimePickerDialog.OnTimeSetListener, hour, minute, DateFormat.is24HourFormat(activity))
    }
}