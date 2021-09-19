package com.example.typicodephotos

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

@Suppress("DEPRECATION")
class Async(var context:Context):AsyncTask<String,Int,String>() {



    override fun doInBackground(vararg params: String?): String? {
        val url = URL("https://jsonplaceholder.typicode.com/photos")

        //https://www.googleapis.com/books/v1/volumes?q=quilting

//        print(url.openConnection())

        val httpClient = url.openConnection() as HttpURLConnection

//        print(httpClient)

        if(httpClient.responseCode == HttpURLConnection.HTTP_OK) {

//            print(httpClient.responseCode)
//            print(HttpURLConnection.HTTP_OK)
//
//            var httpInput=httpClient.inputStream
//            print(httpInput)
//
//            var httpInputStreamReader=InputStreamReader(httpInput)
//            print(httpInputStreamReader)
//
//
//            var httpBufferReader=BufferedReader(httpInputStreamReader)
//            print(httpBufferReader)


            val bufferedReader = BufferedReader(InputStreamReader(httpClient.inputStream))
            //bufferedReader.readLine()
            val stringBuilder = StringBuilder()
            bufferedReader.forEachLine {

                stringBuilder.append(it)

            }
            return stringBuilder.toString()
        }

        return null
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        val intent = Intent().also {
            it.setAction("result")
            it.putExtra("Result",result)
        }
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)

    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
    }
}