package com.app.android.ibet.util

import android.util.Log
import com.app.android.ibet.model.GameModel

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.nio.charset.Charset

class QueryUtils {
    companion object {
        private val  LogTag = this::class.java.simpleName
        private const val BaseURL = "http://10.0.2.2:8000/users/Api/games/?term="


        fun fetchGameData(jsonQueryString: String): ArrayList<GameModel>? {
            val url: URL? = createUrl("${this.BaseURL }$jsonQueryString")
            var jsonResponse: String? = null
           //val (request, response, result) = "http://10.0.2.2:8000/users/api/games/?term=Casino".httpGet().response()
            try {
                jsonResponse = makeHttpRequest(url)
            }
            catch (e: IOException) {
                Log.e(this.LogTag, "Problem making the HTTP request.", e)
            }
            return extractDataFromJson(jsonResponse)



        }


        private fun createUrl(stringUrl: String): URL? {
            var url: URL? = null
            try {
                url = URL(stringUrl)
            } catch (e: MalformedURLException) {
                Log.e(this.LogTag, "Problem building the URL.", e)
            }
            return url
        }

        private fun makeHttpRequest(url: URL?): String {
            var jsonResponse = ""

            if (url == null) {
                return jsonResponse
            }

            var urlConnection: HttpURLConnection? = null
            var inputStream: InputStream? = null
            try {
                urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.readTimeout = 10000 // 10 seconds
                urlConnection.connectTimeout = 15000 // 15 seconds
                urlConnection.requestMethod = "GET"
                urlConnection.connect()

                if (urlConnection.responseCode == 200) {
                    inputStream = urlConnection.inputStream
                    jsonResponse = readFromStream(inputStream)
                }
                else {
                    Log.e(this.LogTag, "Error response code: ${urlConnection.responseCode}")
                }
            }
            catch (e: IOException) {
                Log.e(this.LogTag, "Problem retrieving the product data results: $url", e)
            }
            finally {
                urlConnection?.disconnect()
                inputStream?.close()
            }
            println(jsonResponse)
            return jsonResponse
        }

        private fun readFromStream(inputStream: InputStream?): String {
            val output = StringBuilder()
            if (inputStream != null) {
                val inputStreamReader = InputStreamReader(inputStream, Charset.forName("UTF-8"))
                val reader = BufferedReader(inputStreamReader)
                var line = reader.readLine()
                while (line != null) {
                    output.append(line)
                    line = reader.readLine()
                }
            }

            return output.toString()
        }

        private fun extractDataFromJson(gameJson: String?): ArrayList<GameModel>? {
            //if (TextUtils.isEmpty(gameJson)) {
             //   return null
            //}
            val gameList = ArrayList<GameModel>()

            try {
                //val baseJasonResponse = JSONObject(gameJson)

                val gameArray = JSONArray(gameJson)
                for (i in 0 until gameArray.length()) {
                    val gameObject = gameArray.getJSONObject(i)
                    gameList.add(
                        GameModel(
                            //name
                            returnValueOrDefault<String>(gameObject, "name") as String,
                            returnValueOrDefault<String>(gameObject, "image") as String

                        ))
                }

            } catch (e: JSONException) {
                Log.e(this.LogTag, "Problem parsing the product JSON results", e)

            }
            return gameList
        }


        private inline fun <reified T> returnValueOrDefault(json: JSONObject, key: String): Any? {
            when (T::class) {
                String::class -> {
                    return if (json.has(key)) {
                        json.getString(key)
                    } else {
                        ""
                    }
                }
                Int::class -> {
                    return if (json.has(key)) {
                        json.getInt(key)
                    }
                    else {
                        return -1
                    }
                }
                Double::class -> {
                    return if (json.has(key)) {
                        json.getDouble(key)
                    }
                    else {
                        return -1.0
                    }
                }
                Long::class -> {
                    return if (json.has(key)) {
                        json.getLong(key)
                    }
                    else {
                        return (-1).toLong()
                    }
                }
                JSONObject::class -> {
                    return if (json.has(key)) {
                        json.getJSONObject(key)
                    }
                    else {
                        return null
                    }
                }
                JSONArray::class -> {
                    return if (json.has(key)) {
                        json.getJSONArray(key)
                    }
                    else {
                        return null
                    }
                }
                else -> {
                    return null
                }
            }
        }


    }
}