package com.lq.helloworld

import android.os.AsyncTask
import android.util.Log
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

private const val TAG = "MyAsyncTask"

@Suppress("DEPRECATION")
class MyAsyncTask : AsyncTask<String, Int, Boolean>() {

    private val executor: ExecutorService by lazy {
        Executors.newSingleThreadExecutor()
    }

    fun execute(param: String) {
        executeOnExecutor(executor, param)
    }

    override fun doInBackground(vararg params: String?): Boolean {
        for (index in 0 until 100) {
            Log.d(TAG, "doInBackground: ${params[0]} $index")
//            if (!isCancelled) {
            Thread.sleep(1000)
            publishProgress(index + 1)
//            }
        }
        return true
    }

    override fun onPostExecute(result: Boolean?) {
        super.onPostExecute(result)
        Log.d(TAG, "onPostExecute: ")
    }

    override fun onPreExecute() {
        super.onPreExecute()
        Log.d(TAG, "onPreExecute: ")
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        Log.d(TAG, "onProgressUpdate: ${values[0]}")
    }

}