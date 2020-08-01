package com.vegdev.vegacademy.utils

import com.google.firebase.Timestamp
import java.util.concurrent.TimeUnit

class GenericUtils {

    fun getDateDifference(date1: Timestamp, date2: Timestamp, timeUnit: TimeUnit) : Long {
        val diffInMillis = date2.seconds - date1.seconds
        return timeUnit.convert(diffInMillis, TimeUnit.SECONDS)
    }
}