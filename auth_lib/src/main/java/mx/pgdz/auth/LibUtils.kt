package mx.pgdz.auth

import android.content.Context
import android.util.Log
import android.widget.Toast

object LibUtils {

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun setKeys(key01: String, key02: String) {
        Log.d("LibUtils", "key01: $key01, key02: $key02")
    }

}