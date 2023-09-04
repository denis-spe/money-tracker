package com.example.moneytracker.date

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.Month
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun getCurrentDate(): Map<String, String> {
    // Get the current date.
    val calendar = Calendar.getInstance()

    // Declare the integer values
    // for year, month, day and day of the week

    // Fetch current year, month and day
    val mYear: String = (calendar.get(Calendar.YEAR)).toString()
    val mMonth: String = (calendar.get(Calendar.MONTH) + 1).toString()
    val mDay: String = (calendar.get(Calendar.DAY_OF_MONTH)).toString()

    val weekDay = getDayOfWeek("$mDay/${mMonth}/$mYear")

    return mapOf(
        "dayOfWeek" to weekDay,
        "dayOfMonth" to mDay,
        "month" to getMonthName(mMonth.toInt() - 1),
        "year" to mYear
    )
}

@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.O)
fun calenderContent(
    mContext: Context,
    dayOfWeekState: MutableState<TextFieldValue>,
    dayState: MutableState<TextFieldValue>,
    monthState: MutableState<TextFieldValue>,
    yearState: MutableState<TextFieldValue>,
): DatePickerDialog {
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
        { _: DatePicker, year: Int, month: Int, mDayOfMonth: Int ->
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ClickableDateText(
    dayOfWeekState: MutableState<TextFieldValue>,
    dayState: MutableState<TextFieldValue>,
    monthState: MutableState<TextFieldValue>,
    yearState: MutableState<TextFieldValue>,
    mContext: Context = LocalContext.current
) {

    Column(modifier = Modifier
        .clickable {
            calenderContent(
                mContext,
                dayOfWeekState = dayOfWeekState,
                dayState = dayState,
                monthState = monthState,
                yearState = yearState,
            ).show()
        }
        .fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Date range",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(7.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {

                val day = dayState.value.text

                if (day == "") {
                    val mCalendar = Calendar.getInstance()

                    // Get the current date: currentDate
                    val currentDate: Map<String, String> = getCurrentDate()

                    val dayOfMonth = currentDate["dayOfMonth"]

                    Text(
                        text = "${currentDate["dayOfWeek"]}, ${
                            if (dayOfMonth?.length!! > 1) dayOfMonth else "0$dayOfMonth"
                        }" +
                                " ${currentDate["month"]} ${currentDate["year"]}",
                        fontSize = 18.sp
                    )
                } else {
                    Text(
                        text = "${dayOfWeekState.value.text}, ${dayState.value.text}" +
                                " ${monthState.value.text}" +
                                " ${yearState.value.text}",
                        fontSize = 18.sp
                    )
                }

            }

        }
    }
}