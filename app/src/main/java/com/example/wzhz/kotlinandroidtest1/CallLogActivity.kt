package com.example.wzhz.kotlinandroidtest1

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.CallLog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.util.*

/**
 * Created by wzhz on 2016/1/15.
 */

class CallLogActivity : AppCompatActivity() {

    val TAG: String = "CallLogActivity"

    var mContext: Context? = null

    val mCalendar: Calendar = Calendar.getInstance()

    val REQUEST_CODE_ASK_PERMISSIONS: Int = 111


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_log)

        mContext = this;

        val etNumber: EditText = findViewById(R.id.et_number) as EditText

        val etDuration: EditText = findViewById(R.id.et_duration) as EditText

        val btnDate: Button = findViewById(R.id.btn_date) as Button

        val btnTime: Button = findViewById(R.id.btn_time) as Button

        val btnOk: Button = findViewById(R.id.btn_ok) as Button

        btnDate.setOnClickListener { v -> run {
            val datePickerDialog: DatePickerDialog = DatePickerDialog(mContext,
                    DatePickerDialog.OnDateSetListener { datePicker, year, month, day -> mCalendar.set(year, month, day)},
                    mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH))

            datePickerDialog.show()
        } }

        btnTime.setOnClickListener { v -> run {
            val timePickerDialog: TimePickerDialog = TimePickerDialog(mContext,
                    TimePickerDialog.OnTimeSetListener { timePicker, hour, minute -> run {
                        mCalendar.set(Calendar.HOUR_OF_DAY, hour)
                        mCalendar.set(Calendar.MINUTE, minute)
                    } }, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true)

            timePickerDialog.show()
        } }

        btnOk.setOnClickListener { v -> run {
            val number: String = etNumber.text.toString()

            val during: String = etDuration.text.toString()

            addCall(number, during, mCalendar.timeInMillis)

        } }

    }

    fun addCall(number: String, during: String, time: Long) {

        val hasCallsPermission = checkCallingOrSelfPermission(Manifest.permission.WRITE_CALL_LOG)
        if (hasCallsPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.WRITE_CALL_LOG), REQUEST_CODE_ASK_PERMISSIONS)
            return
        }

        val content: ContentValues = ContentValues()
        content.put(CallLog.Calls.TYPE, CallLog.Calls.INCOMING_TYPE)
        content.put(CallLog.Calls.NUMBER, number)
        content.put(CallLog.Calls.DATE, time)
        content.put(CallLog.Calls.NEW, "0")
        content.put(CallLog.Calls.DURATION, Integer.valueOf(during))
        contentResolver.insert(CallLog.Calls.CONTENT_URI, content)

        Toast.makeText(mContext, "add success!!", Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>?, grantResults: IntArray?) {
        when (requestCode) {
            REQUEST_CODE_ASK_PERMISSIONS -> {
                Log.e(TAG, "requestCode=$requestCode")
            }

        }
    }

}