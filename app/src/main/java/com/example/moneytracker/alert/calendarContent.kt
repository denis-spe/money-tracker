package com.example.moneytracker.alert

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue
import java.time.Month
import java.util.Date
import java.util.Locale

@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.O)
fun calenderContent(
    mContext: Context,
    dayOfWeekState: MutableState<TextFieldValue>,
    dayState: MutableState<TextFieldValue>,
    monthState: MutableState<TextFieldValue>,
    yearState: MutableState<TextFieldValue>,
): DatePickerDialog{
    // Declare the integer values
    // for year, month, day and day of the week
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    // Initializing a calendar
    val mCalendar = Calendar.getInstance()

    // Fetch current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    // Declaring DataPickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        {_: DatePicker, year: Int, month: Int, mDayOfMonth: Int ->
            run {
                val weekDay = getDayOfWeek("$mDayOfMonth/${month + 1}/$year")
                dayOfWeekState.value = TextFieldValue(weekDay)
                dayState.value = TextFieldValue(mDayOfMonth.toString())
                monthState.value = TextFieldValue(getMonthName(month))
                yearState.value = TextFieldValue(year.toString())
            }
        }, mYear, mMonth, mDay
    )

    return mDatePickerDialog

}

/*
Represents month name
@params index: Int of year
 */
fun getMonthName(index: Int): String {
    return Month.values()[index].toString().lowercase()
        .replaceFirstChar { it.uppercase() }
        .substring(0, 3)
}

fun getDayOfWeek(dateString: String): String {
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val date = format.parse(dateString)
    val calendar = Calendar.getInstance()
    calendar.time = date

    // Convert the day of the week to a string representation
    val dayOfWeekString = when (calendar.get(Calendar.DAY_OF_WEEK)) {
        Calendar.SUNDAY -> "Sun"
        Calendar.MONDAY -> "Mon"
        Calendar.TUESDAY -> "Tues"
        Calendar.WEDNESDAY -> "Wed"
        Calendar.THURSDAY -> "Thu"
        Calendar.FRIDAY -> "Fri"
        Calendar.SATURDAY -> "Sat"
        else -> throw IllegalArgumentException("Invalid day of the week")
    }

    return dayOfWeekString
}