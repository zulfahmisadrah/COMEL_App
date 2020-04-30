package com.zulfahmi.comelapp.util

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*


object Commons {

    fun showDatePickerDialog(context: Context, view: EditText) {
        val calendar: Calendar = Calendar.getInstance()
        DatePickerDialog(
            context,
            DatePickerDialog.OnDateSetListener { _, year, month, day ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)
                val myFormat = "dd/MM/yy"
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                view.setText(sdf.format(calendar.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
            .show()
    }

    fun showTimePickerDialog(context: Context, view: EditText) {
        val calendar: Calendar = Calendar.getInstance()
        TimePickerDialog(
            context,
            TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                val myFormat = "HH:mm"
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                view.setText(sdf.format(calendar.time))
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false
        )
            .show()
    }

    fun showSelector(context: Context, title: String, items: Array<String>, onClick: (DialogInterface, Int) -> Unit) {
        val alertBuilder = AlertDialog.Builder(context)
        alertBuilder.setTitle(title)
        alertBuilder.setItems(items) { dialog, which ->
            onClick(dialog, which)
        }.show()
    }

    fun countWordinString(word: String, fromString: String, splitter: Char): Int {
        val temp: List<String> = fromString.split(splitter)
        var count = 0
        for (i in temp.indices) {
            if (word == temp[i]) count++
        }
        return count
    }

    fun deleteItemDialog(context: Context, childName: String, id: String) {
        val dialogTitle = "Delete"
        val dialogMessage = "Are you sure want to delete this data?"
        val toastMessage = "Data has been deleted"
        CustomConfirmDialog(context, dialogTitle, dialogMessage) {
            FirebaseDatabase.getInstance().reference.child("/$childName/$id").removeValue()
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
        }.show()
    }

    fun convertToDate(input: String): String {
        val year = input.subSequence(0,4)
        val monthNumber = Integer.parseInt(input.subSequence(4,6).toString())
        val day = input.subSequence(6,8)
        val hour = input.subSequence(8, 10)
        val minute = input.subSequence(10, 12)

        val monthDate = SimpleDateFormat("MMMM", Locale.getDefault())

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, monthNumber)
        val month = monthDate.format(calendar.time)

        val date = "$day $month $year $hour:$minute"

        return date
    }
}