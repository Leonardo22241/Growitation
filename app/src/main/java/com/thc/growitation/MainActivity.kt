package com.thc.growitation

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.switchmaterial.SwitchMaterial
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.*
import org.json.JSONObject
import java.net.ConnectException

class MainActivity : AppCompatActivity() {

    @SuppressLint("UseSwitchCompatOrMaterialCode", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val response:TextView = findViewById(R.id.response)
        val method:TextView = findViewById(R.id.method)
        method.text = "POST"
        val url:EditText = findViewById(R.id.inputField)
        val client = HttpClient(CIO)
        val buttonSend:Button = findViewById(R.id.buttonSend)
        val switchMethod:SwitchMaterial = findViewById(R.id.switchMethod)

        switchMethod.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                method.text = "GET"
            } else {
                method.text = "POST"
            }
        }

        buttonSend.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO){
                getInfo(url.text.toString(), response, client, method.text.toString())
            }
        }
    }

    private suspend fun getInfo(url:String, responseTextView: TextView, client: HttpClient, method: String){
            if (method == "GET"){
                try {
                    val response: String = client.get(url)
                    responseTextView.text = "Response : $response"
                }
                catch (error:URLParserException) {
                    responseTextView.text = "That didn't work !\nCheck URL address."
                }
                catch (error:ClientRequestException){
                    responseTextView.text = "That didn't work !\nCheck URL address."
                }
            }

        if (method == "POST"){
            try {
                val response: String = client.post(url){
                    header("Hello", "World")
                }
                responseTextView.text = "Response : $response"
            }
            catch (error:URLParserException) {
                responseTextView.text = "That didn't work !\nCheck URL address.\nError : $error"
            }
            catch (error:ClientRequestException){
                responseTextView.text = "That didn't work !\nCheck URL address.\nError : $error"
            }
            catch (error:ConnectException){
                responseTextView.text = "That didn't work !\nCheck URL address.\nError : $error"
            }
            catch (error:Exception){
                responseTextView.text = "That didn't work !\nCheck URL address.\nError : $error"
            }
        }
    }

}