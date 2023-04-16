package ru.startandroid.develop.asynctaskresult

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class MainActivity : AppCompatActivity() {

    val LOG_TAG = "myLogs"

    internal var mt: MyTask? = null
    var tvInfo: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInfo = findViewById<View>(R.id.tvInfo) as TextView
    }

    fun onClick(v: View?) {
        when(v?.id) {
            R.id.btnStart -> {
                mt = MyTask()
                mt!!.execute()
            }
            R.id.btnGet -> showResult()
            else -> {}
        }
    }

    private fun showResult() {
        if (mt == null) return
        var result: Int = -1
        try {
            Log.d(LOG_TAG, "Try to get result")
            result = mt!!.get(1, TimeUnit.SECONDS)
            Log.d(LOG_TAG, "get returns $result")
            Toast.makeText(this, "get returns $result", Toast.LENGTH_LONG).show()
        } catch (e:InterruptedException) {
            e.printStackTrace()
        } catch (e:ExecutionException) {
            e.printStackTrace()
        } catch (e:TimeoutException) {
            Log.d(LOG_TAG, "get timeout, result = $result")
            e.printStackTrace()
        }
    }

    internal inner class MyTask :
        AsyncTask<Void?, Void?, Int>() {
        override fun onPreExecute() {
            super.onPreExecute()
            tvInfo!!.text = "Begin"
            Log.d(LOG_TAG, "Begin")
        }

        override fun doInBackground(vararg params: Void?): Int {
            try {
                Thread.sleep(5000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            return 100500
        }

        override fun onPostExecute(result: Int?) {
            super.onPostExecute(result)
            tvInfo!!.text = "End. Result = $result"
            Log.d(LOG_TAG, "End. Result = $result")
        }
    }
}