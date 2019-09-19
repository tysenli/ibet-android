package com.app.android.ibet.api

import com.app.android.ibet.BuildConfig
import com.app.android.ibet.activity.Login.Login
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import java.lang.Exception

import java.io.IOException





class Api {
/*
    fun post(json : String, url : String):  String? {

        val client = OkHttpClient()

        val JSON = MediaType.get("application/json; charset=utf-8")
        val body = RequestBody.create(JSON, json)
        try {
            val request = Request.Builder()
            // .addHeader("Authorization", "Bearer $token")
                .url(url)
                .post(body)
                .build()

            val response = client.newCall(request).execute()
            //println(response.code())

            if (response.isSuccessful) {
                val res = response.body()!!.string()
                //println(response.request())
                return res
            }
            return null

        }
        catch (e : Exception) {
            throw e
        }
    }
*/

/*
    fun get(url : String) : String? {
        try {
            val request = Request.Builder()
                .header("Authorization", "Token " + Login.token)
                //  .url(Filename/....)
                .url(url)
                .build()
            val response = OkHttpClient().newCall(request).execute()
            if (response.isSuccessful) {
                return response.body()!!.string()
            }
            return null
        }
        catch (e : Exception) {
            throw e
        }
    }

*/


    @Throws(IOException::class)
    fun get(url: String): String {
        var client = OkHttpClient()
        val request = Request.Builder()
            .header("Authorization", "Token " + Login.token)
            .url(url)
            .build()

        client.newCall(request).execute().use { response -> return response.body()!!.string() }
    }





    @Throws(IOException::class)
    fun post( json: String, url: String): String {
        val JSON = MediaType.get("application/json; charset=utf-8")
        var client = OkHttpClient()
        val body = RequestBody.create(JSON, json)
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()
        client.newCall(request).execute().use { response -> return response.body()!!.string() }
    }

    fun myLog(log : String) {
        val logJson = JSONObject()
        logJson.put("line", log)
        logJson.put("source", "Android")
        val message = Api().post(logJson.toString(), BuildConfig.LOG)
        println(message)
    }

}