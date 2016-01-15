package com.example.wzhz.kotlinandroidtest1

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.CallLog
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.util.*

class MainActivity : AppCompatActivity() {

    val TAG: String = "MainActivity"

    var listView: ListView? = null

    var context: Context? = null

    var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this;

        val tvHello: TextView = findViewById(R.id.tv) as TextView
        tvHello.text = "hello, world"
        Log.e(TAG, "this is onCreate")

        var list: List<String> = ArrayList()

        for (i in 1..30) {
            list += "这是第 $i 项"
        }

        listView = findViewById(R.id.list_view) as ListView?
        listView?.adapter = MyAdapter(context!!, list)
        listView?.setOnItemClickListener { adapterView, view, i, l -> run {
            Toast.makeText(context, "onClick position=$i", Toast.LENGTH_SHORT).show()
            if (i == 22 && !TextUtils.isEmpty(password) && password.equals("12345")) {
                Toast.makeText(context, "you are right!", Toast.LENGTH_SHORT).show()
                val intent: Intent = Intent(context, CallLogActivity::class.java)
                startActivity(intent)
            }
        } }

        val etInput: EditText = findViewById(R.id.et) as EditText

        val btnClickMe: Button = findViewById(R.id.btn) as Button
        btnClickMe.setOnClickListener { v -> run {

            password = etInput.text.toString()
//
//            val hasCallsPermission = checkCallingOrSelfPermission(Manifest.permission.WRITE_CALL_LOG)
//            if (hasCallsPermission != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(arrayOf(Manifest.permission.WRITE_CALL_LOG), REQUEST_CODE_ASK_PERMISSIONS)
//                return@setOnClickListener
//            }
//            addCall()
        } }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>?, grantResults: IntArray?) {
        when (requestCode) {
            111 -> {Log.e(TAG, "requestCode=$requestCode")}
        }
    }

    internal class ViewHolder {
        var tvId: TextView? = null
        var tvName: TextView? = null
    }

    class MyAdapter(context: Context, list: List<String>) : BaseAdapter() {

        val context: Context = context

        val list: List<String> = list

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
            var v: View? = convertView
            var data: String? = getItem(position)

            var holder: ViewHolder

            if (v == null) {
                holder = ViewHolder()
                v = LayoutInflater.from(context).inflate(R.layout.item_list_view, parent, false)
                holder.tvId = v?.findViewById(R.id.tv_id) as TextView
                holder.tvName = v?.findViewById(R.id.tv_name) as TextView
                v?.tag = holder
            }

            holder = v?.tag as ViewHolder

            holder.tvId?.text = "id=${position.toString()}"
            holder.tvName?.text = "data=$data"

            return v

        }

        override fun getItem(position: Int): String? {
            return list[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return list.size
        }

    }

}
