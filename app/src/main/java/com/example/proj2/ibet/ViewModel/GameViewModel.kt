package com.example.proj2.ibet.ViewModel

import android.annotation.SuppressLint
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.os.AsyncTask
import android.util.Log
import com.example.proj2.ibet.model.GameModel
import com.example.proj2.ibet.util.QueryUtils


class GameViewModel(application: Application): AndroidViewModel(application) {

    private  var gameList: MutableLiveData<ArrayList<GameModel>> = MutableLiveData()

    fun getGames(): MutableLiveData<ArrayList<GameModel>> {

        var game = ""
        loadGame(game)
        return gameList
    }



    private fun loadGame(query: String) {
        GameAsyncTask().execute(query)
    }



    @SuppressLint("StaticFieldLeak")
    inner class GameAsyncTask: AsyncTask<String, Unit, ArrayList<GameModel>>() {
        override fun doInBackground(vararg params: String?): ArrayList<GameModel>? {
            return QueryUtils.fetchGameData(params[0]!!)
        }

        override fun onPostExecute(result: ArrayList<GameModel>?) {
            if (result == null) {
                Log.e("RESULTS", "No Results Found")
            }
            else {
                gameList.value = result
            }
        }
    }

}