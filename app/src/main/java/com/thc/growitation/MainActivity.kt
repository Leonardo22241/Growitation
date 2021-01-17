package com.thc.growitation

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    @SuppressLint("UseSwitchCompatOrMaterialCode", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val response:TextView = findViewById(R.id.response)
        val method:TextView = findViewById(R.id.method)
        method.text = "POST"
        val url:EditText = findViewById(R.id.textField)
        val buttonSend:Button = findViewById(R.id.buttonSend)
        val queue = Volley.newRequestQueue(this)
        val switchMethod:Switch = findViewById(R.id.switchMethod)

        switchMethod.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                method.text = "GET"
            } else {
                method.text = "POST"
            }
        }

        buttonSend.setOnClickListener {
            getInfo(url.text.toString(), response, queue, method.text.toString())
        }

    }

    private fun getInfo(url:String, responseTextView:TextView, queue: RequestQueue, method:String){
        if (method == "GET"){
            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener { response ->
                    responseTextView.text = "Response: %s".format(response.toString())
                },
                Response.ErrorListener { responseTextView.text = "That didn't work!" })

            queue.add(jsonObjectRequest)
        }
        if (method == "POST"){
            val jsonData = JSONObject()

            val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, jsonData,
                Response.Listener { response ->
                    responseTextView.text = "Response: %s".format(response.toString())
                },
                Response.ErrorListener { responseTextView.text = "That didn't work!" })

            queue.add(jsonObjectRequest)
        }

    }

}